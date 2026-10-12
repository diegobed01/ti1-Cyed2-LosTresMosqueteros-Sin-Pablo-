# Experimental Analysis

## 1. Execution Environment

The performance tests were carried out on the same machine in order to
maintain a consistent execution environment.

- Processor: 12th Gen Intel(R) Core(TM) i5-1235U
- RAM: 16 GB
- Operating system: Microsoft Windows 11 Pro
- Architecture: 64-bit
- Operating system version: 10.0.26200

During the measurements, the same execution conditions were maintained
as much as possible, while avoiding other high-consumption applications
that could affect the recorded times.

---

## 2. Methodology

The metric used for the experiment was execution time in milliseconds.

For each algorithm and each input size, the following procedure was used:

1. Generate the input before starting the measurement.
2. Execute the algorithm 10 times using the same input.
3. Record the execution time of each run.
4. Discard the first execution time.
5. Calculate the average of the remaining 9 executions.

Input generation was not included in the measured time, so the result
corresponded only to the execution of the algorithm.

---

## 3. Input Sizes

The inputs were organized according to the categories defined in the
assignment:

- Toy: `n < 100`
- Small: `100 <= n < 10000`
- Medium: `10000 <= n < 100000`
- Large: `n >= 1000000`

The following sizes were used:

| Category | Sizes used |
| --- | --- |
| Toy | 50 |
| Small | 100, 500, 1000, 5000 |
| Medium | 10000, 25000, 50000 |
| Large | 1000000 |

---

## 4. Input Scenarios

For each algorithm, an input scenario related to the objective of the
problem was used.

### 4.1 Number of Inversions

Lists in descending order were used.

This type of input contains a large number of inversions and allows the
algorithm to be evaluated on a demanding case for inversion counting.

### 4.2 Improving QuickSort

Lists with repeated values were used.

This scenario is directly related to the objective of the problem,
because the three-way partition separates values into elements smaller
than, equal to, and greater than the pivot, improving the behavior of the
algorithm when many equal elements are present.

### 4.3 Closest Points

Deterministically generated points were used.

Each point was represented as:

`(x, 2x)`

For example:

`(0, 0), (1, 2), (2, 4), ...`

Deterministic generation made it possible to use exactly the same input
during the different repetitions for each size.

---

## 5. Results

For each size, 10 executions were performed. The first measurement was
discarded and the average was calculated using the remaining 9
executions.

### 5.1 Number of Inversions

| Input size n | Average time (ms) |
| ---: | ---: |
| 50 | 0.7469 |
| 100 | 0.4170 |
| 500 | 1.2127 |
| 1000 | 1.3437 |
| 5000 | 3.5983 |
| 10000 | 5.9238 |
| 25000 | 14.4079 |
| 50000 | 27.4339 |
| 1000000 | 726.2274 |

### 5.2 Improving QuickSort

| Input size n | Average time (ms) |
| ---: | ---: |
| 50 | 0.0239 |
| 100 | 0.0318 |
| 500 | 0.1124 |
| 1000 | 0.2104 |
| 5000 | 0.8718 |
| 10000 | 1.4744 |
| 25000 | 0.9473 |
| 50000 | 1.5147 |
| 1000000 | 16.3821 |

### 5.3 Closest Points

| Input size n | Average time (ms) |
| ---: | ---: |
| 50 | 0.8008 |
| 100 | 0.5888 |
| 500 | 1.9318 |
| 1000 | 1.8929 |
| 5000 | 6.2502 |
| 10000 | 8.8954 |
| 25000 | 11.4079 |
| 50000 | 24.0538 |
| 1000000 | 1048.9111 |

---

## 6. Experimental and Theoretical Complexity

To determine which function best represents the behavior of the
experimental data, three possible models were compared:

- Linear: `n`
- Linear-logarithmic: `n log n`
- Quadratic: `n²`

The comparison was performed using the coefficient of determination
`R²`. An `R²` value closer to 1 represents a better fit to the
experimental data.

### 6.1 Number of Inversions

The following values were obtained:

| Model | R² |
| --- | ---: |
| n | 0.999824 |
| n log n | 0.999992 |
| n² | 0.998794 |

The best experimental fit was `n log n`.

The resulting function was:

`T(n) = 3.6397 × 10^-5 (n log2(n)) + 0.7257`

with:

`R² = 0.999992`

The theoretical complexity obtained from the recurrence of the algorithm
is:

`Θ(n log n)`

Therefore, the experimental behavior is consistent with the growth
expected from the theoretical analysis.

![Number of Inversions](images/number-inversions.png)

---

### 6.2 Improving QuickSort

The following values were obtained:

| Model | R² |
| --- | ---: |
| n | 0.993322 |
| n log n | 0.992265 |
| n² | 0.987680 |

The best experimental fit was the linear model.

The resulting function was:

`T(n) = 1.5944 × 10^-5 n + 0.4626`

with:

`R² = 0.993322`

This behavior is directly related to the experimental scenario that was
used, in which all elements had the same value.

The three-way partition groups elements equal to the pivot into the
`equal` partition. In this scenario, almost all elements are processed
during a single partition and the `less` and `greater` lists remain
empty.

For this reason, this particular type of input shows behavior close to:

`O(n)`

This does not mean that QuickSort has linear complexity for every input.
In general, its behavior depends on the distribution of the data and the
partitions obtained. The scenario used allows the improvement provided by
the three-way partition to be specifically observed.

![QuickSort](images/quicksort.png)

---

### 6.3 Closest Points

The following values were obtained:

| Model | R² |
| --- | ---: |
| n | 0.999129 |
| n log n | 0.999647 |
| n² | 0.999631 |

The best experimental fit was `n log n`.

The resulting function was:

`T(n) = 5.2686 × 10^-5 (n log2(n)) - 1.8661`

with:

`R² = 0.999647`

The negative constant term belongs only to the mathematical fit and does
not represent a negative execution time.

The theoretical complexity of the algorithm is:

`O(n log n)`

The corresponding recurrence is:

`T(n) = 2T(n/2) + O(n)`

The algorithm divides the set of points into two parts, recursively
solves both halves, and keeps the combining step in linear time.

Therefore, the experimental fit is consistent with the expected
theoretical growth.

![Closest Points](images/closestpoints.png)

---

## 7. Comparison of the Algorithms

### 7.1 Comparison of Experimental Times

The following graph presents the average execution times obtained for the
three algorithms.

Logarithmic scales were used to improve visualization because of the
differences between the input sizes and the recorded execution times.

![Experimental comparison](images/comparation-algorhitms.png)

For `n = 1000000`, the following average execution times were obtained:

| Algorithm | Average time (ms) |
| --- | ---: |
| Number of Inversions | 726.2274 |
| Improving QuickSort | 16.3821 |
| Closest Points | 1048.9111 |

Within the scenarios used, QuickSort presented the lowest execution
times.

Number of Inversions and Closest Points presented higher execution times
and growth consistent with their `n log n` fits.

However, these values should not be interpreted as a direct comparison
of efficiency between equivalent problems, since each algorithm solves a
different problem and uses a different type of input.

---

### 7.2 Comparison of the Fitted Functions

In addition to comparing the experimental times, the functions that best
fit the data for each algorithm were also compared graphically.

The resulting functions were:

**Number of Inversions**

`T(n) = 3.6397 × 10^-5 (n log2(n)) + 0.7257`

**Improving QuickSort**

`T(n) = 1.5944 × 10^-5 n + 0.4626`

**Closest Points**

`T(n) = 5.2686 × 10^-5 (n log2(n)) - 1.8661`

The comparison is shown in the following graph:

![Comparison of fitted functions](images/fitted-function-comparation.png)

It can be observed that Number of Inversions and Closest Points have
growth functions of type `n log n`, while QuickSort, for the repeated
values scenario used in the experiment, shows approximately linear
experimental behavior.

This difference is related to QuickSort's three-way partition, which
groups elements equal to the pivot and reduces the recursive work when
many repeated values are present.

---

## 8. Analysis of Results

### 8.1 Pattern Between Input Size and Execution Time

In general, as the input size increased, the execution time of the three
algorithms also increased.

For Number of Inversions and Closest Points, the growth became more
evident for larger sizes and showed behavior consistent with `n log n`.

QuickSort presented considerably lower execution times because of the
repeated-values scenario used.

For some small and medium input sizes, variations were observed in which
a larger input obtained a lower average execution time than a smaller
input.

For example, for QuickSort:

- `n = 10000`: 1.4744 ms
- `n = 25000`: 0.9473 ms

These variations do not imply that the complexity of the algorithm
decreases. Instead, they are part of the normal behavior of experimental
measurements.

---

### 8.2 Observed Relationship

For Number of Inversions, the model with the highest `R²` was:

`n log n`

with:

`R² = 0.999992`

For Closest Points, it was also:

`n log n`

with:

`R² = 0.999647`

For QuickSort, under the repeated-values scenario, the best fit was:

`n`

with:

`R² = 0.993322`

Therefore, the obtained data show two main types of experimental growth:
linear and linear-logarithmic.

---

### 8.3 Comparison with Theoretical Complexity

Number of Inversions presented an `n log n` fit, matching its theoretical
complexity of `Θ(n log n)`.

Closest Points also presented an `n log n` fit, consistent with its
theoretical complexity of `O(n log n)`.

QuickSort presented a linear fit for the input used. This result does not
contradict its general analysis, because the experiment was specifically
performed using repeated values in order to observe the effect of the
three-way partition.

---

### 8.4 Unusual Behavior

The data do not show perfectly uniform growth.

This is especially noticeable for small input sizes, where the total
execution time is very low and any variation in the system can have a
considerable effect on the measurement.

Some individual execution times were also higher than the others for the
same input.

For this reason, several executions were performed and the average was
used instead of relying on a single measurement.

---

### 8.5 Possible Sources of Error

Some possible sources of variation in the results are:

- operating system processes running in the background;
- memory usage by other applications;
- optimizations performed by the Java Virtual Machine;
- JVM garbage collection;
- normal system variations during execution;
- measurement precision for algorithms with very short execution times.

To reduce the effect of these variables, the same machine was used
throughout the experiment, 10 executions were performed for each input,
the first measurement was discarded, and the average of the remaining 9
measurements was calculated.

---

## 9. Experimental Conclusion

The obtained results showed a clear relationship between input size and
the execution time of the algorithms.

Number of Inversions obtained an `n log n` function as its best
experimental fit, matching the theoretical complexity obtained for the
modified MergeSort algorithm.

Closest Points also showed `n log n` experimental behavior, consistent
with the Divide and Conquer algorithm used.

For QuickSort, the repeated-values scenario produced approximately linear
behavior. This result shows the benefit of using a three-way partition
when many elements are equal to the pivot.

The graphical comparison of the fitted functions made it possible to
observe more clearly the differences between the linear growth obtained
by QuickSort in the selected scenario and the `n log n` growth observed
for Number of Inversions and Closest Points.

The small irregularities observed in the execution times show that
experimental measurements are subject to external factors. However, the
obtained `R²` values and the general trends make it possible to conclude
that the experimental results are consistent with the expected behavior
of the implemented algorithms.