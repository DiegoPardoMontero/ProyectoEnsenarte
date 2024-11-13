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

public class CrearLeccion9 extends AppCompatActivity {

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

        // Ejercicio 1
        Map<String, Object> exercise1 = new HashMap<>();
        exercise1.put("statement", "Observa los siguientes videos. ¿Cuál corresponde a la acción de brindar apoyo o asistencia a alguien?");
        exercise1.put("exerciseType", "video_selection");
        exercise1.put("points", 5);
        exercise1.put("correctAnswer", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Ayudar/ayudarSena.m4v");
        exercise1.put("videos", Arrays.asList("https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Cuidar/cuidarSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Papá/01-Papa-Palabra-1.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Ayudar/ayudarSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Mama/mamaSena.m4v"));

        // Ejercicio 2
        Map<String, Object> exercise2 = new HashMap<>();
        exercise2.put("statement", "Observa los siguientes videos. ¿Cuál muestra el término que se usa para un periodo de siete días?");
        exercise2.put("exerciseType", "video_selection");
        exercise2.put("points", 5);
        exercise2.put("correctAnswer", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Semana/semanaSena.m4v");
        exercise2.put("videos", Arrays.asList("https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Numero/numeroSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Celular/celularSena.mp4",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Semana/semanaSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Ayudar/ayudarSena.m4v"));

        Map<String, Object> exercise3 = new HashMap<>();
        exercise3.put("statement", "Ordena las señas para deletrear la palabra 'ayuda'");
        exercise3.put("exerciseType", "ordering");
        exercise3.put("points", 10);
        exercise3.put("maxLetter", 5);
        exercise3.put("correctAnswer", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_a.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_y.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_u.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_d.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_a.png"));
        exercise3.put("videos", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_a.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_t.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_h.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_y.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_f.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_u.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_d.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_b.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_a.png"));

        // Ejercicio 4
        Map<String, Object> pair1 = new HashMap<>();
        pair1.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Celular/celularSena.mp4");
        pair1.put("word", "Celular");

        Map<String, Object> pair2 = new HashMap<>();
        pair2.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Chaqueta/chaquetaSena.mp4");
        pair2.put("word", "Chaqueta");

        Map<String, Object> pair3 = new HashMap<>();
        pair3.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Computador/computadorSena.mp4");
        pair3.put("word", "Computador");

        Map<String, Object> pair4 = new HashMap<>();
        pair4.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Cama/camaSena.mp4");
        pair4.put("word", "Cama");

        Map<String, Object> exercise4 = new HashMap<>();
        exercise4.put("correctPairs", Arrays.asList(pair1, pair2, pair3, pair4));
        exercise4.put("exerciseType", "matching_videos");
        exercise4.put("points", 5);
        exercise4.put("statement", "Relaciona cada video con el objeto que representa.");

        // Ejercicio 5
        Map<String, Object> exercise5 = new HashMap<>();
        exercise5.put("statement", "Observa los siguientes videos. ¿Cuál representa una dirección que indica hacia abajo en un mapa?");
        exercise5.put("exerciseType", "video_selection");
        exercise5.put("points", 5);
        exercise5.put("correctAnswer", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Sur/surSena.m4v");
        exercise5.put("videos", Arrays.asList("https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Sur/surSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Norte/norteSena.mp4",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Mucho/muchoSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Lejos/lejosSena.m4v"));

        Map<String, Object> exercise6 = new HashMap<>();
        exercise6.put("statement", "Ordena las señas para deletrear la palabra 'hola'");
        exercise6.put("exerciseType", "ordering");
        exercise6.put("points", 10);
        exercise6.put("maxLetter", 4);
        exercise6.put("correctAnswer", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_h.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_o.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_l.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_a.png"));
        exercise6.put("videos", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_h.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_a.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_o.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_e.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_l.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_x.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_g.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_a.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_g.png"));

        // Añadir los ejercicios a la lección

        // Ejercicio 7
        Map<String, Object> exercise7 = new HashMap<>();
        exercise7.put("statement", "Observa el video. ¿Qué medio de transporte se muestra en el video?");
        exercise7.put("exerciseType", "selection");
        exercise7.put("points", 5);
        exercise7.put("hint", "Transmilenio");
        exercise7.put("video_url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Transmilenio/transmilenioSena.mp4");
        exercise7.put("correctAnswer", "Transmilenio");
        exercise7.put("words", Arrays.asList("Ambulancia", "Transmilenio", "Colectivo", "Carro"));

        // Ejercicio 8
        Map<String, Object> foodPair1 = new HashMap<>();
        foodPair1.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Sopa/sopaSena.mp4");
        foodPair1.put("word", "Sopa");

        Map<String, Object> foodPair2 = new HashMap<>();
        foodPair2.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Aguacate/aguacateSena.m4v");
        foodPair2.put("word", "Aguacate");

        Map<String, Object> foodPair3 = new HashMap<>();
        foodPair3.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Sal/salSena.mp4");
        foodPair3.put("word", "Sal");

        Map<String, Object> foodPair4 = new HashMap<>();
        foodPair4.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Cama/camaSena.mp4");
        foodPair4.put("word", "Cama");

        Map<String, Object> exercise8 = new HashMap<>();
        exercise8.put("correctPairs", Arrays.asList(foodPair1, foodPair2, foodPair3, foodPair4));
        exercise8.put("exerciseType", "matching_videos");
        exercise8.put("points", 5);
        exercise8.put("statement", "Relaciona cada video con el alimento que representa.");

        // Ejercicio 9
        Map<String, Object> exercise9 = new HashMap<>();
        exercise9.put("statement", "Observa el video. ¿Cuál es el término que se usa para un hijo varón?");
        exercise9.put("exerciseType", "selection");
        exercise9.put("points", 5);
        exercise9.put("hint", "Hijo");
        exercise9.put("video_url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Hijo/hijoSena.m4v");
        exercise9.put("correctAnswer", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Hijo/hijoSena.m4v");
        exercise9.put("words", Arrays.asList("Hijo", "Papá", "Chaqueta", "Norte"));

        // Ejercicio 10
        Map<String, Object> exercise10 = new HashMap<>();
        exercise10.put("statement", "Observa los siguientes videos. ¿Cuál corresponde a una palabra que indica malestar físico asociado con falta de equilibrio?");
        exercise10.put("exerciseType", "video_selection");
        exercise10.put("points", 5);
        exercise10.put("correctAnswer", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Mareo/mareoSena.m4v");
        exercise10.put("videos", Arrays.asList("https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Ambulancia/ambulanciaSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Tos/tosSena.m4v", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Mareo/mareoSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Gripa/gripaSena.m4v"));

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
        lesson.put("title", "Lección General: Todos los temas");
        lesson.put("exercises", exercises);

        // Guardar la lección en Firestore
        db.collection("lessons").document("lesson9")
                .set(lesson)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firestore", "Lección general añadida con éxito!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firestore", "Error añadiendo la lección general", e);
                    }
                });
    }
}