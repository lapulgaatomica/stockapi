## About

The project is written in Java using Spring Boot, and it exposes 5 endpoints

```
GET /api/stocks which get the List of Stocks
```

### Deploying Locally

At project root, use `./mvnw spring-boot:run` to start the application. The app binds to port `8080` by default.
To change port edit `resources/application.properties`.

For persistence, this project uses H2, an in-memory database.  To inspect the DB, open the H2 web console at `localhost:8080/h2-console`. More details on testing is provided below.

### Testing

**Automated Tests** Unit tests are provided for `StockService.java` and `StockController.jsva`. To run all these tests, execute the
following command at the project root: `./mvnw test`.

### Tech Stack

- JDK 11
- Spring Boot
- H2 (Embedded database)
