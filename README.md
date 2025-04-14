# api-fraude


# API de Información Geográfica - Prueba Técnica

## Descripción

Esta es una API que, dada una dirección IP, obtiene información geográfica sobre el país correspondiente. La información incluye:
- Nombre del país
- Código ISO
- Idiomas oficiales
- Hora actual (según la zona horaria)
- Distancia a Buenos Aires
- Moneda local y su cotización en dólares

### Estadísticas
También permite consultar estadísticas agregadas de uso del servicio:
- Distancia más cercana a Buenos Aires desde la cual se ha consultado el servicio.
- Distancia más lejana.
- Distancia promedio ponderada por las invocaciones.

## Endpoints

### `GET /ip-info/{ip}`

**Descripción:** Obtiene información sobre el país correspondiente a la IP proporcionada.

**Parámetros:**
- `ip` (string): Dirección IP.

**Ejemplo de solicitud:**
```bash
curl -X GET http://localhost:8080/ip-info/83.44.196.93
