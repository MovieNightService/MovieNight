# MovieNight

### Prerequisites:
  1. jdk 14 or later
    https://www.oracle.com/java/technologies/javase/jdk14-archive-downloads.html
  2. git 2.17.0 or later
    https://git-scm.com/downloads
  3. PostgreSQL 11 or later
    https://www.postgresql.org/download/windows/
  4. Gradle 6.3 or higher
    https://gradle.org/releases/
  4. Release `localhost:8761` for discovery-server module
  5. Release `localhost:8081` for mn-app module
  6. Release `localhost:8080` for mn-chat module
  
### Template Getting Started Guide:
  1. Create folders `ticket, pdf, qr` in resources of mn-app module:
  ~~~
    resources 
        db.changelog
        static
        templates
        ticket
            pdf
            qr
  ~~~
  2. Create folder in your local disc `uploaded-docs`
  3. Configure _database_ with credentials **user: postgres / password: root** and in public schema open _console_ and run script:
  `create database movie_night;`
  
### Getting started guide:
  1. Create jar `gradle clean build`
  2. Run application with **green rectangle** help (on the top and right of IntellijIdea IDE)