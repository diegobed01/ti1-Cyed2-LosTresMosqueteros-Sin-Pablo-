# Problem 2 - Complexity Analysis

## Complexity of `partition`

The `partition` function traverses the input list once. For each element, it performs a constant number of comparisons and adds the element to one of the three groups: `less`, `equal`, or `greater`.

At the end, the function reverses the three lists. Since every element belongs to exactly one group, the total cost of these operations is linear.

The time complexity is:

- **Big O:** O(n)
- **Big Omega:** Ω(n)
- **Big Theta:** Θ(n)

The auxiliary space complexity is Θ(n), because the function creates three lists containing all the elements of the input list.

## Complexity of `quickSort`

The `quickSort` function partitions the list and recursively sorts the `less` and `greater` groups. Its complexity depends on the sizes of these groups.

Let:

- `n` be the number of elements in the input list.
- `k` be the number of elements smaller than the pivot.
- `m` be the number of elements greater than the pivot.

The recurrence is:

**T(n) = T(k) + T(m) + cn**

Here, `cn` represents the linear cost of partitioning and combining the lists.

### Best Case

The best case occurs when the recursive groups are approximately balanced.

The recurrence becomes:

**T(n) = 2T(n/2) + cn**

Using the Master Theorem:

- a = 2
- b = 2
- f(n) = cn

We calculate:

**n^(log₂ 2) = n**

Since f(n) = Θ(n), the Master Theorem gives:

**T(n) = Θ(n log n)**

Therefore, the best-case time complexity is **Θ(n log n)**.

### Worst Case

The worst case occurs when the pivot produces highly unbalanced groups. For example, if the elements are distinct and the first element is always the smallest or largest, one group contains n − 1 elements and the other is empty.

The recurrence becomes:

**T(n) = T(n − 1) + cn**

Expanding the recurrence:

**T(n) = T(n − 2) + c(n − 1) + cn**

Continuing the expansion produces a sum of linear terms:

**T(n) = c(2 + 3 + ... + n)**

Since the sum grows quadratically:

**T(n) = Θ(n²)**

Therefore, the worst-case time complexity is **Θ(n²)**.

### Special Case: Repeated Elements

Three-way partitioning improves performance when the input contains repeated values.

If all elements are equal, the `less` and `greater` groups are empty. The algorithm performs one partition and makes no further recursive calls.

In this case:

**T(n) = cn = Θ(n)**

Therefore, the algorithm runs in linear time when all elements are equal.

## Space Complexity

The `partition` function creates groups containing the input elements, requiring Θ(n) auxiliary space.

The `quickSort` function also creates intermediate lists and uses recursive calls.

- **Best-case recursion depth:** Θ(log n)
- **Worst-case recursion depth:** Θ(n)
- **Auxiliary space for partitioned lists:** Θ(n)
- **Worst-case recursion stack:** Θ(n)

The implementation uses immutable lists, so intermediate lists may require additional memory during execution.

## Conclusion

The three-way partition takes Θ(n) time. The overall running time of `quickSort` depends on the distribution of the pivot values and the resulting recursive groups.

- **Best case:** Θ(n log n)
- **Worst case:** Θ(n²)
- **All elements equal:** Θ(n)

Three-way partitioning reduces unnecessary recursive calls on values equal to the pivot, making it particularly useful for lists with many duplicates.
