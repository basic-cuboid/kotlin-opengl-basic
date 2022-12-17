package io.github.basiccuboid.openglbasic

import mu.KotlinLogging
import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL41

private val logger = KotlinLogging.logger {}

object CleanUp {

    private var gameWindowId = 0L
    private val vertexBufferObjectIds = mutableListOf<Int>()
    private val vertexArrayObjectIds = mutableListOf<Int>()
    private val textureIds = mutableListOf<Int>()
    private val shaderProgramIds = mutableListOf<Int>()
    private val shaderIds = mutableListOf<Int>()

    fun purge() {
        logger.info { "Start clean-up process ..." }

        logger.debug { "    Deleting Shaders" }
        shaderIds.forEach(GL41::glDeleteShader)
        logger.debug { "    Deleting ShaderPrograms" }
        shaderProgramIds.forEach(GL41::glDeleteProgram)
        logger.debug { "    Deleting Textures" }
        textureIds.forEach(GL41::glDeleteTextures)
        logger.debug { "    Deleting VertexArrayObjects" }
        vertexArrayObjectIds.forEach(GL41::glDeleteVertexArrays)
        logger.debug { "    Deleting VertexBufferObjects" }
        vertexBufferObjectIds.forEach(GL41::glDeleteBuffers)
        logger.debug { "    Destroying GameWindow" }
        destroyGameWindow()

        logger.info { "... Finished clean-up process" }
    }

    private fun destroyGameWindow() {
        Callbacks.glfwFreeCallbacks(gameWindowId)
        GLFW.glfwSetErrorCallback(null)?.free()
        GLFW.glfwDestroyWindow(gameWindowId)
        GLFW.glfwTerminate()
    }

    fun setGameWindowId(id: Long) {
        gameWindowId = id
    }

    fun addVertexBufferObjectId(id: Int) {
        vertexBufferObjectIds.add(id)
    }

    fun addVertexArrayObjectId(id: Int) {
        vertexArrayObjectIds.add(id)
    }

    fun addTextureId(id: Int) {
        textureIds.add(id)
    }

    fun addShaderProgramId(id: Int) {
        shaderProgramIds.add(id)
    }

    fun addShaderId(id: Int) {
        shaderIds.add(id)
    }
}