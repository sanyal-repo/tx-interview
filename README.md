# TX Interview - Skill Assessment

To install the database, download Docker and run the following command:
```$xslt
docker run -p 3306:3306 --name tx-interview -e MYSQL_DATABASE=tx-interview -e MYSQL_ROOT_PASSWORD=password -d mysql:latest
```

### Challenge
This application includes a database of financial institutions. 
Extend the application to expose two new endpoints:
1. Allows the front-end to search for an active financial institution.
2. Allows an administrator to activate and deactivate an institution from being included in the
 search endpoint.

For the search endpoint, please considering the following scenarios:
* Exact Match
* Partial Match
* Letter Case
* Punctuation

*Optional*
* Typos
* Abbreviations (e.g., AmEx = American Express)
* Aliases (e.g., Marcus = Goldman Sachs)

Do not search the database unless the input string is at least three characters long. 
At most, only return the ten best matches.  Please include test cases where applicable.

Feel free to include any libraries that you feel will assist you with your solution.

### How to Submit
Please send us your source code by email or online repository.