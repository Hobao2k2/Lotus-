package com.example.lotus.utils

import com.example.lotus.data.model.User
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class ConvertPost : JsonDeserializer<User?> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): User? {
        return if (json.isJsonPrimitive) {
            User(json.asString, "", "", "", "", null, null, null, null, null, null, null, emptyList(), emptyList(), emptyList(), "")
        } else if (json.isJsonObject) {
            // Nếu user là đối tượng, parse nó như bình thường
            context?.deserialize(json, User::class.java)
        } else {
            null
        }
    }
}
