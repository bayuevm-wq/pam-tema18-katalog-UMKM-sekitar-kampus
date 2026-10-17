package id.ac.itera.katalogumkm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform