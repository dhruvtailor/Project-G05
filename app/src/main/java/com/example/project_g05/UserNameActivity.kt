package com.example.project_g05

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.project_g05.databinding.ActivityUserNameBinding
import com.example.project_g05.models.Lesson
import com.google.gson.Gson

class UserNameActivity : AppCompatActivity() {
    lateinit var binding : ActivityUserNameBinding
    lateinit var pref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    var lessonList: List<Lesson> = mutableListOf(
        Lesson(1,
            "Introduction",
            "20 min",
            "This crash course series will take you from total beginner to create great-looking sites with HTML & CSS. In this video, we'll cover what HTML & CSS are, as well as setting up our dev environment.",
            "https://www.youtube.com/embed/hu-q2zYwEYs?si=cRz9W6mLP460OkBZ",
            false) ,
        Lesson(2,
            "HTML Basics",
            "20 min",
            "This tutorial will cover the basics of HTML syntax and how to construct HTML tags & documents.",
            "https://www.youtube.com/embed/mbeT8mpmtHA?si=UZTn-vSNYN7-WwhN",
            false),
        Lesson(3,
            "HTML Forms",
            "32 min",
            "This HTML tutorial will explain how to create forms in HTML, (using some newer HTML 5 input fields too). We'll look at email fields, text fields, password fields and more.",
            "https://www.youtube.com/embed/YwbIeMlxZAU?si=9UAzlnx3VcQ8p-uk",
            false),
        Lesson(4, "CSS Basics","43 min",
            "This CSS tutorial for beginners will cover the basic syntax of CSS and how we can use it to make our web pages look better.",
            "https://www.youtube.com/embed/D3iEE29ZXRM?si=kgq1tApEm4ZohxL-",
            false),
        Lesson(5,
            "CSS Classes & Selectors",
            "26 min",
            "The course on CSS Classes & Selectors covers the basics of using class selectors in CSS to style HTML elements effectively, emphasizing the importance of IDs and Classes for precise styling",
            "https://www.youtube.com/embed/FHZn6706e3Q?si=uGx73oELq_d-27n_",
            false)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        editor = pref.edit()

        if (!isFirstTimeVisit()) {
            navigateToWelcomeScreen()
        }

        binding.btnContinue.setOnClickListener {
            val name = binding.etName.text.toString()
            saveUserName(name)
            saveLessonsToSharedPreferences()
            navigateToLessonsList()
        }
    }

    private fun isFirstTimeVisit(): Boolean {
        return pref.getBoolean("KEY_FIRST_TIME", true)
    }

    private fun saveUserName(userName: String) {
        editor.putString("KEY_LOGGED_IN_USER", userName)
        editor.putBoolean("KEY_FIRST_TIME", false)
        editor.apply()
    }

    private fun saveLessonsToSharedPreferences() {
        val gson = Gson()
        val json = gson.toJson(lessonList)
        editor.putString("KEY_LESSON_LIST", json)
        editor.apply()
    }

    private fun navigateToLessonsList() {
        val intent = Intent(this, LessonListActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToWelcomeScreen() {
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}