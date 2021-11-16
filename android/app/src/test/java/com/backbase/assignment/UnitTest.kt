package com.backbase.assignment

import org.junit.Rule
import org.robolectric.annotation.Config

/**
 * Created by Mohamed Hashim on 15/11/2021.
 */

@Config(manifest = "src/main/AndroidManifest.xml")
abstract class UnitTest {
    @Suppress("LeakingThis")
    @Rule
    @JvmField
    val injectMocks = InjectMocksRule.create(this@UnitTest)
}
