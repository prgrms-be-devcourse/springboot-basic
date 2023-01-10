# SpringBoot Basic Weekly Mission
스프링부트 basic 위클리미션(voucher CRUD 서비스)을 코드리뷰하는 Repository입니다.
해당 프로젝트는 command line과 web에서 실행할 수 있습니다.

---

## 설정파일
- `Profiles`는 `Application` config에서 `Active profiles`에 원하는 profile을 설정하고 실행합니다.
  - `dev` = 파일 레포지토리를 이용한 profile입니다.
  - `release` = MySQL 8.0을 이용한 profile입니다.
  - `local` = memory를 이용하여 실행하는 profile입니다.

## 추가적인 환경 설정 방법
1. 먼저 프로젝트 디렉토리 가장 상위에 있는 `docker-config` 라는 디렉토리로 이동하여 인텔리제이를 이용해 `docker-compose.yml` 파일을 실행합니다.
2. 그 후, DataSource 설정을 위해서 인텔리제이에서 `shift`를 두번 연타 후 `DataSource and Drivers`로 들어갑니다.
3. 거기서 DataSource 메뉴에서 왼쪽 상단의 `+` 버튼을 클릭하고 `MySQL`을 선택합니다.
4. 그 후 아래의 그림과 같이 설정합니다.
- Host: localhost, Port: 3306
- User: root
- Password: root1234!
- URL: jdbc:mysql://localhost:3306  
  
=> 이와 같이 설정 후 connection test를 진행하고 성공한다면 적용, 확인 후 다시 `docker-config` 디렉토리로 이동합니다.  
  
5. `docker-config` 디렉토리에 존재하는 schema-init.sql 파일을 실행하고 실행환경을 방금 생성한 `localhost datasource`의 콘솔로 지정합니다.
6. 해당 sql을 실행했다면 환경 설정이 끝났습니다. 추가적으로 docker를 내리고 나서 다시 실행시엔 `docker-compose.yml` 파일만 실행시키고 애플리케이션을 실행시키면 됩니다. 

---

## command 정보 설명
- `exit` : 프로그램을 종료합니다.
- `create` : 바우처를 생성합니다.
- `assign` : 특정 손님에게 바우처를 할당합니다.
- `list vouchers` : 존재하는 모든 바우처를 출력합니다.
- `list voucher wallet` : 특정 손님이 가진 모든 바우처들을 출력합니다.
- `list customers` : 존재하는 모든 손님들을 출력합니다.
- `list customer voucher` : 특정 바우처를 보유한 손님을 출력합니다.
- `remove customer voucher` : 특정 손님이 소유한 바우처의 소유권을 삭제합니다. ( 바우처가 사라지는 것이 아닌 손님의 바우처 지갑 리스트에서 사라지는 것입니다.)
- `list blacklist` : 모든 블랙리스트들을 출력합니다.

## web 환경에서의 실행 설명
- profile 설정 후 `Application`을 실행합니다.
- `localhost:8000`으로 접근합니다.

---

## Api 문서
### Customer
| method | path | description |
| --- | --- | --- |
| GET | /customers | 모든 고객 정보를 출력하는 페이지 |
| GET | /blacklists | 모든 블랙 리스트 정보를 출력하는 페이지 |
| POST | /customers/voucher_wallet | 특정 고객이 보유하고 있는 쿠폰의 목록을 출력하는 경로 |
| DELETE | /customers/voucher_wallet | 특정 고객이 보유하고 있는 쿠폰을 할당해제하는 경로 |

### Voucher
| method | path | description |
| --- | --- | --- |
| GET | /new-voucher | 새로운 쿠폰을 생성을 위해 생성할 쿠폰 정보를 입력하는 페이지 |
| POST | /new-voucher | 쿠폰 정보를 이용하여 쿠폰을 생성하는 경로 |
| GET | /vouchers | 모든 쿠폰을 출력하는 경로 |
| GET | /vouchers/details/{voucherId} | 쿠폰의 상세정보를 출력하는 경로 |
| DELETE | /vouchers | 쿠폰 목록 중 하나를 삭제하는 경로 |
| POST | /not-owned-lists | 할당되지 않은 모든 쿠폰 리스트를 출력하는 경로 |
| POST | /owned-voucher | 쿠폰을 고에게 할당하는 경로 |
