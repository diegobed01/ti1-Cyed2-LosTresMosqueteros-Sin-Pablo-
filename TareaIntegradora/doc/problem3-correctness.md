# Problem 3 - Correctness Proof

## Notation

- `dist²(p, q) = (xp - xq)² + (yp - yq)²` (squared distance).
- For a list of points `L`, `δ(L)` is the minimum squared distance between
  two **different positions** in `L`. If `L` contains only one point,
  `δ(L) = ∞` (in the code, `Infinity = Long.MaxValue`).
- `|L|` is the number of points in `L`.

We work with squared distances because `√` is increasing: the pair that
minimizes `dist²` also minimizes the actual distance.

Therefore, it is enough to prove that the algorithm computes `δ(L)`, and
at the end `closestDistance` returns:

`roundTo4(√δ(L))`

The main proof is about `closestRec`. Before proving it, we establish three
lemmas about the auxiliary functions used in the combine step.

---

## Lemma 1: `mergeByYRC` Merges Correctly

### Property

If `A` and `B` are sorted by `y`, and `accumulator` contains, in reverse
order, a list sorted by `y` whose elements are all `≤` (by `y`) the
elements of `A` and `B`, then:

`mergeByYRC(A, B, accumulator)`

returns:

`reverse(accumulator) ++ merge(A, B)`

That is, a list sorted by `y` containing exactly the elements of
`accumulator`, `A`, and `B`.

### Base Case: `A = Nil`, `B = Nil`

The function returns:

`reverseListRC(accumulator, Nil)`

which is:

`reverse(accumulator)`

Therefore, the property holds.

### I.H.

Assume that the property holds for every pair of lists such that:

`|A| + |B| = k`

### Inductive Step: `|A| + |B| = k + 1`

Let `h` be the selected element:

- the head of `A` if `B = Nil`;
- the head of `B` if `A = Nil`;
- or the head with the smallest `y` if both lists are non-empty.

1. Since `A` and `B` are sorted, `h` is the element with the smallest
   `y` among all the remaining elements of `A` and `B`.

2. By the condition on `accumulator`, `h` is greater than or equal to
   every element already stored in `accumulator` with respect to `y`.
   Therefore, `h :: accumulator` still satisfies the accumulator
   condition.

3. The recursive call has `k` elements remaining between both lists.

4. By the I.H., the result is sorted by `y` and contains all the elements.

Calling the function with:

`accumulator = Nil`

produces `merge(A, B)`. ∎

> The same proof, replacing `y` with `(x, y)` in lexicographic order,
> proves the correctness of `mergeByXRC`.
> This is the same argument used in `mergeAndCount` from Problem 1.

---

## Lemma 2: `scanNeighborsRC` and `scanStripRC`

### 2a. `scanNeighborsRC`

**Property.**

If `Q` is sorted by `y` and every `q ∈ Q` satisfies:

`yq ≥ yp`

then:

`scanNeighborsRC(p, Q, b) = min(b, min{ dist²(p, q) : q ∈ Q })`

### Base Case: `Q = Nil`

The function returns `b`, and:

`min(b, min ∅) = min(b, ∞) = b`

Therefore, the property holds.

### I.H.

Assume that the property holds for the tail `t` of `Q`.

### Inductive Step: `Q = q :: t`

Let:

`dy = yq - yp`

#### Case 1: `dy² ≥ b`

Since `Q` is sorted by `y`, every `q' ∈ Q` satisfies:

`yq' - yp ≥ dy ≥ 0`

Therefore:

`dist²(p, q') ≥ (yq' - yp)² ≥ dy² ≥ b`

No point in `Q` can improve `b`, so returning `b` is correct.

#### Case 2: `dy² < b`

The function makes a recursive call over `t` with:

`b' = min(b, dist²(p, q))`

By the I.H., the result is:

`min(b', min_{q' ∈ t} dist²(p, q'))`

which is equal to:

`min(b, min_{q' ∈ Q} dist²(p, q'))`

Therefore, `scanNeighborsRC` is correct.

---

### 2b. `scanStripRC`

**Property.**

If `S` is sorted by `y`, then:

`scanStripRC(S, b) = min(b, δ(S))`

### Base Case: `S = Nil`

The function returns:

`b = min(b, ∞)`

Therefore, the property holds.

### I.H.

Assume that the property holds for the tail `t` of `S`.

### Inductive Step: `S = p :: t`

Every pair in `S` is either:

- a pair `(p, q)` where `q ∈ t`, or
- a pair completely contained in `t`.

By Lemma 2a:

`b' = scanNeighborsRC(p, t, b)`

and therefore:

`b' = min(b, min_{q ∈ t} dist²(p, q))`

By the I.H.:

`scanStripRC(t, b') = min(b', δ(t))`

Therefore:

`scanStripRC(t, b') = min(b, δ(S))`

Thus, `scanStripRC` is correct. ∎

---

## Lemma 3: The Strip Contains All Useful Cross Pairs

Let `left` and `right` be the two halves, let `midX` be the `x`
coordinate of the first point in `right`, and let:

`d = min(δ(left), δ(right))`

### Property

If:

`p ∈ left`

`q ∈ right`

and:

`dist²(p, q) < d`

then both `p` and `q` belong to the strip:

`{ r : (xr - midX)² < d }`

### Proof

1. The list is sorted by `x` and is divided by **position**, so:

   `xp ≤ midX ≤ xq`

2. Therefore:

   `0 ≤ midX - xp ≤ xq - xp`

   and:

   `0 ≤ xq - midX ≤ xq - xp`

3. Then:

   `(xp - midX)² ≤ (xq - xp)² ≤ dist²(p, q) < d`

   and the same reasoning applies to `q`.

4. Therefore, `buildStripRC` keeps both `p` and `q`.

In addition, `buildStripRC` traverses the list sorted by `y` and preserves
the relative order of the points in reverse order, which is then reversed
once.

Therefore, the strip remains **sorted by `y`**, as required by Lemma 2. ∎

---

## Theorem: `closestRec` Is Correct

We prove the theorem using structural induction.

### Property `P(L)`

For every non-empty list `L` sorted by `x`, with:

`n = |L|`

we have:

`closestRec(L, n) = (δ(L), Y)`

where `Y` contains exactly the points of `L` sorted by `y`.

The induction is structural over the list: every recursive call is made
on the sublists `left` and `right`, which are strictly smaller than `L`
when `|L| ≥ 2`.

---

### Base Case: `L = p :: Nil` (`n = 1`)

1. `closestRec(p :: Nil, 1)`  
   Goal.

2. By the first case of the `match`, the function returns:

   `(Infinity, p :: Nil)`

3. A single point has no pair of different positions, so:

   `δ(L) = ∞ = Infinity`

4. A list containing one element is already sorted by `y`.

Therefore:

`P(p :: Nil)`

holds.

---

### I.H.

For a list `L` with `n ≥ 2`, assume that the property holds for the
sublists `left` and `right`.

`left` contains the first `⌊n/2⌋` points and `right` contains the remaining
`⌈n/2⌉` points.

Therefore:

`closestRec(left, ⌊n/2⌋) = (δ(left), leftByY)`

and:

`closestRec(right, ⌈n/2⌉) = (δ(right), rightByY)`

Both lists are non-empty because `n ≥ 2`, and both remain sorted by `x`.

---

### Inductive Step: `n ≥ 2`

1. `closestRec(L, n)`  
   Goal.

2. `splitListRC(L, ⌊n/2⌋, Nil)` produces `left` and `right`.  
   Definition of `splitListRC`.

3. By the I.H.:

   `d1 = δ(left)`

   and:

   `d2 = δ(right)`

   Also, `leftByY` and `rightByY` are sorted by `y`.

4. Let:

   `d = min(d1, d2)`

5. By Lemma 1:

   `allByY = mergeByYRC(leftByY, rightByY, Nil)`

   contains all the points of `L` sorted by `y`.

   **This proves the second component of the result.**

6. Every pair of different positions in `L` belongs to one of three
   categories:

    - both points are in `left`: their distance is `≥ d1 ≥ d`;
    - both points are in `right`: their distance is `≥ d2 ≥ d`;
    - one point is in each half: a cross pair.

   Let `c` be the minimum distance among the cross pairs.

   Therefore:

   `δ(L) = min(d, c)`

7. Let `S` be the strip.

   By Lemma 2:

   `scanStripRC(S, d) = min(d, δ(S))`

8. **The result is not smaller than `δ(L)`.**

   Since `S` is a sublist of `L`:

   `δ(S) ≥ δ(L)`

   and also:

   `d ≥ δ(L)`

   Therefore:

   `min(d, δ(S)) ≥ δ(L)`

9. **The result is not greater than `δ(L)`.**

    - If `c ≥ d`, then:

      `δ(L) = d`

      and:

      `min(d, δ(S)) ≤ d = δ(L)`

    - If `c < d`, the cross pair that reaches `c` belongs to `S` by
      Lemma 3.

      Therefore:

      `δ(S) ≤ c`

      and:

      `min(d, δ(S)) ≤ c = δ(L)`

10. From steps 8 and 9, the first component of the result is exactly:

    `δ(L)`

Therefore:

`P(L)`

holds.

---

## Conclusion

Since the property holds in the base case and is preserved in the
inductive step, by structural induction `closestRec` returns the minimum
squared distance between different points and the list sorted by `y`.

Since `sortByX` sorts the input by `x` (Lemma 1 applied to `mergeByXRC`)
and `√` is increasing, `closestDistance` returns:

`roundTo4(√δ(L))`

which is the minimum Euclidean distance rounded to four decimal digits. ∎

---

## Observations

- **Points with the same `x` coordinate on both sides.**
  Lemma 3 only uses:

  `xp ≤ midX ≤ xq`

  which holds because the list is divided by position.

  Therefore, the algorithm remains correct even when many points have the
  same `x` coordinate (test 5).

- **Duplicated points.**
  If `d = 0`, the condition:

  `dy² ≥ 0`

  immediately stops the scan and the result is `0` (test 3).

- **Strict inequality in the strip.**
  Using:

  `(x - midX)² < d`

  instead of:

  `(x - midX)² ≤ d`

  does not lose any useful pair, because a cross pair with distance
  `≥ d` cannot improve the current value of `d`.