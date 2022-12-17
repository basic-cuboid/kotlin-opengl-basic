package io.github.basiccuboid.openglbasic

import org.lwjgl.opengl.GL41

object VertexArrayObject {

    fun create(positions: FloatArray, uvCoordinates: FloatArray): Int {
        val id = GL41.glGenVertexArrays()
        CleanUp.addVertexArrayObjectId(id)
        GL41.glBindVertexArray(id)

        val positionsVboId = VertexBufferObject.create(positions)
        GL41.glBindBuffer(GL41.GL_ARRAY_BUFFER, positionsVboId)
        GL41.glVertexAttribPointer(0, 3, GL41.GL_FLOAT, false, 0, 0)

        val uvVboId = VertexBufferObject.create(uvCoordinates)
        GL41.glBindBuffer(GL41.GL_ARRAY_BUFFER, uvVboId)
        GL41.glVertexAttribPointer(1, 2, GL41.GL_FLOAT, false, 0, 0)

        GL41.glEnableVertexAttribArray(0) // positions
        GL41.glEnableVertexAttribArray(1) // texture coordinates

        return id
    }
}