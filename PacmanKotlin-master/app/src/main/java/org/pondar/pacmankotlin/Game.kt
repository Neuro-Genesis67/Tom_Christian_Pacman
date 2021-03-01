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
        //bitmap of the pacman
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
                //  5         1            2        < 10
                if (pacx + pixels + pacBitmap.width < w) {
                    pacx += pixels // move 1 pixel to the right
                }
                println("STATUS: " + "\npacx: " + pacx + "\npixels: " + pixels + "\npackBitmap.width: " + pacBitmap.width + "\n")

            }
            "left" -> {                                           // 1 2 3 4 5 6 7 8 9 10
                //  5        1             2          5            [ o o o o o o o o o o ]
                if (pacx + pixels + pacBitmap.width > w) {
                    pacx -= pixels
                }
                println("STATUS: " + "\npacx: " + pacx + "\npixels: " + pixels + "\npackBitmap.width: " + pacBitmap.width + "\n")
            }
            "down" -> {
                if (pacy + pixels + pacBitmap.height < h) {
                    pacy = pacy + pixels
                }
                println("STATUS: " + "\npacy: " + pacy + "\npixels: " + pixels + "\npackBitmap.height: " + pacBitmap.height + "\n")
            }
            "up" -> {
                if (pacy - pixels - pacBitmap.height < h) {
                    pacy = pacy - pixels
                }
                println("STATUS: " + "\npacy: " + pacy + "\npixels: " + pixels + "\npackBitmap.height: " + pacBitmap.height + "\n")
            }

        }
        doCollisionCheck()
        gameView!!.invalidate()
    }

    fun setGameView(view: GameView) {
        this.gameView = view
    }

    //TODO initialize goldcoins also here
    fun initializeGoldcoins() {
        //DO Stuff to initialize the array list with some coins.
        coinsInitialized = true
    }


    fun newGame() {
        pacx = 50
        pacy = 400
        //reset the points
        coinsInitialized = false
        points = 0
        pointsView.text = "${context.resources.getString(R.string.points)} $points"
        gameView?.invalidate() //redraw screen
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
