package com.sa.githubers.common

interface Mapper<From, To> {
    fun mapFrom(from: From): To
}