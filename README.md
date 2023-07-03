📌 과제 설명
(기본) 바우처 관리 애플리케이션

Maven / Gradle 로 프로젝트를 실제로 구성하고 이때 Spring Boot CLI를 개발PC에 설치해서 명령어들을 사용해보고 프로젝트를 만든다. 그리고 IDE (IntelliJ)에서 실행시켜 본다.

바우처 관리 Command-line Application을 만들어본다.

스프링부트 애플리케이션으로 만든다. (Web기능이 없이 만듭니다. 즉, 서버가 띄지 않고 커맨드라인 애플리케이션으로 동작해야한다.)
프로그램이 시작하면 다음과 같이 지원가능한 명령어를 알려준다.
=== Voucher Program ===
Type **exit** to exit the program.
Type **create** to create a new voucher.
Type **list** to list all vouchers.
create / list 커맨드를 지원한다.
create 커맨드를 통해 바우처를 생성할수 있다. (FixedAmountVoucher, PercentDiscountVoucher)
list 커맨드를 통해 만들어진 바우처를 조회할 수 있다.
바우처 정보를 매모리에 관리한다.
적절한 로그를 기록하고 logback 설정을해서 에러는 파일로 기록된다.

실행가능한 jar 파일을 생성한다.

👩‍💻 요구 사항과 구현 내용
Voucher를 공통 기능을 사용하기 위해 추상 클래스로 구현하였습니다.

추후 다른 할인 정책 추가 가능

VoucherType을 추상화하여 Enum 타입으로 구현하였습니다.

VoucherFactory를 객체를 생성하는 팩토리(Factory) 클래스로 구현하였습니다.

객체를 생성하는 작업을 별도의 클래스로 분리하여 객체 생성의 유연성과 확장성 증가

VoucherRepository를 인터페이스로 구현하였습니다.

DTO를 request와 response로 분리하여 구현하였습니다.

데이터 전송을 위한 객체

실행결과

image
✅ PR 포인트 & 궁금한 점
�학기 졸업 논문으로 인해 시간이 부족하였지만 미흡하게 과제를 완료하기 보다는 기본과제 만이라도 열심히 구현하려고 노력하였습니다.
추후 주말에 심화과제도 구현하겠습니다.
프로젝트 구조에 맞게 역할과 책임을 최대한 고려하며 구현하였으나, 제 구현이 적절했는지 궁굼합니다.
코드의 가독성과 유지보수성을 개선하기 위한 추가적인 개선 사항이 있는지 궁굼합니다.