"""External dependencies for depot."""

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

def depot_repositories():
  native.maven_jar(
      name = "com_google_inject_guice",
      artifact = "com.google.inject:guice:4.2.2",
      sha1 = "6dacbe18e5eaa7f6c9c36db33b42e7985e94ce77",
  )

  native.maven_jar(
      name = 'javax_inject',
      artifact = 'javax.inject:javax.inject:1',
      sha1 = '6975da39a7040257bd51d21a231b76c915872d38',
  )

  native.maven_jar(
      name = 'aopalliance',
      artifact = 'aopalliance:aopalliance:1.0',
      sha1 = '0235ba8b489512805ac13a8f9ea77a1ca5ebe3e8',
  )

  native.maven_jar(
      name = "com_google_inject_extensions_guice_multibindings",
      artifact = "com.google.inject.extensions:guice-multibindings:4.2.0",
      sha1 = "76c96e043d05bc4788e703b3af06a3ece32293bd",
  )

  native.maven_jar(
      name = "junit",
      artifact = "junit:junit:4.13-beta-2",
      sha1 = "02470c41ebd351f7edceac9eea7c414ac847a154",
  )

  native.maven_jar(
      name = "truth",
      artifact = "com.google.truth:truth:0.44",
      sha1 = "11eff954c0c14da7d43276d7b3bcf71463105368",
  )

  native.maven_jar(
      name = "mockito",
      artifact = "org.mockito:mockito-all:1.10.19",
  )

  native.maven_jar(
      name = "jsr330_inject",
      artifact = "javax.inject:javax.inject:1",
      sha1 = "6975da39a7040257bd51d21a231b76c915872d38",
  )

  http_archive(
      name = "com_google_protobuf",
      strip_prefix = "protobuf-3.7.1",
      urls = ["https://github.com/google/protobuf/archive/v3.7.1.zip"],
  )

  http_archive(
      name = "io_grpc_grpc_java",
      strip_prefix = "grpc-java-1.20.0",
      urls = ["https://github.com/grpc/grpc-java/archive/v1.20.0.zip"],
  )

  http_archive(
      name = "googleapis",
      strip_prefix = "googleapis-common-protos-1_3_1",
      url = "https://github.com/googleapis/googleapis/archive/common-protos-1_3_1.zip",
      build_file = "@//:BUILD.googleapis",
  )
