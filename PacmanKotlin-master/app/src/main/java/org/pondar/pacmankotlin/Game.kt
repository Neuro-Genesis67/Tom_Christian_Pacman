package org.pondar.pacmankotlin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.TextView
import java.util.ArrayList
import kotlin.concurrent.fixedRateTimer
import kotlin.math.sqrt


// This class should contain all your game logic
class Game(private var context: Context, view: TextView) {

        private var pointsView : TextView = view
        private var points : Int = 0
        var pacBitmap: Bitmap
        var coinBitmap: Bitmap // Variable to store Coin.PNG image
        var pac_x: Int = 0
        var pac_y: Int = 0


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
        pacBitmap  = BitmapFactory.decodeResource(context.resources, R.drawable.pacman)
        coinBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pacman_coin) // Assign coin.PNG to the coinBitmap variable
    }

    fun doCollisionCheck() {

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

    }
    //  x1 and y1 are the coordinates for pacman, and x2 and y2 are the coordinates for a given coin
    fun getDistance(x1:Int, y1:Int, x2:Int, y2:Int) : Int {
        val result = sqrt(Math.pow(((x2 - x1).toDouble()), 2.0) + Math.pow((y2 - y1).toDouble(), 2.0))
        return result.toInt()
    }

    fun initializeGoldcoins() {
        coins.add(GoldCoin(100, 100))
        coins.add(GoldCoin(300, 300))
        coins.add(GoldCoin(500, 500))
        coins.add(GoldCoin(700, 200))
        coins.add(GoldCoin(400, 850))
        coins.add(GoldCoin(200, 900))
        coins.add(GoldCoin(400, 1300))
        coins.add(GoldCoin(600, 600))
        coins.add(GoldCoin(800, 1200))
        coinsInitialized = true
    }

    fun logPacmanPositionData() {
        println("\n_" + "\nlogPacmanPositionData(): " + "\npacy: " + pac_y + "\npacx: " + pac_x)
    }

    fun newGame() {
        pac_x = 50 // 50 by default
        pac_y = 400 // 400

        //reset the points
        coinsInitialized = false
        points = 0
        pointsView.text = "${context.resources.getString(R.string.points)} $points"
        gameView?.invalidate() //redraw screen
    }

    fun setGameView(view: GameView) {
        this.gameView = view
    }

    fun movePacman(direction: String, pixels: Int) {
        when (direction) {
            "right" -> {
                if (pac_x + pixels + pacBitmap.width < w)
                    pac_x += pixels
            }
            "left" -> {
                if (pac_x - pixels >= 0)
                    pac_x -= pixels
            }
            "down" -> {
                if (pac_y + pixels + pacBitmap.height < h)
                    pac_y += pixels
            }
            "up" -> {
                if (pac_y - pixels >= 0)
                    pac_y -= pixels
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
