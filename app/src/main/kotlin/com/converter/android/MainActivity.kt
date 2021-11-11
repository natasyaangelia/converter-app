package com.converter.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.converter.core.utils.Utility

class MainActivity : AppCompatActivity(), MainView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun hideKeyboard() {
        Utility.hideKeyboard(this)
    }
}
