# Use the official MySQL 8 image as the base image
FROM mysql:8
ENV MYSQL_ROOT_PASSWORD=1234
ENV MYSQL_DATABASE=vouchers
ENV MYSQL_ROOT_USER=root
COPY init.sql /docker-entrypoint-initdb.d/