# dg-spring-boot

A simple REST / GraphQL API for logging into a Database and working with some vehicle data. Filtering and sorting is done on the Front-end as of present.

Key Features: 
* Cloud deployment to AWS Elastic Beanstalk instance with EBS DB
* JSON serialization / deserialization of Vehicle description information with Jackson
* JWT Token based Authentication - token is generated upon successfull login
* SHA1 (legacy) password login authentication - the sample data already contained SHA1 encrypted passwords
* Simple GraphQL API for retreiving vehicle information from DB
* REST Api for retreiving vehicle information from DB
