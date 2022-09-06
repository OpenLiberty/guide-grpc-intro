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
import io.openliberty.guides.systemproto.SystemPropertyPrefix;
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
    public void getProperty(
        SystemPropertyPrefix request, StreamObserver<SystemPropertyValue> observer) {

        // tag::pPrefix[]
        String pPrefix = request.getPropertyPrefix();
        // end::pPrefix[]
        // tag::pValue[]
        String pValue = System.getProperty(pPrefix);
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
        SystemPropertyPrefix request, StreamObserver<SystemProperty> observer) {

        // tag::prefix[]
        String prefix = request.getPropertyPrefix();
        // end::prefix[]
        System.getProperties()
              .stringPropertyNames()
              .stream()
              // tag::filter[]
              .filter(name -> name.startsWith(prefix))
              // end::filter[]
              .forEach(name -> {
                  String pValue = System.getProperty(name);
                  // tag::serverMessage[]
                  SystemProperty value = SystemProperty
                      .newBuilder()
                      .setPropertyPrefix(name)
                      .setPropertyValue(pValue)
                      .build();
                  // end::serverMessage[]
                  // tag::serverNext1[]
                  observer.onNext(value);
                  // end::serverNext1[]
                  System.out.println("server streaming sent property: " + name);
               });
        // tag::serverComplete[]
        observer.onCompleted();
        // end::serverComplete[]
        System.out.println("server streaming was completed!");
    }
    // end::getPropertiesServer[]

    // tag::getPropertiesClient[]
    @Override
    public StreamObserver<SystemPropertyPrefix> getPropertiesClient(
        StreamObserver<SystemProperties> observer) {

        // tag::streamObserverClient[]
        return new StreamObserver<SystemPropertyPrefix>() {

            // tag::clientStreamingMap[]
            private Map<String, String> properties = new HashMap<String, String>();
            // end::clientStreamingMap[]

            // tag::receivingProperties[]
            @Override
            public void onNext(SystemPropertyPrefix spn) {
                String pPrefix = spn.getPropertyPrefix();
                String pValue = System.getProperty(pPrefix);
                System.out.println("client streaming received property: " + pPrefix);
                properties.put(pPrefix, pValue);
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
        // end::streamObserverClient[]
    }
    // end::getPropertiesClient[]

    // tag::getPropertiesBidirect[]
    @Override
    public StreamObserver<SystemPropertyPrefix> getPropertiesBidirect(
        StreamObserver<SystemProperty> observer) {

        // tag::streamObserverBidirectional[]
        return new StreamObserver<SystemPropertyPrefix>() {
            // tag::receiveBidirectionalProperties[]
            @Override
            public void onNext(SystemPropertyPrefix spn) {
                String pPrefix = spn.getPropertyPrefix();
                String pValue = System.getProperty(pPrefix);
                System.out.println("bi-directional streaming received: " + pPrefix);
                // tag::systemPropertyMessage[]
                SystemProperty value = SystemProperty.newBuilder()
                                           .setPropertyPrefix(pPrefix)
                                           .setPropertyValue(pValue)
                                           .build();
                // end::systemPropertyMessage[]
                // tag::serverNext2[]
                observer.onNext(value);
                // end::serverNext2[]
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
        // end::streamObserverBidirectional[]
    }
    // end::getPropertiesBidirect[]
}