
## Requisitos

- Java 17
- Maven
- PostgreSQL

## Configuración

1. Clona el repositorio.
2. Configura la base de datos PostgreSQL y actualiza las credenciales en el archivo `src/main/resources/application.properties`.

```properties
spring.datasource.url=jdbc:postgresql://localhost/postgres
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver
hibernate.dialect=org.hibernate.dialect.HSQLDialect

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true
spring.jpa.format-sql = true