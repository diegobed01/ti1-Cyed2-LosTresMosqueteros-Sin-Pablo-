# Problem 3 - Closest Points

## Test Design

The tests verify the divide and conquer closest pair algorithm under
different input scenarios. Expected values are rounded with `HALF_UP`
to four decimal digits, as required by the assignment.

### Main function: `closestDistance`

| # | Scenario | Input | Expected | What it validates |
| --- | --- | --- | ---: | --- |
| 1 | Assignment example 1 | `(0,0), (3,4)` | 5.0 | Minimum case `n = 2` |
| 2 | Assignment example 2 | `(0,0), (3,4), (1,1)` | 1.4142 | Odd `n`: one half has a single point |
| 3 | Duplicated points | `(1,1), (5,5), (1,1)` | 0.0 | Distance zero and early cut of the scan |
| 4 | Negative coordinates | `(-5,-5), (-4,-4), (10,10)` | 1.4142 | Splitting by `x` with negative values |
| 5 | Same `x` for all points | `(0,0), (0,5), (0,2), (0,9)` | 2.0 | The dividing line cuts a vertical group |
| 6 | Pair crosses the division | `(-10,0), (-1,0), (1,0), (10,0)` | 2.0 | `d1 = d2 = 9`; only the strip finds 2 |
| 7 | Grid | `(0,0), (0,1), (1,0), (1,1)` | 1.0 | Several pairs tie at the minimum |
| 8 | Rounding | `(0,0), (1,2)` | 2.2361 | `√5 = 2.23606…` is rounded half up |
| 9 | `Int` overflow | `(0,0), (50000,50000)` | 70710.6781 | `dx² = 2.5·10⁹` does not fit in `Int` |
| 10 | Extreme coordinates | `(-10⁹,-10⁹), (10⁹,10⁹)` | 2828427124.7462 | Documented limit `|x|, |y| ≤ 10⁹` |
| 11 | Fewer than two points | `List(List(3,4))` | `IllegalArgumentException` | Precondition `n ≥ 2` |
| 12 | Malformed point | `List(List(1,2), List(3))` | `IllegalArgumentException` | Every point has two coordinates |
| 13 | Brute force comparison | 200 pseudo-random points, range 2000 | Same as brute force | Correctness on non-trivial inputs |
| 14 | Brute force, many duplicates | 1000 pseudo-random points, range 60 | Same as brute force | Many repeated coordinates |
| 15 | Brute force, spread points | 1000 pseudo-random points, range 2·10⁶ | Same as brute force | Large and sparse coordinates |

The brute force algorithm exists **only in the test file**, as an oracle.
The pseudo-random points are produced with a recursive linear congruential
generator and a fixed seed, so the tests are deterministic and also respect
the implementation restrictions (no `map`, no `scala.util.Random`).

### Auxiliary functions

| Function | Input | Expected | What it validates |
| --- | --- | --- | --- |
| `sortByX` | `(3,1), (1,5), (1,2), (2,0)` | `(1,2), (1,5), (2,0), (3,1)` | Sorting by `x`, ties by `y` |
| `mergeByYRC` | `[(5,1),(0,4)]` and `[(2,2),(9,7)]` | `(5,1), (2,2), (0,4), (9,7)` | Linear merge by `y` |
| `buildStripRC` | `(-3,0),(1,1),(0,2),(2,3)`, `midX = 0`, `d² = 4` | `(0,2), (1,1)` | Only points with `(x - midX)² < d²` (reversed) |
| `closestRec` | `(0,9), (1,3), (2,6)` | `(10, [(1,3),(2,6),(0,9)])` | Returns `d²` and the points sorted by `y` |
| `closestRec` (base case) | `(4,4)` | `(Long.MaxValue, [(4,4)])` | A single point has no pairs |
