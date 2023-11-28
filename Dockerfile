FROM mysql:latest

ENV MYSQL_ROOT_PASSWORD=1234
ENV MYSQL_DATABASE=voucher_management

COPY ./init.sql /docker-entrypoint-initdb.d/
