# aircraft-stream-analytics

A Spark Streaming job to get statistics from aircrafts within a given zone. This is only a simple demonstrator of connecting a Spark Streaming job to Kafka; but there is no real use case.

It is meant to be run aside my [opensky-kafka-publisher](https://github.com/antoineBonninProjects/opensky-kafka-publisher) project.

*opensky-kafka-publisher* publishes aircraft states data from OpenSky API to a kafka topic.

*aircraft-stream-analytics* consumes this topic (streaming) and computes simple statistics from planes states.
It also pusblishes alerts to another kafka topic for aircraft states containing a squawk Code. Squawk Code may be routine operations.

Spark Streaming job to get analytics data and alerts from aircrafts. Reads a Kafka topic.

# Project use

## Setup

Make sure you have *docker* installed.

Fill in the .envrc file and source it.

```sh
source .envrc
```

## Run from prebuilt image

Pull the abonnin33/aircraft-stream-analytics image
```sh
docker pull abonnin33/aircraft-stream-analytics:latest
```

Then run the code

```sh
docker run abonnin33/aircraft-stream-analytics:latest
```

## Run you own image

You can fork this project and create your own image

```sh
mvn clean package;                                                 # Build the .jar fatjar with dependencies
docker build -t <your-username>/aircraft-stream-analytics:latest;  # Build the docker image
docker login;
docker push <your-username>/aircraft-stream-analytics:latest;      # Push the image: optionnal
```

Then  run it

```sh
docker run <your-username>/aircraft-stream-analytics:latest

```