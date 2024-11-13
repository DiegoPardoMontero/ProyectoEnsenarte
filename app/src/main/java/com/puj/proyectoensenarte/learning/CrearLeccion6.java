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

public class CrearLeccion6 extends AppCompatActivity {

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
        // Ejercicio 1:
        Map<String, Object> exercise1 = new HashMap<>();
        exercise1.put("statement", "Estás organizando un evento deportivo y necesitas incluir un deporte acuático que no sea natación. " +
                "Observa los videos y selecciona el deporte que mejor se ajuste.");
        exercise1.put("exerciseType", "video_selection");
        exercise1.put("points", 5);
        exercise1.put("correctAnswer", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Buceo/buceoSena.m4v");
        exercise1.put("videos", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Futbol/futbolSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Boxeo/boxeoSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Buceo/buceoSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Patinaje/patinajeSena.m4v"));


        // Ejercicio 2: Selección de video

        Map<String, Object> exercise2 = new HashMap<>();
        exercise2.put("statement", "La fruta favorita de los monos es el ________.");
        exercise2.put("exerciseType", "ordering");
        exercise2.put("points", 10);
        exercise2.put("maxLetter", 6);
        exercise2.put("correctAnswer", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_b.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_a.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_n.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_a.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_n.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_o.png"));
        exercise2.put("videos", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_b.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_a.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_n.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_h.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_o.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_n.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_q.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_a.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_g.png"));

        // Ejercicio 3: Ordenar letras

        Map<String, Object> pair1 = new HashMap<>();
        pair1.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Baloncesto/baloncestoSena.m4v");
        pair1.put("word", "Baloncesto");

        Map<String, Object> pair2 = new HashMap<>();
        pair2.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Patinaje/patinajeSena.m4v");
        pair2.put("word", "Patinaje");

        Map<String, Object> pair3 = new HashMap<>();
        pair3.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Voleyball/voleyballSena.m4v");
        pair3.put("word", "Volleyball");

        Map<String, Object> pair4 = new HashMap<>();
        pair4.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Boxeo/boxeoSena.m4v");
        pair4.put("word", "Boxeo");


        Map<String, Object> exercise3 = new HashMap<>();
        exercise3.put("correctPairs", Arrays.asList(pair1, pair2, pair3, pair4));
        exercise3.put("exerciseType", "matching_videos");
        exercise3.put("points", 5);
        exercise3.put("statement", "Estás explicando a alguien los deportes que puedes practicar en el centro deportivo. " +
                "Observa cada video y haz coincidir el deporte con su nombre correcto en lenguaje natural." );


        // Ejercicio 4: Emparejamiento de palabra con imagen

        Map<String, Object> exercise4 = new HashMap<>();
        exercise4.put("statement", "Acabas de ver un video sobre un alimento que es ideal para hacer sándwiches, pizza y arepas. " +
                "Selecciona la opción que crees que mejor lo describe.");
        exercise4.put("exerciseType", "selection");
        exercise4.put("points", 5);
        exercise4.put("hint", "Queso");
        exercise4.put("video_url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Queso/quesoSena.m4v");
        exercise4.put("correctAnswer", "Queso");
        exercise4.put("words", Arrays.asList("Queso", "Salsa", "Pollo", "Cebolla"));



        // Ejercicio 5: Selección de palabra


        Map<String, Object> pair11 = new HashMap<>();
        pair11.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_s.png");
        pair11.put("word", "Sopa");

        Map<String, Object> pair22 = new HashMap<>();
        pair22.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_t.png");
        pair22.put("word", "Tamal");

        Map<String, Object> pair33 = new HashMap<>();
        pair33.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_y.png");
        pair33.put("word", "Yogurt");

        Map<String, Object> pair44 = new HashMap<>();
        pair44.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_a.png");
        pair44.put("word", "Aguacate");


        Map<String, Object> exercise5 = new HashMap<>();
        exercise5.put("correctPairs", Arrays.asList(pair11, pair22, pair33, pair44));
        exercise5.put("exerciseType", "matching");
        exercise5.put("points", 5);
        exercise5.put("statement", "Para recordar los alimentos que debes comprar, haz coincidir cada seña con la letra inicial de cada alimento" );


        // Ejercicio 6: Selección de palabra

        Map<String, Object> exercise6 = new HashMap<>();
        exercise6.put("statement", "Necesitas organizar una actividad para un grupo de personas en un espacio cerrado. " +
                "Selecciona el video que muestra una actividad que no requiere mucho espacio y puede practicarse en interiores.");
        exercise6.put("exerciseType", "video_selection");
        exercise6.put("points", 5);
        exercise6.put("correctAnswer",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Billar/billarSena.m4v");
        exercise6.put("videos", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Boxeo/boxeoSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Billar/billarSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Patinaje/patinajeSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Nadar/nadarSena.m4v"));


        // Ejercicio 7: Selección de palabra
        Map<String, Object> exercise7 = new HashMap<>();
        exercise7.put("statement", "Deletrea el tipo de alimento que es el ajiaco, la mazamorra, el sancocho, etc.");
        exercise7.put("exerciseType", "ordering");
        exercise7.put("points", 10);
        exercise7.put("maxLetter", 7);
        exercise7.put("correctAnswer", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_s.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_o.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_p.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_a.png"));
        exercise7.put("videos", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_o.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_v.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_r.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_s.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_z.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_f.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_p.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_s.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_a.png"));

        // Ejercicio 8: Ordenar letras
        Map<String, Object> pair111 = new HashMap<>();
        pair111.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Arbitro/arbitroSena.m4v");
        pair111.put("word", "Arbitro");

        Map<String, Object> pair222 = new HashMap<>();
        pair222.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Gol/golSena.m4v");
        pair222.put("word", "Gol");

        Map<String, Object> pair333 = new HashMap<>();
        pair333.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Futbol/futbolSena.m4v");
        pair333.put("word", "Futbol");

        Map<String, Object> pair444 = new HashMap<>();
        pair444.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Partido/partidoSena.m4v");
        pair444.put("word", "Partido");


        Map<String, Object> exercise8 = new HashMap<>();
        exercise8.put("correctPairs", Arrays.asList(pair111, pair222, pair333, pair444));
        exercise8.put("exerciseType", "matching_videos");
        exercise8.put("points", 5);
        exercise8.put("statement", "Tienes un amigo que no conoce mucho de futbol y deseas explicarle algunas cosas básicas. " +
                "Empareja las señas relacionadas a este deporte con su definición" );

        //Ejercicio 9:

        Map<String, Object> exercise9 = new HashMap<>();
        exercise9.put("statement", "Observa el video y selecciona la respuesta que completa la frase. " +
                "\"Hoy quiero comer algo _________, así que voy a preparar hamurguesa con papas fritas.\"");
        exercise9.put("exerciseType", "video_selection");
        exercise9.put("points", 5);
        exercise9.put("correctAnswer",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Rico/ricoSena.mp4");
        exercise9.put("videos", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Sal/salSena.mp4",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Ponque/ponqueSena.mp4",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Rico/ricoSena.mp4",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Vino/vinoSena.mp4"));

        // Ejercicio 10: Emparejamiento de palabra con imagen
        Map<String, Object> exercise10 = new HashMap<>();
        exercise10.put("statement", "Hace poco el equipo de la universidad salió ______ del torneo inter-universitario, ¡Que alegría!");
        exercise10.put("exerciseType", "selection");
        exercise10.put("points", 5);
        exercise10.put("hint", "Campeón");
        exercise10.put("video_url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Campeon/campeonSena.m4v");
        exercise10.put("correctAnswer", "Campeón");
        exercise10.put("words", Arrays.asList("Campeón", "Ganador", "Feliz", "Invicto"));

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
        lesson.put("title", "Lección 6: Alimentación y deportes");
        lesson.put("exercises", exercises);

        // Guardar la lección en Firestore
        db.collection("lessons").document("lesson6")
                .set(lesson)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firestore", "Lección 6 añadida con éxito!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firestore", "Error añadiendo la lección 6", e);
                    }
                });
    }
}