#!/bin/bash
source ~/.bashrc

if [ -z "$(lsof -i:8081)" ]; then
    green=8081
    blue=8080
else
    green=8080
    blue=8081
fi

cd /home/ec2-user/server/build/libs/
nohup java -jar -Dspring.profiles.active=prod -Dserver.port=$green StableCoinChecker-0.0.1-SNAPSHOT.jar &> webserverlog.log &

while [ "$(curl -Isw '%{http_code}' -o /dev/null http://localhost:$green)" == 000 ]
do
    echo "새로운 어플리케이션을 실행 중입니다.";
done

echo "set \$service_url http://127.0.0.1:$green;" | sudo tee /etc/nginx/conf.d/service_url.inc

sudo nginx -s reload;

if [ -n "$(lsof -ti:$blue)" ]; then
  kill -15 $(lsof -ti:$blue)
fi
