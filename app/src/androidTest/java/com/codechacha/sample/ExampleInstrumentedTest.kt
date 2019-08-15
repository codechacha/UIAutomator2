package com.codechacha.sample

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent
import androidx.test.uiautomator.*
import org.hamcrest.CoreMatchers.notNullValue


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.codechacha.sample", appContext.packageName)
    }

    @Test
    fun testRunMyApp() {
        // Initialize UiDevice instance
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Press home button
        device.pressHome()

        // Wait for launcher
        val launcherPackage = device.launcherPackageName
        assertThat(launcherPackage, notNullValue())
        device.wait(
            Until.hasObject(By.pkg(launcherPackage)), 5000
        )

        // Launch my app
        val context = InstrumentationRegistry.getTargetContext()
        val intent = context.packageManager
                .getLaunchIntentForPackage(context.packageName)!!
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)

        // Wait for the app to appear
        device.wait(
            Until.hasObject(By.pkg(context.packageName).depth(0)),
            5000
        )
    }

    @Test
    fun testButtonDialog() {
        // Launch the app
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        val context = InstrumentationRegistry.getTargetContext()
        val intent = context.packageManager
            .getLaunchIntentForPackage(context.packageName)!!
        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)

        // Wait for the app to appear
        device.wait(
            Until.hasObject(By.pkg(context.packageName).depth(0)),
            5000
        )

        // Find OK button
        val okButton: UiObject2 = device.findObject(
            By.text("OK").clazz("android.widget.Button")
        )
        // Click OK button
        if (okButton.isEnabled && okButton.isClickable) {
            okButton.click()
        }

        val dlgTitleSelector: BySelector = By.text("My Dialog")
        device.wait(Until.hasObject(dlgTitleSelector), 1000)

        val dlgTitle: UiObject2 = device.findObject(
            dlgTitleSelector
        )
        val dlgDescription: UiObject2 = device.findObject(
            By.text("we are testing with UIAutomator")
        )
        val dlgButton: UiObject2 = device.findObject(
            By.text("OK")
        )
        dlgButton.click()
    }

}
