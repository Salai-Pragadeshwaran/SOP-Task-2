package com.example.stonepaperscissor

import android.content.Intent
import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.android.synthetic.main.content_play.*
import java.lang.Math.random

class PlayActivity : AppCompatActivity() {

    var options = ArrayList<Int>()
    var scoreA = 0
    var scoreB = 0
    val rock = 0
    val paper = 120
    val scissor = 240
    var text = ""
    var aClicked = false
    var bClicked = false
    var singlePlayerMode = false
    var numberOfRounds = 1
    var roundsCompleted = 0

    val resetBgTimer = object: CountDownTimer(1000, 500){
        override fun onTick(millisUntilFinished: Long) {
            //do nothing
        }

        override fun onFinish() {
            layoutA.setBackgroundColor(Color.WHITE)
            layoutB.setBackgroundColor(Color.WHITE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            options = savedInstanceState.getIntegerArrayList("OPTIONS") as ArrayList<Int>
            scoreA = savedInstanceState.getInt("SCORE_A", 0)
            scoreB = savedInstanceState.getInt("SCORE_B", 0)
            aClicked = savedInstanceState.getBoolean("A_CLICKED", false)
            bClicked = savedInstanceState.getBoolean("B_CLICKED", false)
            singlePlayerMode = savedInstanceState.getBoolean("SINGLE_PLAYER_MODE", false)
            roundsCompleted = savedInstanceState.getInt("ROUNDS_COMPLETED", 0)
        }

        setContentView(R.layout.activity_play)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->

            Snackbar.make(view, "Game Reset", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            reset()
        }

        if(intent.getStringExtra("PLAYER_A")!=""){
            playerAName.text = intent.getStringExtra("PLAYER_A")
        }
        if(intent.getStringExtra("PLAYER_B")!=""){
            playerBName.text = intent.getStringExtra("PLAYER_B")
        }

        text = scoreA.toString()
        score_a.text = text
        text = scoreB.toString()
        score_b.text = text
        numberOfRounds = intent.getIntExtra("NO_ROUNDS", 1)
        singlePlayerMode = intent.getBooleanExtra("IS_SINGLE_MODE", false)
        if(singlePlayerMode)
            playerBName.text = "Computer"

        rockA.setOnClickListener{
            if(!aClicked&&(roundsCompleted<numberOfRounds)) {
                if(singlePlayerMode) {
                    options.add(120 * ((0..2).random()))
                    animateComputerOption()
                }
                var res = optionClicked(rock)
                if ((res != -1) && (res != -2)) {
                    if (res == rock) {
                        incrementScoreA()
                    } else {
                        incrementScoreB()
                    }
                }
                if(res==-2)
                    aClicked = true
            }

        }

        paperA.setOnClickListener{
            if(!aClicked&&(roundsCompleted<numberOfRounds)) {
                if(singlePlayerMode) {
                    options.add(120 * ((0..2).random()))
                    animateComputerOption()
                }
                var res = optionClicked(paper)
                if ((res != -1) && (res != -2)) {
                    if (res == paper) {
                        incrementScoreA()
                    } else {
                        incrementScoreB()
                    }
                }
                if(res==-2)
                    aClicked = true
            }
        }

        scissorA.setOnClickListener{
            if(!aClicked&&(roundsCompleted<numberOfRounds)) {
                if(singlePlayerMode) {
                    options.add(120 * ((0..2).random()))
                    animateComputerOption()
                }
                var res = optionClicked(scissor)
                if ((res != -1) && (res != -2)) {
                    if (res == scissor) {
                        incrementScoreA()
                    } else {
                        incrementScoreB()
                    }
                }
                if(res==-2)
                    aClicked = true
            }
        }

        rockB.setOnClickListener{
            if(!singlePlayerMode&&(roundsCompleted<numberOfRounds)){
            if(!bClicked) {
                var res = optionClicked(rock)
                if ((res != -1) && (res != -2)) {
                    if (res == rock) {
                        incrementScoreB()
                    } else {
                        incrementScoreA()
                    }
                }
                if(res==-2)
                    bClicked = true
            }
        }}

        paperB.setOnClickListener{
            if(!singlePlayerMode&&(roundsCompleted<numberOfRounds)){
            if(!bClicked) {
                var res = optionClicked(paper)
                if ((res != -1) && (res != -2)) {
                    if (res == paper) {
                        incrementScoreB()
                    } else {
                        incrementScoreA()
                    }
                }
                if(res==-2)
                    bClicked = true
            }
        }}

        scissorB.setOnClickListener{
            if(!singlePlayerMode&&(roundsCompleted<numberOfRounds)){
            if(!bClicked) {
                var res = optionClicked(scissor)
                if ((res != -1) && (res != -2)) {
                    if (res == scissor) {
                        incrementScoreB()
                    } else {
                        incrementScoreA()
                    }
                }
                if(res==-2)
                    bClicked = true
            }
        }}
    }

    private fun animateComputerOption() {
        val blinkAnim = AnimationUtils.loadAnimation(this, R.anim.blink_anim)
        when(options[0]){
            0->{
                rockB.startAnimation(blinkAnim)
            }
            120->{
                paperB.startAnimation(blinkAnim)
            }
            240->{
                scissorB.startAnimation(blinkAnim)
            }
        }
    }

    private fun reset() {
        aClicked = false
        bClicked = false
        roundsCompleted = 0
        scoreA = 0
        scoreB = 0
        score_a.text = "0"
        score_b.text = "0"
        options.clear()
    }

    private fun incrementScoreA(){
        roundsCompleted++
        scoreA++
        text = scoreA.toString()
        score_a.text = text
        Toast.makeText(this, "${playerAName.text} scores", Toast.LENGTH_SHORT).show()
        layoutA.setBackgroundColor(Color.parseColor("#80c683"))
        layoutB.setBackgroundColor(Color.parseColor("#ef6191"))
        resetBgTimer.start()
        if(roundsCompleted==numberOfRounds)
            showWinMsg()
    }

    private fun showWinMsg() {
        if(scoreA>scoreB)
            Toast.makeText(this, "${playerAName.text} Wins !", Toast.LENGTH_SHORT).show()
        else if(scoreB>scoreA)
            Toast.makeText(this, "${playerBName.text} Wins !", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "Match Tie", Toast.LENGTH_SHORT).show()
    }

    private fun incrementScoreB(){
        roundsCompleted++
        scoreB++
        text = scoreB.toString()
        score_b.text = text
        Toast.makeText(this, "${playerBName.text} scores", Toast.LENGTH_SHORT).show()
        layoutA.setBackgroundColor(Color.parseColor("#ef6191"))
        layoutB.setBackgroundColor(Color.parseColor("#80c683"))
        resetBgTimer.start()

        if(roundsCompleted==numberOfRounds)
            showWinMsg()
    }

    private fun optionClicked(value:Int) :Int {
        if(options.size==0){
            options.add(value)
            return -2
        }
        else {
            if(options[0] == value){
                // draw
                roundsCompleted++
                Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show()
                aClicked = false
                bClicked = false
                options.clear()
                if(roundsCompleted==numberOfRounds)
                    showWinMsg()
                return -1
            }
            else {
                options.add(value)
                aClicked = false
                bClicked = false
                return checkWinner()
            }
        }
    }


    private fun checkWinner() : Int{
        var returnValue = 0
        when((options[0]+options[1])/2){
            60  -> { returnValue = paper }
            180 -> { returnValue = scissor }
            120 -> { returnValue = rock }
        }
        options.clear()
        return returnValue
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.switchMode){
            if(singlePlayerMode) {
                singlePlayerMode = false
                if(intent.getStringExtra("PLAYER_B")!=""){
                    playerBName.text = intent.getStringExtra("PLAYER_B")
                }
                else
                    playerBName.text = "Player B"
                Toast.makeText(this, "Two Player Mode Enabled", Toast.LENGTH_SHORT).show()
            }
            else {
                singlePlayerMode = true
                playerBName.text = "Computer"
                Toast.makeText(this, "Single Player Mode Enabled", Toast.LENGTH_SHORT).show()
            }
            reset()
            return true
        }
        else if(item.itemId==R.id.initialize){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run{
            putIntegerArrayList("OPTIONS", options)
            putInt("SCORE_A", scoreA)
            putInt("SCORE_B", scoreB)
            putBoolean("A_CLICKED", aClicked)
            putBoolean("B_CLICKED", bClicked)
            putBoolean("SINGLE_PLAYER_MODE", singlePlayerMode)
            putInt("ROUNDS_COMPLETED", roundsCompleted)
        }
        super.onSaveInstanceState(outState)
    }

}
