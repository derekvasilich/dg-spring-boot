# dg-spring-boot

A simple REST / GraphQL API for logging into a Database and working with some vehicle data. Filtering and sorting is done on the Front-end as of present.

Key Features: 
* Cloud deployment to AWS Elastic Beanstalk instance with EBS DB
* JSON serialization / deserialization of Vehicle description information with Jackson
* JWT Token based Authentication - token is generated upon successfull login
* Customizable encrypted password login authentication
* Simple GraphQL API for retreiving vehicle information from DB
* REST Api for retreiving vehicle information from DB
* Legacy Angular frontend; it has been replaced with React (Next.js)

TODO:
- [ ] Add pagination to vehicles list
- [ ] Add signup / registration page
- [ ] Get full vehicle descriptions showing
- [ ] Add tests for the API (junit) and frontend (Cypress)