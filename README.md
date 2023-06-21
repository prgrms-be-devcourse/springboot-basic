# SpringBoot Basic Weekly Mission

스프링부트 basic 위클리미션을 코드리뷰하는 Repository입니다.

## 📌 과제 설명

**Spring을 사용하여 바우처 관리 애플리케이션 만들기!**

## 📌 기능 요구 사항

- [ ] 스프링 부트 애플리케이션으로 만들기
- [ ] 프로그램이 시작하면 같이 지원가능한 명령어 알려주기
    - [ ] beryx:text-io 사용하기
    - [ ] 명령어를 입력 받는다. - Input
        - [ ] exit 명령어를 입력받으면 프로그램이 종료된다.
        - [ ] create 명령어를 입력받으면 voucher를 생성하는 창으로 이동한다.
        - [ ] list 명령어를 입력받으면 생성된 voucher를 조회한다.
    - [ ] 명령어 출력 및 Voucher 리스트를 출력한다. - Output
    - [ ] 지원가능한 명령어를 Enum 으로 관리한다. - Command
- [ ] Voucher는 여러가지 종류로 확장이 가능하다. - Voucher
    - [ ] Voucher는 할인을 하는 기능을 가지고 있다. - Voucher.discount()
    - [ ] 고정된 금액을 할인하는 Voucher - FixedAmountVoucher
    - [ ] 비율로 할인을 하는 Voucher - PercentDiscountVoucher
- [ ] Voucher를 생성 할 수 있다. - VoucherService#CreateVoucher()
    - [ ] Voucher는 메모리에 저장한다. - VoucherRepository#save(Voucher voucher)
- [ ] Voucher를 조회 할 수 있다. - VoucherService#findVoucherList()
