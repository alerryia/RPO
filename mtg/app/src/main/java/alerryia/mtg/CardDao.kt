package alerryia.mtg

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCards(cards: List<Card>)

    @Query("select * from cards")
    fun getAllCards(): List<Card>
}