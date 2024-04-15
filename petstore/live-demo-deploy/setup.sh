#!/bin/bash
set -e

NAMESPACE="${NAMESPACE:-petstore}"

## Installing services

helm upgrade --install apicurio apicurio --version 1.0.15 -n apicurio --repo https://one-acre-fund.github.io/oaf-public-charts

# Zilla Petstore Demo
helm upgrade --install zilla oci://ghcr.io/aklivity/charts/zilla --version 0.9.76 -n $NAMESPACE --create-namespace --wait \
    --set-file zilla\\.yaml=zilla.yaml \
    --values values.yaml
