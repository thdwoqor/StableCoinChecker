#!/bin/bash
source ~/.bashrc

echo "> 빈 포트 찾기"
if [ -z "$(lsof -i:8081)" ]; then
    green=8081
    blue=8080
else
    green=8080
    blue=8081
fi

echo "> 빈 포트에 새 어플리케이션 배포"
cd /home/ec2-user/server/build/libs/
nohup java -jar -Dspring.profiles.active=prod -Dserver.port=$green StableCoinChecker-0.0.1-SNAPSHOT.jar &> webserverlog.log &

echo "> $green 10초 후 Health check 시작"
sleep 10

for retry_count in {1..10}
do
  response=$(curl -s http://127.0.0.1:8001/management/health)
  up_count=$(echo $response | grep 'UP' | wc -l)

  if [ $up_count -ge 1 ]
  then
      echo "> Health check 성공"
      break
  else
      echo "> Health check의 응답을 알 수 없거나 혹은 status가 UP이 아닙니다."
      echo "> Health check: ${response}"
  fi

  if [ $retry_count -eq 10 ]
  then
    echo "> Health check 실패. "
    echo "> Nginx에 연결하지 않고 배포를 종료합니다."
    exit 1
  fi

  echo "> Health check 연결 실패. 재시도..."
  sleep 10
done

echo "> Port 전환"
echo "set \$service_url http://127.0.0.1:$green;" | sudo tee /etc/nginx/conf.d/service-url.inc

echo "> Nginx Reload"
sudo nginx -s reload;

echo "> 기존 어플리케이션을 종료"
if [ -n "$(lsof -ti:$blue)" ]; then
  kill -15 $(lsof -ti:$blue)
fi
