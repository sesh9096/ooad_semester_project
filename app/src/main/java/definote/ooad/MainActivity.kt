package definote.ooad

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import definote.ooad.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    val viewModel by viewModels<EntryListViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return EntryListViewModel(applicationContext) as T
                }
            }
        }
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        val context = this
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxHeight(), color = MaterialTheme.colorScheme.background) {
                    val state by viewModel.state.collectAsState()
                    var expanded by remember {mutableStateOf(false) } // we don't care about this surviving config changes
                    Column {
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextField(
                                value = state.searchText,
                                onValueChange = {searchText:String -> viewModel.search(searchText)},
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text(text = "Search") },
                            )
                            Box(
                                 modifier = Modifier
                                     .fillMaxWidth()
                                     .wrapContentSize(Alignment.TopEnd)
                            ) {
                                IconButton(
                                    onClick = { expanded = !expanded },
                                    modifier = Modifier.align(Alignment.TopEnd)
                                ) {
                                    Icon(
                                            imageVector = Icons.Default.Menu,
                                            contentDescription = "More",
                                            modifier = Modifier.align(Alignment.Center)
                                        )
                                }

                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false },
                                    ) {
                                    state.strategies.forEach { strategy: String ->
                                        if (strategy == state.selectedStrategy) {
                                            DropdownMenuItem(
                                                text = { Text(
                                                        text = strategy,
                                                        fontStyle = FontStyle.Italic,
                                                        fontWeight = FontWeight.Bold,
                                                    ) },
                                                onClick = {
                                                   expanded = false
                                                   Toast.makeText(context, "Already using strategy ${strategy}", Toast.LENGTH_SHORT).show()
                                                })
                                        } else {
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        text = strategy,
                                                        fontStyle = FontStyle.Normal,
                                                        fontWeight = FontWeight.Light,
                                                    )
                                                },
                                                onClick = {
                                                    expanded = false
                                                    viewModel.setStrategy(strategy)
                                                    Toast.makeText(
                                                        context,
                                                        "Using strategy ${strategy}",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                })
                                        }
                                    }
                                }
                            }
                        }
                        LazyColumn() {
                            item{
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            // TODO: Make edit activity work and launch here
                                            Intent(
                                                applicationContext,
                                                EditEntryActivity::class.java
                                            ).also {
                                                it.putExtra(
                                                    "ENTRY_OBJECT",
                                                    Entry(
                                                        name = state.searchText,
                                                        description = "",
                                                        part = "n."
                                                    )
                                                )
                                                startActivity(it)
                                            }
                                        }
                                ) {
                                    Text(
                                        text = "Add New entry",
                                        fontSize = 20.sp,
                                        modifier = Modifier
                                            .padding(vertical = 10.dp, horizontal = 10.dp)
                                    )
                                }
                            }
                            items(state.entries) {entry->
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            Intent(
                                                applicationContext,
                                                DisplayEntryActivity::class.java
                                            ).also {
                                                it.putExtra("ENTRY_OBJECT", entry)
                                                startActivity(it)
                                            }
                                        }
                                ){
                                    Text(
                                        text = "${entry.name}(${entry.part})",
                                        fontSize = 20.sp,
                                        modifier = Modifier
                                            .padding(vertical = 10.dp, horizontal = 10.dp)
                                    )
                                    Text(
                                        text = entry.description,
                                        fontSize = 20.sp,
                                        fontStyle = FontStyle.Italic,
                                        color = Color.Gray,
                                        maxLines = 1,
                                        softWrap = false,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier
                                            .padding(vertical = 10.dp, horizontal = 10.dp)
                                            .weight(5F)
                                    )
                                    IconButton(
                                        onClick = {viewModel.deleteEntry(entry)},
                                    ) {
                                        Icon(imageVector = Icons.Default.Delete,
                                            contentDescription = "delete ${entry.name}",
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private fun navigateToDisplayEntryPage(entry: Entry) {
         Intent(applicationContext, DisplayEntryActivity::class.java).also{
            it.putExtra("ENTRY_OBJECT", entry)
            startActivity(it)
        }
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
        entryFactory.generateAndAddEntry("Test", text, "n")
    }
}

//@Composable
//fun EntryDisplay(entry: Entry, modifier: Modifier = Modifier.fillMaxWidth()) {
//    Row(
//        modifier.clickable{
//            Intent(applicationContext, DisplayEntryPage::class.java).also{
//                it.putExtra("ENTRY_OBJECT", entry)
//                startActivity(it)
//            }
//        }
//    ){
//        /*Text(
//            text = entry.name,
//            fontSize = 20.sp,
//        )
//        Text(
//            text = entry.description,
//            modifier = modifier,
//            fontSize = 10.sp,
//        )
//        */
//        TextButton(
//            onClick = { onClick() }
//        ) {
//            Text(entry.name)
//        }
//    }
//}
//@Composable
//fun SearchBar(searchText: String, onSearchTextChanged: (String) -> Unit) {
//    Row(
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        TextField(
//            value = searchText,
//            onValueChange = onSearchTextChanged,
//            modifier = Modifier
//                .padding(horizontal = 3.dp)
//                .weight(10F),
//            label = { Text("Search") }
//        )
//    }
//}

/*
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
MyApplicationTheme {
Column {
val searchText = remember { mutableStateOf("World") }
val searchResult = listOf("Result 1", "Result 2", "Result 3") // Mock search result
var entries by remember {
mutableStateOf(listOf<Entry>())
}
var search = remember { mutableStateOf("World") }
SearchBar(searchText = searchText.value) { searchText.value = it }
Greeting(name = "Android", searchText = search.value, searchResult = searchResult)
// A surface container using the 'background' color from the theme
Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
Column(
horizontalAlignment = Alignment.Start
) {
Row(
modifier = Modifier
.fillMaxSize()
.padding(horizontal = 2.dp)
) {
SearchBar(searchText = searchText.value) { searchText.value = it }
Button(onClick = { / *TODO* / }, modifier = Modifier.width(60.dp)) {
Text("☰")
}
}
LazyColumn {
items(entries) { entry ->
EntryDisplay(entry)
}
}
}
Greeting(
name = "Android",
searchText = searchText.value,
searchResult = searchResult
)
}
}
}
}

*/
