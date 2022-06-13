# cloudKafka

kafka 설치 필요
Window 로 설치 후 아래대로 실행 필요


주키퍼 실행
.\zookeeper-server-start.bat ..\..\config\zookeeper.properties

카프카 실행 
.\kafka-server-start.bat ..\..\config\server.properties


netstat -a 
실행후 포트가 열려있는지 확인필요
주키퍼의  default 포트는 2181이고 카프카의 default 포트는 9092

실행 POST 로 http://localhost:8080/send/first/145444qqqqq 호출하는경우 정상 실행

http://localhost:8080/send/first/failStr 실행 하면 에러 발행수 DTL에 입력되어 info 가 찍히는것을 확인가능
