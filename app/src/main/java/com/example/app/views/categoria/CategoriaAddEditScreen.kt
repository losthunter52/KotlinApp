package com.example.app.views.categoria

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
import com.example.app.data.models.Categoria

@Composable
fun AddEditCategoriaScreen(
    navController: NavController,
    categoriaViewModel: CategoriaViewModel,
    categoria: Categoria
) {
    val desc: MutableLiveData<String> = MutableLiveData( categoria.categoriaDescricao )
    val obs: MutableLiveData<String> = MutableLiveData( categoria.categoriaObservacao )
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                contentColor = Color.White,
                onClick = {
                    if(categoria.categoriaId == -1) {
                        val newCategoria = Categoria(
                            categoriaId = 0,
                            desc.value.toString(),
                            obs.value.toString()
                        )
                        categoriaViewModel.insert(newCategoria)
                        navController.navigate("categorialist") {
                            popUpTo("categorialist") {
                                inclusive = true
                            }
                        }
                    }
                    else {
                        val newCategoria = Categoria(
                            categoriaId = categoria.categoriaId,
                            desc.value.toString(),
                            obs.value.toString()
                        )
                        categoriaViewModel.update(newCategoria)
                        navController.navigate("categorialist") {
                            popUpTo("categorialist") {
                                inclusive = true
                            }
                        }
                    }
                }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Confirm")
            }
            if(categoria.categoriaId != -1)
                FloatingActionButton(
                    contentColor = Color.White,
                    backgroundColor = Color.Red,
                    modifier = Modifier.padding(start = 60.dp),
                    onClick = {
                        categoriaViewModel.delete(categoria)
                        navController.navigate("categorialist"){
                            popUpTo("categorialist"){
                                inclusive = true
                            }
                        }
                    }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                }
        }
    ) {
        AddEditCategoriaForm(
            descricao = desc,
            onDescricaoChange = { desc.value = it },
            observacao = obs,
            onObservacaoChange = { obs.value = it }
        )
    }
}

@Composable
fun AddEditCategoriaForm(
    descricao: MutableLiveData<String>,
    onDescricaoChange: (String) -> Unit,
    observacao: MutableLiveData<String>,
    onObservacaoChange: (String) -> Unit
) {
    val desc = descricao.observeAsState()
    val obs = observacao.observeAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) { Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        text = "Cadastro de Categoria")
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
                Text(text = "Observação")
            },
            value = "${obs.value}",
            onValueChange = onObservacaoChange
        )
    }
}