package com.appygammy.smarthomethings

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.PeripheralManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.IOException


class MainActivity : Activity() {


    private var mLedGPIO: Gpio? = null

    private var mRef = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initGPIO()

        initFirebase()
    }

    private fun initFirebase() {
        val ref = mRef.child("led")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.e("TANAY", error.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    mLedGPIO?.value = snapshot.value as Boolean
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

        })
    }

    private fun initGPIO() {
        val service = PeripheralManager.getInstance()
        try {
            mLedGPIO = service.openGpio(LED)
            mLedGPIO?.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        try {
            mLedGPIO?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            mLedGPIO = null
        }

    }

    companion object {
        private const val LED = "BCM6"
    }
}
