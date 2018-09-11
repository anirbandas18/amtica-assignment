# System requirements:
* Working __internet connection__
* __MySQL 5.7__ community server
* __Oracle JDK 10__ 
* Apache __Maven 3.5.2__ 
* __Redis 4.0.10__ server
* OS: Windows, Linux, OS X
* __200 MB disk space__

--- 

## Technology stack:
* __Presentation layer__: HTML5, Bootstrap, AngularJS
* __Business layer__: Spring Boot, JPA, Hibernate, JMS, Spring MVC, Tomcat
* __Persistence layer__: MySQL, Redis

---

## Assumptions: 
* All required softwares are installed on a single machine. 
* Installation instructions are followed in the order of their writing for smooth installation
* Cookies are enabled on the browser
* No application is running on port 6002 of the machine

---

# Installation instructions:
* Make sure JDK is installed and __JAVA_HOME__ environment variable is set pointing to JDK
* Make sure __M2_HOME__ environment variable is set and pointing to maven binaries
* Start __mysql server__ and make sure it is running on port __3306__
* Start __redis server__ and make sure it is running on port __6379__
* __Checkout the project__ from [GitHub] (https://github.com/anirbandas18/amtica-assignment.git) onto a path on the system using any __git client__
* Open the __command prompt/terminal__ of your OS and navigate to the root folder of the project where it is checked out
* Execute the script ```app_database_init.sql``` under the __scripts/database__ folder of project root directory in mysql server
* Execute the command ```mvn clean package -e``` to build the project
* Execute the command ```cd target``` to move to the directory containing the application binary
* Execute the command ```java -jar amtica-app.jar --spring.profiles.active=test``` to start the web application
* Open a browser and navigate to ```localhost:6002/amtica/`` to verify the installation. You should see a page with registration and login buttons

---

# Author:
> Anirban Das (anirbandas18@live.com)
