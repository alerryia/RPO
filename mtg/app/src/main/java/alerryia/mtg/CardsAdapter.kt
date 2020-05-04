package alerryia.mtg

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.URL

class CardsAdapter(
    private val context: Context,
    var cards: List<Card>,
    private val onItemClickListener: CardsAdapter.OnItemClickListener
) : RecyclerView.Adapter<CardsAdapter.Item>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Item {
        val view = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false)
        return Item(view)
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onBindViewHolder(holder: Item, position: Int) {
        val card = cards[position]

        holder.name.text = card.name
        holder.number.text = card.number
        holder.setName.text = card.setName

        holder.itemView.setOnClickListener { onItemClickListener.onItemClick(card) }

        GlobalScope.launch {
            val stream = URL(card.imageUrl).openStream()
            val bitmap = BitmapFactory.decodeStream(stream)
            withContext(Dispatchers.Main) {
                holder.image.setImageBitmap(bitmap)
            }
            stream.close()
        }
        /*try {
            Glide.with(context)
                .load(card.imageUrl)
                .placeholder(R.drawable.download)
                .fallback(R.drawable.empty)
                .error(R.drawable.empty)
                .into(holder.image)
        } catch (e: Exception) {
            Log.d("Glide", "Failed to load image", e)
        }*/
    }

    interface OnItemClickListener {
        fun onItemClick(card: Card)
    }

    class Item(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.item_image_view)
        val name: TextView = itemView.findViewById(R.id.item_name_view)
        val setName: TextView = itemView.findViewById(R.id.item_set_view)
        val number: TextView = itemView.findViewById(R.id.item_number_view)
    }
}