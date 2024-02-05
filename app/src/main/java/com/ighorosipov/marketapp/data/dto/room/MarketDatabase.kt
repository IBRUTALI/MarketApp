package com.ighorosipov.marketapp.data.dto.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ighorosipov.marketapp.data.model.db.FavoriteEntity
import com.ighorosipov.marketapp.data.model.db.UserEntity

@Database(
    entities = [UserEntity::class, FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MarketDatabase : RoomDatabase() {

    abstract val marketDao: MarketDao

    companion object {

        fun getDB(context: Context): MarketDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                MarketDatabase::class.java,
                DATABASE_NAME
            ).build()
        }

        private const val DATABASE_NAME = "market_db"

    }

}