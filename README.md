# SpringBoot Basic Weekly Mission
## 📌 과제 설명

### **바우처 관리 애플리케이션**
- **기본**
	- [x] SpringBoot CLI를 이용해서 프로젝트를 생성한다. 
	- [x] CLI로 제작한다.
	- [x] 스프링 부트 애플리케이션으로 만든다.
	- [ ] 프로그램이 시작하면 다음과 같이 지원 가능한 명령어를 알려준다.
	``` shell
=== Voucher Program === 
Type exit to exit the program. 
Type create to create a new voucher.
Type list to list all vouchers.
	```
	- [x] create, list 커맨드를 지원한다.
		- [ ] create 커맨드를 통해 바우처를 생성할 수 있다.(FixedAmountVoucher, PercentDiscountVoucher)
		- [ ] list 커맨드를 통해 만들어진 바우처를 조회할 수 있다.
		- [ ] 바우처 정보를 메모리에 관리한다.
	- [ ] 적절한 로그를 기록하고 logback 설정을해서 에러는 파일로 기록된다.
	- [ ] 실행가능한 jar 파일을 생성한다.
- **심화**
	- [ ] 메모리 관리가 아닌 파일로 관리가 되는 Repository 생성
		- [ ] 기존 메모리 레파지토리는 지우지 말고 개발 프로파일에서만 동작하게 설정
	- [ ] 고객 블랙 리스트 명단을 작성한다.
		- [ ] customer_blacklist.csv 파일을 만들고 스프링 애플리케이션에서 해당 파일을 읽을 수 있고 블랙 리스트를 조회 할 수 있다.
	- [ ] YAML 프로퍼티를 만들고 어떤 설정을 만들 수 있을지 고민해본다. 

