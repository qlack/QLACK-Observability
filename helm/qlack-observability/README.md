# QLACK Observability Helm Chart

This repository contains the Helm chart for deploying the QLACK Observability stack, which includes the following components:
- **OpenTelemetry Collector**: For collecting and exporting telemetry data.
- **Grafana Loki**: For log aggregation and querying.
- **Grafana Mimir**: For metrics storage and querying.
- **Grafana Tempo**: For distributed tracing.
- **Grafana**: For visualization and monitoring dashboards.

## Prerequisites

Before deploying this Helm chart, ensure the following prerequisites are met:
- Kubernetes cluster (v1.20+ recommended)
- Helm 3.x installed
- Docker registry credentials (if required for pulling images)
- Persistent storage provisioner (if using persistent volumes)

## Installation

### 1. Add Helm Repositories
Add the required Helm repositories:
```bash
helm repo add open-telemetry https://open-telemetry.github.io/opentelemetry-helm-charts
helm repo add grafana https://grafana.github.io/helm-charts
helm repo update

### 2. Clone the Repository
Clone this repository to your local machine:
```bash
git clone https://github.com/your-repo/qlack-observability.git
cd qlack-observability
```
### 3. Install the Helm Chart
Install the chart with default values:
```bash
helm install qlack-observability ./helm/qlack-observability
```
To customize the installation, modify the values.yaml file or pass custom values using the --set flag.

### 4. Verify the Installation
Check the status of the deployed pods:
```bash
kubectl get pods -n <namespace>
```

## Configuration

### Global Configuration

Set global values such as image pull secrets in the values.yaml file:
```yaml
global:
  imagePullSecrets:
    - name: regcred
```

### Component-Specific Configuration

Each component can be enabled/disabled and configured individually in the values.yaml file.

#### OpenTelemetry Collector
```yaml
otelCollector:
  enabled: true
  config:
    exporters:
      otlphttp/loki:
        endpoint: http://loki:3100/otlp
```
#### Grafana Loki
```yaml
loki:
  enabled: true
  loki:
    storage:
      type: filesystem
```

#### Grafana Mimir
```yaml
mimir:
  enabled: true
  mimir:
    config: |
      blocks_storage:
        backend: filesystem
```

#### Grafana Tempo
```yaml
tempo:
  enabled: true
  tempo:
    storage:
      trace:
        backend: local
```

#### Grafana
```yaml
grafana:
  enabled: true
  datasources:
    datasources.yaml:
      apiVersion: 1
      datasources:
        - name: Loki
          type: loki
          url: http://loki:3100
```

## Accessing Grafana
If you have not enabled ingress for Grafana, follow these steps to access the Grafana dashboard:
1. Forward the Grafana service port:
   ```kubectl port-forward svc/<grafana-service-name> 3000:80 -n <namespace>```.
2. Open your browser and navigate to http://localhost:3000.
3. Use the default credentials:
   - Username: admin
   - Password: admin (or as configured in values.yaml)

If ingress is enabled, access Grafana via the configured ingress URL and use the appropriate credentials as described above.

## Uninstallation
To uninstall the Helm chart:
```bash
helm uninstall qlack-observability
```
To delete all associated resources:
```bash
kubectl delete namespace <namespace>
```

## Troubleshooting
1. Check Pod Logs
   If a component fails to start, check its logs:
```bash
kubectl logs <pod-name> -n <namespace>
```
2. Verify Helm Values
   Ensure the values.yaml file is correctly configured for your environment.
3. Check Kubernetes Events
Inspect events for any issues:
```bash
kubectl get events -n <namespace>
```
## Maintainers
- European Dynamics SA: https://www.eurodyn.com
