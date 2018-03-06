package com.example.ajeshpai.archery


interface EqInterface<T> {
    fun eqv(a:T , b: T): Boolean
    fun neqv(a: T, b: T): Boolean = !eqv(a, b)

}