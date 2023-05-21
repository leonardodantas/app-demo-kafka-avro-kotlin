package com.dantas.demo.usecases

import com.application.kotlin.kafka.schemas.ManufacturerSchema
import com.application.kotlin.kafka.schemas.ProductSchema
import com.dantas.demo.kafka.producers.SendManufactureProducer
import com.dantas.demo.kafka.producers.SendProductProducer
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.nio.ByteBuffer
import java.util.*

@Service
class SendMessage(
        private val sendManufactureProducer: SendManufactureProducer,
        private val sendProductProducer: SendProductProducer
) {

    fun execute() {
        val productSchema = ProductSchema.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setCode(UUID.randomUUID().toString())
                .setName("ProductSchema " + Random().nextInt(100))
                .setPrice(ByteBuffer.wrap(BigDecimal.valueOf(100).unscaledValue().toByteArray()))
                .setPromotionActive(Random().nextBoolean())
                .build()

        sendProductProducer.execute(productSchema)

        val manufacturerSchema = ManufacturerSchema.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setName("ManufacturerSchema " + Random().nextInt(100))
                .setAddress(UUID.randomUUID().toString())
                .setPhone(UUID.randomUUID().toString())
                .build()

        sendManufactureProducer.execute(manufacturerSchema)
    }
}