package com.example.vocavista

import Course

object CartSingleton {
    private val _cart = mutableListOf<Course>()
    val cart: List<Course> get() = _cart

    fun addToCart(course: Course) {
        _cart.add(course)
    }

    fun removeFromCart(course: Course) {
        _cart.remove(course)
    }

    fun calculateTotal(): Double {
        return _cart.sumOf { it.price }
    }
}
