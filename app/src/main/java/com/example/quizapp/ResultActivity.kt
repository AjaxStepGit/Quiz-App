package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.quizapp.Constants.Constant

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val txtName : TextView = findViewById(R.id.tv_name)
        val tv_score : TextView = findViewById(R.id.tv_score)
        val btnFinish : Button = findViewById(R.id.btn_finish)

        txtName.text = intent.getStringExtra(Constant.USER_NAME)

        val totalQuestion : Int = intent.getIntExtra(Constant.TOTAL_QUESTIONS, 0)
        val correctAnswer : Int = intent.getIntExtra(Constant.CORRECT_ANSWER, 0)

        tv_score.text = "Your score is $correctAnswer out of $totalQuestion"

        btnFinish.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}