# Problem 1 - Correctness Proof

## Structural Induction for `mergeAndCount`

### Property

Let `L` and `R` be two sorted lists.

We want to prove that:

`mergeAndCount(L, R) = (M, k)`

where:

- `M` contains the elements of `L` and `R` in sorted order.
- `k` is the number of inversions between the elements of `L` and `R`.

---

## Base Case

### Case 1: `L = Nil`

We want to prove:

`mergeAndCount(Nil, R) = (R, 0)`

1. `mergeAndCount(Nil, R)`  
   Goal.

2. Since `L` has no elements, only the elements of `R` remain.  
   Definition of `mergeAndCount`.

3. No new inversions are generated because there are no elements in `L`.

Therefore:

`mergeAndCount(Nil, R) = (R, 0)`

Then:

`(R, 0) = (R, 0)`

The base case holds.

### Case 2: `R = Nil`

Similarly:

`mergeAndCount(L, Nil) = (L, 0)`

because only the elements of `L` remain and no new inversions are
generated.

---

## Recursive Step

Let:

`L = hL :: tL`

`R = hR :: tR`

### I.H.

We assume that `mergeAndCount` works correctly for smaller lists.

That is:

`mergeAndCount(tL, R)`

and

`mergeAndCount(L, tR)`

correctly return the sorted list and the corresponding number of
inversions.

---

### Case 1: `hL <= hR`

1. `mergeAndCount(hL :: tL, hR :: tR)`  
   Goal.

2. Since `hL <= hR`, `hL` is selected.  
   Definition of `mergeAndCount`.

3. Since `R` is sorted, `hL` is also not greater than the elements that
   appear after `hR`.

4. Therefore, no new inversion is added.

5. The algorithm continues with:

   `mergeAndCount(tL, hR :: tR)`

   Recursive definition.

6. By the I.H., this call correctly sorts the remaining elements and
   correctly counts their inversions.

Therefore, the complete result is sorted and the number of inversions is
correct.

---

### Case 2: `hL > hR`

1. `mergeAndCount(hL :: tL, hR :: tR)`  
   Goal.

2. Since `hL > hR`, `hR` is selected.  
   Definition of `mergeAndCount`.

3. Since `L` is sorted, all the elements remaining in `L` are greater
   than or equal to `hL`.

4. Therefore, `hR` is smaller than all the remaining elements of `L`.

5. This generates:

   `|L|`

   new inversions.

6. The algorithm continues with:

   `mergeAndCount(hL :: tL, tR)`

   Recursive definition.

7. By the I.H., this call correctly sorts the remaining elements and
   correctly counts their inversions.

Then:

`inversions = |L| + remaining inversions`

Therefore, the complete result is sorted and the number of inversions is
correct.

---

## Conclusion

Since the property holds in the base cases and is preserved in the
recursive cases, by structural induction we conclude that `mergeAndCount`
correctly merges two sorted lists and correctly counts the inversions
between them.