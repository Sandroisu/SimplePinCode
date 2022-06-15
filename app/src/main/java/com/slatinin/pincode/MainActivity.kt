package com.slatinin.pincode

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val codeFragment = CodeFragment()
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, codeFragment, "code_fragment").commit()
        }

    }

}