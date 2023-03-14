// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2023 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
// end::copyright[]
package io.openliberty.guides.query.client;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.logging.Logger;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

// tag::grpcImports[]
import io.openliberty.guides.systemproto.SystemProperties;
import io.openliberty.guides.systemproto.SystemProperty;
import io.openliberty.guides.systemproto.SystemPropertyName;
import io.openliberty.guides.systemproto.SystemPropertyPrefix;
import io.openliberty.guides.systemproto.SystemPropertyValue;
import io.openliberty.guides.systemproto.SystemServiceGrpc;
import io.openliberty.guides.systemproto.SystemServiceGrpc.SystemServiceBlockingStub;
import io.openliberty.guides.systemproto.SystemServiceGrpc.SystemServiceStub;
// end::grpcImports[]

public class SystemServiceClient {
    private static Logger logger = Logger.getLogger(
        SystemServiceClient.class.getName());

    private final ManagedChannel channel;
    private final SystemServiceBlockingStub blockingStub;
    private final SystemServiceStub asyncStub;

    // tag::constructor[]
    public SystemServiceClient(String hostname, int port) {
        channel = ManagedChannelBuilder.forAddress(hostname, port)
                                       .usePlaintext()
                                       .build();
        blockingStub = SystemServiceGrpc.newBlockingStub(channel);
        asyncStub = SystemServiceGrpc.newStub(channel);
    }
    // end::constructor[]

    // tag::shutdown[]
    public void shutdown() {
        channel.shutdownNow();
    }
    // end::shutdown[]

    // tag::unary[]
    public String getProperty(String propertyName) {

        SystemPropertyName request = SystemPropertyName.newBuilder()
                                        .setPropertyName(propertyName).build();
        // tag::getProperty[]
        SystemPropertyValue response = blockingStub.getProperty(request);
        // end::getProperty[]

        return response.getPropertyValue();
    }
    // end::unary[]

    // tag::serverStreaming[]
    public Properties getServerStreamingProperties(String propertyPrefix) {

        // tag::placeholder[]
        Properties properties = new Properties();
        // end::placeholder[]
        // tag::countDownLatch1[]
        CountDownLatch countDown = new CountDownLatch(1);
        // end::countDownLatch1[]
        SystemPropertyPrefix request = SystemPropertyPrefix.newBuilder()
                                         .setPropertyPrefix(propertyPrefix).build();
        // tag::getServerStreamingProperties[]
        asyncStub.getServerStreamingProperties(
            request, new StreamObserver<SystemProperty>() {

            // tag::onNext1[]
            @Override
            public void onNext(SystemProperty value) {
                logger.info("server streaming received: "
                   + value.getPropertyName() + "=" + value.getPropertyValue());
                // tag::storeInPlaceholder[]
                properties.put(value.getPropertyName(), value.getPropertyValue());
                // end::storeInPlaceholder[]
            }
            // end::onNext1[]

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                logger.info("server streaming completed");
                // tag::countDownLatch2[]
                countDown.countDown();
                // end::countDownLatch2[]
            }
        });
        // end::getServerStreamingProperties[]

        // wait until completed
        // tag::countDownLatch3[]
        try {
            countDown.await(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // end::countDownLatch3[]

        return properties;
    }
    // end::serverStreaming[]

    // tag::clientStreaming[]
    // tag::clientPropertyPrefix[]
    public Properties getClientStreamingProperties(String propertyPrefix) {
    // end::clientPropertyPrefix[]

        // tag::countDownLatch4[]
        CountDownLatch countDown = new CountDownLatch(1);
        // end::countDownLatch4[]
        Properties properties = new Properties();

        // tag::getClientStreamingProperties[]
        StreamObserver<SystemPropertyName> stream =
            // tag::clientStreamingAsyncStub[]
            asyncStub.getClientStreamingProperties(
            // end::clientStreamingAsyncStub[]
            new StreamObserver<SystemProperties>() {

                @Override
                public void onNext(SystemProperties value) {
                    logger.info("client streaming received a map that has "
                        + value.getPropertiesCount() + " properties");
                    properties.putAll(value.getPropertiesMap());
                }

                @Override
                public void onError(Throwable t) {
                    t.printStackTrace();
                }

                @Override
                public void onCompleted() {
                    logger.info("client streaming completed");
                    // tag::countDownLatch5[]
                    countDown.countDown();
                    // end::countDownLatch5[]
                }
            });
        // end::getClientStreamingProperties[]

        // collect the property names starting with propertyPrefix.
        // tag::clientCollectProperties[]
        List<String> keys = System.getProperties().stringPropertyNames().stream()
                                  .filter(k -> k.startsWith(propertyPrefix))
                                  .collect(Collectors.toList());
        // end::clientCollectProperties[]

        // send messages to the server
        keys.stream()
            // tag::clientMessage1[]
            .map(k -> SystemPropertyName.newBuilder().setPropertyName(k).build())
            // end::clientMessage1[]
            // tag::streamOnNext1[]
            .forEach(stream::onNext);
            // end::streamOnNext1[]
        // tag::clientCompleted1[]
        stream.onCompleted();
        // end::clientCompleted1[]

        // wait until completed
        // tag::countDownLatch6[]
        try {
            countDown.await(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // end::countDownLatch6[]

        return properties;
    }
    // end::clientStreaming[]

    // tag::bidirectionalStreaming[]
    // tag::bidiPropertyPrefix[]
    public Properties getBidirectionalProperties(String propertyPrefix) {
    // end::bidiPropertyPrefix[]

        Properties properties = new Properties();
        // tag::countDownLatch7[]
        CountDownLatch countDown = new CountDownLatch(1);
        // end::countDownLatch7[]

        // tag::getBidirectionalProperties[]
        StreamObserver<SystemPropertyName> stream =
            // tag::getJavaPropertiesAsyncStub[]
            asyncStub.getBidirectionalProperties(
            // end::getJavaPropertiesAsyncStub[]
            new StreamObserver<SystemProperty>() {

                // tag::onNext2[]
                @Override
                public void onNext(SystemProperty value) {
                    logger.info("bidirectional streaming received: "
                        + value.getPropertyName() + "=" + value.getPropertyValue());
                    properties.put(value.getPropertyName(),
                                    value.getPropertyValue());
                }
                // end::onNext2[]

                @Override
                public void onError(Throwable t) {
                    t.printStackTrace();
                }

                @Override
                public void onCompleted() {
                    logger.info("bidirectional streaming completed");
                    // tag::countDownLatch8[]
                    countDown.countDown();
                    // end::countDownLatch8[]
                }
            });
        // end::getBidirectionalProperties[]

        // collect the property names starting with propertyPrefix
        // tag::bidiCollectProperties[]
        List<String> keys = System.getProperties().stringPropertyNames().stream()
                                  .filter(k -> k.startsWith(propertyPrefix))
                                  .collect(Collectors.toList());
        // end::bidiCollectProperties[]

        // post messages to the server
        keys.stream()
              // tag::clientMessage2[]
            .map(k -> SystemPropertyName.newBuilder().setPropertyName(k).build())
            // end::clientMessage2[]
            // tag::streamOnNext2[]
            .forEach(stream::onNext);
            // end::streamOnNext2[]
        // tag::clientCompleted2[]
        stream.onCompleted();
        // end::clientCompleted2[]

        // wait until completed
        // tag::countDownLatch9[]
        try {
            countDown.await(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // end::countDownLatch9[]

        return properties;
    }
    // end::bidirectionalStreaming[]
}
