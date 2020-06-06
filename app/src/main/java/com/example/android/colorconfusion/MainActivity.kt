package com.example.android.colorconfusion

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var counts: Int = 0
    private var score: Int = 0
    private var expectedAnswer: Int = 0
    private lateinit var lBtn: Button
    private lateinit var rBtn: Button
    private lateinit var scoreLabel: TextView
    private lateinit var countLabel: TextView
    private lateinit var textQuery: TextView
    private lateinit var list: List<Button>
    private lateinit var colorMaps: List<ColorMap>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lBtn = findViewById(R.id.button1)
        rBtn = findViewById(R.id.button2)
        list = listOf(lBtn, rBtn)
        scoreLabel = findViewById(R.id.textWins)
        countLabel = findViewById(R.id.textPlayCount)
        textQuery = findViewById(R.id.textQuery)
        setupGame()

    }

    private fun refreshScoreBar() {
        scoreLabel.text = score.toString()
        countLabel.text = counts.toString()
    }

    private fun setupGame() {
        colorMaps = listOf(
            ColorMap(Color.RED, Color::RED.name),
            ColorMap(Color.BLUE, Color::BLUE.name),
            ColorMap(Color.GREEN, Color::GREEN.name),
            ColorMap(Color.YELLOW, Color::YELLOW.name),
            ColorMap(Color.GRAY, Color::GRAY.name),
            ColorMap(Color.BLACK, Color::BLACK.name)
        )
        for (btn in list) {
            btn.setOnClickListener {
                if ((it.background as ColorDrawable).color == ColorDrawable(expectedAnswer).color) {
                    score++
                }
                counts++
                refreshScoreBar()
                resetGameColors()
            }
        }
        resetGameColors()
    }

    private fun resetGameColors() {
        var involveColors: List<ColorMap> = listOf(colorMaps.random(), colorMaps.random())
        setWordAndColor(involveColors)
        setQueryText(involveColors)
        setButtonColors(involveColors)
    }

    private fun setWordAndColor(involvedColors: List<ColorMap>) {
        val wrd: TextView = findViewById(R.id.textColorWord)
        wrd.text = involvedColors[0].name // word
        wrd.setTextColor(involvedColors[1].value) // color
    }

    //param with ("word", "color")
    private fun setQueryText(involvedColors: List<ColorMap>) {
        val queries = arrayOf("Word?", "Color?")
        val currentQuery = queries.random()
        textQuery.text = currentQuery
        expectedAnswer = involvedColors[queries.indexOf(currentQuery)].value
    }

    private fun setButtonColors(involvedColors: List<ColorMap>) {
        val randomFirst = intArrayOf(0, 1).random()
        val randomSecond = if (randomFirst == 0) 1 else 0
        list[randomFirst].setBackgroundColor(involvedColors[0].value)
        list[randomSecond].setBackgroundColor(involvedColors[1].value)
    }
}


data class ColorMap(val value: Int, val name: String)