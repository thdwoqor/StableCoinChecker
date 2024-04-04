#!/bin/bash
yum update -y
sudo yum install java-17-amazon-corretto -y
sudo yum install ruby -y
sudo yum install wget -y
cd /home/ec2-user
wget https://aws-codedeploy-ap-northeast-2.s3.ap-northeast-2.amazonaws.com/latest/install

chmod +x ./install
sudo ./install auto

# https://stackoverflow.com/questions/27086639/user-data-scripts-is-not-running-on-my-custom-ami-but-working-in-standard-amazo
