package com.legstart.recursiveaccordion

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform