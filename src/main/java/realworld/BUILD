package(default_visibility = ["//visibility:public"])

load("@io_bazel_rules_docker//java:image.bzl", "java_image")

java_library(
    name = "server",
    srcs = glob(["*.java"]),
    deps = [
        "@com_google_guava_guava//jar",
        "@com_google_inject_guice//jar",
        "//src/main/java/realworld/core/dao/impl:module",
        "//src/main/java/realworld/infrastructure/concurrent",
        "//src/main/java/realworld/infrastructure/grpc",
        "//src/main/java/realworld/infrastructure/server",
        "//src/main/java/realworld/processors",
    ],
)

java_binary(
    name = "realworldserver",
    main_class = "realworld.RealworldServer",
    runtime_deps = [
        ":server",
    ],
)

java_image(
    name = "realworldserverimage",
    main_class = "realworld.RealworldServer",
    runtime_deps = [
        ":server",
    ],
)
