package com.khan.journalapplication.data

import com.khan.journalapplication.model.Journal

class JournalDataSource{
    fun loadJournal():List<Journal>{
        return listOf(
            Journal(title = "Hello", content = "The sun dipped slowly behind the hills, paintin" +
                    "g the sky in shades of orange and pink. A gentle breeze rustled through the " +
                    "tall grass, carrying the scent of wildflowers. Birds chirped their final songs of " +
                    "the day as the world grew quieter. The golden light touched everything it could re" +
                    "ach, turning even the smallest pebble into something beautiful. I" +
                    "t was a moment of calm, the kind that made you forget time for a while.", mood = "Happy"),
            Journal(title = "Hello", content = "Hello World", mood = "Happy"),
            Journal(title = "Hello", content = "Hello World", mood = "Happy"),
            Journal(title = "Hello", content = "Hello World", mood = "Happy"),
            Journal(title = "Hello", content = "Hello World", mood = "Happy"),
            Journal(title = "Hello", content = "Hello World", mood = "Happy"),
            Journal(title = "Hello", content = "Hello World", mood = "Happy"),
            Journal(title = "Hello", content = "Hello World", mood = "Happy"),
            Journal(title = "Hello", content = "Hello World", mood = "Happy"),
            Journal(title = "Hello", content = "Hello World", mood = "Happy")

        )
    }
}