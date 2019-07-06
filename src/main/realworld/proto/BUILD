package(default_visibility = ["//visibility:public"])

load("@io_grpc_grpc_java//:java_grpc_library.bzl", "java_grpc_library")

proto_library(
    name = "realworld_proto",
    srcs = glob(["*.proto"]),
    deps = [
        "@com_google_protobuf//:empty_proto",
        "@com_google_protobuf//:timestamp_proto",
    ],
)

java_proto_library(
    name = "realworld_java_proto",
    deps = [":realworld_proto"],
)

java_grpc_library(
    name = "realworld_java_grpc",
    srcs = [":realworld_proto"],
    deps = [":realworld_java_proto"],
)
