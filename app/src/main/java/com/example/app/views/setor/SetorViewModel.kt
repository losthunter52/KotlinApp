package com.example.app.views.setor

import androidx.lifecycle.*
import com.example.app.data.dao.SetorDao
import com.example.app.data.models.Setor
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class SetorViewModel(private val dao: SetorDao): ViewModel() {

    val allSetors: LiveData<List<Setor>> = dao.getSetors().asLiveData()

    fun insert(setor: Setor){
        viewModelScope.launch {
            dao.insert(setor)
        }
    }

    fun update(setor: Setor){
        viewModelScope.launch {
            dao.update(setor)
        }
    }

    fun delete(setor: Setor){
        viewModelScope.launch {
            dao.delete(setor)
        }
    }
}

class SetorViewModelFactory(private val dao: SetorDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SetorViewModel::class.java))
            return SetorViewModel(dao) as T
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}