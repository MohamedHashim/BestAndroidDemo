package com.backbase.assignment

import org.junit.Rule

/**
 * Created by Mohamed Hashim on 15/11/2021.
 */

abstract class UnitTest {
    @Suppress("LeakingThis")
    @Rule
    @JvmField
    val injectMocks = InjectMocksRule.create(this@UnitTest)
}
