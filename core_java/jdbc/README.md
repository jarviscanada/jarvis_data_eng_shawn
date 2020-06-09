
# Design Patterns
The difference between DAO and Repository design pattern
## DAO (Data Access Object)
DAO provides an abstraction on database/data files or any other persistence mechanism so that, persistence layer could be manipulated without knowing its implementation details. DAO involves more complex queries since there is involvement in multiple tables. We can say, DAO is an abstraction of data persistence.

## Repository Pattern
This pattern doesn't involve complex queries, rather it focus to use simple ones.  Here, unlike DAO, repository pattern works typically on a single table or DTO by acting as an abstraction between various collection of objects.