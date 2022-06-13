package com.example.app.views.categoria

import androidx.lifecycle.*
import com.example.app.data.dao.CategoriaDao
import com.example.app.data.models.Categoria
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class CategoriaViewModel(private val dao: CategoriaDao): ViewModel() {

    var allCategorias: LiveData<List<Categoria>> = dao.getCategorias().asLiveData()

    fun insert(categoria: Categoria){
        viewModelScope.launch {
            dao.insert(categoria)
        }
    }

    fun update(categoria: Categoria){
        viewModelScope.launch {
            dao.update(categoria)
        }
    }

    fun delete(categoria: Categoria){
        viewModelScope.launch {
            dao.delete(categoria)
        }
    }
}

class CategoriaViewModelFactory(private val dao: CategoriaDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CategoriaViewModel::class.java))
            return CategoriaViewModel(dao) as T
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}