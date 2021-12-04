package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth
        val signup: Button = findViewById(R.id.botonRegistrarse)
        signup.setOnClickListener {
            val usuario: EditText = findViewById(R.id.correoUsuario)
            val contraseña: EditText = findViewById(R.id.contraseñaUsuario)
            crearCuenta(usuario.text.toString(), contraseña.text.toString())
        }
        val signin: Button = findViewById(R.id.botonIniciarsesion)
        signin.setOnClickListener {
            val usuario: EditText = findViewById(R.id.correoUsuario)
            val contraseña: EditText = findViewById(R.id.contraseñaUsuario)
            accederCuenta(usuario.text.toString(), contraseña.text.toString())
        }
    }

    // función que comprueba si el usuario hizo sign in
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload();
        }
    }

    // función para crear cuenta
    private fun crearCuenta(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.i("Correcto", "Usuario creado correctamente")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.i("Incorrecto", "Usuario no creado", task.exception)
                    Toast.makeText(
                        baseContext, "Error en la autenticación",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    // función para acceder a la cuenta
    private fun accederCuenta(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("Correcto", "Acceso correcto")
                    val user = auth.currentUser
                    updateUI(user)
                    Toast.makeText(
                        baseContext, "Correcto",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Log.w("Incorrecto", "Acceso fallido", task.exception)
                    Toast.makeText(
                        baseContext, "Error en la autenticación",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {

    }

    private fun reload() {

    }
}