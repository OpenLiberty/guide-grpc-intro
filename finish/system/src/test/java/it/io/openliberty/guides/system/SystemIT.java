// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2023 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - Initial implementation
 *******************************************************************************/
// end::copyright[]
package it.io.openliberty.guides.system;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import org.mockito.ArgumentCaptor;

import io.openliberty.guides.system.SystemService;
import io.openliberty.guides.systemproto.SystemProperties;
import io.openliberty.guides.systemproto.SystemProperty;
import io.openliberty.guides.systemproto.SystemPropertyName;
import io.openliberty.guides.systemproto.SystemPropertyPrefix;
import io.openliberty.guides.systemproto.SystemPropertyValue;
import io.openliberty.guides.systemproto.SystemServiceGrpc;
import io.openliberty.guides.systemproto.SystemServiceGrpc.SystemServiceBlockingStub;

import io.grpc.testing.GrpcCleanupRule;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.StreamObserver;
import io.grpc.ManagedChannel;

import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SystemIT {

    @Rule
    public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

    private SystemServiceBlockingStub blockingStub;
    private ManagedChannel inProcessChannel;

    @Before
    public void setup() throws Exception {
        String serverName = InProcessServerBuilder.generateName();

        grpcCleanup.register(InProcessServerBuilder
            .forName(serverName).directExecutor()
            .addService(new SystemService()).build().start());

        blockingStub = SystemServiceGrpc.newBlockingStub(
            grpcCleanup.register(InProcessChannelBuilder
                .forName(serverName).directExecutor().build()));

        inProcessChannel = grpcCleanup.register(InProcessChannelBuilder
            .forName(serverName).directExecutor().build());
    }

    @Test
    public void testGetProperty() throws Exception {

        SystemPropertyName request = SystemPropertyName
                                        .newBuilder()
                                        .setPropertyName("os.name")
                                        .build();
        SystemPropertyValue response = blockingStub.getProperty(request);

        assertEquals(System.getProperty("os.name"), response.getPropertyValue());
    }

    @Test
    public void testGetServerStreamingProperties() throws Exception {

        SystemPropertyPrefix request = SystemPropertyPrefix
                                        .newBuilder()
                                        .setPropertyPrefix("os.")
                                        .build();
        Iterator<SystemProperty> response =
            blockingStub.getServerStreamingProperties(request);

        while (response.hasNext()) {
            SystemProperty systemProperty = response.next();
            assertEquals(System.getProperty(systemProperty.getPropertyName()),
                         systemProperty.getPropertyValue());
        }
    }

    @Test
    public void testGetClientStreamingProperties() {

        @SuppressWarnings("unchecked")
        StreamObserver<SystemProperties> responseObserver =
            (StreamObserver<SystemProperties>) mock(StreamObserver.class);
        SystemServiceGrpc.SystemServiceStub stub =
            SystemServiceGrpc.newStub(inProcessChannel);
        ArgumentCaptor<SystemProperties> systemPropertiesCaptor =
            ArgumentCaptor.forClass(SystemProperties.class);
        StreamObserver<SystemPropertyName> requestObserver =
            stub.getClientStreamingProperties(responseObserver);

        List<String> keys = System.getProperties().stringPropertyNames().stream()
                                  .filter(k -> k.startsWith("user."))
                                  .collect(Collectors.toList());
        keys.stream()
            .map(k -> SystemPropertyName.newBuilder().setPropertyName(k).build())
            .forEach(requestObserver::onNext);

        requestObserver.onCompleted();
        verify(responseObserver, timeout(100)).onNext(systemPropertiesCaptor.capture());

        SystemProperties systemProperties = systemPropertiesCaptor.getValue();
        systemProperties.getPropertiesMap()
               .forEach((propertyName, propertyValue) ->
               assertEquals(System.getProperty(propertyName), propertyValue));

        verify(responseObserver, timeout(100)).onCompleted();
        verify(responseObserver, never()).onError(any(Throwable.class));
    }

    @Test
    public void testGetBidirectionalProperties() {

        int timesOnNext = 0;

        @SuppressWarnings("unchecked")
        StreamObserver<SystemProperty> responseObserver =
            (StreamObserver<SystemProperty>) mock(StreamObserver.class);
        SystemServiceGrpc.SystemServiceStub stub =
            SystemServiceGrpc.newStub(inProcessChannel);
        StreamObserver<SystemPropertyName> requestObserver =
            stub.getBidirectionalProperties(responseObserver);
        verify(responseObserver, never()).onNext(any(SystemProperty.class));

        List<String> keys = System.getProperties().stringPropertyNames().stream()
                                  .filter(k -> k.startsWith("java."))
                                  .collect(Collectors.toList());

        for (int i = 0; i < keys.size(); i++) {
            SystemPropertyName spn = SystemPropertyName
                                        .newBuilder()
                                        .setPropertyName(keys.get(i))
                                        .build();
            requestObserver.onNext(spn);
            ArgumentCaptor<SystemProperty> systemPropertyCaptor =
                ArgumentCaptor.forClass(SystemProperty.class);
            verify(responseObserver, timeout(100).times(++timesOnNext))
                .onNext(systemPropertyCaptor.capture());
            SystemProperty systemProperty = systemPropertyCaptor.getValue();
            assertEquals(System.getProperty(systemProperty.getPropertyName()),
                         systemProperty.getPropertyValue());
        }

        requestObserver.onCompleted();
        verify(responseObserver, timeout(100)).onCompleted();
        verify(responseObserver, never()).onError(any(Throwable.class));
    }

}
