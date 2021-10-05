package io.openliberty.guides.system;

import java.util.Properties;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import io.openliberty.guides.system.SystemServiceGrpc;
import io.openliberty.guides.system.SystemServiceRequest;
import io.openliberty.guides.system.SystemServiceResponse;
import io.openliberty.guides.system.SystemServiceGrpc.SystemServiceBlockingStub;
import io.openliberty.guides.system.SystemService;

// @RequestScoped
@Path("/properties")
public class PropertiesResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Properties getProperties() {
        // Created a protocol buffer SystemService request
        SystemServiceRequest systemRequest = SystemServiceRequest.newBuilder().build();
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9080).usePlaintext().build();
        SystemServiceGrpc.SystemServiceBlockingStub systemServiceClient = SystemServiceGrpc.newBlockingStub(channel);

        // Call the RPC and get back the SystemService response (Protocol buffer)
        // Note the call is made as if it was a method from a local object
        SystemServiceResponse systemServiceResponse = systemServiceClient.getSystem(systemRequest);
        channel.shutdownNow();
        // return String.format("os:%s, java_version:%s",
        // systemServiceResponse.getOs(),
        // systemServiceResponse.getJavaVersion());

        Properties props = new Properties();
        props.setProperty("os.name", systemServiceResponse.getOs());
        props.setProperty("java.version", systemServiceResponse.getJavaVersion());
        return props;
    }
}