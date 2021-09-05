package com.jayaspiya.everestbooks.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jayaspiya.everestbooks.dao.BookDAO
import com.jayaspiya.everestbooks.entity.BookEntity

@Database(
    entities = [(BookEntity::class)],
    version = 2
)
abstract class EverestDB: RoomDatabase() {
    abstract fun getBookDAO(): BookDAO

    companion object{
        @Volatile
        private var instance: EverestDB? = null
        fun getInstance(context: Context): EverestDB{
            if(instance == null){
                synchronized(EverestDB::class){
                    instance = buildDatabase(context)
                }
            }
            return instance!!
        }

        private fun buildDatabase(context: Context): EverestDB {
            return Room.databaseBuilder(
                context.applicationContext,
                EverestDB::class.java,
                "EverestDB"
            ).build()
        }
    }
}