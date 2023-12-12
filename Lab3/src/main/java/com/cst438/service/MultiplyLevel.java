package com.cst438.service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import org.springframework.amqp.core.Queue;
import com.cst438.domain.User;
import com.cst438.domain.UserRepository;
import com.cst438.dto.MultiplyResult;
import com.cst438.dto.UserLevel;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MultiplyLevel {
	/*
	 * create or use existing message queue
	 */
	Queue queue = new Queue("multiply-level", true);
	
	@Bean
	public Queue createQueue() {
		return new Queue("multiply-game", true);
	}
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Autowired
	UserRepository userRepository;
	
	/*
	 * send message to MultiplyLevel web service
	 */
	public void sendMessageToLevel(MultiplyResult mr) {
		System.out.println("Sending rabbitmq message: "+ mr);
		String data = asJsonString(mr);
		rabbitTemplate.convertAndSend(queue.getName(), data);
		System.out.println("Message sent.");
	}
	/*
	 * read messages from MultiplyLevel web service
	 */
	@RabbitListener(queues = "multiply-game")
	@Transactional
	public void receiveMessageFromLevel(String data ) {
		System.out.println("Received: "+data);
		UserLevel userLevel = fromJsonString(
                        data, 
                        UserLevel.class);
		// update database
		User user = userRepository.findByAlias(userLevel.alias());
		user.setLevel(userLevel.level());
		userRepository.save(user);		 	
	}
	
	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	private static <T> T  fromJsonString(String str, 
                                           Class<T> valueType ) {
		try {
			return new ObjectMapper().readValue(str, valueType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void postMessageToLevel(MultiplyResult mr) {
		RestTemplate restTemplate = new RestTemplate();		
		ResponseEntity<UserLevel> response = 
                       restTemplate.postForEntity(
                           "http://localhost:8081/multiply_level", 
                           mr, 
                           UserLevel.class);
		if (response.getStatusCodeValue() == 200) {
			// update database
			UserLevel userLevel = response.getBody();
			User user = userRepository.findByAlias(
                                        userLevel.alias());
			user.setLevel(userLevel.level());
			userRepository.save(user);		
		} else {
			// error.
			System.out.println(
                         "Error: unable to post multiply_level "+
                          response.getStatusCodeValue());
		}
      }

}