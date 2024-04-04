#!/bin/bash
source ~/.bashrc

if [ -z "$(lsof -i:8081)" ]; then
    green=8081
    blue=8080
else
    green=8080
    blue=8081
fi

cd /home/ec2-user/my-web-server/build/libs/
jar_file=$(find . -name "*.jar")
nohup java -jar -Dserver.port=$green "$jar_file" &> webserverlog.log &

while [ "$(curl -Isw '%{http_code}' -o /dev/null http://localhost:$green)" == 000 ]
do
    echo "새로운 어플리케이션을 실행 중입니다.";
done

kill -15 $(lsof -ti:$blue)
