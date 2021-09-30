package com.jayaspiya.everestbooks


import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MapsActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MapsActivity::class.java)

    @Test
    fun mapsActivityTest() {
    }
}
