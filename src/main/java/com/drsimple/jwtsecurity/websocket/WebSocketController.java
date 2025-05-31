package com.drsimple.jwtsecurity.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class WebSocketController {


    // Server application will listen for messages sent to the endpoint
    // i.e /app/sendMessage
    @MessageMapping("/sendMessage") //this will receive the message sent to the endpoint /app/sendMessage
    @SendTo("/topic/notifications") //this will broadcast the message to all subscribers of /topic/messages
    public String sendMessage(String message) {
        System.out.println("Sending message: " + message);
        return "Message sent: " + message;
    }
}
