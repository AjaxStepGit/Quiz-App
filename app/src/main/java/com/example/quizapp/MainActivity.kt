package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.quizapp.Constants.Constant

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart : Button = findViewById(R.id.btn_start)
        val edt_name : EditText = findViewById(R.id.edt_name)



        btnStart.setOnClickListener{
            if(edt_name.text.isEmpty()){
                Toast.makeText(this, "Enter you name", Toast.LENGTH_LONG).show()
            }else{
                val intent = Intent(this, QuizQuestionActivity::class.java)
                intent.putExtra(Constant.USER_NAME, edt_name.text.toString())
                startActivity(intent)
                finish()
            }

        }
    }
}