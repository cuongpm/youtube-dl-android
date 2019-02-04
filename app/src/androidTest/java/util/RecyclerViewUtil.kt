package util

import android.support.test.espresso.matcher.BoundedMatcher
import android.support.v7.widget.RecyclerView
import android.view.View
import org.hamcrest.Description

/**
 * Created by cuongpm on 2/4/19.
 */

object RecyclerViewUtil {

    fun recyclerViewSizeIs(size: Int) = object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        var realSize: Int = -1

        override fun describeTo(description: Description?) {
            description?.appendText("RecyclerView size should be $size but it's $realSize")
        }

        override fun matchesSafely(item: RecyclerView?): Boolean {
            realSize = item?.adapter?.itemCount ?: -2
            return realSize == size
        }
    }
}