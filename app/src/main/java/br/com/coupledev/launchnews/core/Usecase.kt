package br.com.coupledev.launchnews.core

import kotlinx.coroutines.flow.Flow

abstract class Usecase<Source> {
    abstract suspend fun execute(): Flow<Source>
}