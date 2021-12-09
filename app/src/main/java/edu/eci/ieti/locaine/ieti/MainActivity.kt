package edu.eci.ieti.locaine.ieti

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.valentinilk.shimmer.shimmer
import dagger.hilt.android.AndroidEntryPoint
import edu.eci.ieti.locaine.ieti.model.User
import edu.eci.ieti.locaine.ieti.ui.theme.IETITheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IETITheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    AddUser()
                }
            }
        }
    }
}

@Composable
fun AddUser(viewModel: UserViewModel = hiltViewModel()) {
    val users by viewModel.users.observeAsState(initial = arrayListOf())
    val isLoading by viewModel.isLoading.observeAsState(initial = false)
    CreateInterface(
        onAddClick = {
            viewModel.addUser()
        },
        users = users,
        isLoading = isLoading

    )
}

@Composable
fun CreateInterface(
    onAddClick :( () -> Unit)? = null,
    users: List<User>,
    isLoading: Boolean
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Simple Rest + Room") },
                actions = {
                    IconButton(onClick = {
                        onAddClick?.invoke()
                    }) {
                        Icon(Icons.Filled.Add, "Add")
                    }
                }
            )
        }
    ) {
        LazyColumn {
            var itemCount = users.size
            if( isLoading) itemCount++

            items(count = itemCount){ index ->
                var auxIndex = index
                if(isLoading) {
                    if (auxIndex == 0)
                        return@items LoadingCard()
                    auxIndex--
                }

            }
        }
    }
}

@Composable
fun LoadingCard() {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 1.dp,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
            .testTag("loadingCard")
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            ImageLoading()
            Spacer()
            Column {
                Spacer()
                Box(modifier = Modifier.shimmer()) {
                    Column {
                        Box(
                            modifier = Modifier
                                .height(15.dp)
                                .fillMaxWidth()
                                .background(Color.Gray)
                        )
                        Spacer()
                        Box(
                            modifier = Modifier
                                .height(15.dp)
                                .fillMaxWidth()
                                .background(Color.Gray)
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun ImageLoading(){
    Box(modifier = Modifier.shimmer()){
        Box( modifier =
            Modifier
                    .size((50.dp))
                    .background(Color.LightGray)
        )
    }
}


@Composable
fun Spacer(size: Int = 8) = Spacer(modifier = Modifier.size(size.dp))

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    IETITheme {
        CreateInterface(onAddClick = null, users= listOf(), isLoading = false)
    }
}