package com.example.chatwave.Data

open class Events<out T>(val content : T) {
    var hasbeenHandled = false
    fun getContentOrNull(): T?{
        return if (hasbeenHandled) null
        else{
            hasbeenHandled = true
            return content
        }
    }
}