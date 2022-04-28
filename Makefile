#########################################################
#	Voucher Management automation build tool in Maven   #
#########################################################

############################################
#				Variables		           #
############################################

VERSION = 0.0.1-SNAPSHOT
NAME = kdt-spring-voucher-week1

############################################
#				Rules		               #
############################################

## Makefile Rules
##

## help	바우처 관리 프로그램의 기능을 설명합니다.
##
help:
		@sed -n '/@sed/!s/## //p' Makefile

## build	바우처 관리 프로그램을 빌드합니다. 만약 파일이 존재하면 삭제하고 다시 빌드합니다.
##
build : clean
		@echo "🛠 Voucher management System building..."
		@mvn package spring-boot:repackage

## start	바우처 관리 프로그램을 실행합니다.
##
start :
		@echo "🏃 Voucher Management System start..."
		@ java -jar target/$(NAME)-$(VERSION).jar

## clean	바우처 관리 프로그램을 삭제합니다.
clean :
		@echo "🧹 Voucher Management System remove..."
		@mvn clean
## run	바우처 관리 프로그램을 빌드 후 실행합니다.
##
run : build start

.PHONY: help build start clean run