#!/bin/bash
set -euxo pipefail

mvn -ntp -pl systemproto -q clean install

mvn -ntp -Dhttp.keepAlive=false \
    -Dmaven.wagon.http.pool=false \
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=120 \
    -pl system -q clean package liberty:create liberty:install-feature liberty:deploy

mvn -ntp -Dhttp.keepAlive=false \
    -Dmaven.wagon.http.pool=false \
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=120 \
    -pl query -q clean package liberty:create liberty:install-feature liberty:deploy

mvn -ntp -pl system -ntp liberty:start
mvn -ntp -pl query -ntp liberty:start

mvn -ntp -Dhttp.keepAlive=false \
    -Dmaven.wagon.http.pool=false \
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=120 \
    -pl query -ntp failsafe:integration-test

mvn -ntp -pl query -ntp failsafe:verify

mvn -ntp -pl system -ntp liberty:stop
mvn -ntp -pl query -ntp liberty:stop
