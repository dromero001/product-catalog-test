Product catalog test

Run the application (requires Java 17/Maven 3.6+):
clone repository
mvn clean package
mvn spring-boot:run

Endpoint: http://localhost:8080/api/products

API documentation: http://localhost:8080/swagger-ui/index.html

Key Architectural Decisions:

Use of H2 Database:
Chosen to simplify testing for this proof-of-concept microservice, even though it’s presented as a production-ready solution.

Hardcoded Discount Rules:
Discounts were hardcoded for simplicity. In a production environment, a database mapping (category-to-discount and special-condition-to-discount) would be more appropriate.

Discount Strategies:
Discounts are implemented as strategies via a strategy applier, making it easy to extend with new discount rules without modifying core logic.

Abstraction via Interfaces:
Interfaces are used to decouple implementations, ensuring flexibility and maintainability.

Comprehensive Testing:
Test classes cover all logic-containing components to:

Validate correctness.

Future-proof against regressions (tests must pass after code changes).
