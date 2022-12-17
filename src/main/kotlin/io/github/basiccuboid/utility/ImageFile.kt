package io.github.basiccuboid.utility

import mu.KotlinLogging
import org.lwjgl.stb.STBImage
import org.lwjgl.system.MemoryStack
import java.io.File
import java.nio.ByteBuffer

private val logger = KotlinLogging.logger {}

data class Image(val width: Int, val height: Int, val pixels: ByteBuffer)

object ImageFile {

    fun readFrom(filePath: String): Image {
        logger.info { "Read Image-File: '$filePath'" }

        val stack = MemoryStack.stackPush()
        val widthBuffer = stack.mallocInt(1)
        val heightBuffer = stack.mallocInt(1)
        val colorBuffer = stack.mallocInt(1)
        val file = File(filePath).absolutePath

        STBImage.stbi_set_flip_vertically_on_load(true)
        val pixels = STBImage.stbi_load(file, widthBuffer, heightBuffer, colorBuffer, 4)
            ?: throw RuntimeException("An error occurred during reading the Image-File: '$filePath'. The reason was: ${STBImage.stbi_failure_reason()}")

        return Image(widthBuffer.get(), heightBuffer.get(), pixels)
    }
}