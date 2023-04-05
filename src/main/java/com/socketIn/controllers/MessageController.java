package com.socketIn.controllers;

import com.socketIn.entities.beens.GreetingServer;
import com.socketIn.entities.beens.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public GreetingServer greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new GreetingServer("Hello, " + message.getName() + "!");

    }
}
