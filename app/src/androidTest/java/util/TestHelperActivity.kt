package util

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.youtubedl.BuildConfig
import dagger.android.AndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Created by cuongpm on 1/29/19.
 */

class TestHelperActivity : AppCompatActivity(), HasSupportFragmentInjector {

    lateinit var fragmentInjector: AndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        if (!BuildConfig.DEBUG) error("This activity should never be called outside test build")
        if (intent?.getBooleanExtra(EXTRA_USE_DEFAULT_TOOLBAR_KEY, false) == true) {
            // assign explicit theme to this activity which enables windows action bar to test
            // tool bar menus and search option.
//            setTheme(R.style.BBMTestTheme)
        }
        super.onCreate(savedInstanceState)
    }

    fun attachFragment(fragment: Fragment) {
        runBlockingOnUiThread {
            supportFragmentManager
                .beginTransaction()
                .add(android.R.id.content, fragment)
                .commitNow()
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    fun runBlockingOnUiThread(block: () -> Unit) {
        val latch = CountDownLatch(1)
        runOnUiThread {
            block()
            latch.countDown()
        }
        latch.await(200, TimeUnit.MILLISECONDS)
    }

    companion object {
        const val EXTRA_USE_DEFAULT_TOOLBAR_KEY = "use_default_toolbar"
    }
}