package com.example.app.views.setor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.app.data.models.Setor

@Composable
fun AddEditSetorScreen(
    navController: NavController,
    setorViewModel: SetorViewModel,
    setor: Setor
) {
    val desc: MutableLiveData<String> = MutableLiveData( setor.setorDescricao )
    val det: MutableLiveData<String> = MutableLiveData( setor.setorDetalhes )
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                contentColor = Color.White,
                onClick = {
                    if(setor.setorId == -1) {
                        val newSetor = Setor(
                            setorId = 0,
                            desc.value.toString(),
                            det.value.toString()
                        )
                        setorViewModel.insert(newSetor)
                        navController.navigate("setorlist") {
                            popUpTo("setorlist") {
                                inclusive = true
                            }
                        }
                    }
                    else {
                        val newSetor = Setor(
                            setorId = setor.setorId,
                            desc.value.toString(),
                            det.value.toString()
                        )
                        setorViewModel.update(newSetor)
                        navController.navigate("setorlist") {
                            popUpTo("setorlist") {
                                inclusive = true
                            }
                        }
                    }
                }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Confirm")
            }
            if(setor.setorId != -1)
                FloatingActionButton(
                    contentColor = Color.White,
                    backgroundColor = Color.Red,
                    modifier = Modifier.padding(start = 60.dp),
                    onClick = {
                        setorViewModel.delete(setor)
                        navController.navigate("setorlist"){
                            popUpTo("setorlist"){
                                inclusive = true
                            }
                        }
                    }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                }
        }
    ) {
        AddEditSetorForm(
            descricao = desc,
            onDescricaoChange = { desc.value = it },
            detalhes = det,
            onDetalheChange = { det.value = it }
        )
    }
}

@Composable
fun AddEditSetorForm(
    descricao: MutableLiveData<String>,
    onDescricaoChange: (String) -> Unit,
    detalhes: MutableLiveData<String>,
    onDetalheChange: (String) -> Unit,
) {
    val desc = descricao.observeAsState()
    val det = detalhes.observeAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) { Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        text = "Cadastro de Setor")
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            label = {
                Text(text = "Descrição")
            },
            value = "${desc.value}",
            onValueChange = onDescricaoChange
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            label = {
                Text(text = "Detalhes")
            },
            value = "${det.value}",
            onValueChange = onDetalheChange
        )
    }
}
