package alerryia.mtg

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MtgActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CardsListFragment())
            .commit()
    }

    fun onCardClick(card: Card) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, CardFragment(card), null)
            .addToBackStack("CardFragment")
            .commit()
    }
}