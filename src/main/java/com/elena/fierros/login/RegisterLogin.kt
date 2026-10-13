package com.elena.fierros.login

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.Calendar

class RegisterLogin : AppCompatActivity() {
    private var fechaNacimiento: Calendar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_login)

        val campoNombre = findViewById<TextInputEditText>(R.id.campo_nombre)
        val campoCorreo = findViewById<TextInputEditText>(R.id.campo_correo)
        val campoContra = findViewById<TextInputEditText>(R.id.campo_contra)
        val campoConfirm = findViewById<TextInputEditText>(R.id.campo_confirm)
        val campoNacimiento = findViewById<TextInputEditText>(R.id.campo_nacimiento)
        val layoutNacimiento = findViewById<TextInputLayout>(R.id.layout_nacimiento)
        val btnRegistrar = findViewById<MaterialButton>(R.id.btn_registrar)
        val btnRegresar = findViewById<TextView>(R.id.btn_regresar)

        val abrirCalendario = {
            val hoy = Calendar.getInstance()
            val dialog = DatePickerDialog(
                this,
                { _, year, month, day ->
                    fechaNacimiento = Calendar.getInstance().apply {
                        set(year, month, day)
                    }
                    campoNacimiento.setText(String.format("%02d/%02d/%d", day, month + 1, year))
                    campoNacimiento.error = null
                },
                hoy.get(Calendar.YEAR) - 18,
                hoy.get(Calendar.MONTH),
                hoy.get(Calendar.DAY_OF_MONTH)
            )
            dialog.datePicker.maxDate = System.currentTimeMillis()
            dialog.show()
        }
        campoNacimiento.setOnClickListener { abrirCalendario() }
        layoutNacimiento.setEndIconOnClickListener { abrirCalendario() }


        btnRegistrar.setOnClickListener {
            val nombre = campoNombre.text.toString().trim()
            val correo = campoCorreo.text.toString().trim()
            val contra = campoContra.text.toString()
            val confirm = campoConfirm.text.toString()
            val nacimiento = campoNacimiento.text.toString()

            if (nombre.isEmpty() || correo.isEmpty() || contra.isEmpty() ||
                confirm.isEmpty() || nacimiento.isEmpty()
            ) {
                if (nombre.isEmpty()) campoNombre.error = "Campo obligatorio"
                if (correo.isEmpty()) campoCorreo.error = "Campo obligatorio"
                if (contra.isEmpty()) campoContra.error = "Campo obligatorio"
                if (confirm.isEmpty()) campoConfirm.error = "Campo obligatorio"
                if (nacimiento.isEmpty()) campoNacimiento.error = "Campo obligatorio"
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                campoCorreo.error = "Correo no válido"
                Toast.makeText(this, "Escribe un correo válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (contra != confirm) {
                campoConfirm.error = "Las contraseñas no coinciden"
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val edad = calcularEdad(fechaNacimiento!!)
            if (edad < 18) {
                campoNacimiento.error = "Debes tener 18 años o más"
                Toast.makeText(this, "Debes ser mayor de 18 años (tienes $edad)", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "¡Registro exitoso!", Toast.LENGTH_LONG).show()

            startActivity(Intent(this, RegisterSuccess::class.java))
            finish()
        }

        btnRegresar.setOnClickListener { finish() }
    }

    private fun calcularEdad(nacimiento: Calendar): Int {
        val hoy = Calendar.getInstance()
        var edad = hoy.get(Calendar.YEAR) - nacimiento.get(Calendar.YEAR)
        if (hoy.get(Calendar.DAY_OF_YEAR) < nacimiento.get(Calendar.DAY_OF_YEAR)) {
            edad--
        }
        return edad
    }
}