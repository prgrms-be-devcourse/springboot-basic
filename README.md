# 📌 SpringBoot Basic Weekly Mission
스프링부트 basic 위클리미션을 코드리뷰하는 Repository입니다.

# 📌 그림 그리기 & 설명하기
![스크린샷 2023-07-10 00-53-29](https://github.com/prgrms-be-devcourse/springboot-basic/assets/102570281/f9188675-6562-4425-b666-bc9eb1396bbb)

# 📌 요구 사항

**(기본)** **바우처 관리 애플리케이션**

- [ ]  바우처 관리 애플리케이션에 단위테스트를 작성해보세요.
   - 가능한 많은 단위 테스트코드를 작성하려고 노력해보세요.
   - 엣지 케이스(예외 케이스)를 고려해서 작성해주세요.
   - Hamcrest 의 메쳐들을 다양하게 작성해보고 익숙해져 보세요.
- [ ]  바우처 관리 애플리케이션에서도 과정에서 다루었던 고객을 적용해보세요.
   - customers 테이블 정의 및 추가
   - CustomerRepository 추가 및 JdbcTemplate을 사용해서 구현
- [ ]  (1주차엔 파일로 관리하게 했다.) 바우처 정보를 DB로 관리해보세요.
   - 바우처에 엔터티에 해당하는 vouchers 테이블을 한번 정의해보세요.
   - 바우처 레포지토리를 만들어보세요. (JdbcTemplate을 사용해서 구현)
   - 기존의 파일에서 바우처를 관리한 것을 vouchers 테이블을 통해서 CRUD가 되게 해보세요.

# 📌 작업 목록

- [ ]  그림그리기 & 소개하기에 맞게 패키지 구조 수정 및 추가
- [ ]  컨트롤러(commadLine)에서 crud 관련 메뉴 추가 및 메소드 호출 구현
  **Voucher 관련 기능작업 목록**
- [ ]  바우처 등록 메소드 코드 구현
- [ ]  바우처 등록 메소드 테스트 코드 작성
- [ ]  id로 바우처 조회 코드 구현
- [ ]  모든 바우처 정보 조회 코드 구현
- [ ] 바우처 read 작업에 대한 테스크 코드 작성
- [ ] 바우처 제거 메소드 코드 구현
- [ ] 바우처 모두 제거 코드 구현
- [ ] 바우처 delete 작업에 대한 테스트 코드 작성
  **Customer 관련 기능작업 목록**
- [ ] customer entity 추가
- [ ] 신규 customer 등록 코드 구현
- [ ] customer 추가 기능에 대한 테스트 코드 구현
- [ ] customer findById 코드 구현
- [ ] customer findByEmail 코드 구현
- [ ] custoemr read 작업에 대한 테스트 코드 작성
- [ ] customer update 코드 구현
- [ ] customer update 기능에 대한 테스트 코드 작성
- [ ] custromer delete 코드 구현
- [ ] customer deleteAll 코드 구현
- [ ] customer delete 작업에 대한 테스트 코드 작성
