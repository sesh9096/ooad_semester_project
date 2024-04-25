package definote.ooad

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import definote.ooad.ui.theme.MyApplicationTheme

class DisplayEntryPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val entry = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("ENTRY_OBJECT", Entry::class.java)
        } else{
            intent.getSerializableExtra("ENTRY_OBJECT") as Entry
        }?:Entry(0,"","Empty Description", "n.")
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    Column {
                        Row{
                            Text (
                                text = entry.name
                            )
                            Text (
                                text = entry.part
                            )
                        }
                        Text (
                            text = entry.description
                        )
                    }
                }
            }
        }
    }
}