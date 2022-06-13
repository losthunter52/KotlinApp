package com.example.app.views.categoria

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
import com.example.app.data.models.Categoria


@Composable
fun CategoriasScreen(
    navController: NavController,
    categoriaViewModel: CategoriaViewModel,
) {
    Scaffold(
        floatingActionButton =  {
            FloatingActionButton(onClick = {
                navController.navigate("addeditcategoria")
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Categoria")
            }
        }
    ) {
        val allCategorias by categoriaViewModel.allCategorias.observeAsState(listOf())
        Column {
            CategoriasList(
                categorias = allCategorias,
                navController = navController
            )
        }
    }
}

@Composable
fun CategoriasList(
    categorias: List<Categoria>,
    navController: NavController
) {
    LazyColumn {
        items(categorias){ categoria ->
            fun onEdit() {
                navController.navigate("addeditcategoria?categoriaId=${categoria.categoriaId}")
            }
            fun onView() {
                navController.navigate("viewcategoria?categoriaId=${categoria.categoriaId}")
            }
            CategoriaEntry(categoria = categoria, onEdit = { onEdit() }, onView = { onView() })
        }
    }
}

@Composable
fun CategoriaEntry(
    categoria: Categoria,
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
                    text = "#${categoria.categoriaId}   -   ${categoria.categoriaDescricao}",
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

