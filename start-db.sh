#!/bin/bash
docker run --name db -e MYSQL_ROOT_PASSWORD=rootpassword -e MYSQL_DATABASE=app -p 3306:3306 -d mysql:latest

echo "Waiting for MySQL to initialize..."
sleep 5

docker exec -i db env MYSQL_ROOT_PASSWORD=rootpassword mysql -uroot -e "create database app;"
echo "Database created successfully!"
