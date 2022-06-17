package com.kafaka.cloudStream.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafaka.cloudStream.config.KafkaConfig;
import com.kafaka.cloudStream.vo.Foo;
import com.kafaka.cloudStream.vo.Foo2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 *
 * author         : AD01701301
 * date           : 2022-06-17
 */
@Component
public class KafkaListener {

	private final Logger logger = LoggerFactory.getLogger(KafkaConfig.class);
	private final TaskExecutor exec = new SimpleAsyncTaskExecutor();
	private final ObjectMapper om = new ObjectMapper();

	@org.springframework.kafka.annotation.KafkaListener(id="fooGroup", topics = "firstTopic")
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
	@org.springframework.kafka.annotation.KafkaListener(id="dltGroup", topics = "firstTopic.DLT")
	public void dltListen(String in) throws UnsupportedEncodingException, JsonProcessingException {
		logger.info("Received from DLT: " + in);
		String decodedString = getDtlMessageDecoding(in);
		Foo foo = om.convertValue(om.readTree(decodedString), Foo.class);
		this.exec.execute(() -> System.out.println("DLT..."  + foo));
	}

	private String getDtlMessageDecoding(String in) throws UnsupportedEncodingException {
		Base64.Decoder decoder = Base64.getDecoder();
		return new String(decoder.decode(in), "UTF-8");
	}
}
