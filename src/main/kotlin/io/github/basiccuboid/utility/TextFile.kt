package io.github.basiccuboid.utility

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

object TextFile {

    fun readFromResources(filePath: String): List<String> {
        logger.info { "Read Text-File (from Resources): '$filePath'" }
        return object {}.javaClass
            .getResourceAsStream(filePath)
            ?.bufferedReader()
            ?.readLines()
            ?: throw RuntimeException("An error occurred during reading the Text-File: '$filePath'")
    }
}