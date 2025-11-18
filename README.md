# QLACK Observability

QLACK Observability is a set of mini-demonstrators showing you how you can integrate full 
observability into your projects. All projects are based on [OpenTelemetry](https://opentelemetry.io),
the open-source standard for collecting telemetry data from your applications.

## Why telemetry is important

In today's complex software systems, understanding the behavior and performance of applications is
crucial. Telemetry provides insights into how applications are performing, helps identify
bottlenecks, and aids in troubleshooting issues. By collecting and analysing telemetry data,
developers and operators can make informed decisions to improve application reliability and user
experience.

Especially in complex microservice architectures, telemetry is essential for understanding the
interactions between services and ensuring that the entire system is functioning as expected.

## Types of Telemetry

When talking about telemetry we, generally, refer to three main types of data:

- **Traces**: Traces provide a detailed view of the execution flow of requests as they propagate
  through various services in a distributed system. They help identify performance bottlenecks and
  understand the interactions between services.
- **Metrics**: Metrics are numerical data points that provide insights into the performance and
  health of applications. They can include data such as request rates, error rates, and latency
  distributions.
- **Logs**: Logs are textual records of events that occur within an application. They provide
  context about specific events and can be used for debugging and troubleshooting issues.

## What is OpenTelemetry

OpenTelemetry is an open-source observability framework that provides a set of tools, APIs, and SDKs
for collecting telemetry data from applications. It supports multiple programming languages and
offers a unified way to instrument applications for tracing, metrics, and logging.

OpenTelemetry is widely adopted in the industry and is supported by major cloud providers and
observability platforms. It provides a vendor-neutral way to collect and export telemetry data,
making it easier to switch between different observability backends.

## QLACK Observability demonstrators

QLACK Observability demonstrators showcase two different ways of integrating telemetry data:

1. **Auto-Instrumentation with Java Agent**: Using the OpenTelemetry Java Agent to automatically
   instrument a Java application without any code changes. The agent captures telemetry data and
   exports it to an OpenTelemetry Collector for further processing and visualisation.\
   **PROS**: No code changes needed, supports a wide range of libraries and frameworks
   out-of-the-box.\
   **CONS**: Requires starting the application with the agent, no support for custom metrics, no
   support for native images.
2. **Manual-Instrumentation**: Manually instrumentation of an application using the OpenTelemetry
   libraries. It involves adding dependencies and configuration to the application to enable
   telemetry collection.\
   **PROS**: More control over instrumentation, supports custom metrics.\
   **CONS**: Requires code changes, less out-of-the-box instrumentation compared to the Java Agent,
   supports native images.

To illustrate both approaches, a Spring Boot and a Quarkus version is provided.

For additional details, you can read the README files in each demostrator for instructions on 
running the demonstrator, an overview of its architecture, and guidance for exploring the telemetry 
data.

## Project structure

The QLACK Observability repository is structured as follows:

- `spring-boot/`: Contains Spring Boot-based demonstrators for both auto-instrumentation and
  manual-instrumentation approaches.
- `quarkus/`: Contains Quarkus-based demonstrators for both auto-instrumentation and
  manual-instrumentation approaches.
- `docker/`: Contains Docker Compose files for setting up the necessary infrastructure components
  such as OpenTelemetry Collector and Grafana stacks.