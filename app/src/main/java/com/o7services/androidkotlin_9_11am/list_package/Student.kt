package com.o7services.androidkotlin_9_11am.list_package

data class Student(
    var rollNo : Int?= 0,
    var name : String?= "",
    var subject : String?= "",
){
    override fun toString(): String {
        return "$rollNo\n$name $subject"
    }
}
