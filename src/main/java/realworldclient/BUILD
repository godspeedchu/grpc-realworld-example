package(default_visibility = ["//visibility:public"])

CLI_SRCS = ["RealworldClientCli.java"]

java_library(
    name = "client",
    srcs = glob(["*.java"],
                exclude = CLI_SRCS),
    deps = [
        "@com_google_guava_guava//jar",
        "@com_google_inject_guice//jar",
        "@io_grpc_grpc_java//core",
        "@io_grpc_grpc_java//netty",
        "//src/main/realworld/proto:realworld_java_grpc",
        "//src/main/realworld/proto:realworld_java_proto",
        "//src/main/java/realworld/core/auth",
    ],
)

java_binary(
    name = "cli",
    main_class = "realworldclient.RealworldClientCli",
    srcs = CLI_SRCS,
    deps = [
        ":client",
        "@com_google_inject_guice//jar",
        "@io_grpc_grpc_java//core",
        "//src/main/realworld/proto:realworld_java_grpc",
        "//src/main/realworld/proto:realworld_java_proto",
    ],
    runtime_deps = [
        "@aopalliance//jar",
        "@javax_inject//jar",
    ],
)
