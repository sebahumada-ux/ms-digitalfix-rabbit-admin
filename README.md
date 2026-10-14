# ms-digitalfix-rabbit-admin

Microservicio administrativo para RabbitMQ dentro de DigitalFix.

Permite crear y eliminar colas, exchanges y bindings mediante una API REST, utilizando `RabbitAdmin` de Spring AMQP.

## Tecnologías

- Java 21
- Spring Boot
- Spring AMQP
- RabbitMQ
- Docker
- Maven

## Puerto

```text
8084
```

## Funcionalidad

Rabbit Admin permite administrar dinámicamente componentes de RabbitMQ desde endpoints REST.

Funciones disponibles:

- Crear colas.
- Eliminar colas.
- Crear exchanges.
- Eliminar exchanges.
- Crear bindings.
- Eliminar bindings.

## Endpoint base

```text
/api/rabbit
```

## Colas

### Crear cola

```http
POST /api/rabbit/queues
```

Body:

```json
{
  "name": "q.prueba",
  "durable": true
}
```

Ejemplo:

```bash
curl -X POST http://localhost:8084/api/rabbit/queues \
  -H "Content-Type: application/json" \
  -d '{"name":"q.prueba","durable":true}'
```

### Eliminar cola

```http
DELETE /api/rabbit/queues/{name}
```

Ejemplo:

```bash
curl -X DELETE http://localhost:8084/api/rabbit/queues/q.prueba
```

## Exchanges

### Crear exchange

```http
POST /api/rabbit/exchanges
```

Body:

```json
{
  "name": "ex.prueba",
  "type": "direct",
  "durable": true
}
```

Ejemplo:

```bash
curl -X POST http://localhost:8084/api/rabbit/exchanges \
  -H "Content-Type: application/json" \
  -d '{"name":"ex.prueba","type":"direct","durable":true}'
```

### Eliminar exchange

```http
DELETE /api/rabbit/exchanges/{name}
```

Ejemplo:

```bash
curl -X DELETE http://localhost:8084/api/rabbit/exchanges/ex.prueba
```

## Bindings

### Crear binding

```http
POST /api/rabbit/bindings
```

Body:

```json
{
  "queue": "q.prueba",
  "exchange": "ex.prueba",
  "routingKey": "prueba.key"
}
```

Ejemplo:

```bash
curl -X POST http://localhost:8084/api/rabbit/bindings \
  -H "Content-Type: application/json" \
  -d '{"queue":"q.prueba","exchange":"ex.prueba","routingKey":"prueba.key"}'
```

### Eliminar binding

```http
DELETE /api/rabbit/bindings
```

Body:

```json
{
  "queue": "q.prueba",
  "exchange": "ex.prueba",
  "routingKey": "prueba.key"
}
```

Ejemplo:

```bash
curl -X DELETE http://localhost:8084/api/rabbit/bindings \
  -H "Content-Type: application/json" \
  -d '{"queue":"q.prueba","exchange":"ex.prueba","routingKey":"prueba.key"}'
```

## Variables de entorno

```text
RABBITMQ_HOST
RABBITMQ_PORT
RABBITMQ_USERNAME
RABBITMQ_PASSWORD
```

Ejemplo:

```properties
RABBITMQ_HOST=rabbitmq-1
RABBITMQ_PORT=5672
RABBITMQ_USERNAME=usuario
RABBITMQ_PASSWORD=contraseña
```

No se deben almacenar credenciales reales dentro del repositorio.

## Ejecución local

Compilar:

```bash
./mvnw clean package -DskipTests
```

Ejecutar:

```bash
./mvnw spring-boot:run
```

## Docker

Construir la imagen:

```bash
docker build -t digitalfix-rabbit-admin:latest .
```

Ejecutar:

```bash
docker run -d \
  --name digitalfix-rabbit-admin \
  -p 8084:8084 \
  -e RABBITMQ_HOST=rabbitmq-1 \
  -e RABBITMQ_PORT=5672 \
  -e RABBITMQ_USERNAME=usuario \
  -e RABBITMQ_PASSWORD=contraseña \
  digitalfix-rabbit-admin:latest
```

## Arquitectura

```text
Cliente / Administrador
        |
        v
Rabbit Admin REST API
        |
        v
RabbitAdminService
        |
        v
Spring AmqpAdmin
        |
        v
RabbitMQ Cluster
```

## Pruebas realizadas

Se verificó correctamente:

- Creación de colas.
- Eliminación de colas.
- Creación de exchanges.
- Eliminación de exchanges.
- Creación de bindings.
- Eliminación de bindings.
- Administración sobre el clúster RabbitMQ desplegado en AWS.

Ejemplo utilizado durante las pruebas:

```text
Queue:
q.prueba.aws

Exchange:
ex.prueba.aws

Routing Key:
prueba.aws
```

Luego de validar su creación y funcionamiento, los elementos de prueba fueron eliminados para dejar limpio el entorno.

## Integración DigitalFix

Rabbit Admin permite administrar la infraestructura de mensajería utilizada por los microservicios de DigitalFix.

El entorno productivo contempla exchanges como:

```text
cmd.direct
cmd.topic
cmd.dead.dlx
```

y colas asociadas a Email, Dispatch y Report, junto con sus respectivas Dead Letter Queues.
