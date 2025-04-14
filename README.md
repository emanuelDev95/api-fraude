
# API REST - Mercado Libre Challenge

Esta API REST, construida con **Java 21**, **Spring WebFlux**, y **MongoDB**, permite obtener información geográfica, de idioma, hora, distancia y moneda de un país dado a partir de una IP. Además, proporciona estadísticas de uso de las consultas realizadas.

## Tecnologías utilizadas

- **Java 21**
- **Spring Boot 3.x**
- **Spring WebFlux** (programación reactiva)
- **MongoDB** (base de datos NoSQL)
- **Lombok** (para simplificar la creación de clases)
- **JUnit 5** (para pruebas unitarias)
- **Spring Data MongoDB** (para interactuar con MongoDB)

## Endpoints

### 1. **GET /ip-info**

#### Descripción
Este endpoint recibe una dirección IP y devuelve la información correspondiente.

#### Parámetros

| Parámetro | Tipo    | Descripción                               |
|-----------|---------|-------------------------------------------|
| `ip`      | String  | Dirección IP de origen para la consulta. |

#### Ejemplo de solicitud

```
GET /ip-info/8.8.8.8
```

#### Ejemplo de respuesta

```json
{
    "ip": "161.185.160.93",
    "date": "14/04/2025 02:54:47",
    "country": "United States",
    "isoCode": "US",
    "languages": [
        "English"
    ],
    "currency": null,
    "conversionRate": null,
    "distanceToBuenosAires": 8530.37426684548
}
```

#### Códigos de respuesta

| Código | Descripción                           |
|--------|---------------------------------------|
| 200    | La solicitud fue exitosa.             |    |
| 404    | No se pudo determinar el país.       |
| 500    | Error en el servidor.                 |

---

### 2. **GET stats**

#### Descripción
Este endpoint devuelve estadísticas  
* Distancia más lejana a Buenos Aires desde la cual se haya consultado el servicio 
* Distancia más cercana a Buenos Aires desde la cual se haya consultado el servicio 
 * Distancia promedio de todas las ejecuciones que se hayan hecho del servicio. 

#### Ejemplo de solicitud

```
GET /stats
```

#### Ejemplo de respuesta

```json
{
    "closestCountry": "Colombia",
    "farthestCountry": "Spain",
    "averageDistance": 8546.097317992871
}
```

#### Códigos de respuesta

| Código | Descripción                           |
|--------|---------------------------------------|
| 200    | La solicitud fue exitosa.             |
| 500    | Error en el servidor.                 |

## Configuración del Proyecto

### Prerequisitos

- **Java 21** o superior.
- **MongoDB** en funcionamiento. Si no tienes MongoDB instalado localmente, puedes usar Docker para ejecutar una instancia.

### Instalación

1. Clona este repositorio:

   ```bash
   git clone https://github.com/emanuelDev95/api-fraude.git
   cd api-fraude/fraudeapi
   ```

2. ejecuta el proyecto utilizando **Docker compose**:

   ```bash
   docker-compose up --build

   ```


   ```

   La API estará disponible en `http://localhost:8080`.

## Colección de Postman
   
   Para facilitar las pruebas y el uso de la API, se ha proporcionado una **Colección de Postman**. Puedes importar la colección a Postman para probar los endpoints directamente desde la interfaz de Postman.
   
   ### Importación de la Colección
   
   1. **Descarga** el archivo de la colección de Postman desde [aqui](/postman/api-fraude.postman_collection.json).
   2. **Abre** Postman y haz clic en el botón "Importar".
   3. Selecciona el archivo descargado o pega el enlace de la colección.
   4. Haz clic en "Importar" y la colección estará disponible en tu espacio de trabajo de Postman.
   
   ### Endpoints disponibles en la Colección de Postman
   
   - **GET /ip-info**: Consulta la información correspondiente a una IP.
   - **GET /stats**: Obtiene las estadísticas de uso de la API.




## Pruebas

Las pruebas unitarias están implementadas usando **JUnit 5** y **Mockito**.

Para ejecutar las pruebas:

```bash
mvn test
```
## Pruebas unitarias

Se realizaron pruebas unitarias alcanzando un 89% de cobertura del codigo.

![alt text](/imgs/coverage.png)

## Estructura de la Base de Datos

La API utiliza **MongoDB** como base de datos NoSQL. Los datos, como las estadísticas de uso, se almacenan en colecciones de MongoDB.

## Consideraciones

- La API es reactiva, utilizando **Spring WebFlux** para manejar solicitudes de manera no bloqueante.
- La base de datos **MongoDB** se usa para almacenar estadísticas de uso, como el número de consultas realizadas y las IPs únicas.
- La API no realiza migraciones de base de datos, ya que MongoDB no requiere este tipo de configuración.

## Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.
