package com.example.stonepaperscissor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, PlayActivity::class.java)
        submit.setOnClickListener {

            var name = (nameA.text).toString()
            intent.putExtra("PLAYER_A", name)
            name = (nameB.text).toString()
            intent.putExtra("PLAYER_B", name)
            if(noRounds.text.toString().toInt()>0) {
                intent.putExtra("NO_ROUNDS", noRounds.text.toString().toInt())
                intent.putExtra("IS_SINGLE_MODE", isSinglePlayer.isChecked)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this, "Enter valid no of rounds", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
