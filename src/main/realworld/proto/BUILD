package(default_visibility = ["//visibility:public"])

load("@io_grpc_grpc_java//:java_grpc_library.bzl", "java_grpc_library")

proto_library(
    name = "realworld_proto",
    srcs = glob(["*.proto"]),
    deps = [
        "@googleapis//:annotations_proto",
        "@com_google_protobuf//:empty_proto",
        "@com_google_protobuf//:timestamp_proto",
        "@com_google_protobuf//:wrappers_proto",
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

genrule(
    name = "realworld_descriptorset",
    srcs = glob(["*.proto"]) + [
        "@googleapis//:google/api/annotations.proto",
        "@com_google_protobuf//:empty_proto",
    ],
    outs = ["realworld.pb"],
    cmd = "$(location @com_google_protobuf//:protoc) " +
          "--descriptor_set_out=$@ " +
          "--include_imports --include_source_info $(SRCS)",
    tools = ["@com_google_protobuf//:protoc"],
)
