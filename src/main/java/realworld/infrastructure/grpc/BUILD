package(default_visibility = ["//visibility:public"])

java_library(
  name = "grpc",
  srcs = glob(["*.java"]),
  deps = [
    "@com_google_guava_guava//jar",
    "@com_google_inject_extensions_guice_multibindings//jar",
    "@com_google_inject_guice//jar",
    "@com_google_protobuf//:protobuf_java",
    "@jsr330_inject//jar",
    "@io_grpc_grpc_java//context",
    "@io_grpc_grpc_java//core",
    "@io_grpc_grpc_java//core:inprocess",
    "@io_grpc_grpc_java//netty",
    "@io_grpc_grpc_java//stub",
  ],
)
