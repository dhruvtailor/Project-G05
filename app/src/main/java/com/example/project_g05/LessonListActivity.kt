package com.example.project_g05

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_g05.adapters.LessonDataAdapter
import com.example.project_g05.databinding.ActivityLessonListBinding
import com.example.project_g05.models.Lesson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LessonListActivity : AppCompatActivity() {
    lateinit var adapter: LessonDataAdapter
    lateinit var lessonList: MutableList<Lesson>
    lateinit var binding : ActivityLessonListBinding
    lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        binding.rvLessons.layoutManager = LinearLayoutManager(this)

        binding.rvLessons.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    override fun onResume() {
        super.onResume()
        lessonList = loadLessonsFromSharedPreferences()
        adapter = LessonDataAdapter(lessonList, goToLessonDetail)
        binding.rvLessons.adapter = adapter
        adapter.notifyDataSetChanged()
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

    val goToLessonDetail = {
            rowNumber:Int ->
        val intent = Intent(this@LessonListActivity, LessonDetailActivity::class.java)
        intent.putExtra("LESSON_DATA", lessonList.get(rowNumber))
        startActivity(intent)
    }

}