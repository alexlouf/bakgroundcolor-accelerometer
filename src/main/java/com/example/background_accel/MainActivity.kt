package com.example.background_accel

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.hardware.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var mLight: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mLight = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {
        var axisx = Math.round(event.values[0] * 10 * 2.55)
        var axisy = Math.round(event.values[1] * 10 * 2.55)
        var axisz = Math.round(event.values[2] * 10 * 2.55)

        if (axisx < 0) {
            axisx *= -1
        }
        if (axisy < 0) {
            axisy *= -1
        }
        if (axisz < 0) {
            axisz *= -1
        }


        textView.text = "Axe x : " + axisx.toString()
        textView2.text = "Axe y : " + axisy.toString()
        textView3.text = "Axe z : " + axisz.toString()

        val hex = String.format("#%02x%02x%02x", axisx, axisy, axisz)

        textView4.text = "Hexa : " + hex

        if (hex.length === 7) {
            background.setBackgroundColor(Color.parseColor(hex))
        }
    }

    override fun onResume() {
        super.onResume()
        mLight?.also { light ->
            sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}
