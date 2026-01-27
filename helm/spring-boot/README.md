# QLACK Spring Boot Helm Chart

This Helm chart deploys the QLACK Spring Boot microservices: `service-todo` and `service-timeauthority` on Kubernetes. It provides a production-ready setup with support for configuration, secrets, ingress, and observability via OpenTelemetry.

## Features
- Deploys Spring Boot microservices as independent Deployments and Services
- Configurable images, resources, environment variables, and security context
- Optional Ingress support for external access
- OpenTelemetry integration for distributed tracing and metrics
- Customizable via `values.yaml` or Helm `--set` overrides

## Prerequisites
- Kubernetes 1.21+
- Helm 3.0+
- (Optional) Ingress controller for external access
- (Optional) OpenTelemetry Collector for observability

## Installation
1. Add or clone the QLACK Observability Helm repository.
2. Install the chart:
   ```sh
   helm install <release-name> ./spring-boot
   ```
3. To customize values:
   ```sh
   helm install <release-name> ./spring-boot -f my-values.yaml
   ```

## Upgrading
To upgrade the release after changing values:
```sh
helm upgrade <release-name> ./spring-boot -f my-values.yaml
```

## Uninstalling
To uninstall the release:
```sh
helm uninstall <release-name>
```

## Configuration
All configuration options are documented in [`values.yaml`](./values.yaml). Key options include:

- `services.todo.*` and `services.timeauthority.*`: Enable/disable, image, resources, environment, ingress, etc.
- `global.imagePullSecrets`: For private registries
- `envFromConfigMap` and `envFromSecret`: Load environment variables from ConfigMaps or Secrets
- `ingress.*`: Enable and configure ingress for each service
- `resources`: Set CPU/memory requests and limits
- `probes`: Configure liveness/readiness probes

### Example: Overriding Images
```sh
helm install <release-name> ./spring-boot \
  --set services.todo.image.repository=myrepo/todo --set services.todo.image.tag=2.0.0 \
  --set services.timeauthority.image.repository=myrepo/timeauthority --set services.timeauthority.image.tag=2.0.0
```

### Example: Enabling Ingress
```sh
helm install <release-name> ./spring-boot \
  --set services.todo.ingress.enabled=true --set services.todo.ingress.hosts[0].host=todo.example.com \
  --set services.timeauthority.ingress.enabled=true --set services.timeauthority.ingress.hosts[0].host=time.example.com
```

## Environment Variables (`env:`)

Each service in this chart can be configured with environment variables using the `env:` section in `values.yaml`. These variables are injected into the container at runtime and control application behavior, observability, and integration with other systems.

### How to Use
- Define environment variables under `services.<service>.env` in `values.yaml`.
- You can override or add variables using a custom values file or with `--set` on the Helm command line.
- Some variables are required for OpenTelemetry or Spring Boot configuration; others are optional and can be customized as needed.

### Example
```yaml
services:
  todo:
    env:
      JAVA_TOOL_OPTIONS: "-Xms256m -Xmx512m"
      OTEL_SERVICE_NAME: "service-todo"
      SPRING_APPLICATION_NAME: "service-todo"
      LOGGING_LEVEL_COM_EURODYN_QLACK_OBSERVABILITY_TODO: "INFO"
      # ... more variables ...
```

### Common Environment Variables
| Variable Name                                 | Description                                                                                 | Example Value                        |
|-----------------------------------------------|---------------------------------------------------------------------------------------------|--------------------------------------|
| JAVA_TOOL_OPTIONS                            | JVM options for the container                                                              | -Xms256m -Xmx512m                    |
| OTEL_SERVICE_NAME                            | OpenTelemetry service name for tracing/metrics                                              | service-todo                         |
| OTEL_TRACES_EXPORTER / OTEL_METRICS_EXPORTER | OTEL exporter type (usually 'otlp')                                                         | otlp                                 |
| OTEL_EXPORTER_OTLP_ENDPOINT                  | OTEL collector endpoint for exporting telemetry                                             | http://otel-collector:4318           |
| OTEL_EXPORTER_OTLP_PROTOCOL                  | Protocol for OTEL exporter                                                                 | http/protobuf                        |
| SPRING_APPLICATION_NAME                      | Spring Boot application name                                                               | service-todo                         |
| LOGGING_LEVEL_COM_EURODYN_QLACK_OBSERVABILITY_TODO | Log level for the main package                                                      | INFO                                 |
| LOGGING_PATTERN_CONSOLE                      | Log output pattern                                                                         | %d{yyyy-MM-dd HH:mm:ss} ...          |
| MANAGEMENT_ENDPOINTS_WEB_EXPOSURE_INCLUDE    | Spring Boot actuator endpoints to expose                                                    | *                                    |
| MANAGEMENT_OTLP_METRICS_EXPORT_URL           | URL for Spring Boot to export metrics via OTLP                                              | http://otel-collector:4318/v1/metrics|
| MANAGEMENT_OTLP_METRICS_EXPORT_STEP          | Metrics export interval                                                                     | 1s                                   |

> **Tip:** You can add custom environment variables for your own application needs. For sensitive values, consider using `envFromSecret` and Kubernetes Secrets.

## Accessing the Services
- By default, services are exposed as ClusterIP. Use port-forwarding or enable ingress for external access.
- Example (port-forward):
  ```sh
  kubectl port-forward svc/<release-name>-todo 8080:80
  kubectl port-forward svc/<release-name>-timeauthority 8081:80
  ```

## Observability
- The chart is pre-configured for OpenTelemetry. Set the OTEL collector endpoint and exporters via environment variables in `values.yaml`.
- For more information, see the [OpenTelemetry documentation](https://opentelemetry.io/docs/).

## Troubleshooting
- Check pod logs:
  ```sh
  kubectl logs -l app.kubernetes.io/instance=<release-name>
  ```
- Check resource status:
  ```sh
  kubectl get all -l app.kubernetes.io/instance=<release-name>
  ```
- For OpenTelemetry issues, verify the OTEL collector endpoint and environment variables.

## File Structure
- `Chart.yaml` - Helm chart metadata
- `values.yaml` - Default configuration values
- `templates/` - Kubernetes resource templates
- `templates/NOTES.txt` - Post-installation instructions

## License
This chart is part of the QLACK Observability project. See the main repository for license details.

## Maintainers
- Eurodyn DevOps Team (<devops@eurodyn.com>)

## Last updated
January 27, 2026
