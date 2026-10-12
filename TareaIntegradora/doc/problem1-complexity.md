# Problem 1 - Complexity Analysis

## Complexity of `mergeAndCount`

The `mergeAndCount` function traverses the elements of both lists
to merge them and count the inversions.

If there are `n` elements between the two lists:

`M(n) = O(n)`

---

## Complexity of `mergeSortAndCount`

The function divides the list into two halves, makes a recursive call
on each half, and then uses `mergeAndCount`.

Therefore:

`T(n) = 2T(n/2) + n`

Base case:

`T(1) = 1`

---

## Solving the Recurrence

### Step 1

We start with:

`T(n) = 2T(n/2) + n`

We apply the recurrence to `T(n/2)`:

`T(n/2) = 2T(n/4) + n/2`

---

### Step 2

We substitute:

`T(n) = 2[2T(n/4) + n/2] + n`

`T(n) = 4T(n/4) + n + n`

`T(n) = 4T(n/4) + 2n`

---

### Step 3

We apply the recurrence again:

`T(n/4) = 2T(n/8) + n/4`

Then:

`T(n) = 4[2T(n/8) + n/4] + 2n`

`T(n) = 8T(n/8) + n + 2n`

`T(n) = 8T(n/8) + 3n`

---

### Step 4

After `i` expansions, the following pattern can be observed:

`T(n) = 2^i T(n/2^i) + in`

---

### Step 5

The recursion ends when the base case is reached:

`n/2^i = 1`

Then:

`2^i = n`

Therefore:

`i = log2(n)`

---

### Step 6

We substitute `i = log2(n)`:

`T(n) = 2^(log2(n)) T(1) + n log2(n)`

Since:

`2^(log2(n)) = n`

and:

`T(1) = 1`

we obtain:

`T(n) = n + n log2(n)`

---

### Step 7

The dominant term is:

`n log n`

Therefore:

`T(n) = O(n log n)`

It can also be expressed more precisely as:

`T(n) = Θ(n log n)`

---

## Verification Using the Master Theorem

The recurrence is:

`T(n) = 2T(n/2) + n`

Comparing it with:

`T(n) = aT(n/b) + cn^d`

we have:

`a = 2`

`b = 2`

`d = 1`

We calculate:

`b^d = 2^1 = 2`

Then:

`a = b^d`

By the second case of the Master Theorem:

`T(n) = Θ(n^d log n)`

Therefore:

`T(n) = Θ(n log n)`

This result matches the one obtained through successive substitutions.

---

## Space Complexity

During the algorithm, auxiliary lists are created to divide and merge
the elements.

Therefore, the auxiliary space complexity is:

`O(n)`

The recursion depth of `mergeSortAndCount` is:

`O(log n)`