package definote.ooad

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import definote.ooad.ui.theme.MyApplicationTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val entryDao = AppDatabase.getInstance(applicationContext).entryDao()
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    var searchStrategy by remember {
                        mutableStateOf(SimpleFilterStrategy(applicationContext))
                    }
                    var searchText by remember { mutableStateOf("") }
                    var searchResult by remember { mutableStateOf(emptyList<Entry>()) }
                    Column {
                        SearchBar(
                            searchText = searchText,
                            onSearchTextChanged = { searchText = it }
                        )
                        Button(
                            onClick = {
                                addTextToDB(searchText)
                                //searchResult = searchDB(searchText)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Add to Dictionary")
                        }
                        Button(
                            onClick = {
                                //navigateToDisplayEntryPage()
                                searchResult = searchDB(searchText)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Search")
                        }
                        Greeting(name = "User", searchText = searchText, searchResult = searchResult)
                        LazyColumn {
                        }
                    }
                }
            }
        }
    }
    private fun navigateToDisplayEntryPage() {
        val intent = Intent(this, DisplayEntryPage::class.java)
        startActivity(intent)
    }
    private fun searchDB(text: String): List<Entry>{
        var result = emptyList<Entry>()
        GlobalScope.launch(Dispatchers.IO) {
            val simpleStrategy = SimpleFilterStrategy(applicationContext)
            result =  simpleStrategy.getEntries(text)
        }
        println("Searching")
        return result
    }
    /*
    private fun searchFile(query: String): List<String> {
        // Assuming "dictionary.txt" is your file
        val file = File(filesDir, "dictionary.txt")
        if (!file.exists()) {
            return emptyList()
        }

        val searchResult = mutableListOf<String>()
        file.forEachLine { line ->
            if (line.contains(query, ignoreCase = true)) {
                searchResult.add(line)
            }
        }
        return searchResult
    }
    */

    private fun addTextToDB(text: String) {
        val entryDao = AppDatabase.getInstance(applicationContext).entryDao()
        val entryFactory = EntryFactory(entryDao)
        println("Insert")
        entryFactory.generateAndAddEntry("Test", text)
    }
}

@Composable
fun EntryDisplay(entry: Entry, modifier: Modifier = Modifier.fillMaxWidth()) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = entry.name,
            fontSize = 20.sp,
        )
        Text(
            text = entry.description,
            modifier = modifier,
            fontSize = 10.sp,
        )
    }
}
@Composable
fun SearchBar(searchText: String, onSearchTextChanged: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = searchText,
            onValueChange = onSearchTextChanged,
            modifier = Modifier
                .padding(horizontal = 3.dp)
                .weight(10F),
            label = { Text("Search") }
        )
    }
}

@Composable
fun Greeting(name: String, searchText: String, searchResult: List<Entry>) {
    Column {
        Text(text = "Hello $name! Searching for: $searchText")
        searchResult.forEach {
            Text(text = it.name)
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    MyApplicationTheme {
//        Column {
//            val searchText = remember { mutableStateOf("World") }
//            val searchResult = listOf("Result 1", "Result 2", "Result 3") // Mock search result
//            var entries by remember {
//                mutableStateOf(listOf<Entry>())
//            }
//            var search = remember { mutableStateOf("World") }
//            SearchBar(searchText = searchText.value) { searchText.value = it }
//            Greeting(name = "Android", searchText = search.value, searchResult = searchResult)
//            // A surface container using the 'background' color from the theme
//            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//                Column(
//                    horizontalAlignment = Alignment.Start
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(horizontal = 2.dp)
//                    ) {
//                        SearchBar(searchText = searchText.value) { searchText.value = it }
//                        Button(onClick = { /*TODO*/ }, modifier = Modifier.width(60.dp)) {
//                            Text("☰")
//                        }
//                    }
//                    LazyColumn {
//                        items(entries) { entry ->
//                            EntryDisplay(entry)
//                        }
//                    }
//                }
//                Greeting(
//                    name = "Android",
//                    searchText = searchText.value,
//                    searchResult = searchResult
//                )
//            }
//        }
//    }
//}
//
