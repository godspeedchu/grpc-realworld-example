package(default_visibility = ["//visibility:private"])

MODULE = ["DaoModule.java"]

java_library(
    name = "module",
    srcs = MODULE,
    visibility = ["//visibility:public"],
    deps = [
        ":impl",
        "//src/main/java/realworld/core/dao",
        "@com_google_inject_guice//jar",
    ],
)

java_library(
    name = "impl",
    srcs = glob(["*.java"], exclude = MODULE),
    deps = [
        "//src/main/realworld/proto:realworld_java_proto",
        "//src/main/realworld/proto/internal:internal_java_proto",
        "//src/main/java/realworld/core/dao",
        "@com_google_guava_guava//jar",
        "@com_google_inject_guice//jar",
        "@com_google_protobuf//:protobuf_java",
    ],
)
