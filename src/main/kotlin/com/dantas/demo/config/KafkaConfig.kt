package com.dantas.demo.config

import com.application.kotlin.kafka.schemas.ManufacturerSchema
import com.application.kotlin.kafka.schemas.ProductSchema
import io.confluent.kafka.serializers.KafkaAvroDeserializer
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig
import io.confluent.kafka.serializers.KafkaAvroSerializer
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.listener.ContainerProperties


@Configuration
class KafkaConfig(
        @Value("\${kafka.bootstrapAddress}")
        private val bootstrapAddress: String,
        @Value("\${kafka.schemaRegistry}")
        private val schemaRegistry: String
) {

    @Bean("productKafkaAvro")
    fun productKafkaAvro(): KafkaTemplate<String, ProductSchema> {
        val properties = HashMap<String, Any>()
        properties[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        properties[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        properties[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = KafkaAvroSerializer::class.java
        properties[KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG] = schemaRegistry
        return KafkaTemplate(DefaultKafkaProducerFactory(properties))
    }

    @Bean("manufacturerKafkaAvro")
    fun manufacturerKafkaAvro(): KafkaTemplate<String, ManufacturerSchema> {
        val properties = HashMap<String, Any>()
        properties[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        properties[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        properties[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = KafkaAvroSerializer::class.java
        properties[KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG] = schemaRegistry
        return KafkaTemplate(DefaultKafkaProducerFactory(properties))
    }

    @Bean
    fun consumerFactory(): DefaultKafkaConsumerFactory<String, Any> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG] = true
        props[KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG] = schemaRegistry
        props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"

        val deserializer = KafkaAvroDeserializer()

        return DefaultKafkaConsumerFactory(
                props,
                StringDeserializer(),
                deserializer)
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, GenericRecord> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, GenericRecord>()
        factory.consumerFactory = consumerFactory()
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL_IMMEDIATE
        factory.containerProperties.isSyncCommits = true;
        return factory
    }

}