# QLACK Observability Helm Chart

This Helm chart deploys a complete observability stack based on the "LGTM" stack (Loki, Grafana, Tempo, Mimir) along with the OpenTelemetry Collector. It is designed to provide logs, metrics, and traces for your applications running in Kubernetes.

## Components

The chart installs and configures the following components:

*   **OpenTelemetry Collector**: Collects telemetry data (metrics, logs, traces) from applications and infrastructure, processes it, and exports it to the storage backends.
*   **Grafana Loki**: A log aggregation system inspired by Prometheus. used for storing and querying logs.
*   **Grafana Mimir**: A scalable, long-term storage for Prometheus metrics.
*   **Grafana Tempo**: A distributed tracing backend.
*   **Grafana**: The visualization platform to view dashboards and explore data from Loki, Mimir, and Tempo.

## Architecture

1.  **Collection**: The OpenTelemetry Collector runs as a DaemonSet (agent) on every node. It collects:
    *   **Logs**: From pods.
    *   **Metrics**: From host and kubelet.
    *   **Traces**: Received from applications via OTLP.
2.  **Processing**: The collector processes the data (batching, memory limiting).
3.  **Export**:
    *   **Logs** are sent to **Loki**.
    *   **Metrics** are sent to **Mimir**.
    *   **Traces** are sent to **Tempo**.
4.  **Visualization**: **Grafana** is pre-configured with datasources for Loki, Mimir, and Tempo, allowing seamless correlation between logs, metrics, and traces.

## Prerequisites

*   Kubernetes 1.20+
*   Helm 3.0+
*   Persistent Volume storage (for Loki, Mimir, Tempo, and Grafana data persistence).

## Installation

### 1. Add Helm Repositories

Ensure you have the necessary Helm repositories added if you are pulling dependencies:

```bash
helm repo add open-telemetry https://open-telemetry.github.io/opentelemetry-helm-charts
helm repo add grafana https://grafana.github.io/helm-charts
helm repo update
```

### 2. Install the Chart

Install the chart using Helm:

```bash
helm install qlack-observability ./helm/qlack-observability -n observability --create-namespace
```

## Configuration

The `values.yaml` file contains the default configuration. You can override these values during installation using `--set` or by providing a custom values file.

### Global Configuration

| Parameter | Description | Default |
|-----------|-------------|---------|
| `global.imagePullSecrets` | List of image pull secrets for private registries. | `[{"name": "regcred"}]` |
| `global.dnsService` | DNS service name for internal resolution. | `rke2-coredns-rke2-coredns` |

### OpenTelemetry Collector (`otelCollector`)

Configures the collection of telemetry data.

| Parameter | Description | Default |
|-----------|-------------|---------|
| `otelCollector.enabled` | Enable the collector. | `true` |
| `otelCollector.mode` | Deployment mode (`daemonset` or `deployment`). | `daemonset` |
| `otelCollector.presets` | Enable/disable specific collection features (host metrics, k8s attributes, etc.). | See `values.yaml` |

### Grafana Loki (`loki`)

Configures log storage.

| Parameter | Description | Default |
|-----------|-------------|---------|
| `loki.enabled` | Enable Loki. | `true` |
| `loki.deploymentMode` | Deployment mode (e.g., `SingleBinary`). | `SingleBinary` |
| `loki.loki.storage.type` | Storage backend type. | `filesystem` |

### Grafana Mimir (`mimir`)

Configures metric storage.

| Parameter | Description | Default |
|-----------|-------------|---------|
| `mimir.enabled` | Enable Mimir. | `true` |
| `mimir.mimir.structuredConfig.blocks_storage.backend` | Storage backend for blocks. | `filesystem` |

### Grafana Tempo (`tempo`)

Configures trace storage.

| Parameter | Description | Default |
|-----------|-------------|---------|
| `tempo.enabled` | Enable Tempo. | `true` |
| `tempo.storage.trace.backend` | Storage backend for traces. | `local` |

### Grafana (`grafana`)

Configures the UI.

| Parameter | Description | Default |
|-----------|-------------|---------|
| `grafana.enabled` | Enable Grafana. | `true` |
| `grafana.adminPassword` | Initial admin password. | `admin` |
| `grafana.ingress.hosts` | Hostnames for Ingress. | `qlack-observability.example.com` |

## Usage after Installation

1.  **Port Forward**:
    If not using Ingress, port-forward Grafana:
    ```bash
    kubectl port-forward svc/qlack-observability-grafana 80:80 -n observability
    ```
2.  **Login**:
    Open your browser to `http://localhost` (or your ingress host).
    *   **User**: `admin`
    *   **Password**: Value of `grafana.adminPassword` (default: `admin`).

3.  **Explore**:
    *   **Go to Explore** in Grafana.
    *   Select **Loki** to query logs.
    *   Select **Prometheus** (Mimir) to query metrics.
    *   Select **Tempo** to query traces.

## Uninstallation

To uninstall the Helm chart:
```bash
helm uninstall qlack-observability -n observability
```

To delete all associated resources (including PVCs if they are not retained):
```bash
kubectl delete namespace observability
```

## Maintainers

*   European Dynamics SA: https://www.eurodyn.com

