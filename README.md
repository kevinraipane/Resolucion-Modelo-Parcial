# Sistema de Gestión de Reactivos - Modelo Parcial

**Institución:** UTN Regional Mar del Plata (UTNMDP)
**Carrera:** Tecnicatura Universitaria en Programación (TUP) - 2do Año
**Materia:** Programación 3
**Profesor:** Tec. Mango Eduardo
**Ayudante:** Tec. Raipane Kevin

---

## 🧪 Descripción del Proyecto

El laboratorio químico de la UTN necesita digitalizar el control de su depósito de reactivos. A diferencia de un producto común, los reactivos químicos tienen niveles de peligrosidad y compatibilidades específicas.

El sistema debe gestionar los Lotes de reactivos que ingresan. Cada lote pertenece a un Reactivo específico. El desafío principal es que el depósito tiene una **Capacidad de Riesgo Máxima**: cada lote aporta "Puntos de Riesgo" al depósito según su peso y peligrosidad. El sistema no debe permitir ingresos que superen el límite de seguridad de la planta.

---

## 🗄️ Diagrama Entidad Relación (DER)

Las entidades deberán estar mapeadas correctamente dentro de Spring Boot. Se deberán agregar las validaciones pertinentes a nivel base de datos.

### Entidad: `reactivos`
| Campo | Tipo |
| :--- | :--- |
| `id` (PK) | integer |
| `nombre` | varchar |
| `nivel_peligro` | integer |
| `es_precursor_quimico` | boolean |
| `activo` | boolean |

### Entidad: `lotes`
| Campo | Tipo |
| :--- | :--- |
| `id` (PK) | integer |
| `nro_lote` | varchar |
| `fecha_recepcion` | date |
| `fecha_vencimiento` | date |
| `cantidad_kg` | decimal |
| `id_reactivo` (FK) | integer |
| `id_estante` (FK) | integer |

### Entidad: `estantes`
| Campo | Tipo |
| :--- | :--- |
| `id` (PK) | integer |
| `codigo_almacen` | varchar |
| `capacidad_max_kg` | decimal |
| `riesgo_limite` | integer |

---

## ⚙️ Requisitos Funcionales

* **Gestión de Reactivos:**
  * El sistema deberá permitir al químico realizar operaciones de Alta, Baja y Modificación sobre reactivos.
  * El sistema deberá permitir al químico visualizar la lista de reactivos.
  * El sistema deberá permitir al químico filtrar reactivos por los campos `nombre`, `nivel_peligro` y `es_precursor_quimico`.

* **Gestión de Lotes:**
  * El sistema deberá permitir al químico realizar operaciones de Alta y Modificación sobre lotes.
  * El sistema deberá permitir al químico visualizar la lista de lotes.
  * El sistema deberá permitir al químico "consumir" un lote, lo cual le restaría una cantidad de kg de su cantidad actual.

* **Gestión de Estantes:**
  * El sistema deberá permitir al químico realizar operaciones de Alta, Baja y Modificación sobre estantes.
  * El sistema deberá permitir al químico visualizar todos los estantes. Deberá poder ordenarlo por nivel de riesgo actual de cada estante, de manera ascendente.

---

## 📋 Reglas de Negocio y Validaciones

* **Baja lógica/física de Reactivos:** Asegurar que no se pueda eliminar un Reactivo si existe un Lote activo asociado (Fecha no vencida y con capacidad mayor a 0).
* **Consumo de Lotes:** Al "consumir" un lote, si la cantidad llega a 0, el lote no se borra de la base de datos (se mantiene para registro histórico), simplemente queda con `cantidad = 0`.
* **Validación de Vencimiento:** No se puede ingresar un lote cuya fecha de vencimiento sea menor a 6 meses desde la fecha actual.
* **Cálculo de Riesgo:** El "Riesgo Acumulado" del estante se calcula como la sumatoria de `(Cantidad_KG * Peligrosidad del reactivo)`.
  * Si el nuevo lote hace que el estante supere su `riesgo_limite`, la operación debe fallar.
* **Restricción de Precursores:** Si el reactivo es un "precursor químico", el sistema debe verificar que el estante pertenezca al almacén con código `"SEC-01"` (Sector de Seguridad).

---

## 🏗️ Arquitectura y Restricciones Técnicas

* **Patrón de Arquitectura:** Se deberá implementar la arquitectura MVC y respetar la separación en capas.
* **Manejo de Excepciones:** Se deberá hacer un correcto manejo de excepciones de manera centralizada, con códigos apropiados según el evento.
* **Buenas Prácticas:** Se deberán seguir las buenas prácticas vistas durante la cursada (patrones de diseño, principios SOLID, etc).
* **DTOs Obligatorios:** No se pueden exponer las Entidades de JPA en los Controllers. Se deben usar DTOs para la entrada y salida de datos.
* **Validaciones DTO:** El uso de `@Valid` y anotaciones de validation (como `@Min`, `@NotNull`) es obligatorio en los DTOs.
* **Custom Exceptions:** Al menos una regla de negocio (ej. el Riesgo Máximo) debe lanzar una excepción propia que sea capturada por un `@ControllerAdvice`.

## Endpoints en Postman con sus body y Params
https://www.postman.com/kevinraipane/workspace/universidad/collection/45517576-a27f5ea4-6eae-4bd6-b894-a8cbe7eb0896?action=share&source=copy-link&creator=45517576
