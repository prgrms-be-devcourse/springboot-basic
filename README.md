# SpringBoot Basic Weekly Mission
스프링부트 basic 위클리미션을 코드리뷰하는 Repository입니다.

주차별 과제는 노션에서 확인하세요!
[노션에서 미션 확인가기](https://www.notion.so/backend-devcourse/Part1-3-38f57acca0dd490db11393701417943a)

# week 1

### jar파일 생성

```shell
mvn clean package spring-boot:repackage 
```
### jar파일 실행

```shell
java -jar target/spring_week1-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev 
java -jar target/spring_week1-0.0.1-SNAPSHOT.jar --spring.profiles.active=local
```

