package dev.sirtimme.iuvo.providers

import org.gradle.api.Project
import org.gradle.api.provider.Provider

/**
 * Returns the most recent commit hash in short form
 *
 * @return the 7-char commit hash
 */
fun Project.getCommitShort(): String {
    return execute("git", "rev-parse", "HEAD").get().substring(0, 7)
}

/**
 * Executes the provided [executable] with its [arguments] on the commandline
 *
 * @param executable the program
 * @param arguments additional arguments for the program
 * @return a [Provider] which contains the result of the command execution
 */
fun Project.execute(executable: String, vararg arguments: String): Provider<String> {
    return providers.of(GitValueProvider::class.java) {
        parameters.commands.set(listOf(executable, *arguments))
    }
}