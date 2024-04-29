package definote.ooad

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import definote.ooad.ui.theme.MyApplicationTheme

class EditEntryActivity : ComponentActivity() {
    fun addEntryToDB(name: String, description: String, part: String, entry: Entry) {
        val entryDao = AppDatabase.getInstance(applicationContext).entryDao()
        val entryFactory = EntryFactory(entryDao)
        println("Insert")
        entryFactory.generateAndAddEntry(name, part, description, entry)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        val entry = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("ENTRY_OBJECT", Entry::class.java)
        } else{
            intent.getSerializableExtra("ENTRY_OBJECT") as Entry
        }?:Entry(0,"","Empty Description", "n.")
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxHeight(), color = MaterialTheme.colorScheme.background) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // State variables to hold the text in each search bar
                        var nameSearchText by remember { mutableStateOf(entry.name)}
                        var partOfSpeechSearchText by remember { mutableStateOf(entry.part) }
                        var descriptionSearchText by remember { mutableStateOf(entry.description) }

                        // Search bar for name
                        TextField(
                            value = nameSearchText,
                            onValueChange = { nameSearchText = it },
                            label = { Text("Enter name") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Search bar for part of speech
                        TextField(
                            value = partOfSpeechSearchText,
                            onValueChange = { partOfSpeechSearchText = it },
                            label = { Text("Enter part of speech") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Search bar for description
                        TextField(
                            value = descriptionSearchText,
                            onValueChange = { descriptionSearchText = it },
                            label = { Text("Enter description") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Button(onClick = {
                            addEntryToDB(nameSearchText, partOfSpeechSearchText, descriptionSearchText, entry)
                            finish()
                        }) {
                            Text(text = "Save")
                        }
                    }
                }
            }
        }
    }
}
