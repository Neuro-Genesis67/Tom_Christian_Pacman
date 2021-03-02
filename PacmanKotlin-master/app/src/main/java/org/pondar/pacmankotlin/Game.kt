package org.pondar.pacmankotlin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.TextView
import java.util.ArrayList
import kotlin.concurrent.fixedRateTimer


/**
 * This class should contain all your game logic
 */

class Game(private var context: Context, view: TextView) {

        private var pointsView : TextView = view
        private var points : Int = 0
        var pacBitmap: Bitmap
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
    init { pacBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pacman) }

    fun movePacman(direction: String, pixels: Int) {
        when (direction) {
            "right" -> {
                if (pacx + pixels + pacBitmap.width < w) {
                    pacx += pixels // pacx = pacx + pixels
                }
                logPositionData(pixels) // Created for testing and troubleshooting purposes
            }
            "left" -> {
                if (pacx - pixels >= 0) {
                    pacx -= pixels // pacx = pacx - pixels
                }
                logPositionData(pixels)
            }
            "down" -> {
                if (pacy + pixels + pacBitmap.height < h) {
                    pacy += pixels // pacy = pacy + pixels
                }
                logPositionData(pixels)
            }
            "up" -> {
                if (pacy - pixels >= 0) {
                    pacy -= pixels // pacy = pacy - pixels
                }
                logPositionData(pixels)
            }

        }
        doCollisionCheck()
        gameView!!.invalidate()
    }

    fun logPositionData(pixels: Int) {
        println("STATUS: " + "\npacy: " + pacy + "\npixels: " + pixels + "\npackBitmap.height: " + pacBitmap.height + "\n")
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

    //TODO initialize goldcoins also here
    fun initializeGoldcoins() {
        //DO Stuff to initialize the array list with some coins.
        coinsInitialized = true
    }



    fun setSize(h: Int, w: Int) {
        this.h = h
        this.w = w
    }

    //TODO check if the pacman touches a gold coin
    //and if yes, then update the neccesseary data
    //for the gold coins and the points
    //so you need to go through the arraylist of goldcoins and
    //check each of them for a collision with the pacman
    fun doCollisionCheck() {

    }


}
