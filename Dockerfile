#sets runtime environment
FROM openjdk:8-jre-alpine

#Exposes a port
EXPOSE 8080

# Runs shell, required for alpine openjdk
RUN apk add --no-cache bash

# Variables stored in the Docker service
ENV API_NAME TRUELAYER_SHAKESPEAREAN_POKEMON_APPLICATION

# Adds executable jar and yaml config for app
ADD ./target/truelayer-shakespearean-pokemon.jar /truelayer-shakespearean-pokemon.jar

#Executes application
CMD java -jar /truelayer-shakespearean-pokemon.jar