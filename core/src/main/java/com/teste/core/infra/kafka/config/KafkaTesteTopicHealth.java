package com.teste.core.infra.kafka.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.ListConsumerGroupOffsetsResult;
import org.apache.kafka.clients.admin.ListOffsetsResult.ListOffsetsResultInfo;
import org.apache.kafka.clients.admin.OffsetSpec;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.TopicPartitionInfo;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

/**
 * 
 * @since   2023-07-03
 * @version 0.9.0
 * @author  <a href="http://vertis-solutions.com">Vertis Solutions</a>
 */
@Component
@AllArgsConstructor
public class KafkaTesteTopicHealth implements HealthIndicator {
	
	private static final String TO_CONSUME = "to_consume";
	private static final String TOTAL_CONSUMED_MESSAGES = "total_consumed_messages";
	private static final String TOTAL_MESSAGES = "total_messages";
	
	private final KafkaAdmin kafkaAdmin;
	
	@Override
	public Health health() {
		
		try(AdminClient client = KafkaAdminClient.create(kafkaAdmin.getConfigurationProperties())) {
			
			long totalMessages = getTotalMessages(client, "teste");
			long totalConsumedMessages = getTotalConsumedMessage(client, "teste", "group-1");
			long toConsume = totalMessages - totalConsumedMessages;
			
			if(toConsume > 1500) {
				
				return Health.down()
							.withDetail("status", "CRITICAL")
							.withDetail(TOTAL_MESSAGES, totalMessages)
							.withDetail(TOTAL_CONSUMED_MESSAGES, totalConsumedMessages)
							.withDetail(TO_CONSUME, toConsume)
						.build();
				
			}else if(toConsume > 1000) {
				return Health.down()
							.withDetail("status", "MAJOR")
							.withDetail(TOTAL_MESSAGES, totalMessages)
							.withDetail(TOTAL_CONSUMED_MESSAGES, totalConsumedMessages)
							.withDetail(TO_CONSUME, toConsume)
						.build();
				
			}else if(toConsume > 500) {
				return Health.down()
						.withDetail("status", "MINOR")
						.withDetail(TOTAL_MESSAGES, totalMessages)
						.withDetail(TOTAL_CONSUMED_MESSAGES, totalConsumedMessages)
						.withDetail(TO_CONSUME, toConsume)
						.build();
			}
			
			return Health.up()
							.withDetail("status", "OK")
							.withDetail(TOTAL_MESSAGES, totalMessages)
							.withDetail(TOTAL_CONSUMED_MESSAGES, totalConsumedMessages)
							.withDetail(TO_CONSUME, toConsume)
						.build();
			
		}catch (Exception e) {
			 return Health.down().withDetail("AlertManagerTopic - Error message", e.getMessage()).build();
		}
	}
	
	
	private long getTotalMessages(AdminClient adminClient, String topic) {
		
		try {
			TopicDescription topicDescription = adminClient.describeTopics(Collections.singleton(topic)).allTopicNames().get().get(topic);
			List<Integer> totalPartitions = topicDescription.partitions().stream().map(TopicPartitionInfo::partition).toList();
			
			
			Map<TopicPartition, OffsetSpec> abc = new HashMap<>();
			
			for (Integer integer : totalPartitions) {
				abc.put(new TopicPartition(topic, integer), OffsetSpec.latest());
			}
			
			Map<TopicPartition, ListOffsetsResultInfo> map = adminClient.listOffsets(abc).all().get();
			
			return map.values().stream().mapToLong(ListOffsetsResultInfo::offset).sum();
			
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
	
	private long getTotalConsumedMessage(AdminClient adminClient, String topic, String groupId) {

		long consumedMessages = 0;
		
		try {
			TopicDescription topicDescription = adminClient.describeTopics(Collections.singletonList(topic)).allTopicNames().get().get(topic);
			ListConsumerGroupOffsetsResult consumerGroupOffsets = adminClient.listConsumerGroupOffsets(groupId);
			List<TopicPartitionInfo> partitions = topicDescription.partitions();
			
			consumedMessages = partitions.stream().mapToLong(partition -> {
				try {
					
					Map<TopicPartition, OffsetAndMetadata> map = consumerGroupOffsets.partitionsToOffsetAndMetadata().get();
					OffsetAndMetadata offsetAndMetadata = map.get(new TopicPartition(topic, partition.partition()));
					
					return offsetAndMetadata.offset();
					
				} catch (Exception e) {
					return 0;
				}
				
			}).sum();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return consumedMessages;
	}
}