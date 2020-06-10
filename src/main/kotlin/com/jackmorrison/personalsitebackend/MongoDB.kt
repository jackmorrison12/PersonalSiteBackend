package com.jackmorrison.personalsitebackend

import com.mongodb.ConnectionString
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoDatabase
import org.litote.kmongo.KMongo

object MongoDB {

    private var DB_NAME: String? = null
    var connectionString: String? = null
    private var kmongo: MongoClient? = null
    private var kdatabase: MongoDatabase? = null

    fun getDatabase(): MongoDatabase {
        if (kdatabase == null) {
            connect()
            database()
        }
        return kdatabase!!
    }

    private fun connect() {
        try {
            if (System.getenv("MONGODB_URI") != null) {
                connectionString = System.getenv("MONGODB_URI") + "?retryWrites=false"
                DB_NAME = connectionString!!.substring(10,25)
                println(DB_NAME)
                kmongo = KMongo.createClient(connectionString!!)
            } else {
                throw error("Can't find database URI")
            }

        } catch (e: Exception) {
            kmongo?.close()
            println("MongoClient failed to connect: $e")
        }
    }

    private fun database() {
        kdatabase = kmongo?.getDatabase(DB_NAME)
    }
}