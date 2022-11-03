package com.imchat.web.controllers.router;

import com.imchat.web.controllers.bean.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

/**
 *
 * /app开头的客户端发送的所有消息都将路由到这些使用@MessageMapping注释的消息处理方法
 * @author Administrator
 */
@Controller
public class ChatController {

    /***
     * /app/chat.sendMessage的消息将路由到sendMessage（）方法
     * @param chatMessage
     * @return
     */
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage){

        return chatMessage;
    }

    /**
     * /app/chat.addUser的消息将路由到addUser（）方法
     * @param chatMessage
     * @param headerAccessor
     * @return
     */
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor){

        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username",chatMessage.getSender());
        return chatMessage;
    }
}
