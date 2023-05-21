package com.dantas.demo.kafka.producers

import com.application.kotlin.kafka.schemas.ManufacturerSchema
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.util.*

@Service
class SendManufactureProducer(
        private val productKafkaAvro: KafkaTemplate<String, ManufacturerSchema>,
        @Value("\${kafka.topics.send.avro}")
        private val topic: String
) {

    fun execute(manufacturerSchema: ManufacturerSchema) {
        val record = ProducerRecord(topic, UUID.randomUUID().toString(), manufacturerSchema)
        productKafkaAvro.send(record)
    }
}