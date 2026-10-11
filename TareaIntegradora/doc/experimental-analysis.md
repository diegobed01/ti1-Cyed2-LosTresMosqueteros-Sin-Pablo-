# Análisis experimental

## 1. Entorno de ejecución

Las pruebas de rendimiento se realizaron en la misma máquina con el fin
de mantener un entorno de ejecución consistente.

- Procesador: 12th Gen Intel(R) Core(TM) i5-1235U
- Memoria RAM: 16 GB
- Sistema operativo: Microsoft Windows 11 Pro
- Arquitectura: 64 bits
- Versión del sistema operativo: 10.0.26200

Durante las mediciones se buscó mantener las mismas condiciones de
ejecución y evitar otras aplicaciones de alto consumo que pudieran
afectar los tiempos registrados.

---

## 2. Metodología

La métrica utilizada para la experimentación fue el tiempo de ejecución
en milisegundos.

Para cada algoritmo y cada tamaño de entrada se siguió el siguiente
procedimiento:

1. Generar la entrada antes de iniciar la medición.
2. Ejecutar el algoritmo 10 veces con la misma entrada.
3. Registrar el tiempo de cada ejecución.
4. Descartar el tiempo de la primera ejecución.
5. Calcular el promedio de las 9 ejecuciones restantes.

La generación de los datos no se incluyó dentro del tiempo medido, de
modo que el resultado correspondiera únicamente a la ejecución del
algoritmo.

---

## 3. Tamaños de entrada

Las entradas se organizaron según las categorías definidas en el
enunciado de la tarea:

- Toy: `n < 100`
- Small: `100 <= n < 10000`
- Medium: `10000 <= n < 100000`
- Large: `n >= 1000000`

Los tamaños utilizados fueron:

| Categoría | Tamaños utilizados |
| --- | --- |
| Toy | 50 |
| Small | 100, 500, 1000, 5000 |
| Medium | 10000, 25000, 50000 |
| Large | 1000000 |

Estas categorías corresponden a las establecidas para la experimentación
en el enunciado.

---

## 4. Escenarios de entrada

Para cada algoritmo se utilizó un escenario de entrada relacionado con
su comportamiento y con el objetivo del problema.

### 4.1 Number of Inversions

Se utilizaron listas en orden descendente.

Este tipo de entrada contiene una gran cantidad de inversiones y permite
evaluar el algoritmo sobre un caso exigente para el conteo de
inversiones.

### 4.2 Improving QuickSort

Se utilizaron listas con valores repetidos.

Este escenario se relaciona directamente con el objetivo del problema,
ya que la partición de tres vías busca mejorar el comportamiento de
QuickSort cuando existen muchos elementos iguales.

### 4.3 Closest Points

Se utilizaron puntos generados de forma determinística.

Cada punto se representó como:

`(x, 2x)`

Por ejemplo:

`(0, 0), (1, 2), (2, 4), ...`

La generación determinística permite utilizar la misma entrada para las
diferentes repeticiones de un mismo tamaño.

---

## 5. Resultados

Para cada tamaño se realizaron 10 ejecuciones. La primera medición fue
descartada y el promedio se calculó utilizando las 9 ejecuciones
restantes.

### 5.1 Number of Inversions

| Tamaño n | Tiempo promedio (ms) |
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

---

### 5.2 Improving QuickSort

| Tamaño n | Tiempo promedio (ms) |
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

---

### 5.3 Closest Points

| Tamaño n | Tiempo promedio (ms) |
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

## 6. Complejidad experimental y teórica

Para cada algoritmo se realizará una gráfica de:

`Tamaño de entrada vs. Tiempo promedio`

Además, se incluirá una función de ajuste de los datos experimentales y
se comparará con la complejidad teórica correspondiente.

### 6.1 Number of Inversions

Complejidad teórica:

`Θ(n log n)`

Pendiente:

- gráfica experimental;
- función de ajuste;
- comparación entre el ajuste experimental y `n log n`.

---

### 6.2 Improving QuickSort

La complejidad de QuickSort depende de la distribución de los datos y
del comportamiento de las particiones.

En esta experimentación se utilizaron valores repetidos, que es el
escenario que busca mejorar la partición de tres vías.

Pendiente:

- gráfica experimental;
- función de ajuste;
- comparación con la complejidad teórica correspondiente.

---

### 6.3 Closest Points

Complejidad teórica:

`O(n log n)`

La implementación utiliza Divide and Conquer y mantiene el paso de
combinación en tiempo lineal, dando lugar a la recurrencia:

`T(n) = 2T(n/2) + O(n)`

Pendiente:

- gráfica experimental;
- función de ajuste;
- comparación entre el ajuste experimental y `n log n`.

---

## 7. Comparación de los algoritmos

Los resultados muestran diferencias claras entre los tiempos registrados
por los tres algoritmos.

Para `n = 1000000` se obtuvieron los siguientes promedios:

| Algoritmo | Tiempo promedio (ms) |
| --- | ---: |
| Number of Inversions | 726.2274 |
| Improving QuickSort | 16.3821 |
| Closest Points | 1048.9111 |

QuickSort presentó los menores tiempos dentro del escenario utilizado,
mientras que Closest Points presentó el mayor tiempo promedio para la
entrada de un millón de elementos.

Sin embargo, esta comparación debe interpretarse teniendo en cuenta que
cada algoritmo resuelve un problema diferente y utiliza un escenario de
entrada diferente.

La comparación final se complementará con las gráficas de las funciones
de ajuste experimentales.

---

## 8. Análisis de resultados

### 8.1 Patrón entre tamaño y tiempo

En Number of Inversions y Closest Points se observa, en términos
generales, un aumento del tiempo de ejecución a medida que aumenta el
tamaño de la entrada.

En QuickSort también existe un crecimiento general, aunque algunos
tamaños presentan tiempos menores que tamaños anteriores.

Por ejemplo, en QuickSort:

- `n = 10000`: 1.4744 ms
- `n = 25000`: 0.9473 ms

Esto muestra que los tiempos experimentales no necesariamente crecen de
forma perfectamente regular.

---

### 8.2 Tipo de relación observada

Pendiente de completar después de obtener las funciones de ajuste de las
gráficas experimentales.

---

### 8.3 Comparación con la complejidad teórica

Pendiente de completar después de comparar las curvas experimentales con
las funciones que representan las complejidades teóricas.

---

### 8.4 Comportamientos inusuales

Se observaron algunas variaciones entre mediciones y entre tamaños
consecutivos.

Estas variaciones pueden ocurrir debido a factores externos a los
algoritmos y no necesariamente indican un cambio en su complejidad
teórica.

---

### 8.5 Posibles fuentes de error

Algunas posibles fuentes de variación en los resultados son:

- procesos del sistema operativo ejecutándose en segundo plano;
- uso de memoria por otras aplicaciones;
- optimizaciones realizadas por la máquina virtual de Java;
- recolección de basura de la JVM;
- variaciones normales del sistema durante la medición;
- precisión del tiempo medido en ejecuciones muy rápidas.

Para reducir estas variaciones se utilizó la misma máquina, se realizaron
10 ejecuciones por entrada, se descartó la primera medición y se calculó
el promedio de las 9 ejecuciones restantes.

---

## 9. Conclusión experimental

Pendiente de completar después de realizar las gráficas, obtener las
funciones de ajuste y comparar los resultados experimentales con las
complejidades teóricas de los tres algoritmos.