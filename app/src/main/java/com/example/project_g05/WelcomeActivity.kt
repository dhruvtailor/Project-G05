package com.example.project_g05

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.project_g05.databinding.ActivityWelcomeBinding
import com.example.project_g05.models.Lesson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WelcomeActivity : AppCompatActivity() {

    lateinit var editor: SharedPreferences.Editor
    lateinit var binding : ActivityWelcomeBinding
    lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        editor = pref.edit()

        binding.btnContinue.setOnClickListener {
            var intent = Intent(this@WelcomeActivity, LessonListActivity::class.java)
            startActivity(intent)
        }

        binding.btnClearData.setOnClickListener {
            editor.clear()
            editor.apply()
            var intent = Intent(this@WelcomeActivity,  UserNameActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    override fun onResume() {
        super.onResume()
        var loggedInUser = pref.getString("KEY_LOGGED_IN_USER","")
        var lessonList = loadLessonsFromSharedPreferences()

        setLessonProgress(lessonList)

        binding.tvWelcomeMsg.text = "Welcome back, ${loggedInUser}"
    }

    private fun loadLessonsFromSharedPreferences(): MutableList<Lesson> {
        val gson = Gson()
        var lessonListFromPrefrence: MutableList<Lesson> = mutableListOf()
        val json = pref.getString("KEY_LESSON_LIST", null)
        val type = object : TypeToken<MutableList<Lesson>>() {}.type
        if (json != null) {
            lessonListFromPrefrence.addAll(gson.fromJson(json, type))
            return lessonListFromPrefrence
        }
        return lessonListFromPrefrence
    }

    private fun setLessonProgress(lessonList: MutableList<Lesson>) {
        var lessonsCompleted: Int = 0
        var lessonsRemaining: Int = 0

        for (currLesson in lessonList) {
            if (currLesson.isLessonCompleted) {
                lessonsCompleted += 1
            } else {
                lessonsRemaining += 1
            }
        }

        var percentComplete:Int = ((lessonsCompleted.toDouble() / lessonList.size.toDouble()) * 100.0).toInt()

        binding.tvLessonsCompleted.text = lessonsCompleted.toString()
        binding.tvLessonsRemaining.text = lessonsRemaining.toString()
        binding.tvOverallProgress.text = "You've completed ${percentComplete}% of the course"
    }

}