package com.example.android.colorconfusion

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private var counts: Int = 0
    private var score: Int = 0
    private var expectedAnswer: Int = 0
    private var colorMaps: List<ColorMap> = listOf(
        ColorMap(Color.RED, Color::RED.name),
        ColorMap(Color.BLUE, Color::BLUE.name),
        ColorMap(Color.GREEN, Color::GREEN.name),
        ColorMap(Color.YELLOW, Color::YELLOW.name),
        ColorMap(Color.GRAY, Color::GRAY.name),
        ColorMap(Color.BLACK, Color::BLACK.name)
    )
    // Use of late initialization
    private lateinit var lBtn: Button
    private lateinit var rBtn: Button
    private lateinit var scoreLabel: TextView
    private lateinit var countLabel: TextView
    private lateinit var textQuery: TextView
    private lateinit var list: List<Button>
    private lateinit var refreshBtn: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lBtn = findViewById(R.id.button1)
        rBtn = findViewById(R.id.button2)
        list = listOf(lBtn, rBtn)
        scoreLabel = findViewById(R.id.textWins)
        countLabel = findViewById(R.id.textPlayCount)
        textQuery = findViewById(R.id.textQuery)
        refreshBtn = findViewById(R.id.fabRevert)
        setupGame()
    }

    private fun refreshScoreBar() {
        scoreLabel.text = score.toString()
        countLabel.text = counts.toString()
    }

    private fun setupGame() {
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
        refreshBtn.setOnClickListener {
            resetGameColors()
            resetScoreBar()
        }
        resetGameColors()
    }

    private fun resetScoreBar() {
        counts = 0
        score = 0
        refreshScoreBar()
    }

    private fun resetGameColors() {
        val randomColorMap1 = colorMaps.random()
        val randomColorMap2 = colorMaps.filter { it != randomColorMap1 }.random()
        val involveColors = listOf(randomColorMap1, randomColorMap2)
        setWordAndColor(involveColors)
        setQueryText(involveColors)
        setButtonColors(involveColors)
    }

    private fun setWordAndColor(involvedColors: List<ColorMap>) {
        // Use of Scope function
        findViewById<TextView>(R.id.textColorWord).let {
            it.text = involvedColors[0].name // word
            it.setTextColor(involvedColors[1].value) // color
        }
    }

    //param with ("word", "color")
    private fun setQueryText(involvedColors: List<ColorMap>) {
        val queries = arrayOf("Word?", "Color?")
        // Use of Scope function
        queries.random().let {
            textQuery.text = it
            expectedAnswer = involvedColors[queries.indexOf(it)].value
        }
    }

    private fun setButtonColors(involvedColors: List<ColorMap>) {
        val randomFirst = intArrayOf(0, 1).random()
        val randomSecond = if (randomFirst == 0) 1 else 0
        list[randomFirst].setBackgroundColor(involvedColors[0].value)
        list[randomSecond].setBackgroundColor(involvedColors[1].value)
    }
}

// Use of data class
data class ColorMap(val value: Int, val name: String)