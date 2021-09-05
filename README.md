# Products Devices Service

---
##Notes

##### I put assumptions because I had some questions related to the requirements

- There are 2 different status for the device[**ready**] and the SIM [**status**]
- Regarding the first required endpoint will take a pramter to filter devices based on the SIM status - the user will pass it during the api call
- The second requirement need to update devices status to be ready for sale or not [SO I created a ready flag for just enable or disable the device]
***

### Setup

- #### With Docker
- `cd` to the project directory
- build maven project and run tests `mvn clean install` to build the project, run tests  and package the .jar
- build docker image locally `docker build -t products .`
- run docker compose file `docker-compose up -d`
- now can access the API
- #### With Maven
- Must have a mysql database which run on port `3306` or you can change the port from `application.yaml`
- Open the terminal 
- `cd` to the project directory
- Then start your app which will run on port `9090` with command `mvn spring-boot:run`

### Test Coverage
##### How get the test coverage report using jacoco

- Build the project  `mvn clean install`
- open the html page from `/target/site/jacoco/index.html`

### Swagger

- Can access swagger doc from http://localhost:9090/swagger-ui.html
 
---
## predefined data
- You Will find data generated in the database table [device - sim]  you can find the insertion script in `resources/liquibase/data/add-devices-data.sql`. So can use the APIs with it's data
