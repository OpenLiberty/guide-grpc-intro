#!/bin/bash
set -euxo pipefail

mvn -pl systemproto -q clean 
mvn -pl systemproto -q protobuf:compile
mvn -pl systemproto -q protobuf:compile-custom

sed -i  "s;javax.;jakarta.;g" systemproto/target/generated-sources/protobuf/grpc-java/io/openliberty/guides/systemproto/SystemServiceGrpc.java

mvn -pl systemproto compile package install

mvn -Dhttp.keepAlive=false \
    -Dmaven.wagon.http.pool=false \
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=120 \
    -pl system -q clean package liberty:create liberty:install-feature liberty:deploy

mvn -Dhttp.keepAlive=false \
    -Dmaven.wagon.http.pool=false \
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=120 \
    -pl query -q clean package liberty:create liberty:install-feature liberty:deploy

mvn -pl system -ntp liberty:start
mvn -pl query -ntp liberty:start

mvn -Dhttp.keepAlive=false \
    -Dmaven.wagon.http.pool=false \
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=120 \
    -pl query -ntp failsafe:integration-test

mvn -pl query -ntp failsafe:verify

mvn -pl system -ntp liberty:stop
mvn -pl query -ntp liberty:stop
