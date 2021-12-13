package edu.eci.ieti.locaine.ieti

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import edu.eci.ieti.locaine.ieti.ui.theme.IETITheme
import edu.eci.ieti.locaine.ieti.viewModel.LoginViewModel

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private lateinit var emailValue: MutableState<String>
    private lateinit var passwordValue: MutableState<String>
    private lateinit var passwordVisibility: MutableState<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            IETITheme {
                Surface(color = MaterialTheme.colors.background) {
                    LoginUser()
                }
            }
        }
    }

    @Composable
    fun LoginUser(viewModel: LoginViewModel = hiltViewModel()) {
        val context = LocalContext.current
        LoginApplication(onLoginClick = {
            Log.d("LoginUser Activity","Si le entré")
            viewModel.login(emailValue.value, passwordValue.value).observe( this, Observer { returnedtokenDto ->
                if(returnedtokenDto.token != ""){
                    Log.d("LoginUser Activity","todo el token ${returnedtokenDto}")
                    val intent = Intent(context, MainActivity::class.java)
                    intent.putExtra("Token", "YourExtraValue")
                    context.startActivity(intent)
                }
            })
        }
        )



    }

    @Composable
    fun LoginApplication(
        onLoginClick :( () -> Unit) ? = null
    ){


        emailValue =  remember { mutableStateOf("")}
        passwordValue = remember { mutableStateOf("")}
        passwordVisibility = remember { mutableStateOf(false)}
       Box( modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter){

            Box( modifier= Modifier
                .fillMaxSize(),
                contentAlignment = Alignment.TopCenter){
                Spacer()
                Image(painterResource(R.drawable.firebox_logo),"content description")
            }


            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.60f)
                .verticalScroll(rememberScrollState())
                .clip(RoundedCornerShape(topStartPercent = 30, topEndPercent = 30))
                .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Spacer()

                Text(text= "Sign In",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                        ),
                    fontSize = 30.sp
                )
                Spacer()
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    OutlinedTextField(value = emailValue.value, onValueChange = {
                        emailValue.value =  it
                    },
                        label = { Text(text = "Email Address")},
                        placeholder = { Text(text = "Email Address")},
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(8.8f) )

                    OutlinedTextField(value = passwordValue.value, onValueChange = {
                        passwordValue.value =  it
                    },
                        trailingIcon = {
                                       IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value  }) {
                                           Icon(painter = painterResource(id = R.drawable.password_eye),
                                               contentDescription = "password eye",
                                           tint = if(passwordVisibility.value) Color.Black else Color.Gray)
                                       }
                        },
                        label = { Text(text = "Password")},
                        placeholder = { Text(text = "Password")},
                        singleLine = true,
                        visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(8.8f) )
                }
                Spacer()
                Button( onClick = { onLoginClick?.invoke()},
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)){
                    Text(text= "Sign In",
                        fontSize = 28.sp
                    )
                }

                Spacer()

                Text(text= "Create An Account",
                    modifier = Modifier.clickable {  },
                    fontSize = 18.sp
                )
            }

        }

    }

    @Composable
    fun Spacer(size: Int = 28) = Spacer(modifier = Modifier.padding(size.dp))

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        IETITheme {
            LoginUser()
        }
    }
}
