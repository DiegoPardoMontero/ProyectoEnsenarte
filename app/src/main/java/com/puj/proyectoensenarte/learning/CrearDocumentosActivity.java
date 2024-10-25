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

public class CrearDocumentosActivity extends AppCompatActivity {

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
        // Datos del ejercicio 1
        Map<String, Object> exercise1 = new HashMap<>();
        exercise1.put("statement", "¿Cuál de estos videos muestra la seña de “comer”?");
        exercise1.put("exerciseType", "video_selection");
        exercise1.put("points", 5);
        exercise1.put("correctAnswer", "video1");
        exercise1.put("videos", Arrays.asList("url_video1", "url_video2", "url_video3", "url_video4"));

        Map<String, Object> exercise2 = new HashMap<>();
        exercise2.put("statement", "¿¿Cuál de estos videos muestra la seña de “dormir”?");
        exercise2.put("exerciseType", "video_selection");
        exercise2.put("points", 5);
        exercise2.put("correctAnswer", "video1");
        exercise2.put("videos", Arrays.asList("url_video1", "url_video2", "url_video3", "url_video4"));

        Map<String, Object> exercise3 = new HashMap<>();
        exercise3.put("statement", "Ordena las señas para deletrear la palabra jugar");
        exercise3.put("exerciseType", "ordering");
        exercise3.put("points", 10);
        exercise3.put("maxLetter", 5);
        exercise3.put("correctAnswer", Arrays.asList("j", "u", "g", "a", "r"));
        exercise3.put("videos", Arrays.asList("url_video1", "url_video2", "url_video3", "url_video4", "url_video5", "url_video6", "url_video7", "url_video8", "url_video9"));

        Map<String, Object> exercise4 = new HashMap<>();
        exercise4.put("statement", "Une la palabra con su primera letra del albafeto en señas");
        exercise4.put("exerciseType", "matching");
        exercise4.put("points", 5);
        exercise4.put("correctAnswer", "word1");
        exercise4.put("words", Arrays.asList("Palabra1", "Palabra2", "Palabra3", "Palabra4"));

        Map<String, Object> exercise5 = new HashMap<>();
        exercise5.put("statement", "¿Qué significa la seña mostrada en el video?");
        exercise5.put("exerciseType", "select_word");
        exercise5.put("points", 5);
        exercise5.put("correctAnswer", "Beber");
        exercise5.put("words", Arrays.asList("Palabra1", "Palabra2", "Palabra3", "Palabra4"));

        Map<String, Object> exercise6 = new HashMap<>();
        exercise6.put("statement", "¿Cuál es el verbo para la seña en el video?");
        exercise6.put("exerciseType", "select_word");
        exercise6.put("points", 5);
        exercise6.put("correctAnswer", "Leer");
        exercise6.put("words", Arrays.asList("Palabra1", "Palabra2", "Palabra3", "Palabra4"));

        Map<String, Object> exercise7 = new HashMap<>();
        exercise7.put("statement", "Ordena las señas para deletrear la palabra nadar");
        exercise7.put("exerciseType", "ordering");
        exercise7.put("points", 10);
        exercise7.put("maxLetter", 5);
        exercise7.put("correctAnswer", Arrays.asList("n", "a", "d", "a", "r"));
        exercise7.put("videos", Arrays.asList("url_video1", "url_video2", "url_video3", "url_video4", "url_video5", "url_video6", "url_video7", "url_video8", "url_video9"));

        Map<String, Object> exercise8 = new HashMap<>();
        exercise8.put("statement", "¿Qué verbo se muestra en el video?");
        exercise8.put("exerciseType", "select_word");
        exercise8.put("points", 5);
        exercise8.put("correctAnswer", "Escribir");
        exercise8.put("words", Arrays.asList("Palabra1", "Palabra2", "Palabra3", "Palabra4"));

        Map<String, Object> exercise9 = new HashMap<>();
        exercise9.put("statement", "Une la palabra con su primera letra del albafeto en señas");
        exercise9.put("exerciseType", "matching");
        exercise9.put("points", 5);
        exercise9.put("correctAnswer", "word1");
        exercise9.put("words", Arrays.asList("Palabra1", "Palabra2", "Palabra3", "Palabra4"));

        Map<String, Object> exercise10 = new HashMap<>();
        exercise10.put("statement", "¿Cuál de estos videos muestra la seña de “dibujar”?");
        exercise10.put("exerciseType", "video_selection");
        exercise10.put("points", 5);
        exercise10.put("correctAnswer", "dibujar");
        exercise10.put("videos", Arrays.asList("url_video1", "url_video2", "url_video3", "url_video4"));

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
        lesson.put("title", "Lección 1");
        lesson.put("exercises", exercises);

        // Guardar la lección en Firestore
        db.collection("lessons").document("lesson1")
                .set(lesson)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firestore", "Lección añadida con éxito!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firestore", "Error añadiendo la lección", e);
                    }
                });
    }
}