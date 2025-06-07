package me.syncwrld.minecraft.pingcompass.sample

enum class TrackingMode {
    HIGHEST, LOWEST;

    fun state() = when (this) {
        HIGHEST -> "§cHighest"
        LOWEST -> "§aLowest"
    }
}