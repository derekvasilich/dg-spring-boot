# start broker
zookeeper-server-start.sh config/zookeeper.properties
# start server
kafka-server-start.sh config/server.properties
# create topic
kafka-topics.sh --create --topic dg-events --bootstrap-server localhost:9092
# describe topic
kafka-topics.sh --describe --topic dg-events --bootstrap-server localhost:9092
# create events
kafka-console-producer.sh --topic dg-events --bootstrap-server localhost:9092
# consume events
kafka-console-consumer.sh --topic dg-events --from-beginning --bootstrap-server localhost:9092
