package com.fdm.setup;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.fdm.model.Map;

@EnableScheduling
@Configuration
public class SchedulerConfig {
	
	SimpMessagingTemplate template;
	
	//this might go in the controller
	@Scheduled(fixedDelay = 1000)
	public void autoUpdateMap() {
		template.convertAndSend("/topic/game", map);
	}

}
