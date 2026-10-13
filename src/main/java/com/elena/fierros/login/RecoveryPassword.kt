package com.elena.fierros.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class RecoveryPassword : AppCompatActivity() {
    lateinit var btnCancelar: Button
    lateinit var btnRecuperar: Button
    lateinit var campoCorreo: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.recovery_password)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnCancelar = findViewById<Button>(R.id.btn_regresar)
        btnRecuperar = findViewById<Button>(R.id.btn_recovery)
        campoCorreo = findViewById<TextInputEditText>(R.id.campo_correo_recuperar)

        btnCancelar.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        btnRecuperar.setOnClickListener {
            val correo = campoCorreo.text.toString().trim()

            if (correo.isEmpty()) {
                campoCorreo.error = "Campo obligatorio"
            } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                campoCorreo.error = "Correo no válido"
            } else {
                Toast.makeText(this, "Enlace de recuperación enviado", Toast.LENGTH_LONG).show()
            }
        }

    }

}