FROM mysql:8.1

MAINTAINER ASak1104

ENV MYSQL_DATABASE=voucher_management_system \
    MYSQL_ROOT_PASSWORD=<password>

ADD schema.sql /docker-entrypoint-initdb.d/

EXPOSE 3306
