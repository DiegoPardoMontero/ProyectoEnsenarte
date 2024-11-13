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

public class CrearLeccion7 extends AppCompatActivity {

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
        exercise1.put("statement", "Observa los siguientes videos. ¿Cuál corresponde a una unidad de tiempo que se repite cada 365 días?");
        exercise1.put("exerciseType", "video_selection");
        exercise1.put("points", 5);
        exercise1.put("correctAnswer", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Ano/anoSena.m4v");
        exercise1.put("videos", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Ano/anoSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Semana/semanaSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Mes/mesSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Hora/horaSena.m4v"));


        // Ejercicio 2: Selección de video

        Map<String, Object> exercise2 = new HashMap<>();
        exercise2.put("statement", "Deletrea en el orden correcto la palabra que representa el día que sigue al lunes.");
        exercise2.put("exerciseType", "ordering");
        exercise2.put("points", 10);
        exercise2.put("maxLetter", 6);
        exercise2.put("correctAnswer", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_m.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_a.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_r.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_t.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_e.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_s.png"));
        exercise2.put("videos", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_t.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_r.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_n.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_s.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_o.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_e.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_q.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_a.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_m.png"));

        // Ejercicio 3: Ordenar letras

        Map<String, Object> pair1 = new HashMap<>();
        pair1.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Noche/nocheSena.m4v");
        pair1.put("word", "Noche");

        Map<String, Object> pair2 = new HashMap<>();
        pair2.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Dia/diaSena.m4v");
        pair2.put("word", "Dia");

        Map<String, Object> pair3 = new HashMap<>();
        pair3.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Hora/horaSena.m4v");
        pair3.put("word", "Hora");

        Map<String, Object> pair4 = new HashMap<>();
        pair4.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Mes/mesSena.m4v");
        pair4.put("word", "Mes");


        Map<String, Object> exercise3 = new HashMap<>();
        exercise3.put("correctPairs", Arrays.asList(pair1, pair2, pair3, pair4));
        exercise3.put("exerciseType", "matching_videos");
        exercise3.put("points", 5);
        exercise3.put("statement", "Relaciona cada video con la palabra que representa una unidad de tiempo en lenguaje natural" );


        // Ejercicio 4: Emparejamiento de palabra con imagen

        Map<String, Object> exercise4 = new HashMap<>();
        exercise4.put("statement", "¿Cuál muestra una palabra que representa un número mayor que diez?");
        exercise4.put("exerciseType", "video_selection");
        exercise4.put("points", 5);
        exercise4.put("correctAnswer", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Catorce/catorceSena.m4v");
        exercise4.put("videos", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Ocho/ochoSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Cinco/cincoSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Catorce/catorceSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Dos/dosSena.m4v"));



        // Ejercicio 5: Selección de palabra


        Map<String, Object> pair11 = new HashMap<>();
        pair11.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Mucho/muchoSena.m4v");
        pair11.put("word", "Mucho");

        Map<String, Object> pair22 = new HashMap<>();
        pair22.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Mil/milSena.m4v");
        pair22.put("word", "Mil");

        Map<String, Object> pair33 = new HashMap<>();
        pair33.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Millon/millonSena.m4v");
        pair33.put("word", "Millón");

        Map<String, Object> pair44 = new HashMap<>();
        pair44.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Poco/pocoSena.m4v");
        pair44.put("word", "Poco");


        Map<String, Object> exercise5 = new HashMap<>();
        exercise5.put("correctPairs", Arrays.asList(pair11, pair22, pair33, pair44));
        exercise5.put("exerciseType", "matching");
        exercise5.put("points", 5);
        exercise5.put("statement", "Para repasar algunas palabras que representan cantidades y un par de número, " +
                "une cada seña con su respectiva palabra" );


        // Ejercicio 6: Selección de palabra

        Map<String, Object> exercise6 = new HashMap<>();
        exercise6.put("statement", "Ya que estamos repasando los números, ¿cual es la seña de 'Número'?");
        exercise6.put("exerciseType", "video_selection");
        exercise6.put("points", 5);
        exercise6.put("correctAnswer",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Numero/numeroSena.m4v");
        exercise6.put("videos", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Boxeo/boxeoSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Billar/billarSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Patinaje/patinajeSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Nadar/nadarSena.m4v"));


        // Ejercicio 7: Selección de palabra
        Map<String, Object> exercise7 = new HashMap<>();
        exercise7.put("statement", "Deletrea el número que corresponde a la cantidad de minutos que hay en media hora.");
        exercise7.put("exerciseType", "ordering");
        exercise7.put("points", 10);
        exercise7.put("maxLetter", 7);
        exercise7.put("correctAnswer", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_t.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_r.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_e.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_i.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_n.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_t.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_a.png"));
        exercise7.put("videos", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_t.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_z.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_r.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_n.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_e.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_t.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_s.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_i.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_a.png"));

        // Ejercicio 8: Ordenar letras
        Map<String, Object> pair111 = new HashMap<>();
        pair111.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Uno/unoSena.m4v");
        pair111.put("word", "Uno");

        Map<String, Object> pair222 = new HashMap<>();
        pair222.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Doce/doceSena.m4v");
        pair222.put("word", "Tres");

        Map<String, Object> pair333 = new HashMap<>();
        pair333.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Cinco/cincoSena.m4v");
        pair333.put("word", "Cinco");

        Map<String, Object> pair444 = new HashMap<>();
        pair444.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Ocho/ochoSena.m4v");
        pair444.put("word", "ocho");


        Map<String, Object> exercise8 = new HashMap<>();
        exercise8.put("correctPairs", Arrays.asList(pair111, pair222, pair333, pair444));
        exercise8.put("exerciseType", "matching_videos");
        exercise8.put("points", 5);
        exercise8.put("statement", "Asocia cada palabra en lenguaje natural con la letra inicial que la representa. (Pista: todas las señas son números)" );

        //Ejercicio 9:

        Map<String, Object> exercise9 = new HashMap<>();
        exercise9.put("statement", "En Colombia, la mayoría de colegios tienen hasta el grado _____");
        exercise9.put("exerciseType", "video_selection");
        exercise9.put("points", 5);
        exercise9.put("correctAnswer",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Once/onceSena.m4v");
        exercise9.put("videos", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Once/onceSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Catorce/catorceSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Diecisiete/diecisieteSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Dieciocho/dieciochoSena.m4v"));

        // Ejercicio 10: Emparejamiento de palabra con imagen
        Map<String, Object> exercise10 = new HashMap<>();
        exercise10.put("statement", "Tengo un amigo que es supremamente impuntual, no importa a qué hora se levante, siempre llega ________");
        exercise10.put("exerciseType", "selection");
        exercise10.put("points", 5);
        exercise10.put("hint", "Tarde");
        exercise10.put("video_url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Tarde/tardeSena.m4v");
        exercise10.put("correctAnswer", "Tarde");
        exercise10.put("words", Arrays.asList("Mañana", "Tarde", "Temprano", "A tiempo"));

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
        lesson.put("title", "Lección 7: Tiempo y cantidades");
        lesson.put("exercises", exercises);

        // Guardar la lección en Firestore
        db.collection("lessons").document("lesson7")
                .set(lesson)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firestore", "Lección 7 añadida con éxito!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firestore", "Error añadiendo la lección 7", e);
                    }
                });
    }
}