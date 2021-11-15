package com.backbase.assignment.core.exceptions

/**
 * Created by Mohamed Hashim on 15/11/2021.
 */

sealed class Failure {
    object DataError : Failure()
    object ServerError : Failure()
}
