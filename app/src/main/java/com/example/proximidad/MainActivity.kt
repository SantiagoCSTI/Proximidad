package com.example.proximidad

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    var TAG:String="Sensores Proximidad"
   lateinit var proximitySensorListener: SensorEventListener
    lateinit var sensorManager: SensorManager
    lateinit var proximitySensor: Sensor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        if(proximitySensor == null) {
            Log.e(TAG, "Proximity sensor not available.");
            finish(); // Close app
        }

        proximitySensorListener = object : SensorEventListener {
            override fun onSensorChanged(sensorEvent: SensorEvent) {
                // More code goes here
                if(sensorEvent.values[0] < proximitySensor.getMaximumRange()) {
                    // Detected something nearby
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                } else {
                    // Nothing is nearby
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                }
           }
            override fun onAccuracyChanged(sensor: Sensor, i: Int) {}
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(proximitySensorListener,
                proximitySensor, 2 * 1000 * 1000)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(proximitySensorListener);
    }

}