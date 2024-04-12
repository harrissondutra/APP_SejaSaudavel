package com.estudo.app_dietacheck.models

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec

@Database(
    entities = [Calc::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration (
            from = 1,
            to = 2,
            spec = AppDatabase.MyAutoMigration::class
        )
    ]
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun calcDao(): CalcDao

    @RenameColumn(tableName = "Calc", fromColumnName = "title", toColumnName = "classification")
    class MyAutoMigration : AutoMigrationSpec

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "fitness_tracker"
                    ).build()
                }
                INSTANCE as AppDatabase
            } else {
                INSTANCE as AppDatabase

            }
        }
    }
}
