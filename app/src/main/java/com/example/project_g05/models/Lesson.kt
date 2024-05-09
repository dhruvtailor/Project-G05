package com.example.project_g05.models

import java.io.Serializable

class Lesson : Serializable {
    var lessonNumber: Int
    var lessonName: String
    var lessonLength: String
    var lessonDescription: String
    var lessonLink: String
    var isLessonCompleted: Boolean

    constructor(
        lessonNumber: Int,
        lessonName: String,
        lessonLength: String,
        lessonDescription: String,
        lessonLink: String,
        isLessonCompleted: Boolean
    ) {
        this.lessonNumber = lessonNumber
        this.lessonName = lessonName
        this.lessonLength = lessonLength
        this.lessonDescription = lessonDescription
        this.lessonLink = lessonLink
        this.isLessonCompleted = isLessonCompleted
    }

    fun setIsLessonCompleted(completed: Boolean) {
        this.isLessonCompleted = completed
    }

    override fun toString(): String {
        return "Lesson(lessonNumber='$lessonNumber', lessonName='$lessonName', lessonLength='$lessonLength', lessonDescription='$lessonDescription', lessonLink='$lessonLink', lessonCompleted=$isLessonCompleted)"
    }
}