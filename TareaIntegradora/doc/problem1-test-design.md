# Problem 1 - Number of Inversions

## Test Design

The tests verify the behavior of the inversion counting algorithm under
different input scenarios.

| Scenario | Input | Expected inversions |
| --- | --- | ---: |
| Single element | `List(5)` | 0 |
| Sorted list | `List(1, 2, 3, 4)` | 0 |
| Descending list | `List(4, 3, 2, 1)` | 6 |
| Assignment example | `List(2, 3, 9, 2, 9)` | 2 |
| Repeated elements | `List(2, 2, 2)` | 0 |
| Negative values | `List(-1, -3, 2, 0)` | 2 |
| Mixed values | `List(3, 1, 2)` | 2 |

The modified merge operation is also tested independently to verify that
it returns a sorted list while correctly counting inversions between the
two input lists.