# Falcon-io-task

The project represents a messaging app, which sends messages to all open apps through Websockets (on top of RabbitMQ) and persists them in the database asynchronously through the messaging queue (Apache Kafka).

## Getting Started

### High-Level Diagram
![alt text](https://github.com/mivanov1988/falcon-io-task/blob/develop/doc/high_level_diagram.png)

### Prerequisites

```
1. Java 8
2. Docker
3. Docker compose
```

### Installing
(Note: all of the following commands need root privileges to connect to the docker host)

#####Build docker image

```
sudo sh gradlew build docker 
```

#####Run docker containers (from root folder of the project). 

Notes: 
1. Мake sure the following ports are NOT busy: 8080, 8081, 8082, 8083, 2181, 9092, 8001, 3306, 61613, 15674 and 15672
2. The application might NOT start at the first run because of lack of synchronization between database service and the web service. The database needs more time to create the schema at the very first start.


```
sudo docker-compose -f src/main/docker/docker-compose.yml up
```

#####Stop docker containers (from root folder of the project)

```
sudo docker-compose -f src/main/docker/docker-compose.yml stop
```

## Running the tests

### Unit tests

Run unit tests

```
gradlew test
```

### Manual tests

1. Open all running instances of the application in the web browser
```
http://localhost:8080
http://localhost:8082
http://localhost:8083
```
2. In order to connect to Websockets, click the "Connect" button.

3. Open one of the instances and send a message via the UI form (or through POST Rest API http://localhost:8080/api/v1/messages/send) - the message will be received immediately from all running instances.

4. Execute GET API for loading of all messages against one of the instances

```
http://localhost:8080/api/v1/messages
```

## Built With

* [Spring Initializr](https://start.spring.io/)
* [Gradle](https://gradle.org/) - Dependency Management

## Versioning

For the versions available, see the [tags on this repository](https://github.com/mivanov1988/falcon-io-task/tags).

## Authors

* **Miroslav Ivanov**