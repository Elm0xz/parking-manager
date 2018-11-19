#!/bin/bash

if [[ $1 == "dev" ]]; then
    mvn clean package
    java -jar target/parking-manager-0.0.1-SNAPSHOT.jar
else
    mvn spring-boot:run
fi