package io.openliberty.guides.system;

import java.util.HashMap;
import java.util.Map;

import io.grpc.stub.StreamObserver;
import io.openliberty.guides.systemproto.SystemProperties;
import io.openliberty.guides.systemproto.SystemProperty;
import io.openliberty.guides.systemproto.SystemPropertyName;
import io.openliberty.guides.systemproto.SystemPropertyValue;
import io.openliberty.guides.systemproto.SystemServiceGrpc;

public class SystemService extends SystemServiceGrpc.SystemServiceImplBase {

    public SystemService() {
    }

    @Override
    public void getProperty(SystemPropertyName request, StreamObserver<SystemPropertyValue> observer) {

        String pName = request.getPropertyName();
        String pValue = System.getProperty(pName);
        SystemPropertyValue value = SystemPropertyValue
                                        .newBuilder()
                                        .setPropertyValue(pValue)
                                        .build();
        observer.onNext(value);
        observer.onCompleted();

    }

    @Override
    public void getPropertiesServer(
        SystemPropertyName request, StreamObserver<SystemProperty> observer) {

        String pName = request.getPropertyName();
        System.getProperties()
              .stringPropertyNames()
              .stream()
              .filter(name -> name.startsWith(pName))
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
        observer.onCompleted();
        System.out.println("server streaming was completed!");
    }

    @Override
    public StreamObserver<SystemPropertyName> getPropertiesClient(
        StreamObserver<SystemProperties> observer) {

        return new StreamObserver<SystemPropertyName>() {

            private Map<String, String> properties = new HashMap<String, String>();
            
            @Override
            public void onNext(SystemPropertyName spn) {
                String pName = spn.getPropertyName();
                String pValue = System.getProperty(pName);
                System.out.println("client streaming received property: " + pName);
                properties.put(pName, pValue);
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                SystemProperties value = SystemProperties.newBuilder()
                                             .putAllProperties(properties)
                                             .build();
                observer.onNext(value);
                observer.onCompleted();
                System.out.println("client streaming was completed!");
            }
        };

    }

    @Override
    public StreamObserver<SystemPropertyName> getPropertiesBidirect(
        StreamObserver<SystemProperty> observer) {

        return new StreamObserver<SystemPropertyName>() {

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

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                observer.onCompleted();
                System.out.println("bi-directional streaming was completed!");
            }
        };

    }

}