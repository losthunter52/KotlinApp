package com.example.app.views.produto

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.filled.Check
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import androidx.lifecycle.MutableLiveData
import com.example.app.data.models.Produto
import com.example.app.views.categoria.CategoriaViewModel
import com.example.app.views.setor.SetorViewModel

@Composable
fun AddEditProdutoScreen(
    navController: NavController,
    categoriaViewModel: CategoriaViewModel,
    setorViewModel: SetorViewModel,
    produtoViewModel: ProdutoViewModel,
    produto: Produto
) {
    val categ: MutableLiveData<Int?> = MutableLiveData()
    val set: MutableLiveData<Int?> = MutableLiveData()
    fun atualizar(){
        categoriaViewModel.allCategorias.value?.forEach{
            if (produto.produtoCategoria == it.categoriaId){
                categ.value = it.categoriaId
            }
        }
        setorViewModel.allSetors.value?.forEach{
            if (produto.produtoSetor == it.setorId){
                set.value = it.setorId
            }
        }
    }
    atualizar()
    val desc: MutableLiveData<String> = MutableLiveData( produto.produtoDescricao )
    val cod: MutableLiveData<String> = MutableLiveData( produto.produtoCodigo )
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                contentColor = Color.White,
                onClick = {
                    if(produto.produtoId == -1) {
                        val newProduto = Produto(
                            produtoId = 0,
                            categ.value,
                            set.value,
                            cod.value.toString(),
                            desc.value.toString()
                        )
                        produtoViewModel.insert(newProduto)
                        navController.navigate("produtolist") {
                            popUpTo("produtolist") {
                                inclusive = true
                            }
                        }
                    }
                    else {
                        val newProduto = Produto(
                            produtoId = produto.produtoId,
                            categ.value,
                            set.value,
                            cod.value.toString(),
                            desc.value.toString()
                        )
                        produtoViewModel.update(newProduto)
                        navController.navigate("produtolist") {
                            popUpTo("produtolist") {
                                inclusive = true
                            }
                        }
                    }
                }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Confirm")
            }
            if(produto.produtoId != -1)
                FloatingActionButton(
                    contentColor = Color.White,
                    backgroundColor = Color.Red,
                    modifier = Modifier.padding(start = 60.dp),
                    onClick = {
                        produtoViewModel.delete(produto)
                        navController.navigate("produtolist"){
                            popUpTo("produtolist"){
                                inclusive = true
                            }
                        }
                    }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                }
        }
    ) {
        AddEditProdutoForm(
            categoria = categ,
            categoriaViewModel = categoriaViewModel,
            onCategoriaChange = { categ.value = it },
            setor = set,
            setorViewModel = setorViewModel,
            onSetorChange = { set.value = it },
            codigo = cod,
            onCodigoChange = { cod.value = it },
            descricao = desc,
            onDescricaoChange = { desc.value = it }
        )
    }
}

@Composable
fun AddEditProdutoForm(
    categoria: MutableLiveData<Int?>,
    categoriaViewModel: CategoriaViewModel,
    onCategoriaChange: (Int?) -> Unit,
    setor: MutableLiveData<Int?>,
    setorViewModel: SetorViewModel,
    onSetorChange: (Int?) -> Unit,
    codigo: MutableLiveData<String>,
    onCodigoChange: (String) -> Unit,
    descricao: MutableLiveData<String>,
    onDescricaoChange: (String) -> Unit
) {
    val desc = descricao.observeAsState()
    val cod = codigo.observeAsState()
    val categorias = categoriaViewModel.allCategorias
    val setors = setorViewModel.allSetors
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) { Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        text = "Cadastro de Produto")
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            label = {
                Text(text = "Codigo")
            },
            value = "${cod.value}",
            onValueChange = onCodigoChange
        )
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
        var selectedCategoria by remember { mutableStateOf("Selecione Uma Categoria")}
        var expandedCategoria by remember { mutableStateOf(false)}
        var selectedSetor by remember { mutableStateOf("Selecione Um Setor")}
        var expandedSetor by remember { mutableStateOf(false)}
        fun atualizar(){
            categorias.value?.forEach{
                if (categoria.value == it.categoriaId){
                    selectedCategoria = "Categoria:  " + it.categoriaDescricao
                }
            }
            setors.value?.forEach{
                if (setor.value == it.setorId){
                    selectedSetor = "Setor:  " + it.setorDescricao
                }
            }
        }
        atualizar()
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            onClick = { expandedCategoria = true }
        ) {
            Text(text = selectedCategoria)
        }
        Box {
            DropdownMenu(
                expanded = expandedCategoria,
                onDismissRequest = { expandedCategoria = false }
            ) {
                categorias.value?.forEach {
                    DropdownMenuItem(onClick = {
                        selectedCategoria = "Categoria:  " + it.categoriaDescricao
                        categoria.value = it.categoriaId
                        onCategoriaChange(it.categoriaId)
                        expandedCategoria = false
                    }) {
                        Text(text = it.categoriaDescricao)
                    }
                }
            }
        }
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            onClick = { expandedSetor = true }
        ) {
            Text(text = selectedSetor)
        }
        Box {
            DropdownMenu(
                expanded = expandedSetor,
                onDismissRequest = { expandedSetor = false }
            ) {
                setors.value?.forEach {
                    DropdownMenuItem(onClick = {
                        selectedSetor = "Setor:   " + it.setorDescricao
                        setor.value = it.setorId
                        onSetorChange(it.setorId)
                        expandedSetor = false
                    }) {
                        Text(text = it.setorDescricao)
                    }
                }
            }
        }
    }
}