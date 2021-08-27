// package io.openliberty.guides.system;

// import io.grpc.Server;
// import io.grpc.ServerBuilder;

// import java.io.IOException;
// import java.util.logging.Logger;

// import javax.inject.Inject;

// import org.eclipse.microprofile.config.inject.ConfigProperty;

// public class SystemServer {

// @Inject
// @ConfigProperty(name = "default.http.port")
// String DEFAULT_PORT;

// private static final Logger logger =
// Logger.getLogger(SystemServer.class.getName());

// private Server server;

// private void start() throws IOException {
// /* The port on which the server should run */

// server = ServerBuilder.forPort(Integer.parseInt(DEFAULT_PORT)).addService(new
// SystemService()).build().start();
// logger.info("Server started, listening on " + DEFAULT_PORT);
// Runtime.getRuntime().addShutdownHook(new Thread(() -> {
// // Use stderr here since the logger may have been reset by its JVM shutdown
// // hook.
// logger.info("*** shutting down gRPC server since JVM is shutting down");
// SystemServer.this.stop();
// logger.info("*** server shut down");
// }));
// }

// private void stop() {
// if (server != null) {
// server.shutdown();
// }
// }

// /**
// * Await termination on the main thread since the grpc library uses daemon
// * threads.
// */
// private void blockUntilShutdown() throws InterruptedException {
// if (server != null) {
// server.awaitTermination();
// }
// }

// public static void main(String[] args) throws IOException,
// InterruptedException {
// final SystemServer server = new SystemServer();
// server.start();
// server.blockUntilShutdown();
// }

// }
