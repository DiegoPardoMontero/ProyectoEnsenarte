package com.puj.proyectoensenarte

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.puj.proyectoensenarte.databinding.ActivityCrearCuentaBinding
import com.puj.proyectoensenarte.onboarding.SliderActivity
import java.text.DateFormat

class CrearCuentaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrearCuentaBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearCuentaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.textoIniciarSesion.setOnClickListener{
            val intent = Intent(this, IniciarSesionActivity::class.java)
            startActivity(intent)
        }
        binding.botonCrearCuenta.setOnClickListener{
            var name : String = binding.textFieldNombre.editText?.text.toString()
            var email : String = binding.textFieldEmail.editText?.text.toString()
            var password : String = binding.textFieldPassword.editText?.text.toString()
            var nickname : String = binding.textFieldUsuario.editText?.text.toString()
            // Validación de los campos antes de crear la cuenta@
            if (validateInput(name, email, password, nickname)) {
                createAccount(name, email, password, nickname)
            }
        }
    }

    private fun validateInput(name: String, email: String, password: String, nickname: String): Boolean {
        if (name.isBlank()) {
            Toast.makeText(this, "Por favor ingresa tu nombre", Toast.LENGTH_SHORT).show()
            return false
        }
        if (email.isBlank()) {
            Toast.makeText(this, "Por favor ingresa tu correo electrónico", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Por favor ingresa un correo válido", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.isBlank() || password.length < 6) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
            return false
        }
        if (nickname.isBlank()) {
            Toast.makeText(this, "Por favor ingresa tu nombre de usuario", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun createAccount(name: String, email: String, password: String, nickname: String) {
        val photoUrl = "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/fotosPerfil%2Favatar_capybara.png?alt=media&token=f9d083f1-1646-4f5e-81b0-30f7a4c678b0"
        val streakDays: Number = 0
        val xpPoints: Number = 0
        val lastExerciseDate: Timestamp? = null
        val num_practice_lessons= 0
        val num_lessons_andina = 0
        val num_lessons_caribe = 0
        val num_lessons_amazonas = 0
        val num_perfect_lessons = 0

        // Marcas de lección completada sin errores, inicializadas en false
        val lessonCompletionMarks = mapOf(
            "lesson1_completedWithoutErrors" to false,
            "lesson2_completedWithoutErrors" to false,
            "lesson3_completedWithoutErrors" to false,
            "lesson4_completedWithoutErrors" to false,
            "lesson5_completedWithoutErrors" to false,
            "lesson6_completedWithoutErrors" to false,
            "lesson7_completedWithoutErrors" to false,
            "lesson8_completedWithoutErrors" to false,
            "lesson9_completedWithoutErrors" to false
        )

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        val uid = user.uid

                        // Crear datos principales del usuario, incluyendo las marcas de lecciones
                        val userMap = hashMapOf(
                            "email" to email,
                            "uid" to uid,
                            "name" to name,
                            "nickname" to nickname,
                            "photo_url" to photoUrl,
                            "streakDays" to streakDays,
                            "xpPoints" to xpPoints,
                            "lastExerciseDate" to lastExerciseDate,
                            "num_practice_lessons" to num_practice_lessons,
                            "num_lessons_andina" to num_lessons_andina,
                            "num_lessons_caribe" to num_lessons_caribe,
                            "num_lessons_amazonas" to num_lessons_amazonas,
                            "num_perfect_lessons" to num_perfect_lessons
                        ) + lessonCompletionMarks // Añadir las marcas de lección al mapa@

                        // Guardar datos del usuario en Firestore
                        db.collection("users").document(uid)
                            .set(userMap)
                            .addOnSuccessListener {
                                Log.d(TAG, "Datos del usuario guardados correctamente")
                                // Crear colección de insignias para el usuario
                                createInsigniaCollection(uid)

                                Toast.makeText(baseContext, "Registrado exitosamente!", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, BottomNavigationActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error al guardar los datos del usuario", e)
                            }
                    }
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "No se pudo registrar: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
    // Función para crear la colección de insignias en Firestore@
    private fun createInsigniaCollection(uid: String) {
        val db = FirebaseFirestore.getInstance()

        // Definir las insignias iniciales con sus estados y URLs desactivados
        val insignias = listOf(
            mapOf(
                "name" to "Insignia de la sabiduría andina",
                "description" to "Otorgada al completar todas las lecciones del nivel andino.",
                "status" to "inactivo",
                "url" to "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/insignias%2Fimg_insig_andine.png?alt=media&token=792abccd-4eab-4c1d-8ec9-1390c6865214",
                "url_deactivated" to "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/insigniasDesactivadas%2Fimg_insig_andine_deac.png?alt=media&token=e51e5f7f-a5a9-4453-bb96-3db3cd653aee"
            ),
            mapOf(
                "name" to "Insignia del navegante caribeño",
                "description" to "Otorgada al completar todas las lecciones del nivel caribeño.",
                "status" to "inactivo",
                "url" to "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/insignias%2Fimg_insig_caribbean.png?alt=media&token=1ad83cd5-a17a-4e61-8ac8-cb23b0305a50",
                "url_deactivated" to "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/insigniasDesactivadas%2Fimg_insig_caribbean_deac.png?alt=media&token=e3ba4d88-acb1-4f61-848c-578c0ebf2641"
            ),
            mapOf(
                "name" to "Insignia del explorador amazónico",
                "description" to "Otorgada al completar todas las lecciones del nivel amazónico",
                "status" to "inactivo",
                "url" to "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/insignias%2Fimg_insig_amazonas.png?alt=media&token=d8bd09d9-b91d-4c25-af4d-348ad308845c",
                "url_deactivated" to "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/insigniasDesactivadas%2Fimg_insig_amazonas_deac.png?alt=media&token=4ef0462a-61ff-4185-8c16-4f4060678fc1"
            ),
            mapOf(
                "name" to "Insignia coleccionador de señas",
                "description" to "Otorgada al aprender 30 señas.   Haz aprendido más de 30 señas. !Tu colección de conocimientos crece cada día más!",
                "status" to "inactivo",
                "url" to "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/insignias%2Fimg_insig_treasure.png?alt=media&token=1fba74b8-bae5-4938-9918-a027d66235d8",
                "url_deactivated" to "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/insigniasDesactivadas%2Fimg_insig_treasure_deac.png?alt=media&token=31b9123d-349b-42f1-8a9f-91693dae997a"
            ),
            mapOf(
                "name" to "Insignia Lección Perfecta",
                "description" to "Haz completado una lección con una puntuación perfecta. !Excelente trabajo",
                "status" to "inactivo",
                "url" to "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/insignias%2Fimg_insig_perfect.png?alt=media&token=5bdd3ddc-0640-487e-a7ba-3d969cb47234",
                "url_deactivated" to "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/insigniasDesactivadas%2Fimg_insig_perfect_deac.png?alt=media&token=f6463644-c21f-4b75-bd4b-5647c75d3551"
            ),
            mapOf(
                "name" to "Insignia de Excelencia",
                "description" to "Haz completado 5 lecciones con una puntuación perfecta.  !Eres un verdadero experto en señas!",
                "status" to "inactivo",
                "url" to "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/insignias%2Fimg_insig_excelence.png?alt=media&token=286cb65a-fafa-43a9-8f2c-00e0fbe1d6b3",
                "url_deactivated" to "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/insigniasDesactivadas%2Fimg_insig_excelence_deac.png?alt=media&token=ee4e4b82-fdb8-4769-90b0-f669b84dcd12"
            ),
            mapOf(
                "name" to "Insignia de Experiencia",
                "description" to "Haz alcanzado 50 puntos de experiencia. !Tu esfuerzo está dando grandes frutos",
                "status" to "inactivo",
                "url" to "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/insignias%2Fimg_insig_50xp.png?alt=media&token=b8752f66-963d-411d-a1b0-fad87e48292f",
                "url_deactivated" to "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/insigniasDesactivadas%2Fimg_insig_50xp_deac.png?alt=media&token=d44daa64-dc13-4f1d-a257-f7bc3d7b2e54"
            ),
            mapOf(
                "name" to "Insignia de Constancia",
                "description" to "Haz iniciado sesión y aprendido señas durante 7 días consecutivos. !La constancia es la clave",
                "status" to "inactivo",
                "url" to "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/insignias%2Fimg_insig_sevendays.png?alt=media&token=b23c1646-b28c-4269-9ab3-d8cda810617c",
                "url_deactivated" to "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/insigniasDesactivadas%2Fimg_insig_sevendays_deac.png?alt=media&token=8ee64c0e-b266-4f41-860c-d11e2b0b8751"
            ),
            mapOf(
                "name" to "Insignia de Aprendiz Rápido",
                "description" to "Haz completado tu primera lección en menos de 5 minutos. !Tu rapidez es impresionante",
                "status" to "inactivo",
                "url" to "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/insignias%2Fimg_insig_liebre.png?alt=media&token=bac31902-14a1-4e91-b1fa-abe801380f70",
                "url_deactivated" to "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/insigniasDesactivadas%2Fimg_insig_liebre_deac.png?alt=media&token=3340a861-43f2-424d-b227-fa595732b4e6"
            ),
            mapOf(
                "name" to "Insignia de explorador dedicado",
                "description" to "Haz completado todas las lecciones de un nivel en un solo día. !Tu dedicación es admirable!",
                "status" to "inactivo",
                "url" to "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/insignias%2Fimg_insig_explorer.png?alt=media&token=75c64d11-e732-4974-a468-cf9c82b86c72",
                "url_deactivated" to "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/insigniasDesactivadas%2Fimg_insig_explorer_deac.png?alt=media&token=b5867d26-5a2f-42c5-91a7-90d1629d7654"
            ),
            mapOf(
                "name" to "Insignia de revisión",
                "description" to "Haz completado tres revisiones de lecciones. !La práctica constante te lleva a la perfección!",
                "status" to "inactivo",
                "url" to "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/insignias%2Fimg_insig_revision.png?alt=media&token=ee9cb7d7-0a58-4a18-908c-86c6337b30b1",
                "url_deactivated" to "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/insigniasDesactivadas%2Fimg_insig_revision_deac.png?alt=media&token=bb87feb0-9701-456f-8365-3d0976439ef8"
            ),
        )

        // Agregar cada insignia como un documento en la subcolección "insignias"
        insignias.forEach { insignia ->
            val insigniaRef = db.collection("users").document(uid).collection("insignias").document()

            insigniaRef.set(insignia)
                .addOnSuccessListener {
                    Log.d(TAG, "Insignia '${insignia["name"]}' agregada a Firestore para el usuario $uid")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error al agregar insignia '${insignia["name"]}'", e)
                }
        }
    }
}
