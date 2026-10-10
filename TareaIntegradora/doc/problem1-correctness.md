# Problema 1 - Demostración de correctitud

## Inducción estructural de `mergeAndCount`

### Propiedad

Sean `L` y `R` dos listas ordenadas.

Queremos demostrar que:

`mergeAndCount(L, R) = (M, k)`

donde:

- `M` contiene los elementos de `L` y `R` en orden.
- `k` es la cantidad de inversiones entre los elementos de `L` y `R`.

---

## Paso base

### Caso 1: `L = Nil`

Queremos demostrar:

`mergeAndCount(Nil, R) = (R, 0)`

1. `mergeAndCount(Nil, R)`  
   Objetivo.

2. Como `L` no tiene elementos, solamente quedan los elementos de `R`.  
   Definición de `mergeAndCount`.

3. No se generan nuevas inversiones porque no existen elementos en `L`.

Por lo tanto:

`mergeAndCount(Nil, R) = (R, 0)`

Entonces:

`(R, 0) = (R, 0)`

Se cumple el paso base.

### Caso 2: `R = Nil`

De forma análoga:

`mergeAndCount(L, Nil) = (L, 0)`

porque solamente quedan los elementos de `L` y no se generan nuevas
inversiones.

---

## Paso recursivo

Sean:

`L = hL :: tL`

`R = hR :: tR`

### H.I.

Suponemos que `mergeAndCount` funciona correctamente para listas más
pequeñas.

Es decir:

`mergeAndCount(tL, R)`

y

`mergeAndCount(L, tR)`

devuelven correctamente la lista ordenada y la cantidad de inversiones
correspondientes.

---

### Caso 1: `hL <= hR`

1. `mergeAndCount(hL :: tL, hR :: tR)`  
   Objetivo.

2. Como `hL <= hR`, se toma `hL`.  
   Definición de `mergeAndCount`.

3. Como `R` está ordenada, `hL` tampoco es mayor que los elementos que
   aparecen después de `hR`.

4. Por lo tanto, no se agrega ninguna inversión nueva.

5. Se continúa con:

   `mergeAndCount(tL, hR :: tR)`

   Definición recursiva.

6. Por H.I., esta llamada ordena correctamente los elementos restantes
   y cuenta correctamente sus inversiones.

Por lo tanto, el resultado completo queda ordenado y el número de
inversiones es correcto.

---

### Caso 2: `hL > hR`

1. `mergeAndCount(hL :: tL, hR :: tR)`  
   Objetivo.

2. Como `hL > hR`, se toma `hR`.  
   Definición de `mergeAndCount`.

3. Como `L` está ordenada, todos los elementos que quedan en `L` son
   mayores o iguales que `hL`.

4. Por lo tanto, `hR` es menor que todos los elementos restantes de `L`.

5. Se generan:

   `|L|`

   nuevas inversiones.

6. Se continúa con:

   `mergeAndCount(hL :: tL, tR)`

   Definición recursiva.

7. Por H.I., esta llamada ordena correctamente los elementos restantes
   y cuenta correctamente sus inversiones.

Entonces:

`inversiones = |L| + inversiones restantes`

Por lo tanto, el resultado completo queda ordenado y el número de
inversiones es correcto.

---

## Conclusión

Como la propiedad se cumple en los casos base y se mantiene en los casos
recursivos, por inducción estructural se concluye que `mergeAndCount`
une correctamente dos listas ordenadas y cuenta correctamente las
inversiones entre ellas.