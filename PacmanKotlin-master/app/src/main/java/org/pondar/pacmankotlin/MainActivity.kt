package org.pondar.pacmankotlin

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    //reference to the game class.
    private var game: Game? = null
    private var myTimer: Timer = Timer()
    var running = true // set to false(?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT //Set portrait mode

        game = Game(this, pointsView)

        //intialize the game view class and game class
        game?.setGameView(gameView)
        gameView.setGame(game)
        game?.newGame(1)

        myTimer.schedule(object : TimerTask() {
            override fun run() {
                timerMethod()
            }
        }, 0, 100)

        initPacmanControls()
    }

    override fun onStop() {
        super.onStop()
        myTimer.cancel()
    }

    private fun timerMethod() {
        this.runOnUiThread(timerTick)
    }


    private val timerTick = Runnable {
        //This method runs in the same thread as the UI.
        // so we can draw
        if (running) {
            when(game!!.pacman_direction) {
                "right" -> { game!!.movePacman("right") }
                "left"  -> { game!!.movePacman("left") }
                "down"  -> { game!!.movePacman("down") }
                "up"    -> { game!!.movePacman("up") }
            }
        }
    }

    fun initPacmanControls() {
        moveRight.setOnClickListener { game?.movePacman("right") }
        moveLeft.setOnClickListener { game?.movePacman("left") }
        moveUp.setOnClickListener { game?.movePacman("up") }
        moveDown.setOnClickListener { game?.movePacman("down") }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    fun pauseGame() {
        running = !running
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        if (id == R.id.action_settings) {
            Toast.makeText(this, "settings clicked", Toast.LENGTH_LONG).show()
            return true
        } else if (id == R.id.action_newGame) {
            Toast.makeText(this, "New Game clicked", Toast.LENGTH_LONG).show()
            game?.newGame(1)
            return true
        } else if (id == R.id.action_pauseGame) {
            pauseGame()
        }
        return super.onOptionsItemSelected(item)
    }
}
