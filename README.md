<h2> Spring Boot </h2>

 1 - # 📇Sounds like you’re building a Spring Boot 3 + Java 21 CRUD project using H2 in-memory DB, Spring Data JPA, and Jakarta Validation.


 2 - # 📇 Contact Manager – Spring Boot (Java 21)

        A simple **Spring Boot CRUD application** to manage contacts with the ability to **upload and display profile images**.  
        Built with **Java 21**, **Spring Data JPA**, **H2 Database**, and **Spring Web**.

    ## 🚀 Features

            ✅ Create, Read, Update, Delete (CRUD) operations for contacts  
            ✅ Upload a profile photo for each contact  
            ✅ Serve uploaded images statically via `/uploads/...`  
            ✅ Paginated and sorted contact list (by name)  
            ✅ H2 in-memory database for easy development  
            ✅ Multipart file upload support (up to 100 MB)  




 3 - # 📇 Employee REST - Spring Boot (Java 21) Jpa:
            HttpResponse, ExceptionHandlers, Xml/Json, Validation , H2
            - Consumes: `application/json`, `application/xml`
            - Produces: `application/json`, `application/xml`

        ├─ pom.xml
        ├─ HELP.md
        ├─ src
        │  ├─ main
        │  │  ├─ java/com/ettarak
        │  │  │  ├─ Application.java                      # Spring Boot entry point
        │  │  │  ├─ entities/Employee.java                # JPA entity with validation
        │  │  │  ├─ repositories/JpaEmployeeRepository.java
        │  │  │  ├─ resources/EmployeeResource.java       # REST controller (CRUD)
        │  │  │  ├─ services/EmployeeService.java         # Service interface
        │  │  │  ├─ services/imp/EmployeeServiceImp.java  # Service implementation
        │  │  │  ├─ exceptions/...                        # Global exception handler, domain exceptions
        │  │  │  └─ utils/DateTimeFormatter.java          # Timestamp formatting utility
        │  │  └─ resources/application.properties         # App config (port, H2, etc.)
        │  └─ test/java/com/ettarak/ApplicationTests.java # JUnit 5 smoke test
        └─ mvnw / mvnw.cmd                                # Maven Wrapper