# Zilla gRPC Proxy on K8s

This demo deploys a gRPC proxy with Zilla to a K8s cluster with a public endpoint. The storage layer is a SASL/SCRAM auth Kafka provider. Metrcis are scrapped and pushed to a public prometheus instance.

## Installing

- Create a k8s secret  with the below yaml.

    ```yaml
    apiVersion: v1
    kind: Secret
    metadata:
        name: kafka-env
    data:
        KAFKA_USER: "<Kafka Username>"
        KAFKA_PASS: "<Kafka Password>"
    ```

- Set your desired k8s cluster config.
- Install all of the services with the setup script.

    ```shell
    ./setup.sh
    ```

    ```shell
    ./teardown.sh
    ```
