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

public class CrearLeccion8 extends AppCompatActivity {

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

        Map<String, Object> pair1 = new HashMap<>();
        pair1.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Avion/avionSena.m4v");
        pair1.put("word", "Avion");

        Map<String, Object> pair2 = new HashMap<>();
        pair2.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Bicicleta/bicicletaSena.m4v");
        pair2.put("word", "Bicicleta");

        Map<String, Object> pair3 = new HashMap<>();
        pair3.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Moto/motoSena.m4v");
        pair3.put("word", "Moto");

        Map<String, Object> pair4 = new HashMap<>();
        pair4.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Bus/busSena.mp4");
        pair4.put("word", "Bus");


        Map<String, Object> exercise1 = new HashMap<>();
        exercise1.put("correctPairs", Arrays.asList(pair1, pair2, pair3, pair4));
        exercise1.put("exerciseType", "matching_videos");
        exercise1.put("points", 5);
        exercise1.put("statement", "Haz la correspondencia entre cada video y el medio de transporte que representa:" );

        // Ejercicio 2:


        Map<String, Object> exercise2 = new HashMap<>();
        exercise2.put("statement", "Observa el video y selecciona la palabra que representa una indicación de ubicación o ruta.");
        exercise2.put("exerciseType", "selection");
        exercise2.put("points", 5);
        exercise2.put("hint", "Dirección");
        exercise2.put("video_url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Direccion/direccionSena.m4v");
        exercise2.put("correctAnswer", "Dirección");
        exercise2.put("words", Arrays.asList("Calle", "Dirección", "Trancón", "Hotel"));



        // Ejercicio 3: Ordenar letras

        Map<String, Object> pair11 = new HashMap<>();
        pair11.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Norte/norteSena.mp4");
        pair11.put("word", "Norte");

        Map<String, Object> pair22 = new HashMap<>();
        pair22.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Sur/surSena.m4v");
        pair22.put("word", "Sur");

        Map<String, Object> pair33 = new HashMap<>();
        pair33.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Oriente/orienteSena.m4v");
        pair33.put("word", "Oriente");

        Map<String, Object> pair44 = new HashMap<>();
        pair44.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Occidente/occidenteSena.m4v");
        pair44.put("word", "Occidente");


        Map<String, Object> exercise3 = new HashMap<>();
        exercise3.put("correctPairs", Arrays.asList(pair11, pair22, pair33, pair44));
        exercise3.put("exerciseType", "matching_videos");
        exercise3.put("points", 5);
        exercise3.put("statement", "Relaciona cada video con la palabra que representa una dirección geográfica en lenguaje natural" );


        // Ejercicio 4: Emparejamiento de palabra con imagen

        Map<String, Object> exercise4 = new HashMap<>();
        exercise4.put("statement", "¿Cuál video muestra un tipo de transporte público característico de Bogotá?");
        exercise4.put("exerciseType", "video_selection");
        exercise4.put("points", 5);
        exercise4.put("correctAnswer", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Transmilenio/transmilenioSena.mp4");
        exercise4.put("videos", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Transmilenio/transmilenioSena.mp4",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Bus/busSena.mp4",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Colectivo/colectivoSena.mp4",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Moto/motoSena.m4v"));



        // Ejercicio 5: Selección de palabra


        Map<String, Object> pair111 = new HashMap<>();
        pair111.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Arriba/arribaSena.m4v");
        pair111.put("word", "Arriba");

        Map<String, Object> pair222 = new HashMap<>();
        pair222.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Abajo/abajoSena.m4v");
        pair222.put("word", "Abajo");

        Map<String, Object> pair333 = new HashMap<>();
        pair333.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Cerca/cercaSena.m4v");
        pair333.put("word", "Cerca");

        Map<String, Object> pair444 = new HashMap<>();
        pair444.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Lejos/lejosSena.m4v");
        pair444.put("word", "Lejos");


        Map<String, Object> exercise5 = new HashMap<>();
        exercise5.put("correctPairs", Arrays.asList(pair111, pair222, pair333, pair444));
        exercise5.put("exerciseType", "matching_videos");
        exercise5.put("points", 5);
        exercise5.put("statement", "Haz la correspondencia entre cada video y la palabra que representa una ubicación relativa:" );


        // Ejercicio 6: Selección de palabra

        Map<String, Object> exercise6 = new HashMap<>();
        exercise6.put("statement", "Hace muchos años había una tradición popular de ir en familia al _________ para ver los" +
                "aviones despegar y aterrizar");
        exercise6.put("exerciseType", "video_selection");
        exercise6.put("points", 5);
        exercise6.put("correctAnswer",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Aeropuerto/aeropuertoSena.m4v");
        exercise6.put("videos", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Restaurante/restauranteSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Oficina/oficinaSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Drogueria/drogueriaSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Aeropuerto/aeropuertoSena.m4v"));


        // Ejercicio 7: Selección de palabra
        Map<String, Object> pair1111 = new HashMap<>();
        pair1111.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Calle/calleSena.m4v");
        pair1111.put("word", "Calle");

        Map<String, Object> pair2222 = new HashMap<>();
        pair2222.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Carretera/carreteraSena.mp4");
        pair2222.put("word", "Carretera");

        Map<String, Object> pair3333 = new HashMap<>();
        pair3333.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Trancon/tranconSena.mp4");
        pair3333.put("word", "Trancón");

        Map<String, Object> pair4444 = new HashMap<>();
        pair4444.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Hotel/hotelSena.m4v");
        pair4444.put("word", "Hotel");


        Map<String, Object> exercise7 = new HashMap<>();
        exercise7.put("correctPairs", Arrays.asList(pair1111, pair2222, pair3333, pair4444));
        exercise7.put("exerciseType", "matching_videos");
        exercise7.put("points", 5);
        exercise7.put("statement", "Haz la correspondencia entre cada video y el espacio urbano o situación que representa:" );

        // Ejercicio 8: Ordenar letras

        Map<String, Object> exercise8 = new HashMap<>();
        exercise8.put("statement", "No tienes idea de los recuerdos que me trae este lugar, ______ concí a mi mejor amigo hace mucho tiempo");
        exercise8.put("exerciseType", "video_selection");
        exercise8.put("points", 5);
        exercise8.put("correctAnswer",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Aqui/aquiSena.m4v");
        exercise8.put("videos", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Abajo/abajoSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Aqui/aquiSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Cerca/cercaSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Direccion/direccionSena.m4v"));

        //Ejercicio 9:

        Map<String, Object> exercise9 = new HashMap<>();
        exercise9.put("statement", "El accidente estuvo feo, afortunadamente la ________ llegó a tiempo para salvarle la vida");
        exercise9.put("exerciseType", "video_selection");
        exercise9.put("points", 5);
        exercise9.put("correctAnswer",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Ambulancia/ambulanciaSena.m4v");
        exercise9.put("videos", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Avion/avionSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Ambulancia/ambulanciaSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Bicicleta/bicicletaSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Bus/busSena.mp4"));

        // Ejercicio 10: Emparejamiento de palabra con imagen
        Map<String, Object> pair11111 = new HashMap<>();
        pair11111.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Restaurante/restauranteSena.m4v");
        pair11111.put("word", "Restaurante");

        Map<String, Object> pair22222 = new HashMap<>();
        pair22222.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Drogueria/drogueriaSena.m4v");
        pair22222.put("word", "Dorguería");

        Map<String, Object> pair33333 = new HashMap<>();
        pair33333.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Plazademercado/plazademercadoSena.m4v");
        pair33333.put("word", "Plaza de mercado");

        Map<String, Object> pair44444 = new HashMap<>();
        pair44444.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Terminaldetransporte/terminaldetransporteSena.m4v");
        pair44444.put("word", "Terminal de transporte");


        Map<String, Object> exercise10 = new HashMap<>();
        exercise10.put("correctPairs", Arrays.asList(pair11111, pair22222, pair33333, pair44444));
        exercise10.put("exerciseType", "matching_videos");
        exercise10.put("points", 5);
        exercise10.put("statement", "Quieres explicarle a un amig@ algunos lugares tipicos de la ciudad, haz la correspondencia " +
                "entre la seña y el lugar al que hace referencia." );

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
        lesson.put("title", "Lección 8: Transporte, ciudad y espacio");
        lesson.put("exercises", exercises);

        // Guardar la lección en Firestore
        db.collection("lessons").document("lesson8")
                .set(lesson)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firestore", "Lección 8 añadida con éxito!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firestore", "Error añadiendo la lección 8", e);
                    }
                });
    }
}