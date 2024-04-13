package definote.ooad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                var search by remember {
                    mutableStateOf("")
                }
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {

                        SearchBar(
                            searchText = search,
                            onSearchTextChanged = { search = it }
                        )
                        LazyColumn {

                        }
                    }
                }
            }
        }
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
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = searchText,
            onValueChange = onSearchTextChanged,
            modifier = Modifier.padding(horizontal = 3.dp).weight(10F),
            label = { Text("Search for") }
        )
    }
}

@Composable
fun Greeting(name: String, searchText: String) {
    Text(text = "Hello $name! Searching for: $searchText")
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    MyApplicationTheme {
        var search by remember {
            mutableStateOf("")
        }
        var entries by remember {
            mutableStateOf(listOf<Entry>())
        }
        var searchText = remember { mutableStateOf("World") }
        Greeting(name = "Android", searchText = searchText.value)
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column (
                horizontalAlignment = Alignment.Start
            ){
                Row (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 2.dp)
                ) {
                    SearchBar(searchText = searchText.value) { searchText.value = it }
                    Button(onClick = { /*TODO*/ }, modifier = Modifier.width(60.dp)) {
                        Text("☰")
                    }
                }
                LazyColumn {
                    items(entries) { entry ->
                        EntryDisplay(entry)
                    }
                }
            }
        }
    }
}

