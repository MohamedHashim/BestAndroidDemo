package com.backbase.assignment

import io.mockk.MockKAnnotations
import org.junit.rules.TestRule

/**
 * Created by Mohamed Hashim on 15/11/2021.
 */

object InjectMocksRule {
    fun create(testClass: Any) = TestRule { statement, _ ->
        MockKAnnotations.init(testClass, relaxUnitFun = true)
        statement
    }
}
