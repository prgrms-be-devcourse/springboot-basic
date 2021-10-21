# [3W2D] Mission

바우처 관리 Command-line Application을 만들어본다. [[참고 링크]](https://dzone.com/articles/interactive-console-applications-in-java)

- `CommandLineApplication` 클래스를 작성한다.
- `AnnotationConfigApplicationContext` 를 이용해서 `IoC Container`를 생성하고 `Service`, `Repository`를 빈으로 등록한다.
- 프로그램이 시작하면 다음과 같이 지원 가능한 명령어를 알려준다.
  ```
  === Voucher Program ===
  Type exit to exit the program.
  Type create to create a new voucher.
  Type list to list all vouchers.
  ```

- `create` / `list` 커맨드를 지원한다.
    - `create` 커맨드를 통해 바우처를 생성할 수 있다. (`FixedAmountVoucher`, `PercentDiscountVoucher`)
        - `list` 커맨드를 통해 만들어진 바우처를 조회할 수 있다.
- 바우처를 메모리에서 관리하도록 설계한다. 다른 미션에서 영속성을 가지도록 변경한다.

##

## 구현

- 초기 화면  
  ![초기](https://user-images.githubusercontent.com/60170616/132293056-a23c026e-ac7a-461f-92fe-bc5b2c980836.png)
- 초기 화면 예외  
  ![main예외](https://user-images.githubusercontent.com/60170616/132293467-dcb80b96-e62a-40ab-a727-7410fb1dfe3b.png)
- create  
  ![create](https://user-images.githubusercontent.com/60170616/132293111-8d6697cd-7225-4498-923c-022c692294fb.png)
- create 예외  
  ![create예외](https://user-images.githubusercontent.com/60170616/132293397-e56651a8-1dff-46ea-a4be-fc3357204f79.png)
- FixedAmountVoucher  
  ![fixed howmuch](https://user-images.githubusercontent.com/60170616/132293144-81649f78-e022-47ee-9c7f-d6c21dd2b4b0.png)
- input amount of discount and Create FixedAmountVoucher  
  ![fixedCreate](https://user-images.githubusercontent.com/60170616/132293184-4d6dbd7a-ba9d-439a-b1f5-4c03c484dd61.png)
- Create FixedAmountVoucher 예외  
  ![fixed예외](https://user-images.githubusercontent.com/60170616/132293611-101aac41-2dd0-4afc-910a-1df453adecf8.png)
- create  
  ![create](https://user-images.githubusercontent.com/60170616/132293111-8d6697cd-7225-4498-923c-022c692294fb.png)
- PercentDiscountVoucher  
  ![percentHowMuch](https://user-images.githubusercontent.com/60170616/132293240-ad19cee2-887f-4e9e-9733-1022963ee0ca.png)
- input percent of discount and Create PercentDiscountVoucher  
  ![createPercent](https://user-images.githubusercontent.com/60170616/132293276-7fc124f9-d474-4a87-b218-c1a31cd8dbdd.png)
- Create PercentDiscountVoucher 예외  
  ![createPercent](https://user-images.githubusercontent.com/60170616/132293740-a3d6f996-017e-48eb-8524-30a2fa802441.png)
- Voucher List  
  ![List](https://user-images.githubusercontent.com/60170616/132295144-cfc706e1-7f56-4ba4-b33b-076d25a8924f.png)
- exit  
  ![exit](https://user-images.githubusercontent.com/60170616/132293837-50dfa313-e67e-4f1c-9465-07e9ec38aeab.png)

___