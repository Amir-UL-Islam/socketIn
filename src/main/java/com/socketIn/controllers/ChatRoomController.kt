package com.socketIn.controllers

import com.socketIn.entities.beens.Messages
import com.socketIn.users.UserStorage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import javax.validation.Valid

@Controller
class ChatRoomController {
    private val log: Logger = LoggerFactory.getLogger(ChatRoomController::class.java)

    @Autowired
    var simpMessagingTemplate: SimpMessagingTemplate? = null // For Automate the sending of messages to a specific user

    @MessageMapping("/websocket/{to}")
    fun privateChatRoom(@DestinationVariable to: String, @Valid @Payload messages: Messages): Messages {
        val isExist = UserStorage.instance?.getUsers()?.contains(to) ?: Boolean

        log.info("isExist: $isExist")
        log.info("to: $to")
        log.info("messages: $messages")

        if (isExist == true) {
            simpMessagingTemplate?.convertAndSend(
                "/user/$to",
                messages
            ) // This line of code will send the message to the user with the username of messages.receiver
            // So, We wil able to send messages to a specific user dynamically without hardcoded the username And Topics

        }
        return Messages()
    }
}