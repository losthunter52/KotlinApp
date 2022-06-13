package com.example.app.views.setor

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app.data.models.Setor


@Composable
fun SetorsScreen(
    navController: NavController,
    setorViewModel: SetorViewModel,
) {
    Scaffold(
        floatingActionButton =  {
            FloatingActionButton(onClick = {
                navController.navigate("addeditsetor")
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Setor")
            }
        }
    ) {
        val allSetors by setorViewModel.allSetors.observeAsState(listOf())
        Column {
            SetorsList(
                setors = allSetors,
                navController = navController
            )
        }
    }
}

@Composable
fun SetorsList(
    setors: List<Setor>,
    navController: NavController
) {
    LazyColumn {
        items(setors){ setor ->
            fun onEdit() {
                navController.navigate("addeditsetor?setorId=${setor.setorId}")
            }
            fun onView() {
                navController.navigate("viewsetor?setorId=${setor.setorId}")
            }
            SetorEntry(setor = setor, onEdit = { onEdit() }, onView = { onView() })
        }
    }
}

@Composable
fun SetorEntry(
    setor: Setor,
    onEdit: () -> Unit,
    onView: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(2.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f),
                    text = "#${setor.setorId}   -   ${setor.setorDescricao}",
                    style = MaterialTheme.typography.h6
                        .copy(fontWeight = FontWeight.Bold)
                )
                Icon(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(32.dp)
                        .clickable {
                            onView()
                        },
                    imageVector = Icons.Default.Search,
                    contentDescription = "View"
                )
                Icon(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(32.dp)
                        .clickable {
                            onEdit()
                        },
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit"
                )
            }
        }
    }
}