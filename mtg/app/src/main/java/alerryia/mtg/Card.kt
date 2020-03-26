package alerryia.mtg

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "cards")
data class Card (

    /*
    A unique id for the card.
     */
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    /*
    The card name. For split, double-faced and flip cards,
    just the name of one side of the card.
     */
    @SerializedName("name")
    val name: String,
    /*
    Converted mana cost.
     */
    @SerializedName("cmc")
    val cmc: Int,
    /*
    The card type.
     */
    @SerializedName("type")
    val type: String,
    /*
    The rarity of the card.
     */
    @SerializedName("rarity")
    val rarity: String,
    /*
    The set the card belongs to.
     */
    @SerializedName("setName")
    val setName: String,
    /*
    The oracle text of the card.
     */
    @SerializedName("text")
    val text: String?,
    /*
    The card number.
    This is printed at the bottom-center of the card in small text.
     */
    @SerializedName("number")
    val number: String,
    /*
    The card image url.
     */
    @SerializedName("imageUrl")
    val imageUrl: String?
)
