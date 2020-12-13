package com.debasish.kotlinroomchecklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ChecklistViewModel(private val repository: ChecklistRepository) : ViewModel() {

    val checklist = repository.checklist

 //   val checklistString = repository.checklistString

    private val statusMessage = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>>
        get() = statusMessage

    fun insert(checklist: Checklist) :Job = viewModelScope.launch {
        val newRowId: Long = repository.insert(checklist)
        if(newRowId > -1) {
            statusMessage.value = Event("Subscriber inserted Sucessfully")
        } else {
            statusMessage.value = Event("Error Occured")
        }
    }


}