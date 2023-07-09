## 📋 요구 사항 분석 및 구현 내용

## **Weekly1 - Basic + intensification**

### 콘솔

> - 바우처 프로그램의 입력, 출력을 한다.
> - GUI로 개선 될 수 있는 상황을 고려하여 Input과 Output을 interface로 생성

- **Input**

``` markdown
menu  
- exit, create, list, blacklist에 대한 입력을 받는다.  
  
voucher  
- 고정된 금액 바우처, 퍼센트 바우처 중 어떤 것을 생성할지에 대한 입력을 받는다.  
- 얼마를 할인 해줄 것인지에 대한 입력을 받는다.  
```  

- **output**

``` markdown
menu  
- exit, create, list에 대한 안내 문구를 출력한다.  
- 프로그램이 종료되었을 때 종료되었다는 안내 문구를 출력한다.  
  
voucher  
- 고정된 금액 바우처, 퍼센트 바우처 중 어떤 것을 생성할지에 대한 안내 문구를 출력한다.  
- 얼마를 할인 해줄 것인지에 대한 안내 문구를 출력한다.  
  
list  
- 저장된 바우처에 대해서 모두 출력한다.  

blacklist
- 파일에 저장된 블랙리스트 고객 명단을 출력한다.
```  

### 바우처

> - 바우처를 생성한다.
> - 바우처를 저장한다.
> - 저장된 바우처를 조회한다.

- **바우처 생성**

``` markdown
- 입력받은 바우처 종류에 따라서 바우처를 생성할 때 고정된 금액 또는 퍼센트를 받아서 바우처를 생성한다.  
```  

- **바우처 저장**

``` markdown
- 생성된 바우처를 메모리에 저장한다. (profile이 local일 때)
- 생성된 바우처를 파일로 저장한다. (profile이 dev일 때)
```  

- **바우처 조회**

``` markdown
- 메모리에 저장된 바우처들을 조회한다.
- 파일에 저장된 바우처들을 조회한다.
```

### 검증

> - 입력값에 대한 검증을 한다.

- **생성되는 바우처 종류에 대한 입력 값**

``` markdown
- 입력 값이 문자열인지 체크한다.
- 퍼센트 바우처일 경우 1과 100사이의 값을 입력했는지 체크한다.
```

### 로깅

> - 로그 레벨에 따라 로그를 남긴다.

- **Error**

``` markdown
- Error레벨은 파일로 로그를 기록한다.
```

- **Info ~ Error**

``` markdown
- Info부터 Error레벨은 콘솔에 로그를 출력한다.
```

### 고객

> - 블랙리스트에 저장된 고객을 조회하는 부분

``` markdown
- 파일에 저장된 블랙리스트 고객 명단을 조회한다.
```

## **Weekly2 - Basic**

### 2-1 테스트 코드 작성

### Domain

**VoucherTest**

- 고정 할인 바우처를 생성한다.
- 고정 할인을 진행한다.
- 퍼센트 할인 바우처를 생성한다.
- 퍼센트 할인을 진행한다.
- 고정 할인 바우처를 생성할 때 입력 값이 숫자가 아니면 예외가 발생한다.
- 퍼센트 할인 바우처를 생성할 때 입력 값이 숫자가 아니면 예외가 발생한다.
- 퍼센트 할인 바우처를 생성할 때 입력 값이 범위 내 숫자가 아닌 경우 예외가 발생한다.

**CustomerTest**

- 고객을 생성한다.

**EnumTest**

- 프로그램 메뉴에 없는 메뉴가 입력되면 예외를 발생시킨다.
- 바우처 타입에 없는 타입이 입력되면 예외를 발생시킨다.
- 고객 타입에 없는 타입이 입력되면 예외를 발생시킨다.

<hr>

### Repository

**FileVoucherRepository**

- 파일 저장소에 고정 할인 바우처가 정상적으로 저장된다.
- 파일 저장소에 퍼센트 할인 바우처가 정상적으로 저장된다.

**MemoryVoucherRepository**

- 메모리 저장소에 고정 할인 바우처가 정상적으로 저장된다.
- 메모리 저장소에 퍼센트 할인 바우처가 정상적으로 저장된다.
- 메모리 저장소에 여러 바우처를 등록 했을 때 정상적으로 바우처 리스트를 가져온다.

**FileCustomerRepository**

- 블랙리스트 명단을 정상적으로 가져온다.

<hr>

### 2-2 Customer CRUD 기능 구현

### 요약

- Jdbc를 활용하여 콘솔에서 `고객을 CRUD` 할 수 있도록 `Controller`, `Service`, `Repository`를 구현했습니다.
- 1주차에서 작성하지 않았던 테스트 코드를 **현재까지 구현 된 기능들에 대해 모두 작성**했습니다.

### Customer

- NamedParameterJdbcTemplate을 이용
- 고객 저장 기능(save)
- 고객 업데이트 기능(update)
- 고객 찾기 기능(findById, findAll)
- 블랙리스트 찾기 기능(getBlackList)
- 고객 삭제 기능(deleteById, deleteAll)
- 기존에 ConsoleApplication에 있었던 고객과 바우처를 기능이 늘어남에 따라 `ConsoleCustomer`와 `ConsoleVoucher`로 분리
- 테스트 코드 구현

<hr>

### 2-3  Voucher CRUD 기능 구현

### 요약

- Jdbc를 활용하여 콘솔에서 `바우처를 CRUD` 할 수 있도록 `Controller`, `Service`, `Repository`를 구현했습니다.

### Voucher

- NamedParameterJdbcTemplate을 이용
- 바우처 저장 기능(save)
- 바우처 업데이트 기능(update)
- 바우처 찾기 기능(findById, findAll)
- 바우처 삭제 기능(deleteById, deleteAll)
- 레이어 간 데이터 이동시 dto를 활용하여 이동할 수 있도록 dto객체 생성
- 테스트 코드 구현

<hr>

### 2-4  Wallet 지갑 기능 구현

### 요약

- Jdbc를 활용하여 콘솔에서 `바우처 지갑(Wallet)을 CREATE, READ, DELETE` 할 수 있도록 `Controller`, `Service`, `Repository`를 구현했습니다.
- Customer와 Wallet의 관계를 1:1로 설정하였습니다.
    - 한 명의 고객은 하나의 바우처만 가질 수 있다.
- Voucher와 Wallet의 관계를 N:1로 설정하였습니다.
    - 한 개의 바우처는 여러명의 고객에게 할당할 수 있다.

![스크린샷 2023-07-10 오전 12 23 51](https://github.com/Hchanghyeon/springboot-basic/assets/92444744/a0c5395b-0b0f-4f21-99ae-f9a5f36a7b03)

    

### Wallet

- NamedParameterJdbcTemplate을 이용
- 특정 고객에게 바우처를 할당(save)
- 고객이 어떤 바우처를 보유할 수 있는지 조회(findByCustomerId)
- 고객이 보유한 바우처 삭제(deleteByWalletId)
- 특정 바우처를 보유한 고객들을 조회(findByVoucherId)
- 바우처 지갑에 있는 모든 리스트 조회(findAll)
- 테스트 코드 구현


