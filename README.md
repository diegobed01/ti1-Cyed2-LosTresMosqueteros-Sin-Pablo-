# Tarea Integradora 1

## Computación y Estructuras Discretas II

Proyecto desarrollado en Scala utilizando programación funcional,
recursión, pattern matching y estructuras de datos inmutables.

La tarea integra tres problemas basados principalmente en estrategias
de Divide and Conquer y una segunda parte de experimentación para
comparar el comportamiento teórico y experimental de los algoritmos.

---

## Integrantes

- Juan Diego Mendoza
- Dylan Pizarro
- Diego Fernando Bedoya

Equipo: **Los Tres Mosqueteros Sin Pablo**

---

## Problemas desarrollados

### Problema 1 - Number of Inversions

Cuenta la cantidad de inversiones presentes en una lista de enteros.

La solución utiliza una modificación de MergeSort que permite ordenar la
lista y contar las inversiones durante el proceso de merge.

Complejidad teórica:

`Θ(n log n)`

---

### Problema 2 - Improving QuickSort

Implementa QuickSort utilizando una partición de tres vías:

- elementos menores que el pivote;
- elementos iguales al pivote;
- elementos mayores que el pivote.

Esta modificación busca mejorar el comportamiento del algoritmo cuando
la entrada contiene muchos elementos repetidos.

---

### Problema 3 - Closest Points

Calcula la menor distancia entre dos puntos de un conjunto utilizando
una estrategia de Divide and Conquer.

Complejidad teórica:

`O(n log n)`

---

## Experimentación

Se realizó una experimentación con diferentes tamaños de entrada:

- Toy
- Small
- Medium
- Large

Para cada algoritmo y tamaño de entrada se realizaron 10 ejecuciones.

La primera medición fue descartada y se calculó el promedio utilizando
las 9 ejecuciones restantes.

Los resultados experimentales, las gráficas, las funciones de ajuste y
la comparación con las complejidades teóricas se encuentran en:

`doc/experimental-analysis.md`

---

## Estructura del proyecto

```text
TareaIntegradora/
├── data/
├── doc/
│   ├── images/
│   ├── experimental-analysis.md
│   ├── problem1-test-design.md
│   ├── problem1-correctness.md
│   ├── problem1-complexity.md
│   ├── problem2-test-design.md
│   ├── problem2-correctness.md
│   ├── problem2-complexity.md
│   ├── problem3-test-design.md
│   ├── problem3-correctness.md
│   └── problem3-complexity.md
│
├── src/
│   ├── main/
│   │   └── scala/
│   │       ├── ClosestPoints/
│   │       ├── Experiment/
│   │       ├── NumberOfInversions/
│   │       └── QuickSort/
│   │
│   └── test/
│       └── scala/
│           ├── ClosestPoints/
│           ├── Experiment/
│           ├── NumberOfInversions/
│           └── QuickSort/
│
├── build.sbt
└── README.md
