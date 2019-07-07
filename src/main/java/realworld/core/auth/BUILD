package(default_visibility = ["//visibility:public"])

java_library(
    name = "auth",
    srcs = glob(["*.java"]),
    deps = [
        "//src/main/realworld/proto/internal:internal_java_proto",
        "@com_google_guava_guava//jar",
        "@com_google_inject_guice//jar",
        "@com_google_protobuf//:protobuf_java",
        "@io_grpc_grpc_java//context",
        "@io_grpc_grpc_java//core",
    ],
)
