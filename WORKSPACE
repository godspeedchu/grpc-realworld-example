workspace(name = "depot")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")
load("//:repositories.bzl", "depot_repositories")

depot_repositories()

http_archive(
    name = "rules_jvm_external",
    sha256 = "735602f50813eb2ea93ca3f5e43b1959bd80b213b836a07a62a29d757670b77b",
    strip_prefix = "rules_jvm_external-4.4.2",
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/refs/tags/4.4.2.zip",
)

load("@rules_jvm_external//:defs.bzl", "maven_install")
load("@rules_jvm_external//:specs.bzl", "maven")
load("@io_grpc_grpc_java//:repositories.bzl", "IO_GRPC_GRPC_JAVA_ARTIFACTS")
load("@io_grpc_grpc_java//:repositories.bzl", "IO_GRPC_GRPC_JAVA_OVERRIDE_TARGETS")

maven_install(
    artifacts = [
        "aopalliance:aopalliance:1.0",
        "com.google.auto.value:auto-value:1.9",
	"com.google.auto.value:auto-value-annotations:1.9",
	"com.google.guava:guava:31.0.1-jre",
	"com.google.inject:guice:4.2.3",
	"javax.inject:javax.inject:1",
	maven.artifact(
            "com.google.truth",
            "truth",
            "1.0.1",
            testonly = True,
        ),
	maven.artifact(
            "junit",
            "junit",
            "4.13.2",
            testonly = True,
        ),
	maven.artifact(
            "org.mockito",
            "mockito-all",
            "1.10.19",
            testonly = True,
        ),
    ] + IO_GRPC_GRPC_JAVA_ARTIFACTS,
    generate_compat_repositories = True,
    override_targets = IO_GRPC_GRPC_JAVA_OVERRIDE_TARGETS,
    repositories = [
        "https://repo.maven.apache.org/maven2/",
    ],
)

load("@maven//:compat.bzl", "compat_repositories")

compat_repositories()

load("//:repositories.bzl", "depot_repositories")

depot_repositories()

load("@com_google_protobuf//:protobuf_deps.bzl", "protobuf_deps")

protobuf_deps()

load("@io_grpc_grpc_java//:repositories.bzl", "grpc_java_repositories")

grpc_java_repositories()

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

# Download the rules_docker repository at release v0.7.0
http_archive(
    name = "io_bazel_rules_docker",
    sha256 = "aed1c249d4ec8f703edddf35cbe9dfaca0b5f5ea6e4cd9e83e99f3b0d1136c3d",
    strip_prefix = "rules_docker-0.7.0",
    urls = ["https://github.com/bazelbuild/rules_docker/archive/v0.7.0.tar.gz"],
)

# OPTIONAL: Call this to override the default docker toolchain configuration.
# This call should be placed BEFORE the call to "container_repositories" below
# to actually override the default toolchain configuration.
# Note this is only required if you actually want to call
# docker_toolchain_configure with a custom attr; please read the toolchains
# docs in /toolchains/docker/ before blindly adding this to your WORKSPACE.

load("@io_bazel_rules_docker//toolchains/docker:toolchain.bzl",
    docker_toolchain_configure="toolchain_configure"
)
docker_toolchain_configure(
  name = "docker_config",
  # OPTIONAL: Path to a directory which has a custom docker client config.json.
  # See https://docs.docker.com/engine/reference/commandline/cli/#configuration-files
  # for more details.
  client_config="/path/to/docker/client/config",
)

# This is NOT needed when going through the language lang_image
# "repositories" function(s).
load(
    "@io_bazel_rules_docker//repositories:repositories.bzl",
    container_repositories = "repositories",
)
container_repositories()

load(
    "@io_bazel_rules_docker//container:container.bzl",
    "container_pull",
)

container_pull(
  name = "java_base",
  registry = "gcr.io",
  repository = "distroless/java",
  # 'tag' is also supported, but digest is encouraged for reproducibility.
  digest = "sha256:deadbeef",
)

load(
    "@io_bazel_rules_docker//java:image.bzl",
    _java_image_repos = "repositories",
)

_java_image_repos()
