package com.dantas.demo.usecases

import org.apache.avro.generic.GenericRecord

interface IExecuteProcess {

    fun execute(genericRecord: GenericRecord)
}