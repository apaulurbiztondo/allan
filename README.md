# This repository contains a collection of code files that enable basic CRUD operations for managing "User" data. 

Informations:

  - Created "User" entity that can be stored in a database and provides boilerplate code using Lombok annotations.
  - Created "UserRepository" interface for managing "User" entities and it provides basic CRUD and common database operations.
  - Created "ResourceNotFoundException" that extends the base "Exception" class. The class is annotated with "@ResponseStatus" to indicate that when an instance of this exception is thrown, it should result in an HTTP response with the "404 Not Found" status.
  - Created "UserService" that provides methods for performing CRUD operations on "User" entities. The class is annotated with "@Service" to indicate that it is a service component that can be automatically detected by Spring framework at runtime.
  - Created "UserController" a REST API controller class that handles requests related to "User" entities and it uses a "UserService" instance to perform these operations and Spring annotations for HTTP methods, request mappings, and exception handling. It also logs messages using the SLF4J logger.

JUnit Test:

  - Created "UserControllerTests" class for "UserController" that uses the Mockito framework to test various controller methods. The tests include getting all users, getting a user by ID, creating a user, updating a user, and deleting a user. The test methods use Mockito annotations to mock the UserService class and verify expected responses and behavior.

Tools used:

  - Java 17
  - Spring Framework
  - Maven
  - Git
  - Eclipse
  - SonarLint extension 

In this project, it uses package-by-feature structure:

- com.example.allan
    - exceptions
        ResourceNotFoundException
    - users
        User
        UserController
        UserRepository
        UserService
