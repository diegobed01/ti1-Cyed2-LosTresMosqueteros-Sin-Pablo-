# Problema 1 - Análisis de complejidad

## Complejidad de `mergeAndCount`

La función `mergeAndCount` recorre los elementos de las dos listas
para unirlas y contar las inversiones.

Si entre las dos listas hay `n` elementos:

`M(n) = O(n)`

---

## Complejidad de `mergeSortAndCount`

La función divide la lista en dos mitades, realiza una llamada recursiva
sobre cada mitad y luego utiliza `mergeAndCount`.

Por lo tanto:

`T(n) = 2T(n/2) + n`

Caso base:

`T(1) = 1`

---

## Resolución de la recurrencia

### Paso 1

Partimos de:

`T(n) = 2T(n/2) + n`

Aplicamos la recurrencia a `T(n/2)`:

`T(n/2) = 2T(n/4) + n/2`

---

### Paso 2

Sustituimos:

`T(n) = 2[2T(n/4) + n/2] + n`

`T(n) = 4T(n/4) + n + n`

`T(n) = 4T(n/4) + 2n`

---

### Paso 3

Aplicamos nuevamente la recurrencia:

`T(n/4) = 2T(n/8) + n/4`

Entonces:

`T(n) = 4[2T(n/8) + n/4] + 2n`

`T(n) = 8T(n/8) + n + 2n`

`T(n) = 8T(n/8) + 3n`

---

### Paso 4

Después de `i` expansiones se observa el patrón:

`T(n) = 2^i T(n/2^i) + in`

---

### Paso 5

La recursión termina cuando se llega al caso base:

`n/2^i = 1`

Entonces:

`2^i = n`

Por lo tanto:

`i = log2(n)`

---

### Paso 6

Sustituimos `i = log2(n)`:

`T(n) = 2^(log2(n)) T(1) + n log2(n)`

Como:

`2^(log2(n)) = n`

y:

`T(1) = 1`

obtenemos:

`T(n) = n + n log2(n)`

---

### Paso 7

El término dominante es:

`n log n`

Por lo tanto:

`T(n) = O(n log n)`

También se puede expresar de manera más precisa como:

`T(n) = Θ(n log n)`

---

## Comprobación con Método Maestro

La recurrencia es:

`T(n) = 2T(n/2) + n`

Comparando con:

`T(n) = aT(n/b) + cn^d`

tenemos:

`a = 2`

`b = 2`

`d = 1`

Calculamos:

`b^d = 2^1 = 2`

Entonces:

`a = b^d`

Por el segundo caso del Método Maestro:

`T(n) = Θ(n^d log n)`

Por lo tanto:

`T(n) = Θ(n log n)`

El resultado coincide con el obtenido mediante sustituciones sucesivas.

---

## Complejidad espacial

Durante el algoritmo se crean listas auxiliares para dividir y mezclar
los elementos.

Por lo tanto, la complejidad espacial auxiliar es:

`O(n)`

La profundidad de la recursión de `mergeSortAndCount` es:

`O(log n)`