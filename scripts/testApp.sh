#!/bin/bash
set -euxo pipefail

mvn -ntp -pl systemproto -q clean 
mvn -ntp -pl systemproto -q protobuf:compile
mvn -ntp -pl systemproto -q protobuf:compile-custom

sed -i  "s;javax.;jakarta.;g" systemproto/target/generated-sources/protobuf/grpc-java/io/openliberty/guides/systemproto/SystemServiceGrpc.java

mvn -ntp -pl systemproto compile package install

mvn -ntp -Dhttp.keepAlive=false \
    -Dmaven.wagon.http.pool=false \
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=120 \
    -pl system -q clean package liberty:create liberty:install-feature liberty:deploy

mvn -ntp -Dhttp.keepAlive=false \
    -Dmaven.wagon.http.pool=false \
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=120 \
    -pl query -q clean package liberty:create liberty:install-feature liberty:deploy

mvn -ntp -pl system liberty:start
mvn -ntp -pl query liberty:start

mvn -ntp -Dhttp.keepAlive=false \
    -Dmaven.wagon.http.pool=false \
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=120 \
    -pl query failsafe:integration-test

mvn -ntp -pl query failsafe:verify

mvn -ntp -pl system liberty:stop
mvn -ntp -pl query liberty:stop
