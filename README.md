# SpringBoot Basic Weekly Mission
스프링부트 basic 위클리미션을 코드리뷰하는 Repository입니다.

## 프로그램 실행방법
``` shell
git clone -b zxcv9203/week2 
make run
```
기본적으로 프로파일은 prod로 실행되며 dev로 변경하고 싶을 경우 다음과 같이 값을 지정하여 실행할 수 있습니다.

```shell
make run PROFILE=dev
```

### Makefile 사용방법
- help : 사용 가능한 명령어 목록을 출력해줍니다.


- build : 바우처 관리 프로그램을 빌드합니다. 만약 빌드된 jar 파일이 있을 경우 삭제하고 다시 빌드합니다.


- start : 바우처 관리 프로그램을 실행합니다. 실행전, 빌드가 완료된 상태여야 합니다.


- clean : 빌드한 바우처 관리 프로그램을 삭제합니다.


- run : 바우처 관리 프로그램을 빌드 후 실행합니다.

