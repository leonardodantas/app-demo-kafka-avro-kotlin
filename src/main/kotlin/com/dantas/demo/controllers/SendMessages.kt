package com.dantas.demo.controllers

import com.dantas.demo.usecases.SendMessage
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("send/messages")
class SendMessages(
        private val sendMessage: SendMessage
) {

    @GetMapping
    fun sendMessages(): String {
        sendMessage.execute()
        return "Send messages"
    }
}