#!/bin/bash
set -euxo pipefail
./mvnw -version

./mvnw -ntp -pl systemproto -q clean install

./mvnw -ntp -pl system verify

./mvnw -ntp -Dhttp.keepAlive=false \
    -Dmaven.wagon.http.pool=false \
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=120 \
    -pl system -q clean package liberty:create liberty:install-feature liberty:deploy

./mvnw -ntp -Dhttp.keepAlive=false \
    -Dmaven.wagon.http.pool=false \
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=120 \
    -pl query -q clean package liberty:create liberty:install-feature liberty:deploy

./mvnw -ntp -pl system liberty:start
./mvnw -ntp -pl query liberty:start

./mvnw -ntp -Dhttp.keepAlive=false \
    -Dmaven.wagon.http.pool=false \
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=120 \
    -pl query failsafe:integration-test

./mvnw -ntp -pl query failsafe:verify

./mvnw -ntp -pl system liberty:stop
./mvnw -ntp -pl query liberty:stop
