# 2025: Trisha's Quicken Cloud Project

## Run the Project

1. **Start the Spring Boot server**
- Run the app from IntelliJ (green start on `DemoApplication.java`)
2. **DataBase Setup**
On startup, Spring executes quicken_project.sql to:
- create the tables
- insert sample account + transaction data
3. **Server Location**
Base URL: http://localhost:8080

## 1) List Accounts
### GET /api/accounts → Returns all accounts

**Browser**
- http://localhost:8080/api/accounts

**Terminal**
- Basic Run Command: curl http://localhost:8080/api/accounts
- Nice Fomat Command: curl http://localhost:8080/api/accounts | python3 -m json.tool

## Work Log

### December 8th, 2025: Setup + Debugging Log
1. JDK / Gradle configuration errors
Error: IntelliJ - "Found invalid Gradle JVM configuration – Please fix JAVA_HOME environment variable and java -version reported Java 1.8."
Cause: Only Java 8/11/17 were installed and JAVA_HOME was pointing to the wrong JDK through my .zshrc
Fix:
Installed OpenJDK 21 with Homebrew: brew install openjdk@21.
Cleaned and rewrote ~/.zshrc 
Set Project SDK and Gradle JVM in IntelliJ to “Homebrew OpenJDK 21.0.9”.
After that, the project built and DemoApplication.main() ran successfully.

2. Git / GitHub permission error (403)
Error: git push -u origin main → remote: Permission to trishajain12/quicken_project.git denied to tjain010.
Cause: Keychain Access had my old school GitHub account, so pushes were authenticated as the wrong user.
Fix:
Opened Keychain Access, searched github.com, and deleted existing GitHub password entries.
Created a new public GitHub repo quicken_project under trishajain12.
When prompted, logged in as trishajain12 using a Personal Access Token with repo scope.
Push succeeded and the repo is now public.

### December 10-11th, 2025: Learning Spring Basics
- Watched a Spring Boot tutorial series and other videos to understand the Spring mental model (Controller → Service → Repository) and dependency injection/IoC, etc.
- Resource: https://www.youtube.com/watch?v=U6-JJfslKLM&list=PLfu_Bpi_zcDNbpxlrBl3Sng5F9qU7Cz5D&index=4

**Project Structure Overview**
- controller/ — receives requests and returns responses
- service/ — holds business logic (calls repository)
- repository/ — talks to the database 
- model/ — Java objects returned as JSON 
- resources/ — configuration and SQL (application.properties, quicken_project.sql)
  
**Work Flow Process Descriptive** 
1. Client send an HTTP request
2. Spring finds the controller method that matches
3. The controller method calls the service
4. The service calls the repository
5. The repository uses JdbcTemplate to run SQL
6. SQL returns rows and then converts each row -> an Account object
7. The controller returns list of accounts
8. Spring automatically converts list of accounts to JSON

**What @SpringBootApplication does**
- Main startup swich for our app
- Spring application context (manages the beans)
- Scans repository for components
- Loose Couple and Automatic wiring with DI (no new in service or repository)

### December 12th, 2025: First API Test
Goal: Make sure API endpoint makes sense to me, works end to end before I use the real database
- Built a simple test endpoint in the controller
- Harded data in the repository to make sure the path is correct, the controller is being called, and JSON is being returned.
- Code snipet:
```
public List<String> findAllAccounts() {
  return List.of("Account_1", "Account_2", "Account_3");
}
```
- This helped me isolate probelms early and understand the flow clearly, see the order with the print statements etc. 

### December 13th, 2025: List Accounts GET (/api/accounts) - Switched From Hardcoded Data to Real Database
Goal: Replace the fake data and connect it with the real data from the accounts table. 

**Work Flow Process** 
1. Client send an HTTP request
  - Browser or curl → GET http://localhost:8080/api/accounts
2. Spring finds the controller method that matches
  - @GetMapping("/api/accounts")
3. The controller method calls the service
  - accountService.listAccounts()
4. The service calls the repository
  - accountRepository.findAllAccounts()
5. The repository uses JdbcTemplate to run SQL
  - SELECT * FROM accounts
6. SQL returns rows and then converts each row -> an Account object
  - (rs, rowNum) -> new Account(rs.getLong("id"), rs.getString("name"), rs.getString("description"))
7. The controller returns list of accounts
  - List of Java objects is returned 
8. Spring automatically converts list of accounts to JSON

**Code Snipets**

Controller 
```
@GetMapping("/api/accounts")
public List<Account> getAllAccounts() {
  return accountService.listAccounts();
}
```
Repository 
```
String sql = "SELECT * FROM accounts";

return jdbcTemplate.query(sql, (rs, rowNum) ->
  new Account(
    rs.getLong("id"),
    rs.getString("name"),
    rs.getString("description")
  )
);
```
Main changes included "List Accounts": This created a Account model with all the things needed like id, name, decription with constructors and getters. We used constructory dependency injects to string can inject clean dependencies.
```
public AccountRepository(JdbcTemplate jdbcTemplate) {
  this.jdbcTemplate = jdbcTemplate;
}
```

**Overall Process**
- Setup a Spring Boot backend with clean layers from Controller to Service to Repository.
- Implemented first working API returning a JSON file
- Connected the app to H2 database and use JDBC template instead of hardcoding the data.
- Challenges/Learnings:
  - Dependency Injection matters: JdbcTemplate must be injected through the constructor; otherwise it can be null and cause crashes.
  - IntelliJ Community does not support the HTTP Client “Run request”
  - curl is the simplest, reliable way to test endpoints locally 
  - Good way of DB → model mapping: JdbcTemplate.query(...) + a row-mapper lambda is a clean way to make rows into Java objects that Spring can make into JSON.
  
**Testing**
Tested using both the browser and terminal as well. 
