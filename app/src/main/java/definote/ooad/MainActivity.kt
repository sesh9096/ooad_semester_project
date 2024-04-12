package definote.ooad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import definote.ooad.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.background) {
                    var searchText by remember { mutableStateOf("") }
                    Column {
                        SearchBar(
                            searchText = searchText,
                            onSearchTextChanged = { searchText = it }
                        )
                        Greeting(name = "Android", searchText = searchText)
                    }
                }
            }
        }
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
fun Greeting(name: String, searchText: String) {
    Text(text = "Hello $name! Searching for: $searchText")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Column {
            val searchText = remember { mutableStateOf("World") }
            SearchBar(searchText = searchText.value) { searchText.value = it }
            Greeting(name = "Android", searchText = searchText.value)
        }
    }
}
