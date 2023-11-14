package com.teste.core.infra.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopic {

    @Bean
    NewTopic topic() {
        return TopicBuilder.name("teste")
                .partitions(10)
                .replicas(1)
                .build();
    }

}
