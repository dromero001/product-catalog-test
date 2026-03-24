# Product Catalog Test

> Solution for managing the company's product catalog

A Spring Boot microservice that provides a REST API for managing and retrieving product information with built-in discount strategies. This project demonstrates best practices in microservice architecture, including clean code principles, design patterns, and comprehensive testing.

## 🚀 Features

- **Product Management API**: RESTful endpoints for managing product catalogs
- **Discount Strategies**: Flexible, extensible discount system based on categories and special conditions
- **Interactive API Documentation**: Swagger UI for easy API exploration and testing
- **Comprehensive Test Coverage**: Unit tests covering all business logic
- **Clean Architecture**: Decoupled components using interfaces and design patterns

## 🛠 Tech Stack

- **Language**: Kotlin
- **Framework**: Spring Boot
- **Database**: H2 (in-memory, for testing and PoC)
- **Build Tool**: Maven 3.6+
- **Java Version**: 17+
- **API Documentation**: Springdoc OpenAPI (Swagger)

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

- **Java 17** or higher
- **Maven 3.6** or higher

To verify your installations:

```bash
java -version
mvn -version
```

## 🔧 Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/dromero001/product-catalog-test.git
cd product-catalog-test
```

### 2. Build the Project

```bash
mvn clean package
```

This command:
- Cleans previous builds
- Compiles the source code
- Runs all tests
- Packages the application as a JAR file

### 3. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

Alternatively, run the packaged JAR directly:

```bash
java -jar target/product-catalog-test-*.jar
```

## 📡 API Endpoints

### Base URL
```
http://localhost:8080/api/products
```

### Available Endpoints

All API endpoints are documented and can be explored using Swagger UI.

**Interactive Documentation**: `http://localhost:8080/swagger-ui/index.html`

## 📚 Project Structure

```
product-catalog-test/
├── .mvn/wrapper/          # Maven wrapper configuration
├── src/
│   ├── main/              # Main application code
│   │   ├── kotlin/        # Kotlin source files
│   │   └── resources/     # Configuration and static resources
│   └── test/              # Test suites
│       ├── kotlin/        # Kotlin test files
│       └── resources/     # Test configuration
├── pom.xml                # Maven project configuration
├── mvnw & mvnw.cmd        # Maven wrapper scripts
└── README.md              # This file
```

## 🏗 Architecture Decisions

### 1. **In-Memory H2 Database**
The H2 database was chosen for this proof-of-concept microservice to simplify testing and rapid development. This is suitable for development and testing scenarios but would be replaced with a production-grade database (PostgreSQL, MySQL, etc.) in production environments.

### 2. **Discount Strategies Pattern**
Discounts are implemented using the **Strategy Pattern**, allowing:
- Easy addition of new discount rules without modifying existing code
- Clear separation of discount logic from core product management
- Flexible combination of multiple discount strategies

```kotlin
// Example: Extending with a new discount strategy
class NewDiscountStrategy : DiscountStrategy {
    override fun apply(price: BigDecimal): BigDecimal {
        // Implementation
    }
}
```

### 3. **Hardcoded Discount Rules**
For this PoC, discount mappings (category-to-discount and special-condition-to-discount) are hardcoded. In a production system, these would be:
- Stored in a database
- Configurable via admin interface
- Reloadable without restarting the service

### 4. **Interface-Based Abstraction**
Interfaces are used throughout the codebase to:
- Decouple implementations from contracts
- Improve testability through mocking
- Enable flexible extension points
- Reduce tight coupling between components

### 5. **Comprehensive Testing**
All logic-containing components include unit tests that:
- ✅ Validate correct behavior
- ✅ Prevent regressions
- ✅ Serve as documentation
- ✅ Enable confident refactoring

## 🧪 Running Tests

Run all tests:

```bash
mvn test
```

Run tests with coverage report:

```bash
mvn test jacoco:report
```

Run a specific test class:

```bash
mvn test -Dtest=ProductServiceTest
```

## 📝 API Usage Examples

### Get All Products
```bash
curl -X GET http://localhost:8080/api/products
```

### Get Product by ID
```bash
curl -X GET http://localhost:8080/api/products/{id}
```

### Create Product
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Product Name",
    "price": 99.99,
    "category": "ELECTRONICS"
  }'
```

For complete API documentation with examples, visit the Swagger UI at:
```
http://localhost:8080/swagger-ui/index.html
```

## 🔄 Key Components

### Discount Applier
Centralized component responsible for applying discount strategies to products. Handles:
- Strategy selection based on product category
- Special condition evaluation
- Price calculation with discounts

### Product Service
Business logic layer that:
- Manages product data
- Applies discounts using the applier
- Validates product information

### REST Controllers
HTTP endpoints that:
- Expose product management functionality
- Handle request/response serialization
- Manage HTTP status codes and error handling

## 🚦 Development Workflow

1. **Create a feature branch**: `git checkout -b feature/your-feature`
2. **Make your changes**: Update code and add tests
3. **Run tests**: `mvn test` (ensure all pass)
4. **Build the project**: `mvn clean package`
5. **Commit and push**: `git commit -m "message"` and `git push`
6. **Create a Pull Request**: Describe your changes and rationale

## 🔐 Future Improvements

- [ ] Replace H2 with PostgreSQL/MySQL for production
- [ ] Externalize discount rules to a configuration database
- [ ] Add Redis caching for frequently accessed products
- [ ] Implement audit logging for product changes
- [ ] Add user authentication and authorization
- [ ] Create admin dashboard for discount management
- [ ] Implement pagination for product listings
- [ ] Add product search and filtering capabilities

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👤 Author

**Daniel Romero**  
GitHub: [@dromero001](https://github.com/dromero001)

## 🤝 Contributing

Contributions are welcome! Please feel free to submit issues or pull requests.

## 📧 Questions?

For questions or issues, please open an issue on the GitHub repository or contact the project maintainer.

---

**Happy coding!** 🎉
