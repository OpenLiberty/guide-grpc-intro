#!/bin/bash
set -euxo pipefail

mvn -pl systemproto install

mvn -Dhttp.keepAlive=false \
    -Dmaven.wagon.http.pool=false \
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=120 \
    -pl system liberty:create liberty:install-feature liberty:deploy
mvn -Dhttp.keepAlive=false \
    -Dmaven.wagon.http.pool=false \
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=120 \
    -pl query liberty:create liberty:install-feature liberty:deploy

mvn -pl system liberty:start
mvn -pl query liberty:start

mvn -Dhttp.keepAlive=false \
    -Dmaven.wagon.http.pool=false \
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=120 \
    -pl query failsafe:integration-test

mvn -pl query failsafe:verify

mvn -pl system liberty:stop
mvn -pl query liberty:stop
