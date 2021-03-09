package org.pondar.pacmankotlin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.TextView
import java.util.ArrayList
import kotlin.concurrent.fixedRateTimer


// This class should contain all your game logic
class Game(private var context: Context, view: TextView) {

        private var pointsView : TextView = view
        private var points : Int = 0
        var pacBitmap: Bitmap
        var coinBitmap: Bitmap
        var pacx: Int = 0
        var pacy: Int = 0


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
        coinBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pacman_coin)
    }

    fun distance(x1:Int, y1:Int, x2:Int, y2:Int) : Float {
        // calculate distance and return it

        return 5.2f
    }

    //TODO check if the pacman touches a gold coin
    //and if yes, then update the neccesseary data
    //for the gold coins and the points
    //so you need to go through the arraylist of goldcoins and
    //check each of them for a collision with the pacman
    fun doCollisionCheck() {

        for (coin in coins) {
            if (!coin.taken) {
                logPacmanPositionData()

                // Calculate distance between pacman and coin, if close enough, coin is taken

            }
        }

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
        println("\n_" + "\nlogPacmanPositionData(): " + "\npacy: " + pacy + "\npacx: " + pacx)
    }

    fun newGame() {
        pacx = 50 // 50 by default
        pacy = 400 // 400

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
                if (pacx + pixels + pacBitmap.width < w) {
                    pacx += pixels // pacx = pacx + pixels
                }
                logPacmanPositionData() // Created for testing and troubleshooting purposes
            }
            "left" -> {
                if (pacx - pixels >= 0) {
                    pacx -= pixels // pacx = pacx - pixels
                }
                logPacmanPositionData()
            }
            "down" -> {
                if (pacy + pixels + pacBitmap.height < h) {
                    pacy += pixels // pacy = pacy + pixels
                }
                logPacmanPositionData()
            }
            "up" -> {
                if (pacy - pixels >= 0) {
                    pacy -= pixels // pacy = pacy - pixels
                }
                logPacmanPositionData()
            }

        }
        doCollisionCheck()
        gameView!!.invalidate()
    }

    fun setSize(h: Int, w: Int) {
        this.h = h
        this.w = w
    }






}
