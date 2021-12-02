package aut.arch.local_data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import aut.arch.local_data.dao.ArchDao
import aut.arch.local_data.models.*
import aut.arch.local_data.models.relations.UserTeamCrossRef

@Database(
    entities = [
        Setup::class,
        Shot::class,
        Team::class,
        Training::class,
        User::class,
        UserTeamCrossRef::class
    ],
    version = 1
)
abstract class ArchDatabase : RoomDatabase() {

    abstract fun archDao(): ArchDao

    companion object {
        @Volatile
        private var INSTANCE: ArchDatabase? = null

        fun getInstance(context: Context): ArchDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ArchDatabase::class.java,
                    "Arch_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}