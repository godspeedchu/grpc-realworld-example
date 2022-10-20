"""External dependencies for depot."""

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

def depot_repositories():
  http_archive(
      name = "com_google_protobuf",
      sha256 = "ce2fbea3c78147a41b2a922485d283137845303e5e1b6cbd7ece94b96ade7031",
      strip_prefix = "protobuf-3.21.7",
      urls = ["https://github.com/protocolbuffers/protobuf/archive/v3.21.7.tar.gz"],
  )

  http_archive(
    name = "io_grpc_grpc_java",
    strip_prefix = "grpc-java-1.50.0",
    urls = [
        "https://github.com/grpc/grpc-java/archive/v1.50.0.tar.gz",
    ],
  )

  http_archive(
      name = "googleapis",
      strip_prefix = "googleapis-common-protos-1_3_1",
      url = "https://github.com/googleapis/googleapis/archive/common-protos-1_3_1.zip",
      build_file = "@//:BUILD.googleapis",
  )

  http_archive(
      name = "io_bazel_rules_docker",
      sha256 = "aed1c249d4ec8f703edddf35cbe9dfaca0b5f5ea6e4cd9e83e99f3b0d1136c3d",
      strip_prefix = "rules_docker-0.7.0",
      urls = ["https://github.com/bazelbuild/rules_docker/archive/v0.7.0.tar.gz"],
  )
