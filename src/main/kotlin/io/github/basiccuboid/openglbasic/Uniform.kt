package io.github.basiccuboid.openglbasic

import mu.KotlinLogging
import org.joml.Matrix4f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL41

private val logger = KotlinLogging.logger {}

class Uniform(shaderProgramId: Int, withTexture: Boolean = true) {

    private val locations: Map<String, Int>

    init {
        logger.info { "Create Uniforms for ShaderProgram with id='$shaderProgramId'" }
        locations = initializeLocations(shaderProgramId, withTexture)
    }

    private fun initializeLocations(shaderProgramId: Int, withTexture: Boolean = true) =
        if (withTexture) {
            mapOf(
                createLocation(shaderProgramId, MODEL_MATRIX),
                createLocation(shaderProgramId, VIEW_MATRIX),
                createLocation(shaderProgramId, PROJECTION_MATRIX),
                createLocation(shaderProgramId, TEXTURE_SAMPLER0),
            )
        } else {
            mapOf(
                createLocation(shaderProgramId, MODEL_MATRIX),
                createLocation(shaderProgramId, VIEW_MATRIX),
                createLocation(shaderProgramId, PROJECTION_MATRIX),
            )
        }

    fun upload(model: Matrix4f, view: Matrix4f, projection: Matrix4f) {
        uploadMatrix(locations[MODEL_MATRIX]!!, model)
        uploadMatrix(locations[VIEW_MATRIX]!!, view)
        uploadMatrix(locations[PROJECTION_MATRIX]!!, projection)
    }

    fun upload(model: Matrix4f, view: Matrix4f, projection: Matrix4f, textureId: Int) {
        uploadMatrix(locations[MODEL_MATRIX]!!, model)
        uploadMatrix(locations[VIEW_MATRIX]!!, view)
        uploadMatrix(locations[PROJECTION_MATRIX]!!, projection)
        activateTexture0(locations[TEXTURE_SAMPLER0]!!, textureId)
    }

    private fun activateTexture0(locationId: Int, textureId: Int) {
        GL41.glUniform1i(locationId, 0)
        GL41.glActiveTexture(textureId)
        GL41.glBindTexture(GL41.GL_TEXTURE_2D, textureId)
    }

    private fun uploadMatrix(locationId: Int, matrix: Matrix4f) {
        val buffer = matrix.get(MATRIX_BUFFER)
        GL41.glUniformMatrix4fv(locationId, false, buffer)
    }

    private fun createLocation(shaderProgramId: Int, name: String): Pair<String, Int> {
        val locationId = GL41.glGetUniformLocation(shaderProgramId, name)
        return Pair(name, locationId)
    }

    companion object {
        private const val MODEL_MATRIX = "modelMatrix"
        private const val VIEW_MATRIX = "viewMatrix"
        private const val PROJECTION_MATRIX = "projectionMatrix"
        private const val TEXTURE_SAMPLER0 = "textureSampler"
        private const val CAPACITY = 16
        private val MATRIX_BUFFER = BufferUtils.createFloatBuffer(CAPACITY)
    }
}