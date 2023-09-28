package com.example.excercise28sep2023

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.excercise28sep2023.ui.theme.Excercise28Sep2023Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Excercise28Sep2023Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App(context = applicationContext);
                }
            }
        }
    }
}

@Composable
fun App(context: Context) {
    var navController = rememberNavController();
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            mainScreen(context, navController);
        }
        composable("go/{name}/{mobile}") {
            var name = it.arguments?.getString("name");
            var mobile = it.arguments?.getString("mobile");
            TVScreen(name = name!!, mobile = mobile!!);
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mainScreen(context: Context, navController: NavController) {
    var name by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }
    var showTv by remember { mutableStateOf(false) }
    var showPopup by remember { mutableStateOf(false) }
    var popupTitle by remember { mutableStateOf("") }
    var popupDesc by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = name, onValueChange = {name = it},
            placeholder = { Text(text = "Name")},
            modifier = Modifier.padding(2.dp))
        TextField(value = mobile, onValueChange = {mobile = it},
            placeholder = { Text(text = "Mobile")},
            modifier = Modifier.padding(2.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = checked, onCheckedChange = { checked = it; showTv = false })
            Text(text = "Agree")
        }
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                if (checked && name.trim().length > 0 && mobile.trim().length > 0) {
                    Toast.makeText(context, "Name($name) Mobile($mobile)", Toast.LENGTH_SHORT).show()
                    showTv = false;
                } else {
                    popupTitle = if (name.trim().length > 0 && mobile.trim().length > 0)  "Agreement" else "Data"
                    popupDesc = if (name.trim().length > 0 && mobile.trim().length > 0) "Please Agree to Continue" else "Please fill in data"
                    showPopup = true;
                }
            }) {
                Text(text = "TO")
            }
            Button(onClick = {
                if (checked && name.trim().length > 0 && mobile.trim().length > 0) {
                    showTv = true
                } else {
                    popupTitle = if (name.trim().length > 0 && mobile.trim().length > 0)  "Agreement" else "Data"
                    popupDesc = if (name.trim().length > 0 && mobile.trim().length > 0) "Please Agree to Continue" else "Please fill in data"
                    showPopup = true;
                }
            }) {
                Text(text = "TV")
            }
        }
        Button(onClick = {
            if (checked && name.trim().length > 0 && mobile.trim().length > 0) {
                navController.navigate("go/$name/$mobile")
            } else {
                popupTitle = if (name.trim().length > 0 && mobile.trim().length > 0)  "Agreement" else "Data"
                popupDesc = if (name.trim().length > 0 && mobile.trim().length > 0) "Please Agree to Continue" else "Please fill in data"
                showPopup = true;
            }
        }) {
            Text(text = "GO")
        }
        if (showTv) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "TV Output:")
                Text(text = "Name(${name+""}) Mobile(${mobile+""})")
            }
        }
        if (showPopup) {
            AlertDialog(
                onDismissRequest = { showPopup = false },
                title = { Text(text = popupTitle) },
                text = { Text(text = popupDesc) },
                confirmButton = {
                    Button(onClick = {showPopup = false}) {
                        Text(text = "OK")
                    }
                }
            )
        }
    }
}


@Composable
fun TVScreen(name: String = "name", mobile: String = "mobile") {
    Column {
        Text(text = "Name : " + name)
        Text(text = "Mobile : " + mobile)
    }
}
