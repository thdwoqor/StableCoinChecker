#!/bin/bash

sudo yum update -y
sudo yum -y install https://dev.mysql.com/get/mysql80-community-release-el9-1.noarch.rpm
sudo rpm --import https://repo.mysql.com/RPM-GPG-KEY-mysql-2022
sudo yum update -y
sudo yum -y install mysql-community-server
sudo systemctl start mysqld

# https://jinhos-devlog.tistory.com/entry/MySQL-8-Community-Edition-%EC%84%A4%EC%B9%98-%EC%A4%91-%EC%98%A4%EB%A5%98
