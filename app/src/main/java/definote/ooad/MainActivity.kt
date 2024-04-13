package definote.ooad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
fun SearchBar(searchText: String, onSearchTextChanged: (String) -> Unit) {
    TextField(
        value = searchText,
        onValueChange = onSearchTextChanged,
        label = { Text("Search") }
    )
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Column {
            val searchText = remember { mutableStateOf("World") }
            val searchResult = listOf("Result 1", "Result 2", "Result 3") // Mock search result
            SearchBar(searchText = searchText.value) { searchText.value = it }
            Greeting(name = "Android", searchText = searchText.value, searchResult = searchResult)
        }
    }
}
