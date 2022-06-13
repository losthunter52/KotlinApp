package com.example.app.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Categoria::class,
            parentColumns = arrayOf("categoriaId"),
            childColumns = arrayOf("produtoCategoria"),
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = Setor::class,
            parentColumns = arrayOf("setorId"),
            childColumns = arrayOf("produtoSetor"),
            onDelete = ForeignKey.SET_NULL
        ),
    ])
data class Produto(
    @PrimaryKey(autoGenerate = true) val produtoId: Int,
    val produtoCategoria: Int?,
    val produtoSetor: Int?,
    val produtoCodigo: String,
    val produtoDescricao: String
)