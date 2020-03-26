# Inmate-Locator
City of Philadelphia Take Home Project

To Run:

Clone repo and move to parent directory where mvnw file is located.
Execute Command - ./mvnw clean install
Should run Junit test cases and generate JAR file.
Execute Command - java -jar InmateLocatorWeb/target/InmateLocatorWeb-0.0.1-SNAPSHOT.jar

Server should start successfully and begin running on localhost:8080.

Simplifications - 
Turned DOB input type into a String so it's easier to test for me. Otherwise would keep it as a Java Date object.

Assumptions-
JWT will be sent in the Authorization Header with prefix of "Bearer ".

Swagger URL - http://localhost:8080/swagger-ui.html

Inmate List - 

[
    {
        "pID": 123456,
        "firstName": "Michael",
        "lastName": "Scott",
        "dob": "05-01-1970",
        "location": "City Hall"
    },
    {
        "pID": 123458,
        "firstName": "Dwight",
        "lastName": "Shrute",
        "dob": "05-01-1970",
        "location": "City Hall"
    },
    {
        "pID": 123459,
        "firstName": "Jim",
        "lastName": "Halpert",
        "dob": "05-01-1970",
        "location": "City Hall"
    },
    {
        "pID": 123460,
        "firstName": "Pam",
        "lastName": "Beasley",
        "dob": "05-01-1970",
        "location": "City Hall"
    },
    {
        "pID": 123457,
        "firstName": "Toby",
        "lastName": "Flenderson",
        "dob": "05-01-1970",
        "location": "Alcatraz"
    },
    {
        "pID": 123461,
        "firstName": "Golden",
        "lastName": "Face",
        "dob": "05-01-1970",
        "location": "Alcatraz"
    }
]