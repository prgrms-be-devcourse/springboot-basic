
## SpringBoot Basic Weekly Mission

### 📝 요구사항 (Week 1)
**🔹 (기본) 바우처 관리 애플리케이션**

- [ ]  (#1) **프로젝트 구성**
- [ ]  (#2) **프로그램이 시작하면 지원가능한 명령어 제시**
- [ ]  (#3) **create / list 커맨드를 지원**
- [ ]  (#4) **바우처 정보 메모리에 관리**
- [ ]  (#5) **로그를 기록하고 `logback` 설정**
- [ ]  (#6) **실행가능한 `jar` 파일 생성**
    
**🔸 (심화) 파일을 통한 데이터관리 기능과 고객 블랙 리스트 명단 관리기능**
- [ ]  (#7) **파일로 관리가 되는 Repository 생성**
- [ ]  (#8) **고객 블랙 리스트 명단 작성**
- [ ]  (#9) **YAML 설정**


<details>
 <summary><b>세부사항</b></summary>
 
**(#1)**
  - Command-line Application <br/>
  - 스프링부트 애플리케이션 (Web 기능 미포함 생성)
 
 **(#2)**
 ```
 === Voucher Program === 
 Type exit to exit the program. 
 Type create to create a new voucher. 
 Type list to list all vouchers. 
 ```
**(#3)**
 - create   - 바우처 생성 (`FixedAmountVoucher`, `PercentDiscountVoucher`)
 - list   - 만들어진 바우처를 조회
 
**(#5)** - 에러는 파일로 기록
 
**(#8)** - customer_blacklist.csv 파일을 통해 관리
 
</details>
