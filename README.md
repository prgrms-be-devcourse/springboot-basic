
# **SpringBoot Basic Weekly Mission 3**


## 🔖 소감

<hr>

## **📌 과제 설명**

### **흐름도**
![흐름도.png](%ED%9D%90%EB%A6%84%EB%8F%84.png)

### **클래스 다이어그램**
![클래스다이어그램.png](%ED%81%B4%EB%9E%98%EC%8A%A4%EB%8B%A4%EC%9D%B4%EC%96%B4%EA%B7%B8%EB%9E%A8.png)

## **✅ PR 포인트 & 궁금한 점**

- nullable 컬럼
  - 테이블에 null 값이 가능한 컬럼은 도메인 클래스에서 어떻게 관리하는지 궁금합니다.
  - 생각해본 해법입니다.
    1. 로직에서는 Optinal로 처리, 컨트롤러 단에서 Optional.get() 으로 직렬화
    2. null 상태를 가리키는 무의미한 인스턴스 객체를 생성해서 사용
- 컨트롤러 인터페이스
  - rest 컨트롤러, view 컨트롤러 모두 리턴 타입만 다를 뿐 나머지 시그니처는 같은 것 같습니다.
  - 만약 두 가지 컨트롤러를 모두 구현해야 하는 경우 리턴타입이 Object인 인터페이스를 만들어도 되는지 궁급합니다.
  ```java
  interface Controller {
    Object allList();
  }
  
  @RestController
  class RestController implements Controller {
    @GetMapping("/all")
    public ResponseEntity<Response> allList() {
        return ResponseEntity.ok(...);
    }
  }
  
  @Controller
  class ViewController implements Controller {
    @GetMapping("/all")
    public String allList() {
        return "뷰이름";
    }
  }
  ```
- DTO
  - MVC 모든 레이어에 최소 두 개씩 DTO가 생기게 되는데 이게 맞는지 모르겠습니다.
  - 특히 service 레이어에서는 두 번의 변환이 발생합니다.
    - 두 번의 변환 사이에 서비스 로직을 넣는다고 해도 절차지향의 느낌이 납니다.

## **👩‍💻 요구 사항과 구현 내용**

- [x] 타임리프
- [x] REST api

## **✅ 피드백 반영사항**
