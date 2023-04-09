package com.socketIn.controllers

import com.socketIn.entities.beens.Messages
import com.socketIn.users.UserStorage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller

@Controller
class ChatRoomController {
    @Autowired
    var simpMessagingTemplate: SimpMessagingTemplate? = null // For Automate the sending of messages to a specific user

//    @MessageMapping("/websocket/chatroom-public") // Think of this as a route or endpoint like in rest controllers
//    @SendTo("/websocket/chatroom/public") // This is the topic/server/group that the message will be sent to
//    fun publicChatRoom(@Payload messages: Messages): Messages {
//        return Messages()
//    }

    @MessageMapping("/websocket/chatroom-private/{to}")
    fun privateChatRoom(@DestinationVariable to:String, @Payload messages: Messages): Messages {
        val isExist = UserStorage.instance?.getUsers()?.contains(to) ?: Boolean

        if(isExist == true){
            simpMessagingTemplate?.convertAndSendToUser(
                messages.receiver,
                "/chatroom-private/$to",
                messages
            ) // This line of code will send the message to the user with the username of messages.receiver
            // So, We wil able to send messages to a specific user dynamically without hardcoded the username And Topics

        }
        return Messages()
    }
}