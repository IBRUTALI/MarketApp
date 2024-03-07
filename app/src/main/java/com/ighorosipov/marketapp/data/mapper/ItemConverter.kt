package com.ighorosipov.marketapp.data.mapper

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ighorosipov.marketapp.data.model.Feedback
import com.ighorosipov.marketapp.data.model.Info
import com.ighorosipov.marketapp.data.model.Price
import java.lang.reflect.Type

class ItemConverter {

    @TypeConverter
    fun feedbackToString(feedback: Feedback?): String? {
        if (feedback == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<Feedback?>() {}.type
        return gson.toJson(feedback, type)
    }

    @TypeConverter
    fun stringToFeedback(string: String?): Feedback? {
        if (string == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<Feedback?>() {}.type
        return gson.fromJson(string, type)
    }

    @TypeConverter
    fun listOfInfoToString(list: List<Info>?): String? {
        if (list == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Info>?>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun stringToListOfInfo(string: String?): List<Info>? {
        if (string == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Info>?>() {}.type
        return gson.fromJson(string, type)
    }

    @TypeConverter
    fun priceToString(price: Price?): String? {
        if (price == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<Price?>() {}.type
        return gson.toJson(price, type)
    }

    @TypeConverter
    fun stringToPrice(string: String?): Price? {
        if (string == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<Price?>() {}.type
        return gson.fromJson(string, type)
    }

    @TypeConverter
    fun tagsToString(tags: List<String>?): String? {
        if (tags == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<String>?>() {}.type
        return gson.toJson(tags, type)
    }

    @TypeConverter
    fun stringToTags(string: String?): List<String>? {
        if (string == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<String>?>() {}.type
        return gson.fromJson(string, type)
    }

}