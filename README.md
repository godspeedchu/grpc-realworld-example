# ![RealWorld Example App](logo.png)

[![Latest release](https://img.shields.io/github/v/release/godspeedchu/grpc-realworld-example.svg)](https://github.com/godspeedchu/grpc-realworld-example/releases/latest)
[![Build Status](https://travis-ci.org/godspeedchu/grpc-realworld-example.svg?branch=master)](https://travis-ci.org/godspeedchu/grpc-realworld-example)

> ### gRPC + Google Cloud ESP codebase containing real world examples (CRUD, auth, advanced patterns, etc) that adheres to the [RealWorld](https://github.com/gothinkster/realworld) spec and API.


### [Demo](https://github.com/gothinkster/realworld)&nbsp;&nbsp;&nbsp;&nbsp;[RealWorld](https://github.com/gothinkster/realworld)


This codebase was created to demonstrate a fully fledged fullstack application built with gRPC + Google Cloud ESP including CRUD operations, authentication, routing, pagination, and more.

We've gone to great lengths to adhere to the gRPC community styleguides & best practices.

For more information on how to this works with other frontends/backends, head over to the [RealWorld](https://github.com/gothinkster/realworld) repo.


# How it works

> ### The application uses gRPC + Google Cloud ESP.

[gRPC](https://grpc.io/) is an open source high performance RPC framework released by Google. It supports multiple language and the RPC uses Google's [Protocol Buffer](https://developers.google.com/protocol-buffers/) as IDL.

[Cloud Endpoints](https://cloud.google.com/endpoints/) is an API Management framework to develop, deploy and manage APIs on Google Cloud Platform. [Extensible Service Proxy (ESP)](https://cloud.google.com/endpoints/docs/frameworks/frameworks-extensible-service-proxy) is an API relay/control plane offered by Cloud Endpoints for OpenAPI and gRPC. For gRPC, ESP provides JSON/REST to gRPC/Protobuf transcoding which means one can build a pure gRPC API and have ESP translate it into REST API with minimum configurations.

This repo uses bazel to build, latest working version is 5.3.2.

# Getting started

> Start the gRPC server to listen at port 8080

```shell
bazel run src/main/java/realworld:realworldserver
```

> Re-generate the API descriptor set (optional)

```shell
bazel run "@com_google_protobuf//:protoc"
bazel-bin/external/com_google_protobuf/protoc --proto_path=. --proto_path=bazel-genfiles/external/com_google_protobuf/ --include_imports --include_source_info --descriptor_set_out=api_descriptor.pb src/main/realworld/proto/realworld.proto
```

> Deploy ESP configurations to GCP

```shell
gcloud endpoints services deploy src/main/realworld/proto/api_descriptor.pb src/main/realworld/proto/api_config.yaml
```

> Start a local ESP to transcode REST to gRPC

```shell
sudo docker run --detach --name="esp" --publish=8082:8082 --volume=$HOME/Downloads:/esp gcr.io/endpoints-release/endpoints-runtime:1.13.0 --service=realworld.endpoints.[YOUR PROJECT ID].cloud.goog --rollout_strategy=managed --http_port=8082 --backend=grpc://docker.for.mac.localhost:8080 --service_account_key=/esp/service-account-creds.json --transcoding_always_print_primitive_fields
```

Not that "transcoding_always_print_primitive_fields" option is required in order to make ESP transcoding to populate fields with default value, such as empty list, boolean field in false, integer at 0, etc.

# To run unit tests

> One unit test implemented under src/test/realworld/processors

```shell
bazel test src/test/java/realworld/processors:LoginUserRequestValidatorTest
bazel test src/test/java/realworld/processors:LoginUserRequestProcessorTest
```
