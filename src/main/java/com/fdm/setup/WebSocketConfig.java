package com.fdm.setup;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/topic");
		registry.setApplicationDestinationPrefixes("/app");

		
	}
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
//		registry.addEndpoint("/game").setAllowedOrigins("http://localhost:8080");
//		registry.addEndpoint("/game").setAllowedOrigins("http://localhost:8080").withSockJS();
		registry.addEndpoint("/chat").setAllowedOrigins("http://localhost:4200");
		registry.addEndpoint("/chat").setAllowedOrigins("http://localhost:4200").withSockJS();
	}

}
