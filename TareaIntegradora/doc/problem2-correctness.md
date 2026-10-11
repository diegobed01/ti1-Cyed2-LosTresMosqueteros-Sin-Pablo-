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

**Inductive Hypothesis (I.H.):** Assume that the partitioning process correctly classifies all elements of `tail` into the three groups.

We must prove that the partitioning process also classifies all elements of `head :: tail` correctly.

**Case 1: `head < pivot`**

The element `head` is added to `less`. By the I.H., all elements of `tail` are already classified correctly. Therefore, all elements of `head :: tail` are classified correctly.

**Case 2: `head == pivot`**

The element `head` is added to `equal`. By the I.H., all elements of `tail` are already classified correctly. Therefore, all elements of `head :: tail` are classified correctly.

**Case 3: `head > pivot`**

The element `head` is added to `greater`. By the I.H., all elements of `tail` are already classified correctly. Therefore, all elements of `head :: tail` are classified correctly.

In each case, `head` is added to exactly one group, and the recursive call processes the remaining elements. Reversing the groups at the end restores their original relative order without losing or duplicating elements.

### Conclusion

Since the property holds for `Nil` and is preserved when a new element is added to the list, by structural induction, the partitioning process correctly classifies all elements into the three groups.

---

## Correctness of `quickSort`

### Property

For every finite list `L` of integers, `quickSort(L)` returns a list that is sorted in non-decreasing order and contains exactly the same elements as `L`.

### Base Case (`L = Nil`)

The function returns:

`quickSort(Nil) = Nil`

The empty list is sorted and contains exactly the same elements as the input list. Therefore, the property holds for the base case.

### Inductive Step (`L` is non-empty)

**Inductive Hypothesis (I.H.):** Assume that `quickSort` correctly sorts every list containing fewer elements than `L`.

We must prove that `quickSort(L)` also returns a sorted list containing exactly the original elements.

1. The function randomly selects an element from `L` as the pivot.

2. The selected pivot is removed from the list, producing a list called `remaining`.

3. The function calls `partition(remaining, pivot)` and obtains `less`, `equal`, and `greater`.

4. By the correctness of the partitioning process, every element in `less` is smaller than `pivot`, every element in `equal` is equal to `pivot`, and every element in `greater` is greater than `pivot`.

5. Since `less` and `greater` contain fewer elements than `L`, the I.H. guarantees that `quickSort(less)` and `quickSort(greater)` return sorted lists containing all their respective input elements.

6. The function constructs the result:

   `quickSort(less) ++ (pivot :: equal) ++ quickSort(greater)`

7. All elements in `quickSort(less)` are smaller than the pivot. The pivot and all elements in `equal` have the same value. All elements in `quickSort(greater)` are greater than the pivot. Therefore, the resulting list is sorted in non-decreasing order.

8. The partitioning process preserves every element of `remaining`, and the selected pivot is included exactly once in the result. Therefore, no original element is lost or duplicated.

The choice of pivot is random, but any element selected from the list satisfies the same reasoning. Thus, the algorithm returns a sorted list containing exactly the original elements.

### Conclusion

Since the property holds for the empty list and is preserved by the recursive step, by structural induction, `quickSort` correctly sorts every finite list of integers.
