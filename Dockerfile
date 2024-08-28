FROM spark:3.5.2-scala

# Add the Kafka JARs to the Spark classpath
ADD target/aircraft-stream-analytics-1.0-jar-with-dependencies.jar /opt/spark/jars/
