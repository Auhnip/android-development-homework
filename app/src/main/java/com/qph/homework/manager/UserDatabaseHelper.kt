package com.qph.homework.manager

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.qph.homework.utils.ConstantsValue

class UserDatabaseHelper(private val context: Context, name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version) {

    private val user1 = arrayOf("计算机1903", "20194635", "20194635", "15303046547", "韶关")
    private val user2 = arrayOf("软件2002", "20200000", "20200000", "13200000000", "南充")

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.apply {
            execSQL(ConstantsValue.createUserSqliteSentence)
            execSQL(ConstantsValue.createWaybillSqliteSentence)
            execSQL(ConstantsValue.insertUserSentence, user1)
            execSQL(ConstantsValue.insertUserSentence, user2)
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

}