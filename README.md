# TrueLayer Shakespearean Pokemon API: Backend Engineer - Credit Challenge

## Prerequisites

- Java 8
- Docker (Optional)

## Build (Unix)

#### Build and run:

```
./mvnw spring-boot:run
```

#### Or build executable jar and then run:

```
./mvnw clean package

java -jar target/truelayer-shakespearean-pokemon.jar
```

Note: tests can be skipped by using `-DskipTests`

## Build (Windows)

#### Build and run:

```
mvnw.cmd spring-boot:run
```

#### Or build executable jar and then run:

```
mvnw.cmd clean package

java -jar target\truelayer-shakespearean-pokemon.jar
```

## Build (Docker)

#### Build docker image and then run:

```
docker build -t truelayer-shakespearean-pokemon .

docker run -p 8080:8080 truelayer-shakespearean-pokemon
```

## Endpoints

Get Pokemon's description in Shakespearean: `GET http://localhost:8080/pokemon/<pokemon-name>`

> Note: The Shakespearean API limits the number of API calls to 60 a day with a distribution of 5 per hour.