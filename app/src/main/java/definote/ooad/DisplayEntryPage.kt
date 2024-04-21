package definote.ooad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import definote.ooad.ui.theme.MyApplicationTheme

class DisplayEntryPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    Column {
                        Text (
                            text = "Name"
                        )
                        Text (
                            text = "Description jfdkfjksldflkjdsjklfjsdfjjsdfklsdlkfsjldkfjlksdjfdsdjfkjsdfjsdjlkf"
                        )
                    }
                }
            }
        }
    }
}