package io.github.basiccuboid.openglbasic

import io.github.basiccuboid.utility.TextFile
import mu.KotlinLogging
import org.lwjgl.opengl.GL41

private val logger = KotlinLogging.logger {}

object ShaderProgram {

    val defaultProgram = createFromResources()

    fun createFromResources(
        vertexShaderFilePath: String = "/shader/vertex.glsl",
        fragmentShaderFilePath: String = "/shader/fragment.glsl",
    ): Int {
        logger.info { "Create a ShaderProgram ..." }

        val vertexShaderCode = TextFile.readFromResources(vertexShaderFilePath)
        val fragmentShaderCode = TextFile.readFromResources(fragmentShaderFilePath)
        val id = createShaderProgram(vertexShaderCode, fragmentShaderCode)

        logger.info { "... Finished ShaderProgram id = '$id'" }

        return id
    }

    fun validate(shaderProgramId: Int) {
        logger.info { "Validate ShaderProgram with id='$shaderProgramId'" }

        GL41.glValidateProgram(shaderProgramId)
        if (GL41.glGetProgrami(shaderProgramId, GL41.GL_VALIDATE_STATUS) == GL41.GL_FALSE) {
            throw RuntimeException(
                "An error occurred during validating the ShaderProgram with id='$shaderProgramId'. The reason was: ${
                    GL41.glGetProgramInfoLog(
                        shaderProgramId
                    )
                }"
            )
        }
    }

    private fun createShaderProgram(
        vertexShaderCode: List<String>,
        fragmentShaderCode: List<String>,
    ): Int {
        logger.debug { "    initialize ShaderProgram" }

        val programId = GL41.glCreateProgram()
        CleanUp.addShaderProgramId(programId)

        val vertexShaderId = createShader(GL41.GL_VERTEX_SHADER)
        compileShader(vertexShaderId, vertexShaderCode)
        GL41.glAttachShader(programId, vertexShaderId)

        val fragmentShaderId = createShader(GL41.GL_FRAGMENT_SHADER)
        compileShader(fragmentShaderId, fragmentShaderCode)
        GL41.glAttachShader(programId, fragmentShaderId)

        linkShaderProgram(programId)

        GL41.glDetachShader(programId, vertexShaderId)
        GL41.glDetachShader(programId, fragmentShaderId)

        return programId
    }

    private fun createShader(type: Int): Int {
        logger.debug { "    create Shader of type: '$type'" }

        val id = GL41.glCreateShader(type)
        if (id == 0) {
            throw RuntimeException("An error occurred during creating a shader of type: '${type}'")
        }
        CleanUp.addShaderId(id)
        return id
    }

    private fun compileShader(id: Int, sourceCode: List<String>) {
        logger.debug { "    compile Shader with id='$id'" }

        val code = sourceCode
            .filter { line -> line != "" }
            .joinToString(separator = "\n")
        GL41.glShaderSource(id, code)
        GL41.glCompileShader(id)

        if (GL41.glGetShaderi(id, GL41.GL_COMPILE_STATUS) == GL41.GL_FALSE) {
            throw RuntimeException(
                "An error occurred during compiling the Shader with id='$id'. The reason was: ${
                    GL41.glGetShaderInfoLog(
                        id
                    )
                }"
            )
        }
    }

    private fun linkShaderProgram(id: Int) {
        logger.debug { "    link ShaderProgram with id='$id'" }

        GL41.glLinkProgram(id)
        if (GL41.glGetProgrami(id, GL41.GL_LINK_STATUS) == GL41.GL_FALSE) {
            throw RuntimeException(
                "An error occurred during linking the ShaderProgram with id='$id'. The reason was: ${
                    GL41.glGetProgramInfoLog(
                        id
                    )
                }"
            )
        }
    }
}