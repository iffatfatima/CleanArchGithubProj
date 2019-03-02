package com.example.domain.test

import com.example.domain.model.Project
import java.util.*
import java.util.concurrent.ThreadLocalRandom

object ProjectDataFactory {

    fun randomUuid(): String{
        return UUID.randomUUID().toString()
    }

    fun randomBoolean(): Boolean {
        return Math.random() < 0.5
    }

    fun makeProject() : Project{
        return Project(randomUuid(), randomUuid(), randomUuid(), randomUuid(), randomUuid(), randomUuid(), randomUuid(), randomBoolean())
    }

    fun randomInt(): Int{
        return ThreadLocalRandom.current().nextInt(0, 1000+1)
    }
    fun makeProjectList(count: Int): List<Project> {
        val projects = mutableListOf<Project>()
        repeat(count){
            projects.add(makeProject())
        }
        return projects
    }

}