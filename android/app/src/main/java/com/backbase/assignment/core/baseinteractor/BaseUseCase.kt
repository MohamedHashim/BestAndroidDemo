package com.backbase.assignment.core.baseinteractor

import com.backbase.assignment.core.exceptions.Failure
import com.backbase.assignment.core.functional.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by Mohamed Hashim on 15/11/2021.
 */
abstract class BaseUseCase<in Params, out Type> where Type : Any {

    /**
     * suspended function to run repository calls in each use useCase
     */
    abstract suspend fun run(params: Params? = null): Flow<Either<Failure, Type>>

    /**
     * invoke function to handle coroutine dispatchers and collect results
     */
    open operator fun invoke(
        job: Job,
        params: Params? = null,
        onResult: (Either<Failure, Type>) -> Unit = {}
    ) {
        val backgroundJob = CoroutineScope(job + Dispatchers.IO).async { run(params) }
        CoroutineScope(job + Dispatchers.Main).launch {
            val await = backgroundJob.await()
            await.catch {
                onResult(Either.Left(Failure.ServerError))
            }.collect { d -> onResult(d) }
        }
    }
}
