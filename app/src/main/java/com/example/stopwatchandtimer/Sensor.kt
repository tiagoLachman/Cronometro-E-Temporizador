package com.example.stopwatchandtimer

import android.content.Context
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Sensor : AppCompatActivity() {

    private lateinit var sensor1: TextView
    private lateinit var sensor2: TextView
    private lateinit var sensor3: TextView
    private var giroscopio: android.hardware.Sensor? = null
    private lateinit var sensorManager:SensorManager;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor)


        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        giroscopio = sensorManager.getDefaultSensor(android.hardware.Sensor.TYPE_ACCELEROMETER);

        sensor1 = findViewById<TextView>(R.id.sensor1);
        sensor2 = findViewById<TextView>(R.id.sensor2);
        sensor3 = findViewById<TextView>(R.id.sensor3);
    }
    private val giroscopioListener = object : SensorEventListener {
        override fun onAccuracyChanged(p0: android.hardware.Sensor?, p1: Int) {
        }

        override fun onSensorChanged(event: SensorEvent) {
            sensor1.text = event.values[0].toString()
            sensor2.text = event.values[1].toString()
            sensor3.text = event.values[2].toString()
        }
    }
    override fun onResume() {
        super.onResume()
        try {
            giroscopio?.let {
                sensorManager.registerListener(
                    giroscopioListener,
                    it,
                    SensorManager.SENSOR_DELAY_NORMAL
                )
            }
        }catch (err: Exception){
            sensor1.text = "null";
        }

    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(giroscopioListener)
    }

}