// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: health_service.proto

package ar.edu.itba.pod.grpc.health;

public final class HealthServiceOuterClass {
  private HealthServiceOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_health_PingRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_health_PingRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_health_PingResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_health_PingResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\024health_service.proto\022\006health\"\r\n\013PingRe" +
      "quest\"\037\n\014PingResponse\022\017\n\007message\030\001 \001(\t2B" +
      "\n\rHealthService\0221\n\004ping\022\023.health.PingReq" +
      "uest\032\024.health.PingResponseB\037\n\033ar.edu.itb" +
      "a.pod.grpc.healthP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_health_PingRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_health_PingRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_health_PingRequest_descriptor,
        new java.lang.String[] { });
    internal_static_health_PingResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_health_PingResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_health_PingResponse_descriptor,
        new java.lang.String[] { "Message", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
