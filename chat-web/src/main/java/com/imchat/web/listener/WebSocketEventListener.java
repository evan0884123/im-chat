package com.imchat.web.listener;

import com.imchat.web.controllers.bean.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * @author Administrator
 */
@Component
@Slf4j
public class WebSocketEventListener {

    private SimpMessageSendingOperations messageTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {

        log.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");

        if (username != null) {
            log.info("User disconnect :" + username);

            ChatMessage chatMessage = new ChatMessage()
                    .setType(ChatMessage.MessageType.LEAVE)
                    .setSender(username);

            messageTemplate.convertAndSend("topic/public", chatMessage);
        }
    }

    @Autowired
    public void setMessageTemplate(SimpMessageSendingOperations messageTemplate) {
        this.messageTemplate = messageTemplate;
    }
}
