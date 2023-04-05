package com.morse.valu.utils.base

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser

object Parser {
    inline fun <reified Type> parse(input: String): Type {
        val gson = Gson()
        return gson.fromJson(input, Type::class.java)
    }

    inline fun <reified Type> toString (input : Type) : String {
        val gson = Gson()
        return gson.toJson(input, Type::class.java)
    }

    inline fun <reified Type> parseResponse(input: String): Type {
        val parser = JsonParser()
        val mJson: JsonElement = parser.parse(input)
        val gson = Gson()
        return gson.fromJson(input, Type::class.java)
    }
}