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
package io.openliberty.guides.system;

import java.util.HashMap;
import java.util.Map;

import io.grpc.stub.StreamObserver;
// tag::importGrpcClasses[]
import io.openliberty.guides.systemproto.SystemProperties;
import io.openliberty.guides.systemproto.SystemProperty;
import io.openliberty.guides.systemproto.SystemPropertyName;
import io.openliberty.guides.systemproto.SystemPropertyValue;
import io.openliberty.guides.systemproto.SystemServiceGrpc;
// end::importGrpcClasses[]

// tag::extends[]
public class SystemService extends SystemServiceGrpc.SystemServiceImplBase {
// end::extends[]

    public SystemService() {
    }

    // tag::getProperty[]
    @Override
    public void getProperty(SystemPropertyName request,
        StreamObserver<SystemPropertyValue> observer) {

        // tag::pName[]
        String pName = request.getPropertyName();
        // end::pName[]
        // tag::pValue[]
        String pValue = System.getProperty(pName);
        // end::pValue[]
        // tag::response[]
        SystemPropertyValue value = SystemPropertyValue
                                        .newBuilder()
                                        .setPropertyValue(pValue)
                                        .build();
        // end::response[]

        // tag::next[]
        observer.onNext(value);
        // end::next[]
        // tag::complete[]
        observer.onCompleted();
        // end::complete[]

    }
    // end::getProperty[]

    // tag::getPropertiesServer[]
    @Override
    public void getPropertiesServer(
        SystemPropertyName request, StreamObserver<SystemProperty> observer) {

        String pName = request.getPropertyName();
        System.getProperties()
              .stringPropertyNames()
              .stream()
              .filter(name -> name.startsWith(pName))
              // tag::serverStreaming[]
              .forEach(name -> {
                  String pValue = System.getProperty(name);
                  SystemProperty value = SystemProperty
                    .newBuilder()
                    .setPropertyName(name)
                    .setPropertyValue(pValue)
                    .build();
                  observer.onNext(value);
                  System.out.println("server streaming sent property: " + name);
               });
               // end::serverStreaming[]
        observer.onCompleted();
        System.out.println("server streaming was completed!");
    }
    // end::getPropertiesServer[]

    // tag::getPropertiesClient[]
    @Override
    public StreamObserver<SystemPropertyName> getPropertiesClient(
        StreamObserver<SystemProperties> observer) {

        return new StreamObserver<SystemPropertyName>() {

            // tag::clientStreamingMap[]
            private Map<String, String> properties = new HashMap<String, String>();
            // end::clientStreamingMap[]
            
            // tag::receivingProperties[]
            @Override
            public void onNext(SystemPropertyName spn) {
                String pName = spn.getPropertyName();
                String pValue = System.getProperty(pName);
                System.out.println("client streaming received property: " + pName);
                properties.put(pName, pValue);
            }
            // end::receivingProperties[]

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            // tag::clientStreamingCompleted[]
            @Override
            public void onCompleted() {
                SystemProperties value = SystemProperties.newBuilder()
                                             .putAllProperties(properties)
                                             .build();
                observer.onNext(value);
                observer.onCompleted();
                System.out.println("client streaming was completed!");
            }
            // end::clientStreamingCompleted[]
        };

    }
    // end::getPropertiesClient[]

    // tag::getPropertiesBidirect[]
    @Override
    public StreamObserver<SystemPropertyName> getPropertiesBidirect(
        StreamObserver<SystemProperty> observer) {

        return new StreamObserver<SystemPropertyName>() {
            // tag::receiveBidirectionalProperties[]
            @Override
            public void onNext(SystemPropertyName spn) {
                String pName = spn.getPropertyName();
                String pValue = System.getProperty(pName);
                System.out.println("bi-directional streaming received: " + pName);
                SystemProperty value = SystemProperty.newBuilder()
                                           .setPropertyName(pName)
                                           .setPropertyValue(pValue)
                                           .build();
                observer.onNext(value);
            }
            // end::receiveBidirectionalProperties[]

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }
            // tag::bidirectionalCompleted[]
            @Override
            public void onCompleted() {
                observer.onCompleted();
                System.out.println("bi-directional streaming was completed!");
            }
            // end::bidirectionalCompleted[]
        };

    }
    // end::getPropertiesBidirect[]
}