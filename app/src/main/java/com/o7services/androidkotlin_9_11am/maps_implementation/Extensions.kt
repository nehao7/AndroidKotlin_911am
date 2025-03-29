package com.o7services.androidkotlin_9_11am.maps_implementation

fun String?.appendIfNotBlank(s: String) = if (this != null && isNotBlank()) "$this$s" else ""

