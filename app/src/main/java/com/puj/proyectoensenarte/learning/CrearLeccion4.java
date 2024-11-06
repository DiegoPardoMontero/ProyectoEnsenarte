package com.puj.proyectoensenarte.learning;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.puj.proyectoensenarte.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CrearLeccion4 extends AppCompatActivity {

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_documentos);

        // Inicializar Firestore
        db = FirebaseFirestore.getInstance();

        // Crear lección y ejercicios
        crearLeccion();
    }

    private void crearLeccion() {
        // Ejercicio 1: Selección de video@
        Map<String, Object> exercise1 = new HashMap<>();
        exercise1.put("statement", "¿Cuál de estos videos muestra la seña de “Chaqueta”?");
        exercise1.put("exerciseType", "video_selection");
        exercise1.put("points", 5);
        exercise1.put("correctAnswer", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Chaqueta/chaquetaSena.mp4");
        exercise1.put("videos", Arrays.asList("https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Sudadera/sudaderaSena.m4v", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Cachucha/cachucaSena.mp4", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Blusa/blusaSena.mp4","https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Chaqueta/chaquetaSena.mp4"));

        // Ejercicio 2: Selección de video
        Map<String, Object> exercise2 = new HashMap<>();
        exercise2.put("statement", " Mira el video y selecciona la seña correcta:");
        exercise2.put("exerciseType", "selection");
        exercise2.put("points", 5);
        exercise2.put("hint", "Computador");
        exercise2.put("video_url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Computador/computadorSena.mp4");
        exercise2.put("correctAnswer", "Computador");
        exercise2.put("words", Arrays.asList("Computador", "Televisor", "Fax", "Ascensor"));


        // Ejercicio 3: Ordenar letras

        Map<String, Object> exercise3 = new HashMap<>();
        exercise3.put("statement", "¿Cuál de estos videos muestra la seña de “Suegro”?");
        exercise3.put("exerciseType", "video_selection");
        exercise3.put("points", 5);
        exercise3.put("correctAnswer", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Suegro/suegroSena.m4v");
        exercise3.put("videos", Arrays.asList("https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Nuera/nueraSena.m4v","https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Suegro/suegroSena.m4v","https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Padres/padresSena.m4v","https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Hermano/hermanoSena.m4v"));


        // Ejercicio 4: Emparejamiento de palabra con imagen
        Map<String, Object> pair1 = new HashMap<>();
        pair1.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Aspiradora/aspiradoraSena.mp4");
        pair1.put("word", "Aspiradora");

        Map<String, Object> pair2 = new HashMap<>();
        pair2.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Celular/celularSena.mp4");
        pair2.put("word", "Celular");

        Map<String, Object> pair3 = new HashMap<>();
        pair3.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Fax/faxSena.mp4");
        pair3.put("word", "Fax");

        Map<String, Object> pair4 = new HashMap<>();
        pair4.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Television/televisionSena.mp4");
        pair4.put("word", "Televisión");


        Map<String, Object> exercise4 = new HashMap<>();
        exercise4.put("correctPairs", Arrays.asList(pair1, pair2, pair3, pair4));
        exercise4.put("exerciseType", "matching_videos");
        exercise4.put("points", 5);
        exercise4.put("statement", "Empareja el objeto con la acción correcta. (haz click dos veces en el video)" );

        // Ejercicio 5: Selección de palabra

        Map<String, Object> exercise5 = new HashMap<>();
        exercise5.put("statement", "Ordena las señas para deletrear la palabra 'Blusa'");
        exercise5.put("exerciseType", "ordering");
        exercise5.put("points", 10);
        exercise5.put("maxLetter", 5);
        exercise5.put("correctAnswer", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_b.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_l.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_u.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_s.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_a.png"));
        exercise5.put("videos", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_b.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_z.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_j.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_r.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_m.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_l.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_s.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_u.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_a.png"));

        // Ejercicio 6: Selección de palabra

        Map<String, Object> pair11 = new HashMap<>();
        pair11.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Hermano/hermanoSena.m4v");
        pair11.put("word", "Hermano");

        Map<String, Object> pair22 = new HashMap<>();
        pair22.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Hijo/hijoSena.m4v");
        pair22.put("word", "Hijo");

        Map<String, Object> pair33 = new HashMap<>();
        pair33.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Papá/01-Papa-Palabra-1.m4v");
        pair33.put("word", "Papá");

        Map<String, Object> pair44 = new HashMap<>();
        pair44.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Mama/mamaSena.m4v");
        pair44.put("word", "Mamá");


        Map<String, Object> exercise6 = new HashMap<>();
        exercise6.put("correctPairs", Arrays.asList(pair11, pair22, pair33, pair44));
        exercise6.put("exerciseType", "matching_videos");
        exercise6.put("points", 5);
        exercise6.put("statement", "Empareja el objeto con la acción correcta. (haz click dos veces en el video)" );

        // Ejercicio 7: Selección de palabra
        Map<String, Object> exercise7 = new HashMap<>();
        exercise7.put("statement", "Yo le pedí a Juan de que me hiciera el favor de _________ mi chaqueta porque no tenía mi maleta para guardarla");
        exercise7.put("exerciseType", "video_selection");
        exercise7.put("points", 5);
        exercise7.put("correctAnswer", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Llevar/llevarSena.m4v");
        exercise7.put("videos", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Camara/camarafotografiaSena.mp4",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Suegro/suegroSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Llevar/llevarSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Zapato/zapatoSena.m4v"));

        // Ejercicio 8: Ordenar letras
        Map<String, Object> exercise8 = new HashMap<>();
        exercise8.put("statement", " Mira el video y selecciona la seña correcta. Maria ayudó a su papá a _______ su chaqueta porque él no sabía donde estaba");
        exercise8.put("exerciseType", "selection");
        exercise8.put("points", 5);
        exercise8.put("hint", "Buscar");
        exercise8.put("video_url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Buscar/buscarSena.m4v");
        exercise8.put("correctAnswer", "Buscar");
        exercise8.put("words", Arrays.asList("Ponerse", "Buscar", "Traer", "Quitarse"));

        //Ejercicio 9:

        Map<String, Object> exercise9 = new HashMap<>();
        exercise9.put("statement", "Ordena las señas para deletrear la palabra 'Hermano'");
        exercise9.put("exerciseType", "ordering");
        exercise9.put("points", 10);
        exercise9.put("maxLetter", 7);
        exercise9.put("correctAnswer", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_h.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_e.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_r.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_m.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_a.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_n.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_0.png"));
        exercise9.put("videos", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_e.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_h.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_j.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_r.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_m.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_l.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_o.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_n.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_a.png"));

        // Ejercicio 10: Emparejamiento de palabra con imagen
        Map<String, Object> exercise10 = new HashMap<>();
        exercise10.put("statement", "Completa la frase: 'Desde la creación del _________, la forma en que nos comunicamos, trabajamos y accedemos a la información ha cambiado drásticamente'");
        exercise10.put("exerciseType", "video_selection");
        exercise10.put("points", 5);
        exercise10.put("correctAnswer", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Internet/internetSena.mp4");
        exercise10.put("videos", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Falda/faldaSena.mp4",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Camiseta/camisetaSena.mp4",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Fax/faxSena.mp4",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Internet/internetSena.mp4"));

        // Añadir los ejercicios a la lección
        Map<String, Object> exercises = new HashMap<>();
        exercises.put("exercise1", exercise1);
        exercises.put("exercise2", exercise2);
        exercises.put("exercise3", exercise3);
        exercises.put("exercise4", exercise4);
        exercises.put("exercise5", exercise5);
        exercises.put("exercise6", exercise6);
        exercises.put("exercise7", exercise7);
        exercises.put("exercise8", exercise8);
        exercises.put("exercise9", exercise9);
        exercises.put("exercise10", exercise10);

        // Crear el documento de la lección
        Map<String, Object> lesson = new HashMap<>();
        lesson.put("title", "Lección 4: Vestuario, Relaciones Personales y Tecnología");
        lesson.put("exercises", exercises);

        // Guardar la lección en Firestore
        db.collection("lessons").document("lesson4")
                .set(lesson)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firestore", "Lección 4 añadida con éxito!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firestore", "Error añadiendo la lección 4", e);
                    }
                });
    }
}