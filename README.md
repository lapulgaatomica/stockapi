## About Project

The project is written in Java using Spring Boot, and it exposes 5 endpoints

**GET** `/api/stocks` **get the List of Stocks** 

**GET** `/api/stocks/1` **gets a single Stock from the list by its ID**

**PUT** `/api/stocks/1` **update the current_price/name of a single Stock**

**DELETE** `/api/stocks/1` **delete a single Stock by its ID**

**POST** `/api/stocks` **create a new Stock**

### Running the app on your local machine

At project root, use `./mvnw spring-boot:run` to start the application. The app binds to port `8080` by default.
To change port edit `resources/application.properties`.

To test the endpoints, you can use the version 2.1 postman collection I provided in the root folder of the project named
`postman_collection_for_stocks_api.json` or navigate to`http://localhost:8080/swagger-ui/`
to use the pleasing and intuitive swagger ui interface I provided to interact with the endpoints.

For persistence, this project uses H2, an in-memory database.

### Testing

**Automated Tests** Unit tests are provided for `StockService.java` and `StockController.java`. To run all these tests,
execute the following command at the project root: `./mvnw test`.

### Tech Stack

- JDK 11
- Spring Boot
- H2 (InMemory database)
