package io.github.basiccuboid.openglbasic

data class OpenGlConfiguration(
    val majorVersion: Int,
    val minorVersion: Int,
    val title: String,
    val screenWidth: Int,
    val screenHeight: Int,
    val fullscreen: Boolean,
    val vSync: Boolean,
    val wireFrane: Boolean,
    val clearColorRed: Float,
    val clearColorGreen: Float,
    val clearColorBlue: Float,
    val nearPlane: Float,
    val farPlane: Float,
) {

    fun prettyFormat(): String = """
        + OpenGL configuration
            OpenGL Version ........................... $majorVersion.$minorVersion
            GameWindow Title ......................... $title
            Screen Size .............................. $screenWidth x $screenHeight
            Fullscreen ............................... $fullscreen
            V-Sync ................................... $vSync
            Wireframe ................................ $wireFrane
            Clear Color (r, b, g, alpha) ............. ($clearColorRed, $clearColorGreen, $clearColorBlue, 1.0)
            Near Plane ............................... $nearPlane
            Far Plane ................................ $farPlane
    """
}
