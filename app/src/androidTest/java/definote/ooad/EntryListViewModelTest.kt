package definote.ooad

import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class EntryListViewModelTest {
    @Test
    fun searchChanges() {
        // Context of the app under test.
        Log.d("View Model Test", "Creating view model")
        val appContext = ApplicationProvider.getApplicationContext() as Context
        val viewModel = EntryListViewModel(context = appContext)
        runBlocking {
            Log.d("View Model Test", "testing search text")
//            assertEquals("Initial search text should be blank","", viewModel.state.collectLatest { assertEquals() })
            val searchText = "asdfghjklqwertyuiop"
            Log.d("View Model Test", "changing search text")
            viewModel.search(searchText)
            assertEquals("Search text should be updated",searchText, viewModel.state.first().searchText)
            Log.d("View Model Test", "finished testing")
        }
    }
}