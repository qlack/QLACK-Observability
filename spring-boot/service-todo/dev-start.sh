#!/usr/bin/env sh

mvn spring-boot:run \
  -Dspring-boot.run.jvmArguments="
    -javaagent:bin/opentelemetry-javaagent.jar
    -Dotel.service.name=service-todo
    -Dotel.traces.exporter=otlp
    -Dotel.metrics.exporter=otlp
    -Dotel.logs.exporter=otlp
    -Dotel.exporter.otlp.endpoint=http://localhost:4318
    -Dotel.resource.attributes.process.command_args=''
    -Dotel.instrumentation.http.client.exclude-urls=\"http://localhost:4318/v1/metrics\"
  "
