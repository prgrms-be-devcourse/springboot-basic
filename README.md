# w3-SpringBoot_Part_A

### 210816 _ W3D1
- DDD에 대해 배움
- Entity와 VO
- 의존성이 높은 코드의 문제점과 인터페이스를 통해 이를 해결하기
- 주문관리 시스템 코드 따라하기

### 210817 _ W3D2
![스크린샷 2021-08-16 오후 8 27 15](https://user-images.githubusercontent.com/68773492/129556581-8f9154f3-991f-4dc4-84ed-4f0b3f790997.png)
- 멘토님의 피드백시 코드구조 이해를 돕기위해 수업중에 나온 다이어그램 추가하였습니다.
- repository와 service코드 추가
- IoC 컨테이너의 역할에 대한 이해 (OrderContext클래스에서 객체의 생성과 의존성을 모두 관리한다.)

### 210818 _ W3D3
- 생성자를 통한 의존성주입
- circular depency문제
- 컴포넌트 스캔

### 210823 _ 코드리뷰
 1. 명령어 enum클래스 생성
 2. commandApplication에 있던 출력문 모두 IoIntercation클래스로 이동
 3. 타입선택시 사용자로부터 입력받은 숫자로 비교해서 fixed, percent로 구분하지않고 enum에 getter 두고 매칭되는것으로 enum가져오기
 4. Consumer -> BlackConsumer
 5. valueobject삭제, 파일을 읽어 메모리에 로드할때 fixed와 percent의 분기방법 (파일에적힌 문자열과 비교하여 분기하지않고 enum으로 변환후에 타입으로 분기하도록 변경 ( voucherFileManager.fileToMemory 메소드 )
 6. FileManager를 상속받는 VoucherFileManager을 두고 해당 클래스에서 fileToMemory, MemoryToFile 메서드로 파일과 메모리이동을 담당 , @Component를 통해 빈으로 등록후 VoucherRepository에서 생성자주입을 통해 사용
