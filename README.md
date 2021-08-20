# w3-SpringBoot_Part_A

## 미션내용
- 컴포넌트 스캔을 통해서 빈이 등록되게 해보세요
- 메모리 관리가 아닌 파일로 관리가 되는 Repository를 한번 만들어보세요.
  - 기존 메모리 레포지토리는 지우지 말아주세요.

## 구현내용

![Class Diagram D3](/img/orderproject_D3.png)

- 컴포넌트 스캔을 통해서 빈이 등록
  - 수업중에 진행한 사항이라 다른 변경하지 않았습니다.

- 파일로 관리가 되는 Repository
  - Json 파일을 이용해서 데이터를 저장 하도록 바꾸었습니다.
  - Class Diagram의 JsonVoucherRepository(이하 JsonVR) 가 **새로 적용된 부분**입니다.
  - 기존 MemoryVoucherRepository(이하 MemVR) 에 있던 **@Repository 를 주석처리**했습니다.
  - MemVR에 적용되어있던 storage를 그대로 적용했습니다. 
    - 저장하는 행위는 하나의 정보만 저장하면 되지만 저장된 양이 많으면 읽어오는데 오래걸릴것으로 예상
    - 저장시(insert()) storage와 json 둘다 저장.
    - 검색시(findById()) storage 먼저 검색 후 없으면 json데이터 불러와 storage에 저장 후 재 검색.
    - 전체조회시(getAllEntry()) storage에 json 데이터를 불러온 후 storage EntrySet 전송.

## PR포인트 & 궁금한점

- Repository 에서 데이터를 가져와서 Console 에서 list를 출력하는 과정이 적절한것인지 궁금합니다. **(D2 미션과 연장)**
  - Entry로 가져오는 방법이 적절한가(데이터가 안전하지 않아보임)
  - Console에서 처리하는 과정이 적절한가

- json 파일 저장과정에서 private 메서드가 3개 생겨났습니다. 
  - 적절하게 구성된 것인지 궁금합니다. ('좀더 작은단위로 더 쪼개야 한다' 라거나 '더 가독성 좋게 바꿔야한다' 등 다양한 관점에서 깔끔한 java식 코드 구현을 알려주세요)

- **Spring도 처음이고 심지어 java 개발이 처음이기 때문에 아주 자잘한 팁이라도 과하게 많이 주시면 감사드리겠습니다!**
