package com.elena.fierros.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var btn_ingresar: Button
    lateinit var btn_registrarse: Button
    lateinit var campo_correo: EditText
    lateinit var campo_contra: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btn_ingresar = findViewById<Button>(R.id.btn_in)
        btn_registrarse = findViewById<Button>(R.id.btn_sign)
        campo_correo = findViewById<EditText>(R.id.campo_correo)
        campo_contra = findViewById<EditText>(R.id.campo_contra)

        btn_ingresar.setOnClickListener {
            val intent = Intent(this, RegisterLogin::class.java)
            startActivity(intent)
        }

        btn_registrarse.setOnClickListener {
            var user = campo_correo.text.toString()
            var password = campo_contra.text.toString()

            if (user == "root" && password == "rootpass"){
                Toast.makeText(this, "Login Completed", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Incorrect User", Toast.LENGTH_SHORT).show()
            }

        }

    }
}