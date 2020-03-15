package com.icd.wksh.websocket.listenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
public class SubscribeEventListener implements ApplicationListener<SessionSubscribeEvent> {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@Override
	public void onApplicationEvent(SessionSubscribeEvent sessionSubscribeEvent) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionSubscribeEvent.getMessage());

		messagingTemplate.convertAndSend("/topic", "Welcome!!");
	}
}