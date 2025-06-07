package me.syncwrld.minecraft.pingcompass.sample

enum class TrackingMode(val state: String) {
    HIGHEST("§cHighest"),
    LOWEST("§aLowest");

    fun state(): String {
        return this.state;
    }
}