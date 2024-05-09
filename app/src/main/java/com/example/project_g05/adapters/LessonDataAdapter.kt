package com.example.project_g05.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_g05.R
import com.example.project_g05.models.Lesson

class LessonDataAdapter(var yourListData:List<Lesson>,
                        var goToLessonDetail: (Int)->Unit) : RecyclerView.Adapter<LessonDataAdapter.LessonDataViewHolder>() {

    inner class LessonDataViewHolder(itemView: View) : RecyclerView.ViewHolder (itemView) {

    }

    // tell the function which layout file to use for each row of the recycler view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonDataViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.lesson_row_layout, parent, false)
        return LessonDataViewHolder(view)
    }

    // how many items are in the list
    override fun getItemCount(): Int {
        return yourListData.size
    }

    override fun onBindViewHolder(holder: LessonDataViewHolder, position: Int) {
        val rlMain = holder.itemView.findViewById<RelativeLayout>(R.id.rlMain)

        val tvLessonTitle = holder.itemView.findViewById<TextView>(R.id.tvLessonTitle)
        val tvLessonLength = holder.itemView.findViewById<TextView>(R.id.tvLessonLength)
        val ivLessonNumber = holder.itemView.findViewById<ImageView>(R.id.ivLessonNumber)
        val ivCompleted = holder.itemView.findViewById<ImageView>(R.id.ivCompleted)

        val context = holder.itemView.context
        val lesson = yourListData.get(position)
        // 2. Set its value
        tvLessonTitle.text = lesson.lessonName
        tvLessonLength.text = "Length: ${lesson.lessonLength}"

        if (lesson.lessonNumber == 1) {
            ivLessonNumber.setImageResource(context.resources.getIdentifier("one", "drawable",context.packageName))
        } else if (lesson.lessonNumber == 2) {
            ivLessonNumber.setImageResource(context.resources.getIdentifier("two", "drawable",context.packageName))
        } else if (lesson.lessonNumber == 3) {
            ivLessonNumber.setImageResource(context.resources.getIdentifier("three", "drawable",context.packageName))
        } else if (lesson.lessonNumber == 4) {
            ivLessonNumber.setImageResource(context.resources.getIdentifier("four", "drawable",context.packageName))
        } else if (lesson.lessonNumber == 5) {
            ivLessonNumber.setImageResource(context.resources.getIdentifier("five", "drawable",context.packageName))
        }

        if(lesson.isLessonCompleted) {
            ivCompleted.setImageResource(
                context.resources.getIdentifier(
                    "check_mark",
                    "drawable",
                    context.packageName
                )
            )
        }

        rlMain.setOnClickListener {
            goToLessonDetail(position)
        }
    }

}