package(default_visibility = ["//visibility:public"])

java_library(
    name = "tests",
    srcs = glob(["*.java"]),
    deps = [
        "//src/main/realworld/proto:realworld_java_proto",
        "//src/main/realworld/proto/internal:internal_java_proto",
        "//src/main/java/realworld/core/dao",
        "//src/main/java/realworld/core/dao/impl:module",
        "//src/main/java/realworld/processors",
        "@com_google_guava_guava//jar",
        "@com_google_inject_guice//jar",
        "@com_google_protobuf//:protobuf_java",
        "@io_grpc_grpc_java//core",
        "@junit//jar",
        "@mockito//jar",
        "@truth//jar",
    ],
)

java_test(
    name = "LoginUserRequestValidatorTest",
    size = "small",
    runtime_deps = [
        ":tests",
    ],
)

java_test(
    name = "LoginUserRequestProcessorTest",
    size = "small",
    runtime_deps = [
        ":tests",
    ],
)
