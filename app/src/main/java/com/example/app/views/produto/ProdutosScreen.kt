package com.example.app.views.produto

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app.data.models.Produto


@Composable
fun ProdutosScreen(
    navController: NavController,
    produtoViewModel: ProdutoViewModel,
) {
    Scaffold(
        floatingActionButton =  {
            FloatingActionButton(onClick = {
                navController.navigate("addeditproduto")
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Produto")
            }
        }
    ) {
        val allProdutos by produtoViewModel.allProdutos.observeAsState(listOf())
        Column {
            ProdutosList(
                produtos = allProdutos,
                navController = navController
            )
        }
    }
}

@Composable
fun ProdutosList(
    produtos: List<Produto>,
    navController: NavController
) {
    LazyColumn {
        items(produtos){ produto ->
            fun onEdit() {
                navController.navigate("addeditproduto?produtoId=${produto.produtoId}")
            }
            fun onView() {
                navController.navigate("viewproduto?produtoId=${produto.produtoId}")
            }
            ProdutoEntry(produto = produto, onEdit = { onEdit() }, onView = { onView()} )
        }
    }
}

@Composable
fun ProdutoEntry(
    produto: Produto,
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
                    text = "#${produto.produtoId}   -   ${produto.produtoDescricao}",
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