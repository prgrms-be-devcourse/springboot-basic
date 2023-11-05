# SpringBoot Basic Weekly Mission
스프링부트 basic 위클리미션을 코드리뷰하는 Repository입니다.

주차별 과제는 데브코스 노션에서 확인하세요!


### 사용방법
1. pull 받는다.
2. docker를 띄운다.
3. `docker build -t mysql-image .`
4. `docker run -dit --name mysql-container -p 3300:3306 mysql-image`
5. docker 올라온 것을 확인 후 프로젝트 실행 

기존 3000포트 -> 3300포트로 변경(react 충돌)
