package com.example.mobileui.test.factory

import java.util.*
import java.util.concurrent.ThreadLocalRandom

object ProjectDataFactory {

    fun randomString():String{
        return UUID.randomUUID().toString()
    }

    fun randomInt(): Int{
        return ThreadLocalRandom.current().nextInt(0, 1000+1)
    }

    fun randomLong(): Long{
        return randomInt().toLong()
    }

    fun randomBoolean(): Boolean{
        return Math.random() > 0.5
    }
}