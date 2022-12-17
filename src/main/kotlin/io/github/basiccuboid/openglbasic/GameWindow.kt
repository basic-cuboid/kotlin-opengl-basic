package io.github.basiccuboid.openglbasic

import mu.KotlinLogging
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.glfw.GLFWKeyCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL41
import org.lwjgl.system.MemoryUtil

private val logger = KotlinLogging.logger {}

object GameWindow {

    fun create(openGlConfiguration: OpenGlConfiguration): Long {
        logger.info { "Create Game-Window ..." }

        initializeGlfw()
        val id = initializeGameWindow(openGlConfiguration)
        CleanUp.setGameWindowId(id)
        initializeKeyCallback(id)
        centerGameWindow(id, openGlConfiguration)

        logger.info { "... Finished Game-Window" }

        return id
    }

    private fun initializeGlfw() {
        logger.debug { "    initialize GLFW" }
        GLFWErrorCallback.createPrint(System.err).set()
        if (!GLFW.glfwInit()) {
            throw RuntimeException("An error occurred during initializing GLFW")
        }
    }

    private fun initializeGameWindow(config: OpenGlConfiguration): Long {
        logger.debug { "    initialize Game-Window" }

        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL41.GL_TRUE)
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL41.GL_TRUE)
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, config.majorVersion)
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, config.minorVersion)
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL41.GL_TRUE)
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE)

        val monitor = GLFW.glfwGetPrimaryMonitor()
        val id = GLFW.glfwCreateWindow(
            config.screenWidth,
            config.screenHeight,
            config.title,
            monitor,
            MemoryUtil.NULL
        )
        if (id == MemoryUtil.NULL) {
            throw RuntimeException("An error occurred during initializing the Game-Window")
        }

        GLFW.glfwMakeContextCurrent(id)
        GL.createCapabilities()
        GL41.glClearColor(config.clearColorRed, config.clearColorGreen, config.clearColorBlue, 1.0f)
        GLFW.glfwSwapInterval(if (config.vSync) 1 else 0)
        GLFW.glfwShowWindow(id)
        GLFW.glfwSetInputMode(id, GLFW.GLFW_STICKY_KEYS, GLFW.GLFW_TRUE)

        GL41.glEnable(GL41.GL_DEPTH_TEST)
        GL41.glEnable(GL41.GL_BLEND)
        GL41.glBlendFunc(GL41.GL_SRC_ALPHA, GL41.GL_ONE_MINUS_SRC_ALPHA)
        GL41.glEnable(GL41.GL_CULL_FACE)
        GL41.glCullFace(GL41.GL_BACK)

        if (config.wireFrane) {
            GL41.glPolygonMode(GL41.GL_FRONT_AND_BACK, GL41.GL_LINE)
        }

        return id
    }

    private fun initializeKeyCallback(id: Long) {
        logger.debug { "    initialize Key Callback" }

        val callback: GLFWKeyCallback = object : GLFWKeyCallback() {
            override fun invoke(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
                if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
                    GLFW.glfwSetWindowShouldClose(id, true)
                }
            }
        }

        GLFW.glfwSetKeyCallback(id, callback)
    }

    private fun centerGameWindow(id: Long, config: OpenGlConfiguration) {
        logger.debug { "    center Game-Window" }

        val videoMode = GLFW.glfwGetVideoModes(GLFW.glfwGetPrimaryMonitor())
            ?: throw RuntimeException("No Video-Mode was found for the primary monitor")

        val xPos = videoMode.width() - config.screenWidth / 2
        val yPos = videoMode.height() - config.screenHeight / 2
        GLFW.glfwSetWindowPos(id, xPos, yPos)
    }
}