package io.openliberty.guides.systemproto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * tag::SystemService[]
 * </pre>
 */
// tag::generated[]
@javax.annotation.Generated(
// end::generated[]
    value = "by gRPC proto compiler (version 1.51.0)",
    comments = "Source: SystemService.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class SystemServiceGrpc {

  private SystemServiceGrpc() {}

  public static final String SERVICE_NAME = "io.openliberty.guides.systemproto.SystemService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.openliberty.guides.systemproto.SystemPropertyName,
      io.openliberty.guides.systemproto.SystemPropertyValue> getGetPropertyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getProperty",
      requestType = io.openliberty.guides.systemproto.SystemPropertyName.class,
      responseType = io.openliberty.guides.systemproto.SystemPropertyValue.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.openliberty.guides.systemproto.SystemPropertyName,
      io.openliberty.guides.systemproto.SystemPropertyValue> getGetPropertyMethod() {
    io.grpc.MethodDescriptor<io.openliberty.guides.systemproto.SystemPropertyName, io.openliberty.guides.systemproto.SystemPropertyValue> getGetPropertyMethod;
    if ((getGetPropertyMethod = SystemServiceGrpc.getGetPropertyMethod) == null) {
      synchronized (SystemServiceGrpc.class) {
        if ((getGetPropertyMethod = SystemServiceGrpc.getGetPropertyMethod) == null) {
          SystemServiceGrpc.getGetPropertyMethod = getGetPropertyMethod =
              io.grpc.MethodDescriptor.<io.openliberty.guides.systemproto.SystemPropertyName, io.openliberty.guides.systemproto.SystemPropertyValue>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getProperty"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.openliberty.guides.systemproto.SystemPropertyName.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.openliberty.guides.systemproto.SystemPropertyValue.getDefaultInstance()))
              .setSchemaDescriptor(new SystemServiceMethodDescriptorSupplier("getProperty"))
              .build();
        }
      }
    }
    return getGetPropertyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.openliberty.guides.systemproto.SystemPropertyPrefix,
      io.openliberty.guides.systemproto.SystemProperty> getGetServerStreamingPropertiesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getServerStreamingProperties",
      requestType = io.openliberty.guides.systemproto.SystemPropertyPrefix.class,
      responseType = io.openliberty.guides.systemproto.SystemProperty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<io.openliberty.guides.systemproto.SystemPropertyPrefix,
      io.openliberty.guides.systemproto.SystemProperty> getGetServerStreamingPropertiesMethod() {
    io.grpc.MethodDescriptor<io.openliberty.guides.systemproto.SystemPropertyPrefix, io.openliberty.guides.systemproto.SystemProperty> getGetServerStreamingPropertiesMethod;
    if ((getGetServerStreamingPropertiesMethod = SystemServiceGrpc.getGetServerStreamingPropertiesMethod) == null) {
      synchronized (SystemServiceGrpc.class) {
        if ((getGetServerStreamingPropertiesMethod = SystemServiceGrpc.getGetServerStreamingPropertiesMethod) == null) {
          SystemServiceGrpc.getGetServerStreamingPropertiesMethod = getGetServerStreamingPropertiesMethod =
              io.grpc.MethodDescriptor.<io.openliberty.guides.systemproto.SystemPropertyPrefix, io.openliberty.guides.systemproto.SystemProperty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getServerStreamingProperties"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.openliberty.guides.systemproto.SystemPropertyPrefix.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.openliberty.guides.systemproto.SystemProperty.getDefaultInstance()))
              .setSchemaDescriptor(new SystemServiceMethodDescriptorSupplier("getServerStreamingProperties"))
              .build();
        }
      }
    }
    return getGetServerStreamingPropertiesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.openliberty.guides.systemproto.SystemPropertyName,
      io.openliberty.guides.systemproto.SystemProperties> getGetClientStreamingPropertiesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getClientStreamingProperties",
      requestType = io.openliberty.guides.systemproto.SystemPropertyName.class,
      responseType = io.openliberty.guides.systemproto.SystemProperties.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<io.openliberty.guides.systemproto.SystemPropertyName,
      io.openliberty.guides.systemproto.SystemProperties> getGetClientStreamingPropertiesMethod() {
    io.grpc.MethodDescriptor<io.openliberty.guides.systemproto.SystemPropertyName, io.openliberty.guides.systemproto.SystemProperties> getGetClientStreamingPropertiesMethod;
    if ((getGetClientStreamingPropertiesMethod = SystemServiceGrpc.getGetClientStreamingPropertiesMethod) == null) {
      synchronized (SystemServiceGrpc.class) {
        if ((getGetClientStreamingPropertiesMethod = SystemServiceGrpc.getGetClientStreamingPropertiesMethod) == null) {
          SystemServiceGrpc.getGetClientStreamingPropertiesMethod = getGetClientStreamingPropertiesMethod =
              io.grpc.MethodDescriptor.<io.openliberty.guides.systemproto.SystemPropertyName, io.openliberty.guides.systemproto.SystemProperties>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getClientStreamingProperties"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.openliberty.guides.systemproto.SystemPropertyName.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.openliberty.guides.systemproto.SystemProperties.getDefaultInstance()))
              .setSchemaDescriptor(new SystemServiceMethodDescriptorSupplier("getClientStreamingProperties"))
              .build();
        }
      }
    }
    return getGetClientStreamingPropertiesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.openliberty.guides.systemproto.SystemPropertyName,
      io.openliberty.guides.systemproto.SystemProperty> getGetBidirectPropertiesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getBidirectProperties",
      requestType = io.openliberty.guides.systemproto.SystemPropertyName.class,
      responseType = io.openliberty.guides.systemproto.SystemProperty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<io.openliberty.guides.systemproto.SystemPropertyName,
      io.openliberty.guides.systemproto.SystemProperty> getGetBidirectPropertiesMethod() {
    io.grpc.MethodDescriptor<io.openliberty.guides.systemproto.SystemPropertyName, io.openliberty.guides.systemproto.SystemProperty> getGetBidirectPropertiesMethod;
    if ((getGetBidirectPropertiesMethod = SystemServiceGrpc.getGetBidirectPropertiesMethod) == null) {
      synchronized (SystemServiceGrpc.class) {
        if ((getGetBidirectPropertiesMethod = SystemServiceGrpc.getGetBidirectPropertiesMethod) == null) {
          SystemServiceGrpc.getGetBidirectPropertiesMethod = getGetBidirectPropertiesMethod =
              io.grpc.MethodDescriptor.<io.openliberty.guides.systemproto.SystemPropertyName, io.openliberty.guides.systemproto.SystemProperty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getBidirectProperties"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.openliberty.guides.systemproto.SystemPropertyName.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.openliberty.guides.systemproto.SystemProperty.getDefaultInstance()))
              .setSchemaDescriptor(new SystemServiceMethodDescriptorSupplier("getBidirectProperties"))
              .build();
        }
      }
    }
    return getGetBidirectPropertiesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SystemServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SystemServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SystemServiceStub>() {
        @java.lang.Override
        public SystemServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SystemServiceStub(channel, callOptions);
        }
      };
    return SystemServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SystemServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SystemServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SystemServiceBlockingStub>() {
        @java.lang.Override
        public SystemServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SystemServiceBlockingStub(channel, callOptions);
        }
      };
    return SystemServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SystemServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SystemServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SystemServiceFutureStub>() {
        @java.lang.Override
        public SystemServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SystemServiceFutureStub(channel, callOptions);
        }
      };
    return SystemServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * tag::SystemService[]
   * </pre>
   */
  public static abstract class SystemServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * tag::getProperty[]
     * </pre>
     */
    public void getProperty(io.openliberty.guides.systemproto.SystemPropertyName request,
        io.grpc.stub.StreamObserver<io.openliberty.guides.systemproto.SystemPropertyValue> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetPropertyMethod(), responseObserver);
    }

    /**
     * <pre>
     * tag::getServerStreamingProperties[]
     * </pre>
     */
    public void getServerStreamingProperties(io.openliberty.guides.systemproto.SystemPropertyPrefix request,
        io.grpc.stub.StreamObserver<io.openliberty.guides.systemproto.SystemProperty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetServerStreamingPropertiesMethod(), responseObserver);
    }

    /**
     * <pre>
     * tag::getClientStreamingProperties[]
     * </pre>
     */
    public io.grpc.stub.StreamObserver<io.openliberty.guides.systemproto.SystemPropertyName> getClientStreamingProperties(
        io.grpc.stub.StreamObserver<io.openliberty.guides.systemproto.SystemProperties> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getGetClientStreamingPropertiesMethod(), responseObserver);
    }

    /**
     * <pre>
     * tag::getBidirectProperties[]
     * </pre>
     */
    public io.grpc.stub.StreamObserver<io.openliberty.guides.systemproto.SystemPropertyName> getBidirectProperties(
        io.grpc.stub.StreamObserver<io.openliberty.guides.systemproto.SystemProperty> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getGetBidirectPropertiesMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetPropertyMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                io.openliberty.guides.systemproto.SystemPropertyName,
                io.openliberty.guides.systemproto.SystemPropertyValue>(
                  this, METHODID_GET_PROPERTY)))
          .addMethod(
            getGetServerStreamingPropertiesMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                io.openliberty.guides.systemproto.SystemPropertyPrefix,
                io.openliberty.guides.systemproto.SystemProperty>(
                  this, METHODID_GET_SERVER_STREAMING_PROPERTIES)))
          .addMethod(
            getGetClientStreamingPropertiesMethod(),
            io.grpc.stub.ServerCalls.asyncClientStreamingCall(
              new MethodHandlers<
                io.openliberty.guides.systemproto.SystemPropertyName,
                io.openliberty.guides.systemproto.SystemProperties>(
                  this, METHODID_GET_CLIENT_STREAMING_PROPERTIES)))
          .addMethod(
            getGetBidirectPropertiesMethod(),
            io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
              new MethodHandlers<
                io.openliberty.guides.systemproto.SystemPropertyName,
                io.openliberty.guides.systemproto.SystemProperty>(
                  this, METHODID_GET_BIDIRECT_PROPERTIES)))
          .build();
    }
  }

  /**
   * <pre>
   * tag::SystemService[]
   * </pre>
   */
  public static final class SystemServiceStub extends io.grpc.stub.AbstractAsyncStub<SystemServiceStub> {
    private SystemServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SystemServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SystemServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * tag::getProperty[]
     * </pre>
     */
    public void getProperty(io.openliberty.guides.systemproto.SystemPropertyName request,
        io.grpc.stub.StreamObserver<io.openliberty.guides.systemproto.SystemPropertyValue> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetPropertyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * tag::getServerStreamingProperties[]
     * </pre>
     */
    public void getServerStreamingProperties(io.openliberty.guides.systemproto.SystemPropertyPrefix request,
        io.grpc.stub.StreamObserver<io.openliberty.guides.systemproto.SystemProperty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetServerStreamingPropertiesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * tag::getClientStreamingProperties[]
     * </pre>
     */
    public io.grpc.stub.StreamObserver<io.openliberty.guides.systemproto.SystemPropertyName> getClientStreamingProperties(
        io.grpc.stub.StreamObserver<io.openliberty.guides.systemproto.SystemProperties> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getGetClientStreamingPropertiesMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * tag::getBidirectProperties[]
     * </pre>
     */
    public io.grpc.stub.StreamObserver<io.openliberty.guides.systemproto.SystemPropertyName> getBidirectProperties(
        io.grpc.stub.StreamObserver<io.openliberty.guides.systemproto.SystemProperty> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getGetBidirectPropertiesMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * <pre>
   * tag::SystemService[]
   * </pre>
   */
  public static final class SystemServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<SystemServiceBlockingStub> {
    private SystemServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SystemServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SystemServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * tag::getProperty[]
     * </pre>
     */
    public io.openliberty.guides.systemproto.SystemPropertyValue getProperty(io.openliberty.guides.systemproto.SystemPropertyName request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetPropertyMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * tag::getServerStreamingProperties[]
     * </pre>
     */
    public java.util.Iterator<io.openliberty.guides.systemproto.SystemProperty> getServerStreamingProperties(
        io.openliberty.guides.systemproto.SystemPropertyPrefix request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetServerStreamingPropertiesMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * tag::SystemService[]
   * </pre>
   */
  public static final class SystemServiceFutureStub extends io.grpc.stub.AbstractFutureStub<SystemServiceFutureStub> {
    private SystemServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SystemServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SystemServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * tag::getProperty[]
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.openliberty.guides.systemproto.SystemPropertyValue> getProperty(
        io.openliberty.guides.systemproto.SystemPropertyName request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetPropertyMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_PROPERTY = 0;
  private static final int METHODID_GET_SERVER_STREAMING_PROPERTIES = 1;
  private static final int METHODID_GET_CLIENT_STREAMING_PROPERTIES = 2;
  private static final int METHODID_GET_BIDIRECT_PROPERTIES = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SystemServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SystemServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_PROPERTY:
          serviceImpl.getProperty((io.openliberty.guides.systemproto.SystemPropertyName) request,
              (io.grpc.stub.StreamObserver<io.openliberty.guides.systemproto.SystemPropertyValue>) responseObserver);
          break;
        case METHODID_GET_SERVER_STREAMING_PROPERTIES:
          serviceImpl.getServerStreamingProperties((io.openliberty.guides.systemproto.SystemPropertyPrefix) request,
              (io.grpc.stub.StreamObserver<io.openliberty.guides.systemproto.SystemProperty>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_CLIENT_STREAMING_PROPERTIES:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.getClientStreamingProperties(
              (io.grpc.stub.StreamObserver<io.openliberty.guides.systemproto.SystemProperties>) responseObserver);
        case METHODID_GET_BIDIRECT_PROPERTIES:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.getBidirectProperties(
              (io.grpc.stub.StreamObserver<io.openliberty.guides.systemproto.SystemProperty>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class SystemServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SystemServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.openliberty.guides.systemproto.SystemServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SystemService");
    }
  }

  private static final class SystemServiceFileDescriptorSupplier
      extends SystemServiceBaseDescriptorSupplier {
    SystemServiceFileDescriptorSupplier() {}
  }

  private static final class SystemServiceMethodDescriptorSupplier
      extends SystemServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SystemServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SystemServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SystemServiceFileDescriptorSupplier())
              .addMethod(getGetPropertyMethod())
              .addMethod(getGetServerStreamingPropertiesMethod())
              .addMethod(getGetClientStreamingPropertiesMethod())
              .addMethod(getGetBidirectPropertiesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
