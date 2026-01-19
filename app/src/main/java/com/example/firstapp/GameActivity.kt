package com.example.firstapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog // library to show dialog when lose
import androidx.appcompat.app.AppCompatActivity

enum class Suit {  // نوع الورق
    HEARTS, DIAMONDS, CLUBS, SPADES
}

data class Card( //playing card
    val value: Int,  // the value of the card, from 1 to 13
    val suit: Suit  // نوع الورق
)

class GameActivity : AppCompatActivity() {

    private lateinit var tvCurrentCard: TextView  //  defult value متفير لا يملك قيمة عند التصريح
    private lateinit var tvScore: TextView
    private lateinit var btnHigher: Button
    private lateinit var btnLower: Button
    private lateinit var imgCorrect: ImageView

    private var deck: MutableList<Card> = mutableListOf()  //	متغير له قيمة من البداية
    private var currentIndex = 0
    private var currentCard: Card? = null
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        tvCurrentCard = findViewById(R.id.tvCurrentCard)
        tvScore = findViewById(R.id.tvScore)
        btnHigher = findViewById(R.id.btnHigher)
        btnLower = findViewById(R.id.btnLower)
        imgCorrect = findViewById(R.id.imgCorrect)

        imgCorrect.visibility = View.GONE // لاخفاء صورؤ صح




        startNewGame()

        btnHigher.setOnClickListener {
            handleGuess(isHigher = true)
        }

        btnLower.setOnClickListener {
            handleGuess(isHigher = false)
        }
    }

    private fun startNewGame() {
        deck = mutableListOf()   // تفريغ القائمة السابقة (لو في لعبة قديمة)
        val suits = Suit.values()
        for (suit in suits) {
            for (value in 1..13) {
                deck.add(Card(value, suit))
            }
        }

        deck.shuffle() // تخلط الأوراق

        score = 0
        currentIndex = 0
        currentCard = deck[currentIndex]

        imgCorrect.visibility = View.GONE // لاخفاء صورؤ صح

        updateScoreText()
        updateCardText()
    }

    private fun handleGuess(isHigher: Boolean) {   // master function (head of logic hhh)
        val previousCard = currentCard ?: return

        if (currentIndex >= deck.size - 1) {
            Toast.makeText(this, "Inga fler kort. Spelet startar om.", Toast.LENGTH_SHORT).show()
            startNewGame()
            return
        }




        currentIndex++   // Next card in the deck
        val nextCard = deck[currentIndex]
        currentCard = nextCard   //		نخليه هو currentCard الآن

        val guessIsCorrect = if (isHigher) {
            nextCard.value > previousCard.value
        } else {
            nextCard.value < previousCard.value
        }

        if (guessIsCorrect) {
            score++
            imgCorrect.visibility = View.VISIBLE
            // Toast.makeText(this, "Rätt! 🎉", Toast.LENGTH_SHORT).show()
            updateScoreText()
            updateCardText()
        } else {
            imgCorrect.visibility = View.GONE
            showGameOverDialog()
        }
    }

    private fun showGameOverDialog() {
        AlertDialog.Builder(this)
            .setTitle("Fel!")
            .setMessage("Din gissning var fel.\nVill du spela igen? Poängen nollställs.")  // /n سطر جديد
            .setPositiveButton("Ja") { _, _ ->
                startNewGame()
            }
            .setNegativeButton("Nej") { dialog, _ ->
                dialog.dismiss()    // فقط إغلاق النافذة بدون إعادة
            }
            .show()
    }

    private fun updateCardText() {
        val card = currentCard ?: return
        val valueText = when (card.value) {
            13 -> "A"
            10 -> "J"
            11 -> "Q"
            12 -> "K"
            else -> card.value.toString()
        }

        val suitSymbol = when (card.suit) {
            Suit.HEARTS -> "♥"
            Suit.DIAMONDS -> "♦"
            Suit.CLUBS -> "♣"
            Suit.SPADES -> "♠"
        }

        tvCurrentCard.text = "Kort: $valueText $suitSymbol"
    }

    private fun updateScoreText() {
        tvScore.text = "Poäng: $score"
    }
}