// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2022 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - Initial implementation
 *******************************************************************************/
// end::copyright[]
package io.openliberty.guides.query;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import io.openliberty.guides.systemproto.SystemProperties;
import io.openliberty.guides.systemproto.SystemProperty;
import io.openliberty.guides.systemproto.SystemPropertyName;
import io.openliberty.guides.systemproto.SystemPropertyValue;
import io.openliberty.guides.systemproto.SystemServiceGrpc;
import io.openliberty.guides.systemproto.SystemServiceGrpc.SystemServiceBlockingStub;
import io.openliberty.guides.systemproto.SystemServiceGrpc.SystemServiceStub;

@ApplicationScoped
@Path("/properties")
public class PropertiesResource {

    @Inject
    @ConfigProperty(name = "system.hostname", defaultValue = "localhost")
    String SYSTEM_HOST;

    @Inject
    @ConfigProperty(name = "system.port", defaultValue = "9080")
    int SYSTEM_PORT;

    @GET
    @Path("/{propertyName}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPropertiesString(@PathParam("propertyName") String propertyName) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress(SYSTEM_HOST, SYSTEM_PORT)
                                     .usePlaintext().build();
        SystemServiceBlockingStub client = SystemServiceGrpc.newBlockingStub(channel);
        SystemPropertyName request = SystemPropertyName.newBuilder()
                                             .setPropertyName(propertyName).build();
        SystemPropertyValue response = client.getProperty(request);
        channel.shutdownNow();
        return response.getPropertyValue();

    }

    @GET
    @Path("/os")
    @Produces(MediaType.APPLICATION_JSON)
    public Properties getOSProperties() {

        ManagedChannel channel = ManagedChannelBuilder.forAddress(SYSTEM_HOST, SYSTEM_PORT)
                                     .usePlaintext().build();
        SystemServiceStub client = SystemServiceGrpc.newStub(channel);

        Properties properties = new Properties();
        CountDownLatch countDown = new CountDownLatch(1);
        SystemPropertyName request = SystemPropertyName.newBuilder()
                                         .setPropertyName("os.").build();
        client.getPropertiesServer(request, new StreamObserver<SystemProperty>() {

            @Override
            public void onNext(SystemProperty value) {
                System.out.println("server streaming received: " 
                   + value.getPropertyName() + "=" + value.getPropertyValue());
                properties.put(value.getPropertyName(), value.getPropertyValue());
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("server streaming completed");
                countDown.countDown();
            }

        });

          // wait until completed
        try {
            countDown.await(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        channel.shutdownNow();

        return properties;
    }

    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public Properties getUserProperties() {

        ManagedChannel channel = ManagedChannelBuilder.forAddress(SYSTEM_HOST, SYSTEM_PORT)
                                     .usePlaintext().build();
        SystemServiceStub client = SystemServiceGrpc.newStub(channel);

        CountDownLatch countDown = new CountDownLatch(1);
        
        Properties properties = new Properties();
        StreamObserver<SystemPropertyName> v = client.getPropertiesClient(
            new StreamObserver<SystemProperties>() {

                @Override
                public void onNext(SystemProperties value) {
                    System.out.println("client streaming received a map that has " 
                        + value.getPropertiesCount() + " properties");
                    properties.putAll(value.getPropertiesMap());
                }

                @Override
                public void onError(Throwable t) {
                    t.printStackTrace();
                }

                @Override
                public void onCompleted() {
                    System.out.println("client streaming completed");
                    countDown.countDown();
                }

            });

        // collect the property names starting with user.
        List<String> keys = System.getProperties()
                                  .stringPropertyNames()
                                  .stream()
                                  .filter(k -> k.startsWith("user."))
                                  .collect(Collectors.toList());

        // send messages to the server
        keys.stream()
              .map(k -> SystemPropertyName.newBuilder().setPropertyName(k).build())
              .forEach(v::onNext);
          v.onCompleted();

          // wait until completed
        try {
            countDown.await(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        channel.shutdownNow();

        return properties;
    }

    @GET
    @Path("/java")
    @Produces(MediaType.APPLICATION_JSON)
    public Properties getJavaProperties() {

        ManagedChannel channel = ManagedChannelBuilder.forAddress(SYSTEM_HOST, SYSTEM_PORT)
                                      .usePlaintext().build();
        SystemServiceStub client = SystemServiceGrpc.newStub(channel);
        
        Properties properties = new Properties();
        CountDownLatch countDown = new CountDownLatch(1);
        StreamObserver<SystemPropertyName> v = client.getPropertiesBidirect(
                new StreamObserver<SystemProperty>() {

                    @Override
                    public void onNext(SystemProperty value) {
                        System.out.println("bidirectional streaming received: "
                            + value.getPropertyName() + "=" + value.getPropertyValue());
                        properties.put(value.getPropertyName(), value.getPropertyValue());
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("bidirectional streaming completed");
                        countDown.countDown();
                    }
                    
                });

        // collect the property names starting with java
        List<String> keys = System.getProperties()
                                  .stringPropertyNames()
                                  .stream()
                                  .filter(k -> k.startsWith("java."))
                                  .collect(Collectors.toList());

        // post messages to the server
        keys.stream()
              .map(k -> SystemPropertyName.newBuilder().setPropertyName(k).build())
              .forEach(v::onNext);
          v.onCompleted();

          // wait until completed
        try {
            countDown.await(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        channel.shutdownNow();

        return properties;
    }
}