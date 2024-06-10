package com.example.opsc7311.util

object IdGenerator {

    private var id: Int = 3

    fun getNewId(): Int
    {
        id += 1
        return id
    }
}