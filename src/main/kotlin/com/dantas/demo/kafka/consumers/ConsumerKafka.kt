package com.dantas.demo.kafka.consumers

import com.application.kotlin.kafka.schemas.ManufacturerSchema
import com.application.kotlin.kafka.schemas.ProductSchema
import com.dantas.demo.usecases.IExecuteProcess
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.context.ApplicationContext
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ConsumerKafka(
        private val applicationContext: ApplicationContext
) {

    @KafkaListener(
            topics = ["\${kafka.topics.send.avro}"],
            groupId = "apacheAvro",
            containerFactory = "kafkaListenerContainerFactory"
    )
    fun listener(record: ConsumerRecord<String, GenericRecord>) {
        val bean = applicationContext.getBean(record.value().javaClass.name, IExecuteProcess::class.java);
        bean.execute(record.value())
    }

}