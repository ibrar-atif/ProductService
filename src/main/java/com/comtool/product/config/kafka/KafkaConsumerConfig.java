package com.comtool.product.config.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.comtool.product.dto.ProductDto;


@Configuration
@EnableKafka
public class KafkaConsumerConfig {
	@Value("${consumer.kafka.bootstrap.servers}")
	private String bootstrapServers;

	@Value("${consumer.key.deserializer}")
	private String keyDeserializer;

	@Value("${consumer.value.deserializer}")
	private String valueDeserializer;

	@Value("${consumer.group.id}")
	private String groupId;

	@Value("${consumer.enable.auto.commit}")
	private String enableAutoCommit;

	@Value("${consumer.session.timeout.ms}")
	private String sessionTimeoutMSec;

	@Value("${consumer.fetch.min.bytes}")
	private String fetchMinBytes;

	@Value("${consumer.receive.buffer.bytes}")
	private String receiveBufferBytes;

	@Value("${consumer.max.partition.fetch.bytes}")
	private String maxPartitionFetchBytes;

	@Value("${consumer.max.poll.records}")
	private String maxPollRecords;

	@Value("${consumer.auto.commit.interval.ms}")
	private String autoCommitInterval;

	@Value("${consumer.request.timeout.ms}")
	private String requestTimeout;

	@Value("${consumer.heartbeat.interval.ms}")
	private String heartbeatInterval;

	@Value("${consumer.auto.offset.reset}")
	private String consumerAutoOffset;

	@Value("${consumer.fetch.wait.max.ms}")
	private String consumerFetchWait;

	@Bean
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeoutMSec);
		props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, fetchMinBytes);
		props.put(ConsumerConfig.RECEIVE_BUFFER_CONFIG, receiveBufferBytes);
		props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, maxPartitionFetchBytes);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumerAutoOffset);
		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
		props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, consumerFetchWait);
		props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, requestTimeout);
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
		props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, heartbeatInterval);
		return props;
	}
	
	@Bean(name="kafkaConsumerFactory")
	public ConsumerFactory<String, ProductDto> kafkaConsumerFactory(){
		JsonDeserializer<ProductDto> deserializer = new JsonDeserializer<>(ProductDto.class);
	    deserializer.setRemoveTypeHeaders(false);
	    deserializer.addTrustedPackages("*");
	    deserializer.setUseTypeMapperForKey(true);
	    
		return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(),
				deserializer);
	}
	



	@Bean(name="kafkaListenerContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<String, ProductDto> kafkaListenerContainerFactory(){

		ConcurrentKafkaListenerContainerFactory<String, ProductDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(kafkaConsumerFactory());
		factory.getContainerProperties().setPollTimeout(10000);
		return factory;
	
	}

}
