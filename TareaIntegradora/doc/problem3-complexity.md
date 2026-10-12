# Problem 3 - Complexity Analysis

## Cost of the Auxiliary Functions

Let `n` be the number of points received by each function.

| Function | Cost | Justification |
| --- | --- | --- |
| `toPointsRC`, `listLengthRC`, `reverseListRC` | `O(n)` | One step per element |
| `splitListRC` | `O(n)` | Traverses `n/2` elements and then reverses the accumulator |
| `mergeByXRC`, `mergeByYRC` | `O(n)` | Each step consumes one element; at the end, the list is reversed once |
| `buildStripRC` | `O(n)` | One step per point |
| `scanStripRC` | `O(n)` | Each point checks at most 8 neighbors (see below) |
| `distanceSquared`, `minLong`, `roundTo4` | `O(1)` | Arithmetic operations |

---

## Why Does Each Point Check at Most 8 Neighbors?

This is the key part that makes the combine step linear.

Let `p` be a point in the strip and let `d = min(d1, d2) > 0`.
`scanNeighborsRC` continues only while the next point `q` satisfies
`(yq - yp)² < d`, that is, `0 ≤ yq - yp < √d`.
Let `δ = √d`.

All the points visited before stopping are inside the rectangle

`[midX - δ, midX + δ] × [yp, yp + δ)`

with width `2δ` and height `δ`.

1. We divide the left half of the rectangle into 4 squares of side
   `δ/2`, and we do the same with the right half.
2. Two points from `left` inside the same square would be at a distance
   `≤ δ/√2 < δ`, which would contradict the fact that `d1 ≥ d` is the
   minimum distance inside `left`. The same reasoning applies to `right`.
3. Therefore, the rectangle contains at most 4 points from `left` and
   4 points from `right`: **at most 8 points, including `p`**.
4. Therefore, `p` is compared with at most 7 points, plus one additional
   comparison that triggers the stopping condition:
   **at most 8 comparisons per point**.

If `d = 0`, the stopping condition occurs on the first comparison.
If `d = ∞` (only when `n = 2`), the strip contains 2 points.
In every case, the work performed per point is constant, so
`scanStripRC` has cost `O(n)`.

> The number 7 does not appear in the code: the stopping condition
> `dy² ≥ best` is sufficient, and this argument guarantees that it is
> reached quickly.

---

## Recurrence Equation of `closestRec`

In each call with `n ≥ 2`:

- there are two recursive calls, on `⌊n/2⌋` and `⌈n/2⌉` points;
- the work outside the recursive calls consists of
  `splitListRC` + `mergeByYRC` + `buildStripRC` + `reverseListRC` +
  `scanStripRC`, all of which are `O(n)`. In total, this work is at most
  `cn` for some constant `c`.

Therefore:

`T(n) = T(⌊n/2⌋) + T(⌈n/2⌉) + cn`

Base case:

`T(1) = c0`

To solve the recurrence, we assume `n = 2^k`, so the recurrence becomes:

`T(n) = 2T(n/2) + cn`

> **Why is it not `2T(n/2) + O(n log n)`?**
> If each recursive call sorted its strip by `y` from the beginning,
> the combine step would cost `O(n log n)` and the total complexity would
> become `O(n log² n)`. To avoid this, `closestRec` also returns its
> points sorted by `y`, so the combine step only performs a linear
> `merge` of both halves, just like merge sort.

---

## Solving the Recurrence

### Step 1

We start with:

`T(n) = 2T(n/2) + cn`

We apply the recurrence to `T(n/2)`:

`T(n/2) = 2T(n/4) + cn/2`

---

### Step 2

We substitute:

`T(n) = 2[2T(n/4) + cn/2] + cn`

`T(n) = 4T(n/4) + cn + cn`

`T(n) = 4T(n/4) + 2cn`

---

### Step 3

We apply the recurrence again:

`T(n/4) = 2T(n/8) + cn/4`

Then:

`T(n) = 4[2T(n/8) + cn/4] + 2cn`

`T(n) = 8T(n/8) + 3cn`

---

### Step 4

After `i` expansions, the following pattern can be observed:

`T(n) = 2^i T(n/2^i) + icn`

---

### Step 5

The recursion ends at the base case:

`n/2^i = 1`

Then:

`2^i = n`

Therefore:

`i = log2(n)`

---

### Step 6

We substitute `i = log2(n)`:

`T(n) = n T(1) + cn log2(n)`

`T(n) = c0 n + cn log2(n)`

---

### Step 7

The dominant term is `n log n`, so:

`T(n) = Θ(n log n)`

It is `Θ` and not only `O` because each level of the recursion executes
`mergeByYRC` over all the points at that level, which costs at least `n`
per level for `log2(n)` levels.

For values of `n` that are not powers of 2, `T` is increasing in `n`, so:

`T(n) ≤ T(2^⌈log2 n⌉) ≤ T(2n) = Θ(n log n)`

---

## Verification Using the Master Theorem

`T(n) = 2T(n/2) + cn`

Comparing it with:

`T(n) = aT(n/b) + cn^d`

we have:

`a = 2`

`b = 2`

`d = 1`

`b^d = 2^1 = 2`

Since `a = b^d`, by the second case of the Master Theorem:

`T(n) = Θ(n^d log n) = Θ(n log n)`

This result matches the one obtained through successive substitutions.

---

## Total Time Complexity of `closestDistance`

| Stage | Cost |
| --- | --- |
| `toPointsRC` (validate and convert) | `Θ(n)` |
| `listLengthRC` | `Θ(n)` |
| `sortByX` (merge sort by `x`, same recurrence `2T(n/2) + cn`) | `Θ(n log n)` |
| `closestRec` | `Θ(n log n)` |
| `sqrt` and `roundTo4` | `Θ(1)` |

**Total: `Θ(n log n)`**, which is the complexity required by the
assignment.

---

## Space Complexity

- **Auxiliary memory: `O(n)`.**
  At any moment, only the lists belonging to the calls on the current
  recursion path are alive, and their total size is

  `n + n/2 + n/4 + … ≤ 2n`

  The lists belonging to calls that have already finished can be released.
  A total of `Θ(n log n)` list cells are created throughout the execution,
  but they are not all alive at the same time.

- **Call stack: `O(log n)`.**
  `closestRec` and `sortByX` are not tail-recursive, but their depth is
  `⌈log2 n⌉` (about 20 calls for `n = 10⁶`).

  The 10 functions annotated with `@tailrec` are compiled as loops and use
  `O(1)` stack space.

For this reason, using `@tailrec` in the merge functions is necessary and
not only a stylistic choice: a recursive merge that is not tail-recursive
would require `n` stack frames and could overflow the stack for `n = 10⁶`.

With the current implementation, `10⁶` points were processed successfully
using the default stack.