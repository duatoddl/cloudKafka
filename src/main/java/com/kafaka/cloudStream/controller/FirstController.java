package com.kafaka.cloudStream.controller;

import com.kafaka.cloudStream.vo.Foo;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *
 * author         : AD01701301
 * date           : 2022-06-13
 */
@RestController
public class FirstController {

	@Resource
	private KafkaTemplate<Object, Object> kafkaTemplate;

	@PostMapping("/send/first/{message}")
	public void sendFirst(@PathVariable String message) {
		this.kafkaTemplate.send("firstTopic", new Foo(message));
	}
}
