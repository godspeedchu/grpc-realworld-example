package(default_visibility = ["//visibility:public"])

proto_library(
    name = "internal_proto",
    srcs = glob(["*.proto"]),
    deps = [
        "@com_google_protobuf//:timestamp_proto",
    ],
)

java_proto_library(
    name = "internal_java_proto",
    deps = [":internal_proto"],
)
