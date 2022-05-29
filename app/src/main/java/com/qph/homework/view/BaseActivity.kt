package com.qph.homework.view

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.qph.homework.manager.ActivityCollecteor

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCollecteor.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollecteor.removeActivity(this)
    }
}