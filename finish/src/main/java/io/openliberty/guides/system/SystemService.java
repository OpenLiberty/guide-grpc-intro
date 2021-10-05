package io.openliberty.guides.system;

import java.util.Properties;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import io.openliberty.guides.system.SystemServiceGrpc;
import io.openliberty.guides.system.SystemServiceRequest;
import io.openliberty.guides.system.SystemServiceResponse;

public class SystemService extends SystemServiceGrpc.SystemServiceImplBase {

    // required public zero-arg constructor
    public SystemService() {
    }

    /**
     * Unary RPC example
     */
    @Override
    public void getSystem(SystemServiceRequest request, StreamObserver<SystemServiceResponse> responseObserver) {
        String os = System.getProperty("os.name");
        String javaVersion = System.getProperty("java.version");
        // String name = request

        // Create response
        SystemServiceResponse response = SystemServiceResponse.newBuilder().setOs(os).setJavaVersion(javaVersion)
                .build();

        // Send the response
        responseObserver.onNext(response);

        // Complete the RPC call
        responseObserver.onCompleted();
    }
}