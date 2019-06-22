import kotlin.String

/**
 * Find which updates are available by running
 *     `$ ./gradlew buildSrcVersions`
 * This will only update the comments.
 *
 * YOU are responsible for updating manually the dependency version. */
object Versions {
    const val kotlin_big_math: String = "0.0.1" 

    const val buildsrcversions: String = "0.3.2" 

    const val kotlintest_runner_junit5: String = "3.3.2" 

    const val org_jetbrains_kotlin: String = "1.3.31" 

    const val junit_jupiter_api: String = "5.4.2" 

    /**
     *
     *   To update Gradle, edit the wrapper file at path:
     *      ./gradle/wrapper/gradle-wrapper.properties
     */
    object Gradle {
        const val runningVersion: String = "5.4.1"

        const val currentVersion: String = "5.4.1"

        const val nightlyVersion: String = "5.5-20190428000050+0000"

        const val releaseCandidate: String = ""
    }
}
