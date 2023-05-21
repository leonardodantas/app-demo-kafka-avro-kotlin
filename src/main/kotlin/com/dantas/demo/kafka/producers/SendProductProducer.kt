package com.dantas.demo.kafka.producers

import com.application.kotlin.kafka.schemas.ProductSchema
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.util.*

@Service
class SendProductProducer(
        private val productKafkaAvro: KafkaTemplate<String, ProductSchema>,
        @Value("\${kafka.topics.send.avro}")
        private val topic: String
) {

    fun execute(productSchema: ProductSchema) {
        val record = ProducerRecord(topic, UUID.randomUUID().toString(), productSchema)
        productKafkaAvro.send(record)
    }
}