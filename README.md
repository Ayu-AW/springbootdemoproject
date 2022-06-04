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

Only students with the right credentials can log in. Otherwise, a message is displayed stating: “Wrong Credentials”. Valid students are able to see the courses they are registered for. Valid students are able to register for any course in the system as long as they are not already registered.


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

##### StudentI:
| Abstract method         | Return type    | Parameters                    | Description                                                                                                     | 
|-------------------------|----------------|-------------------------------|-----------------------------------------------------------------------------------------------------------------|
| getStudentByEmail       | Student        | String email                  | return student if exists, also handle commit,rollback, and exceptions                                           |
| validateStudent         | boolean        | String email, String password | match email and password to database to gain access to courses, also handle commit,rollback, and exceptions     |
| registerStudentToCourse | void           | String email, int courseId    | register a course to a student (collection to prevent duplication), also handle commit,rollback, and exceptions |
| getStudentCourses       | List\<Course>  | String email                  | get all the student courses list (use native query), also handle commit,rollback, and exceptions                | 

---
#### Requirement 3 - Service layer:
implement interfaces:
- StudentService
- CourseService
---