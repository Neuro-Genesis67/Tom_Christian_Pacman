package org.pondar.pacmankotlin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import java.util.ArrayList
import kotlin.concurrent.fixedRateTimer
import kotlin.math.sqrt


// This class should contain all your game logic
class Game(private var context: Context, view: TextView) {

    private var pointsView: TextView = view
    private var points: Int = 0
    var pacBitmap: Bitmap
    var coinBitmap: Bitmap
    var enemyBitmap: Bitmap
    var pac_x: Int = 0
    var pac_y: Int = 0
    var enemy_x: Int = 0
    var enemy_y: Int = 0
    var enemy_speed = 20
    var coinAmount: Int = 0
    var pacman_direction = ""
    var pacman_speed = 0
    var level = 1
    var coinsLeft = true

    //did we initialize the coins?
    var coinsInitialized = false

    //the list of goldcoins - initially empty
    var coins = ArrayList<GoldCoin>()

    //a reference to the gameview
    private var gameView: GameView? = null
    private var h: Int = 0
    private var w: Int = 0


    // When game object is created, a packman bit image is added to the screen
    init {
        pacBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pacman)
        coinBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pacman_coin) // Assign coin.PNG to the coinBitmap variable
        enemyBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pacman_enemy)

    }

    fun doCollisionCheck() {
        anyCoinsLeft()

        // Did i hit an enemy
        if (getDistance(pac_x, pac_y, enemy_x, enemy_y) < 100) {
            Toast.makeText(context, "You got eaten by Binky and lost the game!", Toast.LENGTH_LONG).show()
            level = 1
            newGame(1)
        }

        // Did i eat all coins
        for (coin in coins) {
            // If the coin hasn't been taken yet
            if (!coin.taken) {
                // If the distance between pacman and coin is less than 100
                if (getDistance(pac_x, pac_y, coin.coin_x, coin.coin_y) < 100) {
                    // Take the coin
                    coin.taken = true
                    // Add 1 point to score
                    points += 1
                    // Update score on the screen
                    pointsView.text = "${context.resources.getString(R.string.points)} $points"
                }
            }
        }

        // Check if game is won
        when (level) {
            1 -> {
                if (!coinsLeft) {
                    level = 2
                    newGame(2)
                }
            }
            2 -> {
                if (!coinsLeft) {
                    level = 3
                    newGame(3)
                }
            }
            3 -> {
                if (!coinsLeft) {
                    Toast.makeText(context, "Congratulations, you won!", Toast.LENGTH_LONG).show()
                    level = 1
                    newGame(1)
                }
            }
        }
    }

    fun anyCoinsLeft() {
        coinsLeft = false
        for (coin in coins) {
            if (!coin.taken) {
                coinsLeft = true
            }
        }
    }

    //  x1 and y1 are the coordinates for pacman, and x2 and y2 are the coordinates for a given coin
    fun getDistance(x1: Int, y1: Int, x2: Int, y2: Int): Int {
        val result = sqrt(Math.pow(((x2 - x1).toDouble()), 2.0) + Math.pow((y2 - y1).toDouble(), 2.0))
        return result.toInt()
    }

    fun initializeGoldcoins() {
        var i = 0
        while (i < coinAmount) {
            coins.add(GoldCoin((0..w - 100).random(), (0..h - 100).random()))
            i++
        }
        coinsInitialized = true
    }

    fun logPacmanPositionData() {
        println("\n_" + "\nlogPacmanPositionData(): " + "\npacy: " + pac_y + "\npacx: " + pac_x)
    }

    fun newGame(level: Int) {
        when(level) {
            1 -> {
                pac_x = 100  // 50 by default
                pac_y = 400 // 400
                enemy_x = 600
                enemy_y = 900
                pacman_speed = 20
                coins = ArrayList<GoldCoin>()
                coinsInitialized = false
                points = 0
                pointsView.text = "${context.resources.getString(R.string.points)} $points"
                gameView?.invalidate() //redraw screen
                enemy_speed = 20
                coinAmount = 1
            }
            2 -> {
                pac_x = 100  // 50 by default
                pac_y = 400 // 400
                enemy_x = 600
                enemy_y = 900
                pacman_speed = 30
                coins = ArrayList<GoldCoin>()
                coinsInitialized = false
                pointsView.text = "${context.resources.getString(R.string.points)} $points"
                gameView?.invalidate() //redraw screen
                enemy_speed = 30
                coinAmount = 2
            }
            3 -> {
                pac_x = 100  // 50 by default
                pac_y = 400 // 400
                enemy_x = 600
                enemy_y = 900
                pacman_speed = 40
                coins = ArrayList<GoldCoin>()
                coinsInitialized = false
                pointsView.text = "${context.resources.getString(R.string.points)} $points"
                gameView?.invalidate() //redraw screen
                enemy_speed = 40
                coinAmount = 3
            }
        }
    }

    fun setGameView(view: GameView) {
        this.gameView = view
    }

    fun movePacman(direction: String) {
        pacman_direction = direction
        when (direction) {
            "right" -> {
                if (pac_x + pacman_speed + pacBitmap.width < w)
                    pac_x += pacman_speed
            }
            "left" -> {
                if (pac_x - pacman_speed >= 0)
                    pac_x -= pacman_speed
            }
            "down" -> {
                if (pac_y + pacman_speed + pacBitmap.height < h)
                    pac_y += pacman_speed
            }
            "up" -> {
                if (pac_y - pacman_speed >= 0)
                    pac_y -= pacman_speed
            }
        }
        var enemy_direction = (1..4).random()
        when (enemy_direction) {
            1 -> {
                if (enemy_x + pacman_speed + enemy_speed + enemyBitmap.width < w)
                    enemy_x += pacman_speed + enemy_speed // Added 20 to spice up enemy's movement, implement properly later
            }
            2 -> {
                if (enemy_x - pacman_speed + enemy_speed >= 0)
                    enemy_x -= pacman_speed + enemy_speed
            }
            3 -> {
                if (enemy_y + pacman_speed + enemy_speed + enemyBitmap.height < h)
                    enemy_y += pacman_speed + enemy_speed
            }
            4 -> {
                if (enemy_y - pacman_speed + enemy_speed >= 0)
                    enemy_y -= pacman_speed + enemy_speed
            }
        }
        logPacmanPositionData()
        doCollisionCheck()
        gameView!!.invalidate()
    }

    fun setSize(h: Int, w: Int) {
        this.h = h
        this.w = w
    }

}
