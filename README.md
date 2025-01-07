# Tool Rental Application
A Spring Boot application for managing tool rentals. 
The app allows users to make checkout requests to rent tools with support for caching, database interactions, and business logic for calculating rental charges.

# Technologies Used
* Java
* Spring Boot
* H2 (in-memory database) was used in order to demonstrates how this application could be wired up to a much more extensive selection of tools from a real database;
* Spring Data JPA for accessing the database
* Spring Cache to improve database performance
* Spring Validation in order to automatically validate incoming checkout requests and conveniently generate error messages
* OpenAPI/Swagger to simplify manual endpoint interactions
* Lombok to reduce boilerplate code clutter

# Testing Technologies/Strategies
* JUnit was used as a basic unit testing framework.
* SpringBootTests were used wherever it was necessary to spin up a larger slice of the application to test more than a single unit at a time.
* Mockito framework was used wherever it was necessary to mock dependencies.
* In most places (where applicable), parameterized testing was used as a convenient way to add additional testing when necessary
* End to End application testing used data-driven tests with test cases stored as JSON entries. This allows the end to end tests to be data-driven for easily adding more tests.
