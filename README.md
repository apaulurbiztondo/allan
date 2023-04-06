# This repository contains a collection of code files that enable basic CRUD operations for managing "user" data. 

Features:
  - Create "User" entity that can be stored in a database and provides boilerplate code using Lombok annotations.
  - Create Spring Data JPA repository interface for managing "User" entities, which extends the "JpaRepository" interface and is annotated with "@Repository". It provides basic CRUD and common database operations for "User" entities
  - Create "UserService" that provides methods for performing CRUD operations on "User" entities. The class is annotated with "@Service" to indicate that it is a service component that can be automatically detected by Spring framework at runtime. The class has a constructor that takes a "UserRepository" instance as a parameter, which is injected using the "@Autowired" annotation. The class has methods for getting all users, getting a user by ID, creating a new user, updating an existing user, and deleting a user. The methods make use of the "UserRepository" instance to perform the respective database operations. The class also has a private method for creating a "ResourceNotFoundException" instance.
