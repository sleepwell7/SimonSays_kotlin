package com.example.simonsays

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class Red : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_red)

        //Fetch buttons and textviews
        val title = findViewById<TextView>(R.id.textTitle)
        val scoreText = findViewById<TextView>(R.id.scoreText)
        val greenbtn = findViewById<Button>(R.id.greenbtn)
        val bluebtn = findViewById<Button>(R.id.bluebtn)
        val yellowbtn = findViewById<Button>(R.id.yellowbtn)
        val redbtn = findViewById<Button>(R.id.redbtn)
        val restartbtn = findViewById<Button>(R.id.restartbtn)
        val activitiesArray = arrayOf(Green::class.java, Yellow::class.java, Blue::class.java, Red::class.java)

        //Get count, index and color from intent
        var score = intent.getIntExtra("score", -2)
        var count = intent.getIntExtra("count", -3)
        val colors = intent.getStringArrayListExtra("colors")

        //Update displayed score
        scoreText.text = score.toString()

        //update simon says color
        if (score != count){
            val temp: String = "Color: " + (count + 1)
            title.text = temp
        }else{
            val temp: String = "Simon says " + colors!![count]
            title.text = temp
        }


        fun gameOver(newTitle: String){
            colors!![count] = newTitle
            title.text = newTitle
            restartbtn.visibility = View.VISIBLE
            redbtn.text = newTitle
            yellowbtn.text = newTitle
            bluebtn.text = newTitle
            greenbtn.text = newTitle
        }

        //Update game based on users choice
        fun onCorrect(answer: String, classNum: Int) {
            if (colors!![count] == answer) {
                val intent = Intent(this@Red, activitiesArray[classNum])
                if ((count + 1) == colors.size) {
                    gameOver("You Win")
                } else {
                    if (count == score) {
                        count = -1
                        score++
                    }
                    count++
                    intent.putStringArrayListExtra("colors", colors)
                    intent.putExtra("count", count)
                    intent.putExtra("score", score)
                    startActivity(intent)
                }
            }else if (restartbtn.visibility != 0){
                gameOver("GAME OVER")
            }
        }



        // On click listeners for each button
        greenbtn.setOnClickListener {
            onCorrect("Green",0)
        }
        yellowbtn.setOnClickListener {
            onCorrect("Yellow",1)
        }
        bluebtn.setOnClickListener {
            onCorrect("Blue",2)
        }
        redbtn.setOnClickListener {
            onCorrect("Red",3)
        }

        restartbtn.setOnClickListener {
            val intent = Intent(this@Red, MainActivity::class.java)
            startActivity(intent)
        }
    }
}