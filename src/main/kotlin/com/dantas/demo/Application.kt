package com.dantas.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AppDemoKafkaAvroKotlinApplication

fun main(args: Array<String>) {
    runApplication<AppDemoKafkaAvroKotlinApplication>(*args)
}
