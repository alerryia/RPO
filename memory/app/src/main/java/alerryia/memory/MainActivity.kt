package alerryia.memory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageButton
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var playGround = emptyArray<ImageButton>()
    var playImages = emptyArray<Int>()
    var guessed = 0
    var lastIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initGround()
        reset()
    }

    private fun initGround() {
        playGround += findViewById<ImageButton>(R.id.cell1)
        playGround += findViewById<ImageButton>(R.id.cell2)
        playGround += findViewById<ImageButton>(R.id.cell3)
        playGround += findViewById<ImageButton>(R.id.cell4)
        playGround += findViewById<ImageButton>(R.id.cell5)
        playGround += findViewById<ImageButton>(R.id.cell6)
        playGround += findViewById<ImageButton>(R.id.cell7)
        playGround += findViewById<ImageButton>(R.id.cell8)
        playGround += findViewById<ImageButton>(R.id.cell9)
        playGround += findViewById<ImageButton>(R.id.cell10)
        playGround += findViewById<ImageButton>(R.id.cell11)
        playGround += findViewById<ImageButton>(R.id.cell12)

        for (button in playGround) {
            button.setOnClickListener{
                if (it != null)
                    onCellClick(it as ImageButton)
            }
        }

        playImages += R.drawable.one
        playImages += R.drawable.one
        playImages += R.drawable.two
        playImages += R.drawable.two
        playImages += R.drawable.three
        playImages += R.drawable.three
        playImages += R.drawable.four
        playImages += R.drawable.four
        playImages += R.drawable.five
        playImages += R.drawable.five
        playImages += R.drawable.six
        playImages += R.drawable.six
    }

    private fun shuffleImages() {
        for (i in playImages.size - 1 downTo 1 step 1) {
            var j = Random.nextInt(i + 1)
            var tmp = playImages[j]
            playImages[j] = playImages[i]
            playImages[i] = tmp
        }
    }

    private fun reset() {
        shuffleImages()

        for (button in playGround) {
            button.setImageResource(R.drawable.none)
            button.isEnabled = true
        }

        guessed = 0
        lastIndex = -1
    }

    private fun lockAll(unlock: Boolean) {
        for (button in playGround) {
            button.isEnabled = unlock
        }
    }

    fun onCellClick(button: ImageButton) {
        val currentIndex = playGround.indexOf(button)

        if (lastIndex == -1) {
            
            lastIndex = currentIndex
            button.setImageResource(playImages[currentIndex])

        } else if (currentIndex == lastIndex) {

            lastIndex = -1
            button.setImageResource(R.drawable.none)

        } else if (playImages[currentIndex] != playImages[lastIndex]) {

            button.setImageResource(playImages[currentIndex])
            lockAll(false)
            Handler().postDelayed({
                button.setImageResource(R.drawable.none)
                playGround[lastIndex].setImageResource(R.drawable.none)
                lastIndex = -1
                lockAll(true)
            }, 1000)

        } else {
            guessed++

            button.setImageResource(playImages[currentIndex])
            button.isEnabled = false
            playGround[lastIndex].isEnabled = false

            if (guessed == 9) reset()

            lastIndex = -1
        }
    }
}
