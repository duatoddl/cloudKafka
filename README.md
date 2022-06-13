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

