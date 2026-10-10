# Problem 2 - QuickSort

## Test Design

The tests verify the behavior of the QuickSort algorithm under different input scenarios. They also verify that the three-way partition separates elements into smaller, equal, and greater groups according to the pivot.

| Scenario | Input | Expected result |
| --- | --- | --- |
| Single element | `List(5)` | `List(5)` |
| Empty list | `List()` | `List()` |
| Repeated values | `List(2, 3, 9, 2, 2)` | `List(2, 2, 2, 3, 9)` |
| Many repeated values | `List(4, 4, 1, 9, 4, 1, 4, 4)` | `List(1, 1, 4, 4, 4, 4, 4, 9)` |
| Negative values | `List(-2, 5, -2, 0, 5, -4)` | `List(-4, -2, -2, 0, 5, 5)` |
| Mixed values | `List(3, 1, 2)` | `List(1, 2, 3)` |
| Already sorted list | `List(1, 2, 3, 4)` | `List(1, 2, 3, 4)` |
| Descending list | `List(4, 3, 2, 1)` | `List(1, 2, 3, 4)` |

## Test Design of `partition`

The partition operation is also tested independently to verify that it returns three lists containing the elements smaller than, equal to, and greater than the pivot.

| Scenario | Input | Pivot | Expected result |
| --- | --- | ---: | --- |
| Three-way partition | `List(4, 1, 9, 4, 1, 4)` | 4 | `(List(1, 1), List(4, 4, 4), List(9))` |
| Negative values | `List(-2, 5, -2, 0, 5, -4)` | 0 | `(List(-2, -2, -4), List(0), List(5, 5))` |

The tests are implemented using MUnit and compare the actual results with the expected results using `assertEquals`.
