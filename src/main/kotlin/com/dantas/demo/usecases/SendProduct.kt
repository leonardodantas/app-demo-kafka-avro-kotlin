package com.dantas.demo.usecases

import com.application.kotlin.kafka.schemas.ProductSchema
import org.apache.avro.generic.GenericRecord
import org.springframework.stereotype.Service

@Service("com.application.kotlin.kafka.schemas.ProductSchema")
class SendProduct : IExecuteProcess {

    override fun execute(genericRecord: GenericRecord) {
        println("ProductSchema")
        val productSchema: ProductSchema = genericRecord as ProductSchema
        println(productSchema.id)
    }
}