# Problema 3 - Demostración de correctitud

## Notación

- `dist²(p, q) = (xp - xq)² + (yp - yq)²` (distancia al cuadrado).
- Para una lista de puntos `L`, `δ(L)` es la menor distancia al cuadrado
  entre dos posiciones **distintas** de `L`. Si `L` tiene un solo punto,
  `δ(L) = ∞` (en el código, `Infinity = Long.MaxValue`).
- `|L|` es la cantidad de puntos de `L`.

Trabajamos con distancias al cuadrado porque `√` es creciente: el par que
minimiza `dist²` también minimiza la distancia. Por eso basta demostrar que
el algoritmo calcula `δ(L)`, y al final `closestDistance` devuelve
`roundTo4(√δ(L))`.

La demostración principal es sobre `closestRec`. Antes demostramos tres
lemas sobre las funciones auxiliares que usa en el paso de combinación.

---

## Lema 1: `mergeByYRC` mezcla correctamente

### Propiedad

Si `A` y `B` están ordenadas por `y` y `acum` contiene, en orden inverso,
una lista ordenada por `y` cuyos elementos son todos `≤` (en `y`) que los
de `A` y `B`, entonces `mergeByYRC(A, B, acum)` devuelve
`reverse(acum) ++ merge(A, B)`: una lista ordenada por `y` con exactamente
los elementos de `acum`, `A` y `B`.

### Paso base: `A = Nil`, `B = Nil`

Se devuelve `reverseListRC(acum, Nil)`, que es `reverse(acum)`. Se cumple.

### H.I.

La propiedad se cumple para todo par de listas con `|A| + |B| = k`.

### Paso inductivo: `|A| + |B| = k + 1`

Sea `h` el elemento que se toma: la cabeza de `A` si `B = Nil`, la cabeza
de `B` si `A = Nil`, o la cabeza con menor `y` si ambas son no vacías.

1. Como `A` y `B` están ordenadas, `h` es el elemento de menor `y` entre
   todos los que quedan en `A` y `B`.
2. Por la hipótesis sobre `acum`, `h` es mayor o igual (en `y`) que todos
   los elementos de `acum`, así que `h :: acum` sigue cumpliendo la
   condición del acumulador.
3. La llamada recursiva tiene `k` elementos entre las dos listas.
4. Por H.I., el resultado está ordenado por `y` y contiene todos los
   elementos.

Llamando con `acum = Nil` se obtiene `merge(A, B)`. ∎

> La misma demostración, cambiando `y` por `(x, y)` en orden lexicográfico,
> prueba `mergeByXRC`. Es el mismo argumento usado en `mergeAndCount` del
> Problema 1.

---

## Lema 2: `scanNeighborsRC` y `scanStripRC`

### 2a. `scanNeighborsRC`

**Propiedad.** Si `Q` está ordenada por `y` y todo `q ∈ Q` cumple
`yq ≥ yp`, entonces

`scanNeighborsRC(p, Q, b) = min(b, min{ dist²(p, q) : q ∈ Q })`.

**Paso base: `Q = Nil`.** Se devuelve `b`, y `min(b, min ∅) = min(b, ∞) = b`.

**H.I.** La propiedad se cumple para la cola `t` de `Q`.

**Paso inductivo: `Q = q :: t`.** Sea `dy = yq - yp`.

- **Caso `dy² ≥ b` (corte).** Como `Q` está ordenada por `y`, todo
  `q' ∈ Q` cumple `yq' - yp ≥ dy ≥ 0`, así que
  `dist²(p, q') ≥ (yq' - yp)² ≥ dy² ≥ b`. Ningún punto de `Q` mejora `b`,
  y devolver `b` es correcto.
- **Caso `dy² < b`.** Se llama con `b' = min(b, dist²(p, q))` sobre `t`.
  Por H.I. el resultado es `min(b', min_{q' ∈ t} dist²(p, q'))`
  `= min(b, min_{q' ∈ Q} dist²(p, q'))`.

### 2b. `scanStripRC`

**Propiedad.** Si `S` está ordenada por `y`, entonces
`scanStripRC(S, b) = min(b, δ(S))`.

**Paso base: `S = Nil`.** Se devuelve `b = min(b, ∞)`.

**H.I.** La propiedad se cumple para la cola `t` de `S`.

**Paso inductivo: `S = p :: t`.** Todo par de `S` es de la forma `(p, q)`
con `q ∈ t` o es un par dentro de `t`. Por 2a,
`b' = scanNeighborsRC(p, t, b) = min(b, min_{q ∈ t} dist²(p, q))`.
Por H.I., `scanStripRC(t, b') = min(b', δ(t)) = min(b, δ(S))`. ∎

---

## Lema 3: la franja contiene todos los pares cruzados útiles

Sean `left` y `right` las dos mitades, `midX` la `x` del primer punto de
`right` y `d = min(δ(left), δ(right))`.

**Propiedad.** Si `p ∈ left`, `q ∈ right` y `dist²(p, q) < d`, entonces
`p` y `q` pertenecen a la franja `{ r : (xr - midX)² < d }`.

**Demostración.**

1. La lista está ordenada por `x` y la dividimos por **posición**, así que
   `xp ≤ midX ≤ xq`.
2. Por lo tanto `0 ≤ midX - xp ≤ xq - xp` y `0 ≤ xq - midX ≤ xq - xp`.
3. Entonces `(xp - midX)² ≤ (xq - xp)² ≤ dist²(p, q) < d`, y lo mismo para `q`.
4. Así, `buildStripRC` conserva a `p` y a `q`.

Además, `buildStripRC` recorre la lista ordenada por `y` y conserva el
orden relativo (en reversa, que luego se invierte una vez), así que la
franja queda **ordenada por `y`**, como necesita el Lema 2. ∎

---

## Teorema: `closestRec` es correcta (inducción estructural)

### Propiedad `P(L)`

Para toda lista no vacía `L` ordenada por `x` con `n = |L|`:

`closestRec(L, n) = (δ(L), Y)`

donde `Y` contiene exactamente los puntos de `L` ordenados por `y`.

La inducción es estructural sobre la lista: cada llamada recursiva se hace
sobre las sublistas `left` y `right`, que son estrictamente más pequeñas
que `L` cuando `|L| ≥ 2`.

---

### Paso base: `L = p :: Nil` (`n = 1`)

1. `closestRec(p :: Nil, 1)`. Objetivo.
2. Por el primer caso del `match`, se devuelve `(Infinity, p :: Nil)`.
3. Un solo punto no tiene pares de posiciones distintas, así que
   `δ(L) = ∞ = Infinity`.
4. Una lista de un elemento ya está ordenada por `y`.

Por lo tanto `P(p :: Nil)` se cumple.

---

### H.I.

Para `L` con `n ≥ 2`, suponemos que la propiedad se cumple para las
sublistas `left` (los primeros `⌊n/2⌋` puntos) y `right` (los
`⌈n/2⌉` restantes):

- `closestRec(left, ⌊n/2⌋) = (δ(left), leftByY)`
- `closestRec(right, ⌈n/2⌉) = (δ(right), rightByY)`

Ambas son no vacías porque `n ≥ 2`, y ambas siguen ordenadas por `x`.

---

### Paso inductivo: `n ≥ 2`

1. `closestRec(L, n)`. Objetivo.
2. `splitListRC(L, ⌊n/2⌋, Nil)` produce `left` y `right`.
   Definición de `splitListRC`.
3. Por H.I., `d1 = δ(left)` y `d2 = δ(right)`, y `leftByY`, `rightByY`
   están ordenadas por `y`.
4. Sea `d = min(d1, d2)`.
5. Por el Lema 1, `allByY = mergeByYRC(leftByY, rightByY, Nil)` contiene
   todos los puntos de `L` ordenados por `y`. **Esto prueba la segunda
   componente.**
6. Todo par de posiciones distintas de `L` es de uno de tres tipos:
   - ambos en `left`: su distancia es `≥ d1 ≥ d`;
   - ambos en `right`: su distancia es `≥ d2 ≥ d`;
   - uno en cada mitad (par cruzado). Sea `c` el mínimo de estos.

   Por lo tanto `δ(L) = min(d, c)`.
7. Sea `S` la franja. Por el Lema 2, el resultado es
   `scanStripRC(S, d) = min(d, δ(S))`.
8. **El resultado no es menor que `δ(L)`.** Como `S` es una sublista de
   `L`, `δ(S) ≥ δ(L)`, y además `d ≥ δ(L)`. Entonces
   `min(d, δ(S)) ≥ δ(L)`.
9. **El resultado no es mayor que `δ(L)`.**
   - Si `c ≥ d`, entonces `δ(L) = d` y `min(d, δ(S)) ≤ d = δ(L)`.
   - Si `c < d`, el par cruzado que alcanza `c` está en `S` por el
     Lema 3, así que `δ(S) ≤ c` y `min(d, δ(S)) ≤ c = δ(L)`.
10. De 8 y 9, la primera componente es exactamente `δ(L)`.

Por lo tanto `P(L)` se cumple.

---

## Conclusión

Como la propiedad se cumple en el caso base y se conserva en el paso
inductivo, por inducción estructural `closestRec` devuelve la menor
distancia al cuadrado entre puntos distintos y la lista ordenada por `y`.

Como `sortByX` ordena la entrada por `x` (Lema 1 aplicado a `mergeByXRC`)
y `√` es creciente, `closestDistance` devuelve
`roundTo4(√δ(L))`, la menor distancia euclidiana redondeada a cuatro
decimales. ∎

---

## Observaciones

- **Puntos con la misma `x` a ambos lados.** El Lema 3 solo usa
  `xp ≤ midX ≤ xq`, que se cumple porque partimos por posición. Por eso el
  algoritmo es correcto aunque haya muchas `x` repetidas (test 5).
- **Puntos duplicados.** Si `d = 0`, la condición `dy² ≥ 0` corta el
  escaneo de inmediato y el resultado es `0` (test 3).
- **Desigualdad estricta en la franja.** Usar `(x - midX)² < d` en lugar de
  `≤ d` no pierde nada: un par cruzado con distancia `≥ d` no mejora `d`.
