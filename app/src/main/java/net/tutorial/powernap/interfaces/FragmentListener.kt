package net.tutorial.powernap.interfaces

interface FragmentListener {
    fun fragmentCallback(message: String, position: Int? = null)
}