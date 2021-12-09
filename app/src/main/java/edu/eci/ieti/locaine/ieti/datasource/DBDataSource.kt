package edu.eci.ieti.locaine.ieti.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.eci.ieti.locaine.ieti.model.User
import edu.eci.ieti.locaine.ieti.model.UserDao

@Database(entities = [User::class], version = 1)
abstract class DBDataSource: RoomDatabase() {

    abstract fun userDao(): UserDao

}