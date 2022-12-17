package io.github.basiccuboid.utility

import org.junit.jupiter.api.Test
import strikt.api.expectCatching
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isA
import strikt.assertions.isFailure

internal class TextFileTest {

    @Test
    fun `read a valid text-file`() {
        expectThat(
            TextFile.readFromResources("/files/valid-text-file.txt")
        ).containsExactly(
            "1. Line",
            "2. Line",
            "3. Line"
        )
    }

    @Test
    fun `try to read a text-file that does not exists`() {
        expectCatching { TextFile.readFromResources("/files/this-file-does-not-exist.txt") }
            .isFailure()
            .isA<RuntimeException>()
    }
}