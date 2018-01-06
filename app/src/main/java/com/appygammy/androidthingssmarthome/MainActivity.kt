package com.appygammy.androidthingssmarthome

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Switch
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var state = true

    private var mRef = FirebaseDatabase.getInstance().reference

    private lateinit var switch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        switch = findViewById(R.id.switch_1)
        switch.setOnClickListener(this)

        setLedValue(state)

    }

    private fun setLedValue(state: Boolean) {
        mRef.child("led").setValue(state).addOnCompleteListener { p0 -> Log.e("TANAY", p0.isSuccessful.toString()) }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.switch_1 -> {
                state = !state
                setLedValue(state)
            }
        }
    }
}
