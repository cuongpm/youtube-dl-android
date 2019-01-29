package util.rule

import android.content.Intent
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import dagger.android.AndroidInjector
import util.TestHelperActivity

/**
 * Created by cuongpm on 1/29/19.
 */

class InjectedFragmentTestRule<T : Fragment>(
    private val fragmentInjector: (T) -> Unit,
    private val useDefaultToolbar: Boolean
) : IntentsTestRule<TestHelperActivity>(TestHelperActivity::class.java, false, false) {

    constructor(fragmentInjector: (T) -> Unit) : this(fragmentInjector, false)

    lateinit var fragment: T

    fun launchFragment(fragment: T) {
        super.launchActivity(Intent().also {
            it.putExtra(TestHelperActivity.EXTRA_USE_DEFAULT_TOOLBAR_KEY, useDefaultToolbar)
        })
        attachFragment(fragment)
    }

    override fun launchActivity(startIntent: Intent?): TestHelperActivity {
        error("You should use launchFragment() instead")
    }

    override fun afterActivityLaunched() {
        super.afterActivityLaunched()

        activity.fragmentInjector = AndroidInjector {
            @Suppress("UNCHECKED_CAST")
            fragmentInjector(it as T)
        }
    }

    fun launchFragment(fragment: () -> T) {
        super.launchActivity(Intent().also {
            it.putExtra(TestHelperActivity.EXTRA_USE_DEFAULT_TOOLBAR_KEY, useDefaultToolbar)
        })
        activity.runBlockingOnUiThread {
            attachFragment(fragment.invoke())
        }
    }

    fun launchDialogFragment(fragment: T) {
        super.launchActivity(Intent().also {
            it.putExtra(TestHelperActivity.EXTRA_USE_DEFAULT_TOOLBAR_KEY, useDefaultToolbar)
        })
        activity.runBlockingOnUiThread {
            (fragment as DialogFragment).show(activity.supportFragmentManager, "test-tag")
        }
    }

    private fun attachFragment(fragment: T) {
        this.fragment = fragment
        activity.attachFragment(fragment)
    }

}
