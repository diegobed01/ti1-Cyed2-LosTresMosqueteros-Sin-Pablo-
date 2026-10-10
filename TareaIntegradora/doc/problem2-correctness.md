# Problem 2 - Correctness Proof

## Correctness of `partition`

### Property

Let `L` be a list of integers and `pivot` be an integer.

We want to prove that:

`partition(L, pivot) = (less, equal, greater)`

such that:

- `less` contains all elements of `L` smaller than `pivot`.
- `equal` contains all elements of `L` equal to `pivot`.
- `greater` contains all elements of `L` greater than `pivot`.

The three lists must preserve all elements of the original list without losing or duplicating any element.

### Base Case (`L = Nil`)

When the input list is empty, the function returns:

`partition(Nil, pivot) = (Nil, Nil, Nil)`

Since the original list contains no elements, all three resulting lists must be empty. Therefore, the property holds for the base case.

### Inductive Step (`L = head :: tail`)

**Inductive Hypothesis (I.H.):** Assume that `partition(tail, pivot)` correctly classifies all elements of `tail` into the three groups.

We must prove that `partition(head :: tail, pivot)` also classifies all elements correctly.

**Case 1: `head < pivot`**

By the definition of `partition`, `head` is added to `less`. By the I.H., all elements of `tail` are already classified correctly. Therefore, all elements of `head :: tail` are classified correctly.

**Case 2: `head == pivot`**

By the definition of `partition`, `head` is added to `equal`. By the I.H., all elements of `tail` are already classified correctly. Therefore, all elements of `head :: tail` are classified correctly.

**Case 3: `head > pivot`**

By the definition of `partition`, `head` is added to `greater`. By the I.H., all elements of `tail` are already classified correctly. Therefore, all elements of `head :: tail` are classified correctly.

In each case, `head` is added to exactly one group, and the recursive call preserves the classification of the remaining elements.

### Conclusion

Since the property holds for `Nil` and is preserved when a new element is added to the list, by structural induction, `partition` correctly classifies all elements into the three groups.

---

## Correctness of `quickSort`

### Property

For every list `L` of integers, `quickSort(L)` returns a list that is sorted in non-decreasing order and contains exactly the same elements as `L`.

### Base Case (`L = Nil`)

The function returns:

`quickSort(Nil) = Nil`

The empty list is sorted and contains exactly the same elements as the input list. Therefore, the property holds for the base case.

### Inductive Step (`L = pivot :: tail`)

**Inductive Hypothesis (I.H.):** Assume that `quickSort` correctly sorts lists smaller than `L`.

We must prove that `quickSort(pivot :: tail)` also returns a sorted list containing exactly the original elements.

1. The function calls `partition(tail, pivot)` and obtains `less`, `equal`, and `greater`.
2. By the correctness of `partition`, every element in `less` is smaller than `pivot`, every element in `equal` is equal to `pivot`, and every element in `greater` is greater than `pivot`.
3. By the I.H., `quickSort(less)` and `quickSort(greater)` return sorted lists containing all their respective input elements.
4. The function constructs the result:

   `quickSort(less) ++ (pivot :: equal) ++ quickSort(greater)`

5. All elements in `quickSort(less)` are smaller than the pivot, the pivot and elements in `equal` have the same value, and all elements in `quickSort(greater)` are greater than the pivot. Therefore, the resulting list is sorted in non-decreasing order.
6. The partition preserves every element of `tail`, and the pivot is explicitly included in the result. Therefore, no original element is lost or duplicated.

Thus, `quickSort(pivot :: tail)` returns a sorted list containing exactly the same elements as the input list.

### Conclusion

Since the property holds for the empty list and is preserved by the recursive step, by structural induction, `quickSort` correctly sorts every finite list of integers.
