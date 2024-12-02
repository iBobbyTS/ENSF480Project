# Theatre System
## Requirements
- MySQL
- Java 21
- Maven
## Create Database
Login into MySQL: `mysql -u root -p`
Create database:
```sql
CREATE USER 'ensf480'@'localhost' IDENTIFIED BY 'ensf480';
CREATE DATABASE theater480;
GRANT ALL PRIVILEGES ON *.* TO 'ensf480'@'localhost' with GRANT OPTION;
```
You can also use your own database and user by modifying line 2-4 of `src/main/resources/application.properties`
## Running the program
`mvn spring-boot:run`
## Data persistence
Modify line 9 and 12 in `src/main/resources/application.properties` to change the behaviour of database initialization.
## Target
Visit [localhost:8080](http://localhost:8080) in a local browser
## TODO
-- admin
  -- add showtime
- user
  -- vip
  -- see showtimes
  - select seat with seat map
  - buy ticket (get email)
  - buy ticket with coupon
  - cancel ticket (get coupon with email)
- registered user
  -- update payment method
  -- pay anual fee
  - see ticket history
  - cancel ticket (get credit in account)
  - buy ticket with credit