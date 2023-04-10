package com.socketIn.users

import com.socketIn.controllers.ChatRoomController
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
class UsersController {
    private val log: Logger = LoggerFactory.getLogger(ChatRoomController::class.java)
    @GetMapping("/registration/{userName}")
    fun getNewUser(@PathVariable userName: String): Any? {
        try {
            UserStorage.instance?.setUser(userName)
        } catch (e: Exception) {
            log.error("Error: ${e.message}")
            return ResponseEntity.badRequest().body(e.message)
        }
        return ResponseEntity.ok().body("User $userName has been registered successfully")
    }

    @GetMapping("/fetchAll")
    fun fetchAll(): Set<String?>? {
        return UserStorage.instance?.getUsers()
    }

}