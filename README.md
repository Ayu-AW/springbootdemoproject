# Springboot Application Example
## School Management System

### Technologies
- Springboot
- Maven
- Java corretto 11
- Spring JPA
- MariaDB
- Spring Security
- Thymeleaf


### Business Requirement:

Your task is to create a basic School Management System where students can register for courses, and view the course assigned to them.

### Work-Flow:

Website with links to each service
- homepage
- create/modify student
- create/modify course
- view students
- find student by email
- view courses

starter dependencies:
- Spring web
- Spring Boot DevTools
- Lombok
- Thymeleaf
- Mariadb Driver 
- Spring Data JPA
- Validation 


#### Requirement 1 - Models:
Models requires:
- no args constructor
- all args constructor
- required args constructor
- setters and getter
- toString (exclude collections to avoid infinite loops)
- override equals and hashcode methods (don't use lombok here)
- helper methods
##### Student (`@Table(name = "student")`):
| Field    | Datatype      | Description                 | Database attributes `@Column()`                                                                                                                                                                       | 
|----------|---------------|-----------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| email    | String        | Student’s unique identifier | Primary key, 50 character limit, name `email`                                                                                                                                                         |
| name     | String        | Student’s name              | 50 character limit, not null, name `name`                                                                                                                                                             |
| password | String        | Student’s password          | 50 character limit not null, name `password`                                                                                                                                                          |
| courses  | List\<Course> | Student courses list        | Join table strategy name `student_courses` , name of student primary key column `student_email` and inverse primary key (courses) column `courses_id` , fetch type `eager`, cascade all except remove |

##### Course (`@Table(name = "course")`):

| Field      | Datatype       | Description              | Database attributes `@Column()`                                   | 
|------------|----------------|--------------------------|-------------------------------------------------------------------|
| id         | int            | Course unique identifier | Primary key                                                       |
| name       | String         | Course name              | 50 character limit, not null                                      |
| instructor | String         | Instructor name          | 50 character limit not null                                       |
| students   | List\<Student> | Course learners list     | fetch type `eager`, cascade all except remove, mappedBy `courses` | 

---
#### Requirement 2 - Data Access Object  (dao) interfaces:

##### StudentRepository:
##### CourseRepository:
| Abstract method         | Return type   | Parameters                    | Description                                                                                                     | 
|-------------------------|---------------|-------------------------------|-----------------------------------------------------------------------------------------------------------------|
| findStudentCourses      | List\<Course> | String email                  | return student courses                                                                                          |

---


#### Requirement 3 - Service layer:
implement interfaces:
- StudentService: implement StudentRepository required methods.
- CourseService: implement CourseRepository required methods.

---

#### Requirement 4 - Controllers
##### HomeController
  - general endpoints
##### StudentController
  - mapping for services
##### CourseController
  - mapping for services

---

#### Requirement 5 - View using Thymeleaf

- build a template using thymeleaf fragments

#### Requirement 6 - Spring Security
- implement spring security
- hide/show services using thymeleaf

#### Requirement 7 - Testing
- use mock database H2 to test data
- AssertJ tests