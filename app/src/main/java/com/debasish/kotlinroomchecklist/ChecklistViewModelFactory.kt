package com.debasish.kotlinroomchecklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ChecklistViewModelFactory(private val repository: ChecklistRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChecklistViewModel::class.java)){
            return ChecklistViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}