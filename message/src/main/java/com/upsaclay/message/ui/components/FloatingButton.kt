import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FloatinButton() {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Action */ }) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        // Appliquer le paddingValues à l'élément enfant
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            item {
                //trouver comment récupérer la fonction pivé RecentAnnouncementSection()
            }
        }
    }
}
