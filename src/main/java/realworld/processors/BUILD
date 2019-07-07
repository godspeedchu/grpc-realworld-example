package(default_visibility = ["//visibility:public"])

java_library(
    name = "processors",
    srcs = glob(["*.java"]),
    deps = [
        "@aopalliance//jar",
        "@com_google_guava_guava//jar",
        "@com_google_inject_guice//jar",
        "@com_google_protobuf//:protobuf_java",
        "@io_grpc_grpc_java//stub",
        "@javax_inject//jar",
        "//src/main/realworld/proto:realworld_java_grpc",
        "//src/main/realworld/proto:realworld_java_proto",
        "//src/main/realworld/proto/internal:internal_java_proto",
        "//src/main/java/realworld/core/auth",
        "//src/main/java/realworld/core/dao",
        "//src/main/java/realworld/infrastructure/concurrent",
        "//src/main/java/realworld/infrastructure/exceptions",
        "//src/main/java/realworld/infrastructure/grpc",
    ],
)
