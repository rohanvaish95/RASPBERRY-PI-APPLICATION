package com.example.newapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.io.OutputStream
import java.net.Socket

class MainActivity : AppCompatActivity() {

    private lateinit var socket: Socket


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connectToServer()

        findViewById<Button>(R.id.btnOn).setOnClickListener {
            sendCommand("ON")
        }

        findViewById<Button>(R.id.btnOff).setOnClickListener {
            sendCommand("OFF")
        }
    }
    private lateinit var outputStream: OutputStream
    private fun connectToServer() {
        Thread {
            try {
                socket = Socket("192.168.137.16",5555)
                outputStream = socket.getOutputStream()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun sendCommand(command: String) {
        Thread {
            try {
                outputStream.write(command.toByteArray())
                val response = socket.getInputStream().bufferedReader().readLine()
                println("Response from server: $response")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }
}
