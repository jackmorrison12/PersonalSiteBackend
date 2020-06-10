package com.jackmorrison.personalsitebackend

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.litote.kmongo.findOneById
import org.litote.kmongo.getCollection
import org.litote.kmongo.save
import org.litote.kmongo.updateOneById

class FragmentDAO {
    private fun getCollection() =
        MongoDB.getDatabase().getCollection<APIFragment>()

    private fun Any?.toJson() = toGson(this)
    private fun <R> toGson(r: R): String =
        GsonBuilder().setPrettyPrinting().create().toJson(r)
    private fun String?.toFragment(): APIFragment {
        val type = object: TypeToken<APIFragment>(){}.type
        return GsonBuilder().create().fromJson(this, type)
    }

    fun getAll(): String = getCollection().find().into(mutableListOf<APIFragment>()).toJson()

    fun get(id: String): String = getCollection().findOneById(id).toJson()

    fun save(body: String): String = try {
        getCollection().save(body.toFragment())
        "{ \"success\": true, \"message\": null }"
    } catch (e: Exception) {
        println("request save failed: $e")
        "{ \"success\": false, \"message\": $e }"
    }

    fun update(id: String, body: String): String = try {
        getCollection().updateOneById(id, body.toFragment())
        get(id)
    } catch (e: Exception) {
        println("request update failed: $e")
        "{ \"success\": false, \"message\": $e }"
    }
}