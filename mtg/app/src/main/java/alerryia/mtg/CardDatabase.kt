package alerryia.mtg

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Card::class], version = 1)
abstract class CardDatabase : RoomDatabase() {
    abstract fun getCardDao(): CardDao
}