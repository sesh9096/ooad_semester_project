package definote.ooad

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import definote.ooad.ui.theme.MyApplicationTheme

class DisplayEntryActivity : ComponentActivity() {
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
                    Column {
                        Row {
                            Text(
                                text = "${entry.name}(${entry.part})",
                                fontSize = 30.sp,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        Text(
                            text = entry.description
                        )
                        Button(onClick = {
                            Intent(applicationContext, EditEntryActivity::class.java).also {
                                it.putExtra("ENTRY_OBJECT", entry)
                                startActivity(it)
                            }
                        }) {
                            Text(text = "Edit")
                        }
                    }
                }
            }
        }
    }
}