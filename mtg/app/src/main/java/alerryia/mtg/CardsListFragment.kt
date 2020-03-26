package alerryia.mtg

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.cards_list_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CardsListFragment : Fragment(), CardsAdapter.OnItemClickListener {

    var cardsAdapter: CardsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cards_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val applicationContext = activity?.applicationContext!!

        val mtgApi = (activity?.application as MtgApplication).mtgService
        val db = Room.databaseBuilder(applicationContext, CardDatabase::class.java, "mtg.db")
            .fallbackToDestructiveMigration()
            .build()
        val cardDao = db.getCardDao()

        fail_loading.visibility = View.GONE
        progress_bar.visibility = View.GONE

        cardsAdapter = CardsAdapter(applicationContext, emptyList(), this)
        recycler_view.adapter = cardsAdapter
        recycler_view.layoutManager = LinearLayoutManager(
            applicationContext,
            LinearLayoutManager.VERTICAL, false
        )

        load_button.setOnClickListener {

            recycler_view.visibility = View.INVISIBLE
            fail_loading.visibility = View.GONE
            progress_bar.visibility = View.VISIBLE

            GlobalScope.launch {

                var cards: List<Card>? = emptyList()

                try {
                    val result = mtgApi?.getCards()

                    cards = result?.cards
                    if (cards != null) {
                        cardDao.addCards(cards)
                    }

                } catch (e: Exception) {
                    Log.d("mtgApi", "Failed to load cards from the remote", e)

                    cards = cardDao.getAllCards()

                } finally {

                    if (cards == null || cards.isEmpty()) {
                        withContext(Dispatchers.Main) {
                            fail_loading.visibility = View.VISIBLE
                        }
                    } else {
                        cardsAdapter?.cards = cards
                        withContext(Dispatchers.Main) {
                            recycler_view.visibility = View.VISIBLE
                        }
                    }
                }

                withContext(Dispatchers.Main) {
                    progress_bar.visibility = View.GONE
                }
            }
        }
    }

    override fun onItemClick(card: Card) {
        (activity as? MtgActivity)?.onCardClick(card)
    }
}