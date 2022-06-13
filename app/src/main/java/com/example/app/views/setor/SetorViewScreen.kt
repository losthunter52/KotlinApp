package com.example.app.views.setor

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.app.data.models.Setor

@Composable
fun SetorViewScreen(
    setor: Setor
) {
    Column {
        Text(text = "ID:   " + setor.setorId.toString())
        Text(text = "Setor:   " + setor.setorDescricao)
        Text(text = "Detalhes:   " + setor.setorDetalhes)
    }
}