package com.example.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.app.data.dao.SetorDao
import com.example.app.data.dao.CategoriaDao
import com.example.app.data.dao.ProdutoDao
import com.example.app.data.models.Setor
import com.example.app.data.models.Categoria
import com.example.app.data.models.Produto

@Database(
    entities = [Setor::class, Categoria::class, Produto::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun setorDao(): SetorDao
    abstract fun categoriaDao(): CategoriaDao
    abstract fun produtoDao(): ProdutoDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}