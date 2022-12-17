package io.github.basiccuboid.openglbasic

import io.github.basiccuboid.utility.Image
import io.github.basiccuboid.utility.ImageFile
import mu.KotlinLogging
import org.lwjgl.opengl.GL41
import org.lwjgl.stb.STBImage

private val logger = KotlinLogging.logger {}

object Texture {

    fun create(filePath: String): Int {
        val image = ImageFile.readFrom(filePath)
        return initialize(image)
    }

    private fun initialize(image: Image): Int {
        logger.info { "Create Texture with dimension: ${image.width}x${image.height}" }

        val id = GL41.glGenTextures()
        CleanUp.addTextureId(id)

        GL41.glBindTexture(GL41.GL_TEXTURE_2D, id)
        GL41.glPixelStorei(GL41.GL_UNPACK_ALIGNMENT, 1)
        GL41.glTexImage2D(
            GL41.GL_TEXTURE_2D,
            0,
            GL41.GL_RGBA,
            image.width,
            image.height,
            0,
            GL41.GL_RGBA,
            GL41.GL_UNSIGNED_BYTE,
            image.pixels
        )
        // NEAREST Filtering instead of LINEAR for sharp edges, because we are using PixelArt
        GL41.glTexParameteri(
            GL41.GL_TEXTURE_2D,
            GL41.GL_TEXTURE_MIN_FILTER,
            GL41.GL_NEAREST_MIPMAP_NEAREST
        )
        GL41.glTexParameteri(GL41.GL_TEXTURE_2D, GL41.GL_TEXTURE_MAG_FILTER, GL41.GL_NEAREST)
        GL41.glGenerateMipmap(GL41.GL_TEXTURE_2D)
        STBImage.stbi_image_free(image.pixels)

        return id
    }
}