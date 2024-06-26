package io.aklivity.zilla.example.todo;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.errors.LogAndContinueExceptionHandler;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.StreamsBuilderFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class KafkaConfig
{
    @Value("${spring.kafka.bootstrap-servers:localhost:19092}")
    private String bootstrapServers;

    @Value("${spring.kafka.security-protocol:PLAINTEXT}")
    private String securityProtocol;

    @Value("${spring.kafka.application-id:todo-command-service}")
    private String applicationId;

    @Value("${spring.kafka.sasl.jaas.config:#{null}}")
    private String saslJaasConfig;

    @Value("${spring.kafka.sasl.mechanism:#{null}}")
    private String saslMechanism;

    @Value("${spring.kafka.streams.state.dir:#{null}}")
    private String stateDir;

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    KafkaStreamsConfiguration kafkaStreamsConfig()
    {
        Map<String, Object> props = new HashMap<>();

        props.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(StreamsConfig.SECURITY_PROTOCOL_CONFIG, securityProtocol);
        if (saslJaasConfig != null)
        {
            props.put("sasl.mechanism", saslMechanism);
            props.put("sasl.jaas.config", saslJaasConfig);
        }
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serde.class.getName());
        props.put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, LogAndContinueExceptionHandler.class);
        if (stateDir != null)
        {
            props.put(StreamsConfig.STATE_DIR_CONFIG, stateDir);
        }
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 0);

        return new KafkaStreamsConfiguration(props);
    }

    @Bean
    public StreamsBuilderFactoryBeanCustomizer streamsBuilderFactoryBeanCustomizer()
    {
        return sfb -> sfb.setStreamsUncaughtExceptionHandler(exception
            -> StreamsUncaughtExceptionHandler.StreamThreadExceptionResponse.REPLACE_THREAD);
    }
}
