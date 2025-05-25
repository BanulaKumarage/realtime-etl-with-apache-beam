# Real-Time ETL Pipeline

A real-time data processing pipeline built with Apache Beam that consumes Kafka events, processes it, and stores aggregated results in PostgreSQL database.

## Project Overview

This project demonstrates a real-time Extract, Transform, Load (ETL) pipeline for IoT temperature data using:

- **Apache Beam** - For building the data processing pipeline
- **Apache Kafka** - As the streaming data source
- **PostgreSQL** - For storing processed data

The pipeline:
1. Consumes IoT temperature events from a Kafka topic
2. Filters events with temperatures above 80 degrees
3. Counts events per device in 5-second windows
4. Persists the counts to a PostgreSQL database

## Prerequisites

- Java 8 or higher
- Maven
- Docker and Docker Compose (for running Kafka and PostgreSQL)

## Setup and Installation

### 1. Clone the repository

```bash
git clone <repository-url>
cd realtime-etl
```

### 2. Start the infrastructure services

The project includes a Docker Compose file that sets up Kafka, Zookeeper, and PostgreSQL:

```bash
docker-compose -f services.yaml up -d
```

### 3. Create the database table

Connect to the PostgreSQL database and create the required table:

```sql
CREATE TABLE event (
    device_id VARCHAR(255) PRIMARY KEY,
    events_count BIGINT
);
```

4. Create a topic through the Kafka control center: [localhost:9021/clusters](http://localhost:9021/clusters)


## Configuration

Configuration is managed through `src/main/resources/application.properties`:

```properties
kafkaBootstrapServers=localhost:9092
kafkaTopic=iot-events
dbDriverClass=org.postgresql.Driver
jdbcUrl=jdbc:postgresql://localhost:5432/iot
dbUserName=root
dbPassword=root
```

Modify these properties as needed for your environment.

## Usage

### Running the pipeline

Run through the main class `RealTimeStreamingETL`

### Producing Test Data

You can use the Kafka console producer to send test data:

```bash
docker exec -it kafka-tools kafka-console-producer --broker-list broker:29092 --topic iot-events
```

Then input JSON data in the format:

```json
{"deviceId":"deviceA","temperature":85,"unit":"C"}
```

## Blog

For a detailed explanation of this project, check out below blog posts: 
- [Building a Real-Time ETL Pipeline with Apache Beam and Kafka — Part 1: Consuming Events](https://medium.com/@banulakumarage/building-a-real-time-etl-pipeline-with-apache-beam-and-kafka-part-1-consuming-events-74627f5465b4)
- [Building a Real-Time ETL Pipeline with Apache Beam and Kafka — Part 2: Processing and Persisting
  ](https://medium.com/@banulakumarage/building-a-real-time-etl-pipeline-with-apache-beam-and-kafka-part-2-processing-and-persisting-fa41d03756e3)
