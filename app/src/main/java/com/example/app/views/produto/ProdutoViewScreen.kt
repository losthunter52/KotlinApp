package com.example.app.views.produto

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.app.data.models.Categoria
import com.example.app.data.models.Produto
import com.example.app.data.models.Setor

@Composable
fun ProdutoViewScreen(
    produto: Produto,
    categoria: Categoria,
    setor: Setor
) {
    Column {
        Text(text = "ID:   " + produto.produtoId.toString())
        Text(text = "Codigo:   " + produto.produtoCodigo)
        Text(text = "Descrição:   " + produto.produtoDescricao)
        Text(text = "Setor:   " + setor.setorDescricao)
        Text(text = "Categoria:   " + categoria.categoriaDescricao)
    }
}