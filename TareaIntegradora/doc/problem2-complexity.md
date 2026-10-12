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

The `quickSort` function chooses a pivot randomly, partitions the remaining elements into the `less`, `equal`, and `greater` groups, and recursively sorts the `less` and `greater` groups.

Let:

- `n` be the number of elements in the input list.
- `k` be the number of elements smaller than the pivot.
- `m` be the number of elements greater than the pivot.

The recurrence for a particular pivot choice is:

**T(n) = T(k) + T(m) + cn**

Here, `cn` represents the linear cost of partitioning and combining the lists. The sizes of the recursive groups depend on the selected pivot.

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

### Expected Case

Since the pivot is chosen randomly, the expected running time for distinct elements is:

**E[T(n)] = Θ(n log n)**

Random pivot selection reduces the likelihood of repeatedly obtaining highly unbalanced partitions, although it does not eliminate the worst case.

### Worst Case

The worst case occurs when the selected pivots repeatedly produce highly unbalanced groups. One recursive group contains n − 1 elements and the other is empty.

The recurrence becomes:

**T(n) = T(n − 1) + cn**

Expanding the recurrence produces a sum of linear terms:

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

The three-way partition takes Θ(n) time. Randomized pivot selection gives an expected time complexity of Θ(n log n) for distinct elements, while the worst case remains Θ(n²). When all elements are equal, three-way partitioning allows the algorithm to run in Θ(n) time.
