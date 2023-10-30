# 도서 관리 애플리케이션

스프링부트 basic 위클리미션을 코드리뷰하는 Repository입니다.

## Environment Variables

To run this project, you will need to add the following environment variables to
your `src/main/resources/application.yml` file

```yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: YOUR_DATABASE_URL
    username: YOUR_DB_USER_NAME
    password: YOUR_DB_PASSWORD
```

## Initialize Database

### Using Dockerfile

Create image from Dockerfile

```bash
$ docker build -t <IMAGE_NAME> ./src/main/resources
```

To create container, run the following command

```bash
$ docker run --name <CONTAINER_NAME> -e MYSQL_ROOT_PASSWORD=<PASSWORD> -d -p 3306:3306 <IMAGE_NAME>
```

### Using Custom Database

To Create database, use `schema.sql` from `scr/main/resources/`

## Running Tests

To run tests you will need to add the following environment variables to your `scr/test/resources/application.yml` file

```
url: YOUR_DATABASE_URL
username: YOUR_DB_USER_NAME
password: YOUR_DB_PASSWORD
```

To run tests, run the following command

```bash
$ gradle test
```

## Run Locally

Clone the project branch `ASak1104/week2`

```bash
$ git clone -b ASak1104/week2 https://github.com/ASak1104/voucher-management-system.git
```

Go to the project directory

```bash
$ cd voucher-management-system
```

Add environment Variables in application.yml

`scr/main/resources/application.yml`

`scr/test/resources/application.yml`

Build project

```bash
$ ./gradlew build
```

To run application, run the following command

```bash
$ ./gradlew run --console=plain
```

