package authenticate;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.68.1)",
    comments = "Source: auth_service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class AuthServiceGrpc {

  private AuthServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "authenticate.AuthService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<authenticate.AuthServiceOuterClass.AuthRequest,
      authenticate.AuthServiceOuterClass.AuthResponse> getAuthenticateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Authenticate",
      requestType = authenticate.AuthServiceOuterClass.AuthRequest.class,
      responseType = authenticate.AuthServiceOuterClass.AuthResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<authenticate.AuthServiceOuterClass.AuthRequest,
      authenticate.AuthServiceOuterClass.AuthResponse> getAuthenticateMethod() {
    io.grpc.MethodDescriptor<authenticate.AuthServiceOuterClass.AuthRequest, authenticate.AuthServiceOuterClass.AuthResponse> getAuthenticateMethod;
    if ((getAuthenticateMethod = AuthServiceGrpc.getAuthenticateMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getAuthenticateMethod = AuthServiceGrpc.getAuthenticateMethod) == null) {
          AuthServiceGrpc.getAuthenticateMethod = getAuthenticateMethod =
              io.grpc.MethodDescriptor.<authenticate.AuthServiceOuterClass.AuthRequest, authenticate.AuthServiceOuterClass.AuthResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Authenticate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  authenticate.AuthServiceOuterClass.AuthRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  authenticate.AuthServiceOuterClass.AuthResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("Authenticate"))
              .build();
        }
      }
    }
    return getAuthenticateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<authenticate.AuthServiceOuterClass.ValidateRequest,
      authenticate.AuthServiceOuterClass.ValidateResponse> getValidateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Validate",
      requestType = authenticate.AuthServiceOuterClass.ValidateRequest.class,
      responseType = authenticate.AuthServiceOuterClass.ValidateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<authenticate.AuthServiceOuterClass.ValidateRequest,
      authenticate.AuthServiceOuterClass.ValidateResponse> getValidateMethod() {
    io.grpc.MethodDescriptor<authenticate.AuthServiceOuterClass.ValidateRequest, authenticate.AuthServiceOuterClass.ValidateResponse> getValidateMethod;
    if ((getValidateMethod = AuthServiceGrpc.getValidateMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getValidateMethod = AuthServiceGrpc.getValidateMethod) == null) {
          AuthServiceGrpc.getValidateMethod = getValidateMethod =
              io.grpc.MethodDescriptor.<authenticate.AuthServiceOuterClass.ValidateRequest, authenticate.AuthServiceOuterClass.ValidateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Validate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  authenticate.AuthServiceOuterClass.ValidateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  authenticate.AuthServiceOuterClass.ValidateResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("Validate"))
              .build();
        }
      }
    }
    return getValidateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<authenticate.AuthServiceOuterClass.InvalidateRequest,
      authenticate.AuthServiceOuterClass.InvalidateResponse> getInvalidateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Invalidate",
      requestType = authenticate.AuthServiceOuterClass.InvalidateRequest.class,
      responseType = authenticate.AuthServiceOuterClass.InvalidateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<authenticate.AuthServiceOuterClass.InvalidateRequest,
      authenticate.AuthServiceOuterClass.InvalidateResponse> getInvalidateMethod() {
    io.grpc.MethodDescriptor<authenticate.AuthServiceOuterClass.InvalidateRequest, authenticate.AuthServiceOuterClass.InvalidateResponse> getInvalidateMethod;
    if ((getInvalidateMethod = AuthServiceGrpc.getInvalidateMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getInvalidateMethod = AuthServiceGrpc.getInvalidateMethod) == null) {
          AuthServiceGrpc.getInvalidateMethod = getInvalidateMethod =
              io.grpc.MethodDescriptor.<authenticate.AuthServiceOuterClass.InvalidateRequest, authenticate.AuthServiceOuterClass.InvalidateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Invalidate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  authenticate.AuthServiceOuterClass.InvalidateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  authenticate.AuthServiceOuterClass.InvalidateResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("Invalidate"))
              .build();
        }
      }
    }
    return getInvalidateMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AuthServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AuthServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AuthServiceStub>() {
        @java.lang.Override
        public AuthServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AuthServiceStub(channel, callOptions);
        }
      };
    return AuthServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AuthServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AuthServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AuthServiceBlockingStub>() {
        @java.lang.Override
        public AuthServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AuthServiceBlockingStub(channel, callOptions);
        }
      };
    return AuthServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AuthServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AuthServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AuthServiceFutureStub>() {
        @java.lang.Override
        public AuthServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AuthServiceFutureStub(channel, callOptions);
        }
      };
    return AuthServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void authenticate(authenticate.AuthServiceOuterClass.AuthRequest request,
        io.grpc.stub.StreamObserver<authenticate.AuthServiceOuterClass.AuthResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAuthenticateMethod(), responseObserver);
    }

    /**
     */
    default void validate(authenticate.AuthServiceOuterClass.ValidateRequest request,
        io.grpc.stub.StreamObserver<authenticate.AuthServiceOuterClass.ValidateResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getValidateMethod(), responseObserver);
    }

    /**
     */
    default void invalidate(authenticate.AuthServiceOuterClass.InvalidateRequest request,
        io.grpc.stub.StreamObserver<authenticate.AuthServiceOuterClass.InvalidateResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getInvalidateMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service AuthService.
   */
  public static abstract class AuthServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return AuthServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service AuthService.
   */
  public static final class AuthServiceStub
      extends io.grpc.stub.AbstractAsyncStub<AuthServiceStub> {
    private AuthServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AuthServiceStub(channel, callOptions);
    }

    /**
     */
    public void authenticate(authenticate.AuthServiceOuterClass.AuthRequest request,
        io.grpc.stub.StreamObserver<authenticate.AuthServiceOuterClass.AuthResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAuthenticateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void validate(authenticate.AuthServiceOuterClass.ValidateRequest request,
        io.grpc.stub.StreamObserver<authenticate.AuthServiceOuterClass.ValidateResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getValidateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void invalidate(authenticate.AuthServiceOuterClass.InvalidateRequest request,
        io.grpc.stub.StreamObserver<authenticate.AuthServiceOuterClass.InvalidateResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getInvalidateMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service AuthService.
   */
  public static final class AuthServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<AuthServiceBlockingStub> {
    private AuthServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AuthServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public authenticate.AuthServiceOuterClass.AuthResponse authenticate(authenticate.AuthServiceOuterClass.AuthRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAuthenticateMethod(), getCallOptions(), request);
    }

    /**
     */
    public authenticate.AuthServiceOuterClass.ValidateResponse validate(authenticate.AuthServiceOuterClass.ValidateRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getValidateMethod(), getCallOptions(), request);
    }

    /**
     */
    public authenticate.AuthServiceOuterClass.InvalidateResponse invalidate(authenticate.AuthServiceOuterClass.InvalidateRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getInvalidateMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service AuthService.
   */
  public static final class AuthServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<AuthServiceFutureStub> {
    private AuthServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AuthServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<authenticate.AuthServiceOuterClass.AuthResponse> authenticate(
        authenticate.AuthServiceOuterClass.AuthRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAuthenticateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<authenticate.AuthServiceOuterClass.ValidateResponse> validate(
        authenticate.AuthServiceOuterClass.ValidateRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getValidateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<authenticate.AuthServiceOuterClass.InvalidateResponse> invalidate(
        authenticate.AuthServiceOuterClass.InvalidateRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getInvalidateMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_AUTHENTICATE = 0;
  private static final int METHODID_VALIDATE = 1;
  private static final int METHODID_INVALIDATE = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_AUTHENTICATE:
          serviceImpl.authenticate((authenticate.AuthServiceOuterClass.AuthRequest) request,
              (io.grpc.stub.StreamObserver<authenticate.AuthServiceOuterClass.AuthResponse>) responseObserver);
          break;
        case METHODID_VALIDATE:
          serviceImpl.validate((authenticate.AuthServiceOuterClass.ValidateRequest) request,
              (io.grpc.stub.StreamObserver<authenticate.AuthServiceOuterClass.ValidateResponse>) responseObserver);
          break;
        case METHODID_INVALIDATE:
          serviceImpl.invalidate((authenticate.AuthServiceOuterClass.InvalidateRequest) request,
              (io.grpc.stub.StreamObserver<authenticate.AuthServiceOuterClass.InvalidateResponse>) responseObserver);
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
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getAuthenticateMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              authenticate.AuthServiceOuterClass.AuthRequest,
              authenticate.AuthServiceOuterClass.AuthResponse>(
                service, METHODID_AUTHENTICATE)))
        .addMethod(
          getValidateMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              authenticate.AuthServiceOuterClass.ValidateRequest,
              authenticate.AuthServiceOuterClass.ValidateResponse>(
                service, METHODID_VALIDATE)))
        .addMethod(
          getInvalidateMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              authenticate.AuthServiceOuterClass.InvalidateRequest,
              authenticate.AuthServiceOuterClass.InvalidateResponse>(
                service, METHODID_INVALIDATE)))
        .build();
  }

  private static abstract class AuthServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AuthServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return authenticate.AuthServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AuthService");
    }
  }

  private static final class AuthServiceFileDescriptorSupplier
      extends AuthServiceBaseDescriptorSupplier {
    AuthServiceFileDescriptorSupplier() {}
  }

  private static final class AuthServiceMethodDescriptorSupplier
      extends AuthServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    AuthServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (AuthServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AuthServiceFileDescriptorSupplier())
              .addMethod(getAuthenticateMethod())
              .addMethod(getValidateMethod())
              .addMethod(getInvalidateMethod())
              .build();
        }
      }
    }
    return result;
  }
}
