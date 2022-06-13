package com.example.app.views.produto

import androidx.lifecycle.*
import com.example.app.data.dao.ProdutoDao
import com.example.app.data.models.Produto
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ProdutoViewModel(private val dao: ProdutoDao): ViewModel() {

    val allProdutos: LiveData<List<Produto>> = dao.getProdutos().asLiveData()

    fun insert(produto: Produto){
        viewModelScope.launch {
            dao.insert(produto)
        }
    }

    fun update(produto: Produto){
        viewModelScope.launch {
            dao.update(produto)
        }
    }

    fun delete(produto: Produto){
        viewModelScope.launch {
            dao.delete(produto)
        }
    }
}

class ProdutoViewModelFactory(private val dao: ProdutoDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProdutoViewModel::class.java))
            return ProdutoViewModel(dao) as T
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}