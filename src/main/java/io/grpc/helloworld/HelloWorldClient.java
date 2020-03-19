package io.grpc.helloworld;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloWorldClient {
  private static final Logger logger = Logger.getLogger(HelloWorldClient.class.getName());
  private final GreeterGrpc.GreeterBlockingStub blockingStub;
  public HelloWorldClient(Channel channel) {
    blockingStub = GreeterGrpc.newBlockingStub(channel);
  }

  public void greet(String name) {
    logger.info("Will try to greet " + name + " ...");
    HelloRequest request = HelloRequest.newBuilder().setName(name).build();
    HelloReply response;
    try {
      response = blockingStub.sayHello(request);
    } catch (StatusRuntimeException e) {
      logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
      return;
    }
    logger.info("Greeting: " + response.getMessage());
  }

  public static void main(String[] args) throws Exception {
    String user = "world";
    String target = "localhost:5050";
    ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
    try {
      HelloWorldClient client = new HelloWorldClient(channel);
      client.greet(user);
    } finally {
      channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }
  }
}