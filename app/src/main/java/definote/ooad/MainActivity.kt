package definote.ooad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import definote.ooad.ui.theme.MyApplicationTheme
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    var searchText by remember { mutableStateOf("") }
                    var searchResult by remember { mutableStateOf(emptyList<String>()) }
                    Column {
                        SearchBar(
                            searchText = searchText,
                            onSearchTextChanged = { searchText = it }
                        )
                        Button(
                            onClick = {
                                addTextToDictionary(searchText)
                                searchResult = searchFile(searchText)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Add to Dictionary")
                        }
                        Greeting(name = "Android", searchText = searchText, searchResult = searchResult)
                        LazyColumn {

                        }
                    }
                }
            }
        }
    }
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

    private fun addTextToDictionary(text: String) {
        // Assuming "dictionary.txt" is your file
        val file = File(filesDir, "dictionary.txt")
        file.appendText("$text\n")
    }
}

@Composable
fun EntryDisplay(entry: Entry, modifier: Modifier = Modifier.fillMaxWidth()) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "${entry.getName()}.!",
            fontSize = 20.sp,
        )
        Text(
            text = "${entry.getDescription()}",
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
            modifier = Modifier.padding(horizontal = 3.dp).weight(10F),
            label = { Text("Search") }
        )
    }
}

@Composable
fun Greeting(name: String, searchText: String, searchResult: List<String>) {
    Column {
        Text(text = "Hello $name! Searching for: $searchText")
        searchResult.forEach {
            Text(text = it)
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
