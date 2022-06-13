package com.example.app.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Categoria (
    @PrimaryKey(autoGenerate = true) val categoriaId: Int,
    val categoriaDescricao: String,
    val categoriaObservacao: String
)