// package io.openliberty.guides.system;

// import io.openliberty.*;
// import io.openliberty.guides.system.SystemServiceGrpc;
// import io.openliberty.guides.system.SystemServiceRequest;
// import io.openliberty.guides.system.SystemServiceResponse;
// import
// io.openliberty.guides.system.SystemServiceGrpc.SystemServiceBlockingStub;
// import io.openliberty.guides.system.SystemService;

// import java.io.IOException;
// import java.io.PrintWriter;
// import java.util.logging.Logger;

// import javax.annotation.PostConstruct;
// import javax.servlet.ServletException;
// import javax.servlet.annotation.WebServlet;
// import javax.servlet.http.HttpServlet;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import javax.ws.rs.Path;

// import io.grpc.ManagedChannel;
// import io.grpc.ManagedChannelBuilder;

// @WebServlet(name = "system", urlPatterns = { "/" })
// public class SystemServlet extends HttpServlet {

// // private static final Logger logger =
// // Logger.getLogger(SystemClientServlet.class.getName());
// private SystemServiceGrpc.SystemServiceBlockingStub systemServiceClient;

// // @PostConstruct
// private void startService() {
// // logger.info("about to build gRPC channel");

// // The client side gRPC code will use a gRPC ManagedChannel to connect to the
// // gRPC server
// // side service. Server side is configured to be listening on port 9080.
// // todo if 9443 remove plaintext
// ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",
// 9080).usePlaintext().build();

// // build the stub for client to use to make gRPC calls to the server side
// // service
// systemServiceClient = SystemServiceGrpc.newBlockingStub(channel);

// // logger.info("gRPC channel initialized!");
// // logger.info("channel: " + channel);
// // logger.info("stub: " + systemServiceClient);
// }

// /**
// * Stop the gRPC channel
// */
// private void stopService(ManagedChannel channel) {
// channel.shutdownNow();
// }

// @Override
// protected void doGet(HttpServletRequest request, HttpServletResponse
// response)
// throws ServletException, IOException {

// // Created a protocol buffer SystemService request
// SystemServiceRequest systemRequest =
// SystemServiceRequest.newBuilder().build();

// // Call the RPC and get back the SystemService response (Protocol buffer)
// // Note the call is made as if it was a method from a local object
// SystemServiceResponse systemServiceResponse =
// systemServiceClient.getSystem(systemRequest);
// response.getWriter().println("os:" + systemServiceResponse.getOs());

// }
// }