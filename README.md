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
## Running the program
`mvn spring-boot:run`
## Target
Visit [localhost:8080](http://localhost:8080) in a local browser
## TODO
- admin
  - add showtime
- user
  - see showtimes
  - select seat with seat map
  - buy ticket (get email)
  - buy ticket with coupon
  - cancel ticket (get coupon with email)
- registered user
  - update payment method
  - pay anual fee
  - see ticket history
  - cancel ticket (get credit in account)
  - buy ticket with credit