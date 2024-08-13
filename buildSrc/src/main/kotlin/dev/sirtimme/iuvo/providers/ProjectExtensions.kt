package dev.sirtimme.iuvo.providers

import org.gradle.api.Project
import org.gradle.api.provider.Provider

/**
 * Returns the name of the current git branch
 *
 * @return the name of the current git branch
 */
fun Project.currentBranch(): String {
    return execute("git", "rev-parse", "--abbrev-ref", "HEAD").get()
}

/**
 * Returns the most recent commit hash in short form
 *
 * @return the 7-char commit hash
 */
fun Project.getCommitShort(): String {
    return execute("git", "rev-parse", "HEAD").get().substring(0, 7)
}

/**
 * Executes the provided [commands] on the commandline
 *
 * @param commands the command with its arguments comma-separated
 * @return a [Provider] which contains the result of the command execution
 */
fun Project.execute(vararg commands: String): Provider<String> {
    return providers.of(GitValueProvider::class.java) {
        parameters.commands.set(commands.toList())
    }
}