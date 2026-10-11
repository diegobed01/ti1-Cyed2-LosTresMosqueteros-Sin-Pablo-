# Problema 3 - Análisis de complejidad

## Costo de las funciones auxiliares

Sea `n` la cantidad de puntos que recibe cada función.

| Función | Costo | Justificación |
| --- | --- | --- |
| `toPointsRC`, `listLengthRC`, `reverseListRC` | `O(n)` | Un paso por elemento |
| `splitListRC` | `O(n)` | Recorre `n/2` elementos y luego invierte el acumulador |
| `mergeByXRC`, `mergeByYRC` | `O(n)` | Cada paso consume un elemento; al final se invierte una vez |
| `buildStripRC` | `O(n)` | Un paso por punto |
| `scanStripRC` | `O(n)` | Cada punto revisa a lo sumo 8 vecinos (ver abajo) |
| `distanceSquared`, `minLong`, `roundTo4` | `O(1)` | Operaciones aritméticas |

---

## ¿Por qué cada punto revisa a lo sumo 8 vecinos?

Esta es la parte clave para que la combinación sea lineal.

Sea `p` un punto de la franja y sea `d = min(d1, d2) > 0`. `scanNeighborsRC`
solo sigue avanzando mientras el siguiente punto `q` cumple `(yq - yp)² < d`,
es decir, `0 ≤ yq - yp < √d`. Llamemos `δ = √d`.

Todos los puntos que se visitan sin cortar están en el rectángulo

`[midX - δ, midX + δ] × [yp, yp + δ)`

de ancho `2δ` y alto `δ`.

1. Dividimos la mitad izquierda del rectángulo en 4 cuadrados de lado
   `δ/2`, y lo mismo con la mitad derecha.
2. Dos puntos de `left` dentro del mismo cuadrado estarían a distancia
   `≤ δ/√2 < δ`, lo que contradice que `d1 ≥ d` es la menor distancia
   dentro de `left`. Lo mismo vale para `right`.
3. Entonces en el rectángulo hay a lo sumo 4 puntos de `left` y 4 de
   `right`: **a lo sumo 8 puntos, contando a `p`**.
4. Por lo tanto `p` se compara con a lo sumo 7 puntos, más una comparación
   adicional que dispara el corte: **a lo sumo 8 comparaciones por punto**.

Si `d = 0`, el corte ocurre en la primera comparación. Si `d = ∞` (solo
cuando `n = 2`), la franja tiene 2 puntos. En todos los casos el trabajo
por punto es constante, y `scanStripRC` cuesta `O(n)`.

> El número 7 no aparece en el código: la condición de corte
> `dy² ≥ best` es suficiente, y este argumento garantiza que se cumple
> pronto.

---

## Ecuación de recurrencia de `closestRec`

En cada llamada con `n ≥ 2`:

- dos llamadas recursivas, sobre `⌊n/2⌋` y `⌈n/2⌉` puntos;
- trabajo fuera de las llamadas:
  `splitListRC` + `mergeByYRC` + `buildStripRC` + `reverseListRC` +
  `scanStripRC`, todos `O(n)`. En total, a lo sumo `cn` para una
  constante `c`.

Por lo tanto:

`T(n) = T(⌊n/2⌋) + T(⌈n/2⌉) + cn`

Caso base:

`T(1) = c0`

Para la resolución tomamos `n = 2^k`, con lo que la recurrencia queda:

`T(n) = 2T(n/2) + cn`

> **¿Por qué no es `2T(n/2) + O(n log n)`?** Si cada llamada ordenara su
> franja por `y` desde cero, la combinación costaría `O(n log n)` y el
> total sería `O(n log² n)`. Para evitarlo, `closestRec` devuelve también
> sus puntos ordenados por `y`, y la combinación solo hace un `merge`
> lineal de las dos mitades, igual que en merge sort.

---

## Resolución de la recurrencia

### Paso 1

Partimos de:

`T(n) = 2T(n/2) + cn`

Aplicamos la recurrencia a `T(n/2)`:

`T(n/2) = 2T(n/4) + cn/2`

---

### Paso 2

Sustituimos:

`T(n) = 2[2T(n/4) + cn/2] + cn`

`T(n) = 4T(n/4) + cn + cn`

`T(n) = 4T(n/4) + 2cn`

---

### Paso 3

Aplicamos nuevamente la recurrencia:

`T(n/4) = 2T(n/8) + cn/4`

Entonces:

`T(n) = 4[2T(n/8) + cn/4] + 2cn`

`T(n) = 8T(n/8) + 3cn`

---

### Paso 4

Después de `i` expansiones se observa el patrón:

`T(n) = 2^i T(n/2^i) + icn`

---

### Paso 5

La recursión termina en el caso base:

`n/2^i = 1`

Entonces `2^i = n`, es decir, `i = log2(n)`.

---

### Paso 6

Sustituimos `i = log2(n)`:

`T(n) = n T(1) + cn log2(n)`

`T(n) = c0 n + cn log2(n)`

---

### Paso 7

El término dominante es `n log n`, así que:

`T(n) = Θ(n log n)`

Es `Θ` y no solo `O` porque cada nivel de la recursión ejecuta
`mergeByYRC` sobre todos los puntos del nivel, y eso cuesta al menos `n`
por nivel durante `log2(n)` niveles.

Para `n` que no es potencia de 2, `T` es creciente en `n`, así que
`T(n) ≤ T(2^⌈log2 n⌉) ≤ T(2n) = Θ(n log n)`.

---

## Comprobación con el Método Maestro

`T(n) = 2T(n/2) + cn`

Comparando con `T(n) = aT(n/b) + cn^d`:

`a = 2`, `b = 2`, `d = 1`

`b^d = 2^1 = 2`

Como `a = b^d`, por el segundo caso del Método Maestro:

`T(n) = Θ(n^d log n) = Θ(n log n)`

El resultado coincide con el obtenido por sustituciones sucesivas.

---

## Complejidad temporal total de `closestDistance`

| Etapa | Costo |
| --- | --- |
| `toPointsRC` (validar y convertir) | `Θ(n)` |
| `listLengthRC` | `Θ(n)` |
| `sortByX` (merge sort por `x`, misma recurrencia `2T(n/2) + cn`) | `Θ(n log n)` |
| `closestRec` | `Θ(n log n)` |
| `sqrt` y `roundTo4` | `Θ(1)` |

**Total: `Θ(n log n)`**, que es la complejidad que pide el enunciado.

---

## Complejidad espacial

- **Memoria auxiliar: `O(n)`.** En cada momento solo están vivas las
  listas de las llamadas del camino actual de la recursión, que suman
  `n + n/2 + n/4 + … ≤ 2n`. Las listas de las llamadas que ya terminaron
  se pueden liberar. En total se crean `Θ(n log n)` celdas de lista a lo
  largo de la ejecución, pero no al mismo tiempo.
- **Pila de llamadas: `O(log n)`.** `closestRec` y `sortByX` no son de
  cola, pero su profundidad es `⌈log2 n⌉` (unas 20 llamadas para
  `n = 10⁶`). Las 10 funciones `@tailrec` se compilan como ciclos y usan
  pila `O(1)`.

 Por eso `@tailrec` en los `merge` es necesario y no solo estético: un
 `merge` recursivo que no sea de cola necesitaría `n` marcos de pila y
 desbordaría con `n = 10⁶`. Con la implementación actual se procesaron
 10⁶ puntos sin problemas con la pila por defecto.
