package com.example.app.views.categoria

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.app.data.models.Categoria

@Composable
fun CategoriaViewScreen(
    categoria: Categoria
) {
    Column {
        Text(text = "ID:   " + categoria.categoriaId.toString())
        Text(text = "Categoria:   " + categoria.categoriaDescricao)
        Text(text = "Observação:   " + categoria.categoriaObservacao)
    }
}