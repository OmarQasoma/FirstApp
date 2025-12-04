package com.example.firstapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// 4 أنواع الكروت (suits)
enum class Suit {
    HEARTS, DIAMONDS, CLUBS, SPADES
}

// كرت واحد: قيمة + نوع
data class Card(
    val value: Int,   // 1..13
    val suit: Suit    // Hearts, Diamonds, Clubs, Spades
)

class GameActivity : AppCompatActivity() {

    private lateinit var tvCurrentCard: TextView
    private lateinit var tvScore: TextView
    private lateinit var btnHigher: Button
    private lateinit var btnLower: Button

    private var deck: MutableList<Card> = mutableListOf()
    private var currentIndex = 0
    private var currentCard: Card? = null
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // ربط عناصر الواجهة
        tvCurrentCard = findViewById(R.id.tvCurrentCard)
        tvScore = findViewById(R.id.tvScore)
        btnHigher = findViewById(R.id.btnHigher)
        btnLower = findViewById(R.id.btnLower)

        // نبدأ لعبة جديدة
        startNewGame()

        // زر "Högre"
        btnHigher.setOnClickListener {
            handleGuess(isHigher = true)
        }

        // زر "Lägre"
        btnLower.setOnClickListener {
            handleGuess(isHigher = false)
        }
    }

    // تحضير deck جديد (52 كرت) وبدء اللعبة من أول كرت
    private fun startNewGame() {
        // نبني deck
        deck = mutableListOf()
        val suits = Suit.values()
        for (suit in suits) {
            for (value in 1..13) {
                deck.add(Card(value, suit))
            }
        }

        deck.shuffle() // نخلط الكروت

        score = 0
        currentIndex = 0
        currentCard = deck[currentIndex]

        updateScoreText()
        updateCardText()
    }

    // لما اللاعب يختار Högre أو Lägre
    private fun handleGuess(isHigher: Boolean) {
        val previousCard = currentCard ?: return

        // لو وصلنا لنهاية deck نعيد اللعبة
        if (currentIndex >= deck.size - 1) {
            Toast.makeText(this, "Inga fler kort. Spelet startar om.", Toast.LENGTH_SHORT).show()
            startNewGame()
            return
        }

        // نسحب الكرت التالي
        currentIndex++
        val nextCard = deck[currentIndex]
        currentCard = nextCard

        // نتحقق من صحة التخمين (نقارن القيم فقط)
        val guessIsCorrect = if (isHigher) {
            nextCard.value > previousCard.value
        } else {
            nextCard.value < previousCard.value
        }

        if (guessIsCorrect) {
            score++
            Toast.makeText(this, "Rätt! 🎉", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Fel! Poängen nollställs.", Toast.LENGTH_SHORT).show()
            score = 0
        }

        updateScoreText()
        updateCardText()
    }

    // تحديث نص الكرت على الشاشة
    private fun updateCardText() {
        val card = currentCard ?: return
        val valueText = when (card.value) {
            1 -> "A"
            11 -> "J"
            12 -> "Q"
            13 -> "K"
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

    // تحديث نص السكور
    private fun updateScoreText() {
        tvScore.text = "Poäng: $score"
    }
}