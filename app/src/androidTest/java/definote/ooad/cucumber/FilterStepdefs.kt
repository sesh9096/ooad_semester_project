package definote.ooad.cucumber;

import android.content.Context;
import android.util.Log

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry


import definote.ooad.AppDatabase;
import definote.ooad.Entry;
import definote.ooad.EntryDao;
import definote.ooad.EntryListViewModel;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.TestScope
import org.junit.Assert.*

class FilterStepdefs {
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val viewModel = EntryListViewModel(appContext);
    private val entryDao = AppDatabase.getInstance(appContext).entryDao();

    @OptIn(ExperimentalCoroutinesApi::class)
    @Given("^I use the Exact Match Strategy$")
    fun iUseTheExactMatchStrategy() {
        viewModel.setStrategy("exact");
        TestScope().backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect {}
        }
    }

    @When("^I search \"([^\"]*)\"$")
    fun iSearch(arg0:String) {
        Log.i("search changed",arg0)
        // Write code here that turns the phrase above into concrete actions
        viewModel.search(arg0)
    }
    @Then("^I should see the search Text \"([^\"]*)\"$")
    fun seeSearchText (arg0:String) {
        assertEquals(viewModel.state.value.searchText,arg0)
    }
    @And("^I should see a list containing an Entry with the name \"([^\"]*)\"$")
    fun seeAListContaining (name:String) {
        val entries = viewModel.state.value.entries
        assertEquals(entries[0].name, name)
    }

}
