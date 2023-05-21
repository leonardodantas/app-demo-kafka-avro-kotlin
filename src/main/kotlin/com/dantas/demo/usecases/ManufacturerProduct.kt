package com.dantas.demo.usecases

import com.application.kotlin.kafka.schemas.ManufacturerSchema
import org.apache.avro.generic.GenericRecord
import org.springframework.stereotype.Service


@Service("com.application.kotlin.kafka.schemas.ManufacturerSchema")
class ManufacturerProduct : IExecuteProcess {

    override fun execute(genericRecord: GenericRecord) {
        println("ManufacturerSchema")
        val manufacturerSchema: ManufacturerSchema = genericRecord as ManufacturerSchema
        println(manufacturerSchema.id)
    }
}