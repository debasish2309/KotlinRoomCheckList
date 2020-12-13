package com.debasish.kotlinroomchecklist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Checklist::class], version = 1)
abstract class CheckListDatabase : RoomDatabase() {

    abstract val checklistDao : ChecklistDao

    companion object{
        @Volatile
        private var INSTANCE : CheckListDatabase? = null
        fun getInstance(context: Context):CheckListDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null) {
                    instance =Room.databaseBuilder(
                        context.applicationContext,
                        CheckListDatabase::class.java,
                        "checklist_data_database"
                    ).build()
                }
                return instance
            }
        }
    }
}