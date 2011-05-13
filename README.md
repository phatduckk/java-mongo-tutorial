More or less this is a bunch fof unit tests showing u how to do various queries.
My goal is to cover almost all operations (or at least the ones i care about) using:

1. the vanilla java driver
2. morphia's datastores
3. morphia's daos

Then show how u can use guice to help configure set stuff up for ya...

- run mongodb on port 27666 (/path/to/executable/mongod --port 27666)
- import project into IntelliJ (community/free edition will work fine) or Eclipse (which sucks ass) and start poking around & running tests
- you could run mvn test but it would be fucking worthless to not look at the code