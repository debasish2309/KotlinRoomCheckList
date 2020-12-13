package com.debasish.kotlinroomchecklist

class ChecklistRepository(private val dao: ChecklistDao) {

    val checklist = dao.getAllNotes()

 //   val checklistString = dao.getOnlyNotes()

    suspend fun insert(checklist: Checklist) :Long {
        return dao.insertNote(checklist)
    }

    suspend fun update(checklist: Checklist): Int {
        return dao.updateNote(checklist)
    }

    suspend fun delete(checklist: Checklist): Int {
        return dao.deleteNote(checklist)
    }

    suspend fun deleteAll() :Int {
        return dao.deleteAll()
    }
}