# Week 4 - SpringBoot Part 2

## [W4] Mission

### 📌 기본 Mission - 바우처 관리 애플리케이션

- [X] 바우처 관리 애플리케이션에 **단위테스트를 작성**한다.
    - 가능한 많은 단위 테스트 코드를 작성
    - 엣지 케이스(예외 케이스)를 고려해서 작성
    - Hamcrest matcher들을 다양하게 작성해보고 익숙해져 본다.
- [X] 바우처 관리 애플리케이션에서도 과정에서 다루었던 **고객을 적용**한다.
    - customers 테이블 정의 및 추가
    - CustomerRepository 추가 및 JdbcTemplate을 사용해서 구현
- [X] 바우처 정보를 **DB로 관리**한다. (W3에는 파일로 관리하게 했다.)
    - 바우처 entity에 해당하는 vouchers 테이블 정의
    - 바우처 repository를 만들어본다. (JdbcTemplate을 사용해서 구현)
    - 기존 파일에서 바우처를 관리한 것을 vouchers 테이블을 통해서 CRUD가 되게 한다.

### 📌 심화 Mission - 바우처 지갑

- [X] 특정 고객에게 바우처 할당
- [X] 고객이 어떤 바우처를 보유하고 있는지 조회
- [X] 고객이 보유한 바우처 제거
- [X] 특정 바우처를 보유한 고객 조회

___