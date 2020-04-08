package com.fdm.setup;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * The configuration for socketed communication
 * @author KILA
 * @version 1.0
 *
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	/**
	 * Configures the url extensions which contain the socket endpoints
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/topic");
		registry.setApplicationDestinationPrefixes("/app");
	}
	
	/**
	 * Registers the end points for socket communication, allows both regular sockets and SockJS sockets
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/game").setAllowedOrigins("http://localhost:4200");
		registry.addEndpoint("/game").setAllowedOrigins("http://localhost:4200").withSockJS();
		registry.addEndpoint("/chat").setAllowedOrigins("http://localhost:4200");
		registry.addEndpoint("/chat").setAllowedOrigins("http://localhost:4200").withSockJS();
	}

}
