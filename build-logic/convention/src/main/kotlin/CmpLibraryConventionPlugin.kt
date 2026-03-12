import org.gradle.api.Plugin
import org.gradle.api.Project

class CmpLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            with(pluginManager){
                //every cmp library is kmp library
                apply("com.example.chirpappkmp.convention.kmp.library")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.compose")
            }
        }
    }
}