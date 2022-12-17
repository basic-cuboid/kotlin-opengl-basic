package io.github.basiccuboid.openglbasic

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL41

object VertexBufferObject {

    fun create(data: FloatArray, type: Int = GL41.GL_STATIC_DRAW): Int {
        val buffer = BufferUtils.createFloatBuffer(data.size)
        buffer.put(data)
        buffer.flip()

        val id = GL41.glGenBuffers()
        CleanUp.addVertexBufferObjectId(id)
        GL41.glBindBuffer(GL41.GL_ARRAY_BUFFER, id)
        GL41.glBufferData(GL41.GL_ARRAY_BUFFER, buffer, type)

        return id
    }
}