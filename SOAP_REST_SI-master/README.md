# SOAP_REST_SI

## Authors' details

- Mads Andersen - 1. sem
- Malene Hansen - 1. sem
- Benjamin Højgaard - 1. sem

## Development  and  implementation  
- Language: Java ver. 11
- Local in-memory H2 database
- 1 REST service (internal)
- 1 SOAP service (external)
- 1 Client program consuming both services

## Business  and technical features
- Program consists of a client that can call a REST service to perform CRUD operations.
- Before a call to the REST is carried out, a call to a SOAP service is made to verify that the supplied ISBN number is a valid ISBN13 number.
- The REST service is developed by the group.
- The SOAP service is an external public webservice called from the Client application.

## Installation instructions 
1. Clone or download the zip file off of github.
2. Open in Intellij IDE.
3. Import Maven changes when prompted.
4. First run the Spring Boot DemoApplication.
5. Then run the Client application.
6. Type desired method with optional parameters as needed.

### Methods
GET, POST, PUT and DELETE are supported.
POST and PUT require a TITLE and AUTHOR parameter.

    - GETALL

    - GET,{ISBN}

    - POST,{ISBN},{TITLE},{AUTHOR}

    - PUT,{ISBN},{TITLE},{AUTHOR}

    - DELETE,{ISBN}
