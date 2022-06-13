package com.example.app.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Setor(
    @PrimaryKey(autoGenerate = true) val setorId: Int,
    val setorDescricao: String,
    val setorDetalhes: String
)