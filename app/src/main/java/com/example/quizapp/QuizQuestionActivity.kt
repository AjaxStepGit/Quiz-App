package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.quizapp.Constants.Constant
import com.example.quizapp.Modal.Question
import java.lang.reflect.Type

class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition : Int = 1
    private var mSelectedOptionPosition : Int = 0
    private var mQuestionList : ArrayList<Question>? = null
    private var mUserName : String? = null
    private var mCorrectAnswer : Int = 0

    private var progressBar : ProgressBar? = null
    private var tvProgress : TextView? = null
    private var tvQuestion : TextView? = null
    private var optionOne : TextView? = null
    private var optionTwo : TextView? = null
    private var optionThree : TextView? = null
    private var optionFour : TextView? = null
    private var flagImage : ImageView? = null
    private var btnSubmit : Button? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)

        mUserName = intent.getStringExtra(Constant.USER_NAME)


        progressBar = findViewById(R.id.progress_bar)
        tvProgress = findViewById(R.id.tv_progress)
        tvQuestion = findViewById(R.id.tv_question)
        optionOne = findViewById(R.id.optionOne)
        optionTwo = findViewById(R.id.optionTwo)
        optionThree = findViewById(R.id.optionThree)
        optionFour = findViewById(R.id.optionFour)
        flagImage = findViewById(R.id.flag_img)
        btnSubmit = findViewById(R.id.btnSubmit)

        mQuestionList = Constant.getQuestions()

        setQuestion()


    }

    private fun selectedOptionView(tv : TextView, selectedOptionNum : Int){
        defaultOptionsView()

        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.active_option_border_bg
        )
    }

    private fun setQuestion() {
        //mCurrentPosition = 1
        defaultOptionsView()
        val question: Question = mQuestionList!![mCurrentPosition - 1]

        progressBar?.progress = mCurrentPosition
        flagImage?.setImageResource(question.image)
        tvProgress?.text = "$mCurrentPosition / ${progressBar?.max}"
        tvQuestion?.text = question.questions
        optionOne?.text = question.optionOne
        optionTwo?.text = question.optionTwo
        optionThree?.text = question.optionThree
        optionFour?.text = question.optionFour

        optionOne?.setOnClickListener(this)
        optionTwo?.setOnClickListener(this)
        optionThree?.setOnClickListener(this)
        optionFour?.setOnClickListener(this)
        btnSubmit?.setOnClickListener(this)

        if(mCurrentPosition == mQuestionList!!.size){
            btnSubmit?.text = "FINISH"
        }else{
            btnSubmit?.text = "SUBMIT"
        }
    }

    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()
        optionOne?.let{
            options.add(0, it)
        }

        optionTwo?.let{
            options.add(1, it)
        }

        optionThree?.let{
            options.add(2, it)
        }

        optionFour?.let{
            options.add(3, it)
        }

        for(option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            //option.setTextColor(Color.parseColor("#FF0000"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.optionOne -> {
                optionOne?.let{
                    selectedOptionView(it, 1)
                }
            }
            R.id.optionTwo -> {
                optionTwo?.let{
                    selectedOptionView(it, 2)
                }
            }
            R.id.optionThree -> {
                optionThree?.let{
                    selectedOptionView(it, 3)
                }
            }
            R.id.optionFour -> {
                optionFour?.let{
                    selectedOptionView(it, 4)
                }
            }
            R.id.btnSubmit -> {
                if(mSelectedOptionPosition == 0){
                    mCurrentPosition++

                    when {
                        mCurrentPosition <= mQuestionList!!.size ->{
                            setQuestion()
                        }
                        else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constant.USER_NAME, mUserName)
                            intent.putExtra(Constant.CORRECT_ANSWER, mCorrectAnswer)
                            intent.putExtra(Constant.TOTAL_QUESTIONS, mQuestionList?.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                }else{
                    val question = mQuestionList?.get(mCurrentPosition-1)
                    Log.i("Correct Answer", question!!.correctAnswer.toString())
                    Log.i("M Selected  Answer", mSelectedOptionPosition.toString())
                    if(question!!.correctAnswer != mSelectedOptionPosition){
                        answerView(mSelectedOptionPosition, R.drawable.incorrect_option_bg)
                    }else{
                        mCorrectAnswer++
                    }

                    answerView(question.correctAnswer, R.drawable.correct_option_bg);

                    if(mCurrentPosition == mQuestionList!!.size){
                        btnSubmit?.text = "FINISH"
                    }else{
                        btnSubmit?.text = "GO TO NEXT QUESTION"
                    }

                    mSelectedOptionPosition = 0
                }
            }
        }

    }


    private fun answerView(answer: Int, drawableView : Int){
        when(answer){
            1 -> {
                optionOne?.background = ContextCompat.getDrawable(
                    this@QuizQuestionActivity, drawableView);
            }
            2 -> {
                optionTwo?.background = ContextCompat.getDrawable(this@QuizQuestionActivity, drawableView);
            }
            3 -> {
                optionThree?.background = ContextCompat.getDrawable(this@QuizQuestionActivity, drawableView);
            }
            4 -> {
                optionFour?.background = ContextCompat.getDrawable(this@QuizQuestionActivity , drawableView);
            }

        }
    }


}