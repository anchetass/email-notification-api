pluginManagement {
    repositories {
        val azureArtifactsGradleAccessToken: String? by settings
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
        }
        maven {
            url = uri("https://pkgs.dev.azure.com/ffuf/_packaging/Pass/maven/v1")
            credentials {
                username = "AZURE_ARTIFACTS"
                password = System.getenv("AZURE_ARTIFACTS_ENV_ACCESS_TOKEN_2") ?: azureArtifactsGradleAccessToken
            }
        }
        gradlePluginPortal()
    }
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "org.springframework.boot" -> useModule("org.springframework.boot:spring-boot-gradle-plugin:${requested.version}")
                "de.ffuf.pass.gradle" -> useModule("de.ffuf.pass:PassGradlePlugin:${requested.version}")
            }
        }
    }
}

//include("AuthenticationPlayground")

rootProject.name = "sbshelly"
rootProject.buildFileName = "build.gradle.kts"
