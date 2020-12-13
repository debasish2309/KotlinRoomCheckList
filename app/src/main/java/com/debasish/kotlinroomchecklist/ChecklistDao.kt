package com.debasish.kotlinroomchecklist

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ChecklistDao{

    @Insert
    suspend fun insertNote(checklist: Checklist) : Long

    @Update
    suspend fun updateNote(checklist: Checklist): Int

    @Query("UPDATE table_checklist SET note = :newNote WHERE id = :id")
    suspend fun updateNewNote(newNote : String , id: Int )

    @Query("UPDATE table_checklist SET completed = :completed WHERE id = :id")
    suspend fun updateStatus(completed : Boolean , id: Int)

    @Delete
    suspend fun deleteNote(checklist: Checklist): Int

    @Query("DELETE FROM table_checklist WHERE id = :id")
    suspend fun deleteSingleNote(id: Int)

    @Query("DELETE FROM table_checklist")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM table_checklist ORDER BY completed  ASC ,noteCreated DESC ")
    fun getAllNotes():LiveData<List<Checklist>>

    @Query("SELECT note FROM table_checklist")
    suspend fun getOnlyNotes(): List<String>
}