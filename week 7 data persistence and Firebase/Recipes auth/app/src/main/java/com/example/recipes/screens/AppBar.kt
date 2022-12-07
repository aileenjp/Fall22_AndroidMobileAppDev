package com.example.recipes.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.recipes.R
import io.grpc.InternalChannelz.id

@Composable
fun AppBar(login: () -> Unit, logout: () -> Unit){
//fun AppBar(){
    var dropDownMenuExpanded by rememberSaveable {mutableStateOf(false)}

    TopAppBar(
        title = {Text(text = stringResource(id = R.string.app_name))},
        actions ={
            // options icon (vertical dots)
            IconButton(onClick = {dropDownMenuExpanded = true})
            {
                Icon(imageVector = Icons.Outlined.MoreVert, contentDescription = "Options")
            }
                DropdownMenu(
                    expanded = dropDownMenuExpanded,
                    onDismissRequest = { dropDownMenuExpanded = false },
                ) {
                    DropdownMenuItem(onClick = {
                        login()
                        dropDownMenuExpanded = false
                    }) {
                        Text(stringResource(id = R.string.login))
                    }
                    DropdownMenuItem(onClick = {
                        logout()
                        dropDownMenuExpanded = false
                    }) {
                        Text(stringResource(id = R.string.logout))
                    }
                }
        }
    )
}
