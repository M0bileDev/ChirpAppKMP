import com.example.chirpappkmp.convention.configureAndroidLibraryTarget
import com.example.chirpappkmp.convention.configureIosTargets
import com.example.chirpappkmp.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class CmpApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.kotlin.multiplatform.library")
                //kmp
                apply("org.jetbrains.kotlin.multiplatform")
                //compose library references
                apply("org.jetbrains.compose")
                //compose compiler
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            configureAndroidLibraryTarget()
            configureIosTargets()

            dependencies {
                "androidMainImplementation"(libs.findLibrary("androidx-compose-ui-tooling").get())
            }
        }
    }
}