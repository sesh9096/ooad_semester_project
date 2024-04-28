package definote.ooad

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.TestScope

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
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun searchChanges() {
        // Context of the app under test.
        Log.d("View Model Test", "Creating view model")
        val appContext = ApplicationProvider.getApplicationContext() as Context
        val viewModel = EntryListViewModel(context = appContext)
        // cold flow must have observer
        TestScope().backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect {}
        }
        runBlocking {
            Log.d("View Model Test", "testing search text")
            assertEquals("Initial search text should be blank","", viewModel.state.value.searchText)
            val searchText = "asdfghjklqwertyuiop"
            viewModel.search(searchText)
            // Hopefully avoid concurrency issues where search text has not updated yet
            delay(100)
            assertEquals("Search text should be updated",searchText, viewModel.state.value.searchText)
            Log.d("View Model Test", "finished testing")
        }
    }
    @Test
    fun strategyChanges() {
        // Context of the app under test.
        Log.d("View Model Test", "Creating view model")
        val appContext = ApplicationProvider.getApplicationContext() as Context
        val viewModel = EntryListViewModel(context = appContext)
        // cold flow must have observer
        TestScope().backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect {}
        }
        runBlocking {
            val strategies = viewModel.state.value.strategies
            assertEquals("Initial strategy should be the first one",strategies[0],viewModel.state.value.selectedStrategy)
            viewModel.setStrategy(strategies[1])
            // Hopefully avoid concurrency issues where search text has not updated yet
            delay(100)
            assertEquals("Strategy should be updated",strategies[1], viewModel.state.value.selectedStrategy)
        }
    }
}