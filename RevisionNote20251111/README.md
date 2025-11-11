# RevisionNote20251111

A simple Spring Boot REST API to manage revision notes. It exposes CRUD endpoints for a `Note` entity and persists data to an in-memory H2 database at runtime. The application seeds a couple of sample notes at startup.


## Stack
- Language: Java 21
- Framework: Spring Boot 3.5.7
  - Starters: Web, Data JPA, Validation, Actuator
- Database: H2 (in-memory)
- Build/Package manager: Maven (with Maven Wrapper `mvnw`/`mvnw.cmd`)
- Lombok for boilerplate reduction


## Requirements
- JDK 21 installed and on PATH
- No need to install Maven explicitly; the Maven Wrapper included in the repo can be used

Optional:
- A REST client (curl, Postman, HTTPie) for testing endpoints
- Browser access to H2 console


## Getting Started

### Clone
```bash
# PowerShell / Bash
git clone <this-repo-url>
cd RevisionNote20251111
```

### Build
- Windows (PowerShell):
```powershell
./mvnw.cmd clean package
```
- Linux/macOS:
```bash
./mvnw clean package
```
This produces an executable jar in `target/`.

### Run (dev)
- Using Spring Boot plugin:
  - Windows:
    ```powershell
    ./mvnw.cmd spring-boot:run
    ```
  - Linux/macOS:
    ```bash
    ./mvnw spring-boot:run
    ```
- Or run the packaged jar:
  ```bash
  java -jar target/RevisionNote20251111-0.0.1-SNAPSHOT.jar
  ```

Application starts on port `8080` by default.

### Stop
- Press `Ctrl+C` in the terminal running the app.


## Entry Point
- Main class: `com.ettarak.Application`
  - Seeds two sample notes on startup via a `CommandLineRunner` bean.


## API Overview
Base path: `http://localhost:8080`

All endpoints are under the `/notes` resource.

- GET `/notes`
  - Returns a list of notes wrapped in a response envelope.
- GET `/notes/{id}`
  - Returns a single note by id. If not found, a custom error is returned.
- POST `/notes`
  - Creates a note. Example request body:
    ```json
    {
      "title": "Title 3",
      "content": "Some content",
      "level": "HIGH"
    }
    ```
  - Returns `201 Created` with `Location: /notes/{id}`.
- PUT `/notes`
  - Updates an existing note. Provide `id` with the updated fields:
    ```json
    {
      "id": 1,
      "title": "Updated title",
      "content": "Updated content",
      "level": "LOW"
    }
    ```
  - Returns `202 Accepted`.
- DELETE `/notes/{id}`
  - Deletes a note by id.

Note: The controller uses a wrapper model `HttpResponse<T>` for responses; exact envelope fields can be reviewed in `src/main/java/com/ettarak/models/HttpResponse.java`.

### Sample curl
```bash
# List notes
curl http://localhost:8080/notes

# Get one
curl http://localhost:8080/notes/1

# Create
curl -X POST http://localhost:8080/notes \
  -H "Content-Type: application/json" \
  -d '{"title":"Title 3","content":"Body","level":"MEDIUM"}'

# Update
curl -X PUT http://localhost:8080/notes \
  -H "Content-Type: application/json" \
  -d '{"id":1,"title":"New","content":"New body","level":"LOW"}'

# Delete
curl -X DELETE http://localhost:8080/notes/1
```


## Configuration
Configuration file: `src/main/resources/application.properties`

Defaults:
```
spring.application.name=RevisionNote20251111
server.port=8080
spring.datasource.url=jdbc:h2:mem:db
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
```

- H2 console: `http://localhost:8080/h2-console` (use JDBC URL `jdbc:h2:mem:db`, username `sa`, empty password)

### Overriding via environment variables
Spring Boot supports overriding properties using environment variables. Common examples:
- `SERVER_PORT=9090`
- `SPRING_DATASOURCE_URL=jdbc:h2:mem:anotherDb`
- `SPRING_DATASOURCE_USERNAME=sa`
- `SPRING_DATASOURCE_PASSWORD=<your_password>`

On Windows PowerShell:
```powershell
$env:SERVER_PORT="9090"; ./mvnw.cmd spring-boot:run
```

On Linux/macOS:
```bash
SERVER_PORT=9090 ./mvnw spring-boot:run
```


## Scripts and Useful Maven commands
- Run app: `./mvnw spring-boot:run` (Windows: `./mvnw.cmd spring-boot:run`)
- Build jar: `./mvnw clean package`
- Run tests: `./mvnw test`
- Format/Analyze (if configured via plugins): `./mvnw verify`  // TODO: add formatting/static analysis plugins if desired


## Tests
- Test sources: `src/test/java`
- Run all tests:
  - Windows: `./mvnw.cmd test`
  - Linux/macOS: `./mvnw test`


## Project Structure
```
RevisionNote20251111/
├─ pom.xml
├─ mvnw, mvnw.cmd
├─ src/
│  ├─ main/
│  │  ├─ java/com/ettarak/
│  │  │  ├─ Application.java
│  │  │  ├─ controllers/NoteController.java
│  │  │  ├─ dtos/NoteDto.java
│  │  │  ├─ entities/Note.java
│  │  │  ├─ enums/Level.java
│  │  │  ├─ exceptions/(HandleException, NoteNotFoundException).java
│  │  │  ├─ models/HttpResponse.java
│  │  │  ├─ repositories/NoteRepository.java
│  │  │  ├─ services/(NoteService, implimentation/NoteServiceImp).java
│  │  │  └─ utils/DateFormatter.java
│  │  └─ resources/application.properties
│  └─ test/java/com/ettarak/RevisionNote20251111ApplicationTests.java
└─ target/ (build output)
```


## Health/Actuator
The project includes `spring-boot-starter-actuator`. Default exposure is limited.
- TODO: Document exposed endpoints and any custom actuator config if/when added.


## Error Handling
Custom exceptions and a handler are present:
- `NoteNotFoundException`
- `HandleException` (global exception handling)

- TODO: Document the error payload structure from `HandleException` and `HttpResponse`.


## License
No license file detected.
- TODO: Add a LICENSE file and update this section accordingly.


## Contributing
- TODO: Add contribution guidelines if contributions are expected.


## Additional Notes
- The application seeds two notes at startup via `Application.init(CommandLineRunner)`.
- Lombok is used; ensure annotation processing is enabled in your IDE.
- H2 is in-memory; data is lost on application restart. For persistence across runs, switch to a file-based or external database and update `spring.datasource.*` properties.
