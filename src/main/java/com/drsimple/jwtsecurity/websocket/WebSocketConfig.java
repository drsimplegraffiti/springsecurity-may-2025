package com.drsimple.jwtsecurity.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");  //the prefix of your topics, topics are used for broadcasting messages
        config.setApplicationDestinationPrefixes("/app"); //the prefix of your endpoints
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") //ws is the endpoint for WebSocket connections that clients will connect to
                .setAllowedOrigins("http://localhost:63342") //allow all origins for simplicity, you can restrict this to specific domains in production
                .withSockJS(); //enable SockJS fallback options for browsers that don't support WebSocket
    }

    // get the 63342 from the port of your client application, this is the default port for JetBrains IDEs like IntelliJ IDEA
}
