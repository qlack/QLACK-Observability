{{/*
  spring-stack.name: Returns the release name or nameOverride, truncated to 63 chars (K8s limit).
*/}}
{{- define "spring-stack.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/*
  spring-stack.chart: Returns the chart name and version, used for labeling.
*/}}
{{- define "spring-stack.chart" -}}
{{ .Chart.Name }}-{{ .Chart.Version }}
{{- end -}}

{{/*
  spring-stack.labels: Standard labels for all resources, including chart, managed-by, instance, and any global labels.
*/}}
{{- define "spring-stack.labels" -}}
helm.sh/chart: {{ include "spring-stack.chart" . }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- with .Values.global.labels }}
{{- toYaml . | nindent 0 }}
{{- end }}
{{- end -}}

{{/*
  spring-stack.selectorLabels: Selector labels for matching pods/services.
*/}}
{{- define "spring-stack.selectorLabels" -}}
app.kubernetes.io/instance: {{ .Release.Name }}
app.kubernetes.io/name: {{ include "spring-stack.name" . }}
{{- end -}}
