#  Kafka

### Kafka commands - create topic
docker exec -it broker kafka-topics --create --topic customers --bootstrap-server broker:9092

### Kafka commands - see topic
docker exec -it broker kafka-topics --describe --topic customers --bootstrap-server broker:9092

### Kafka commands - consumer
docker exec -it broker kafka-console-consumer --topic customers  --bootstrap-server broker:9092

### Kafka commands - producer
docker exec -it broker kafka-console-producer  --topic customers  --bootstrap-server broker:9092


