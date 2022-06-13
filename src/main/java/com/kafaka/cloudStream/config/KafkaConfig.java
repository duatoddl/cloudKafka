package com.kafaka.cloudStream.config;

import com.kafaka.cloudStream.vo.Foo2;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.util.backoff.FixedBackOff;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 *
 * author         : AD01701301
 * date           : 2022-06-13
 */
@Configuration
public class KafkaConfig {

	private final Logger logger = LoggerFactory.getLogger(KafkaConfig.class);
	private final TaskExecutor exec = new SimpleAsyncTaskExecutor();

	/*
	 * Boot will autowire this into the container factory.
	 */
	@Bean
	public SeekToCurrentErrorHandler errorHandler(KafkaOperations<Object, Object> kafkaTemplate) {
		return new SeekToCurrentErrorHandler(
			new DeadLetterPublishingRecoverer(kafkaTemplate), new FixedBackOff(1000L, 2));
	}

	@Bean
	public RecordMessageConverter converter() {
		return new JsonMessageConverter();
	}

	@Bean
	public NewTopic first() {
		return new NewTopic("firstTopic", 1, (short) 1);
	}

	@Bean
	public NewTopic dlt() {
		return new NewTopic("firstTopic.DLT", 1, (short) 1);
	}

	@KafkaListener(id="fooGroup", topics = "firstTopic")
	public void firstListen(Foo2 fst) {
		logger.info("Received from firstTopic: " + fst);
		if(fst.getFoo().startsWith("fail")) {
			throw new RuntimeException("failed");
		}
		this.exec.execute(() -> System.out.println("firstGroup..." + fst));
	}

	/**
	 * dead letter topic
	 *
	 * @param in
	 */
	@KafkaListener(id="dltGroup", topics = "firstTopic.DLT")
	public void dltListen(String in) throws UnsupportedEncodingException {
		logger.info("Received from DLT: " + in);
		Base64.Decoder decoder = Base64.getDecoder();
		String decodedString = new String(decoder.decode(in), "UTF-8");
		this.exec.execute(() -> System.out.println("DLT..."  + decodedString));
	}

	@Bean
	@Profile("default")
	public ApplicationRunner runner() {
		return args -> {
			System.out.println("Hit Enter to terminate...");
			System.in.read();
		};
	}

}
