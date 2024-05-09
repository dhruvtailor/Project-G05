package com.example.project_g05

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.project_g05.databinding.ActivityLessonDetailBinding
import com.example.project_g05.models.Lesson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LessonDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityLessonDetailBinding
    lateinit var pref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        editor = pref.edit()

        var lesson = intent.getSerializableExtra("LESSON_DATA") as Lesson

        if (lesson.isLessonCompleted) {
            binding.tvLessonCompleted.visibility = View.VISIBLE
            binding.btnMarkComplete.visibility = View.GONE
        } else {
            binding.tvLessonCompleted.visibility = View.GONE
            binding.btnMarkComplete.visibility = View.VISIBLE
        }

        binding.tvLessonName.text = lesson.lessonName
        binding.tvLessonNumber.text = lesson.lessonNumber.toString()
        binding.tvLessonLength.text = "Length : ${lesson.lessonLength}"
        binding.tvLessonDescription.text = lesson.lessonDescription


        binding.btnWatchLesson.setOnClickListener {
            loadWebView(lesson.lessonLink)
        }

        binding.btnMarkComplete.setOnClickListener {
            saveUpdatedLessonToSharedPrefrence(lesson)
        }
    }

    private fun loadWebView(url: String) {
        binding.webViewTutorial.apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    view?.loadUrl(request?.url.toString())
                    return true
                }
            }
            loadUrl(url)
            binding.webViewTutorial.visibility = View.VISIBLE
        }
    }

    private fun saveUpdatedLessonToSharedPrefrence(lesson: Lesson) {
        val gson = Gson()
        var lessonListFromPrefrence: MutableList<Lesson> = mutableListOf()
        var json = pref.getString("KEY_LESSON_LIST", null)
        val type = object : TypeToken<MutableList<Lesson>>() {}.type
        if (json != null) {
            lessonListFromPrefrence = gson.fromJson(json, type)
            for (currLesson in lessonListFromPrefrence) {
                if(currLesson.lessonNumber == lesson.lessonNumber) {
                    currLesson.isLessonCompleted = true
                    json = gson.toJson(lessonListFromPrefrence)
                    editor.putString("KEY_LESSON_LIST", json)
                    editor.apply()
                    binding.btnMarkComplete.visibility = View.GONE
                    binding.tvLessonCompleted.visibility = View.VISIBLE
                }
            }
        }
    }
}