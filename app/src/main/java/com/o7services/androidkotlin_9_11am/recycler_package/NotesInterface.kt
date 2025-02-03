package com.o7services.androidkotlin_9_11am.recycler_package

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotesInterface {
    @Insert
    fun insertTodo(notesEntity: NotesEntity)

    @Query("SELECT * FROM NotesEntity")
    fun getList() : List<NotesEntity>

    @Update
    fun updateTodoEntity(notesEntity: NotesEntity)

    @Delete
    fun deleteTodoEntity(notesEntity: NotesEntity)


}