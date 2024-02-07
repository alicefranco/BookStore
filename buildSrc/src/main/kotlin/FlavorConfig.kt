object FlavorConfig {
    const val DEFAULT_DIMENSION_NAME = "default"

    object BuildType {
        const val DEBUG = "debug"
        const val RELEASE = "release"
    }

    object Endpoint {
        const val DEVELOPMENT = "\"https://www.googleapis.com/books/v1/\""
        const val PRODUCTION = "\"https://www.googleapis.com/books/v1/\""
    }

    object Flavor {
        const val DEVELOPMENT = "dev"
        const val PRODUCTION = "prod"
    }
}