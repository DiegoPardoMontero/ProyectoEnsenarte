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

public class CrearLeccion5 extends AppCompatActivity {

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
        exercise1.put("statement", "Observa el video y selecciona el verbo que representa una acción de aseo personal.");
        exercise1.put("exerciseType", "video_selection");
        exercise1.put("points", 5);
        exercise1.put("correctAnswer",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Embetunar/01EmbetunarPalabra.mp4/01EmbetunarPalabra.mp4");
        exercise1.put("videos", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Toalla/Toallapalabra.mp4/Toallapalabra.mp4",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Jabon/jabonSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Embetunar/01EmbetunarPalabra.mp4/01EmbetunarPalabra.mp4",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Panal/panalSena.m4v"));

        // Ejercicio 2: Selección de video

        Map<String, Object> exercise2 = new HashMap<>();
        exercise2.put("statement", "Deletrea la palabra 'Infección' con las imágenes de las señas.");
        exercise2.put("exerciseType", "ordering");
        exercise2.put("points", 10);
        exercise2.put("maxLetter", 9);
        exercise2.put("correctAnswer", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_i.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_n.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_f.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_e.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_c.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_c.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_i.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_o.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_n.png"));
        exercise2.put("videos", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_i.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_e.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_n.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_f.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_o.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_n.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_c.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_i.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_c.png"));

        // Ejercicio 3: Ordenar letras

        Map<String, Object> pair1 = new HashMap<>();
        pair1.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Aspiradora/aspiradoraSena.mp4");
        pair1.put("word", "Fiebre");

        Map<String, Object> pair2 = new HashMap<>();
        pair2.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Celular/celularSena.mp4");
        pair2.put("word", "Desmayarse");

        Map<String, Object> pair3 = new HashMap<>();
        pair3.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Fax/faxSena.mp4");
        pair3.put("word", "Gripa");

        Map<String, Object> pair4 = new HashMap<>();
        pair4.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Dolor/Dolerpalabra.mp4/Dolerpalabra.mp4");
        pair4.put("word", "Doler");


        Map<String, Object> exercise3 = new HashMap<>();
        exercise3.put("correctPairs", Arrays.asList(pair1, pair2, pair3, pair4));
        exercise3.put("exerciseType", "matching_videos");
        exercise3.put("points", 5);
        exercise3.put("statement", "Empareja el objeto con la acción correcta. (haz click dos veces en el video)" );


        // Ejercicio 4: Emparejamiento de palabra con imagen

        Map<String, Object> exercise4 = new HashMap<>();
        exercise4.put("statement", "Mi papá y yo solemos tener _________ en casa por si alguna amiga llega a necesitar una.");
        exercise4.put("exerciseType", "video_selection");
        exercise4.put("points", 5);
        exercise4.put("correctAnswer", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Toallahigenica/toallahiguienicaSenas.mp4");
        exercise4.put("videos", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Perfume/Perfumepalabra.mp4/Perfumepalabra.mp4",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Jabon/jabonSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Toallahigenica/toallahiguienicaSenas.mp4",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Toalla/Toallapalabra.mp4/Toallapalabra.mp4"));

        // Ejercicio 5: Selección de palabra

        Map<String, Object> exercise5 = new HashMap<>();
        exercise5.put("statement", "Selecciona la palabra a la que corresponde esta seña");
        exercise5.put("exerciseType", "selection");
        exercise5.put("points", 5);
        exercise5.put("hint", "Sano");
        exercise5.put("video_url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Sano/sanoSena.m4v");
        exercise5.put("correctAnswer", "Sano");
        exercise5.put("words", Arrays.asList("Sano", "Bien", "Quitarse", "Feliz"));



        // Ejercicio 6: Selección de palabra
        Map<String, Object> exercise6 = new HashMap<>();
        exercise6.put("statement", "Observa el video y selecciona la palabra que corresponde al video mostrado.");
        exercise6.put("exerciseType", "selection");
        exercise6.put("points", 5);
        exercise6.put("hint", "Jeringa");
        exercise6.put("video_url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Sano/sanoSena.m4v");
        exercise6.put("correctAnswer", "Jeringa");
        exercise6.put("words", Arrays.asList("Algodon", "Cirugía", "Sangre", "Jeringa"));



        // Ejercicio 7: Selección de palabra
        Map<String, Object> exercise7 = new HashMap<>();
        exercise7.put("statement", "Deletrea la palabra 'Perfume' con las imágenes de las señas.");
        exercise7.put("exerciseType", "ordering");
        exercise7.put("points", 10);
        exercise7.put("maxLetter", 7);
        exercise7.put("correctAnswer", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_p.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_e.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_r.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_f.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_u.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_m.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_e.png"));
        exercise7.put("videos", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_p.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_x.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_r.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_e.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_k.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_f.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_e.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_u.png",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/abecedarioJuegos_copia/letra_m.png"));

        // Ejercicio 8: Ordenar letras
        Map<String, Object> pair11 = new HashMap<>();
        pair11.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Hospital/hospitalSena.m4v");
        pair11.put("word", "Hospital");

        Map<String, Object> pair22 = new HashMap<>();
        pair22.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Sangre/sangreSena.m4v");
        pair22.put("word", "Sangre");

        Map<String, Object> pair33 = new HashMap<>();
        pair33.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Tos/tosSena.m4v");
        pair33.put("word", "Tos");

        Map<String, Object> pair44 = new HashMap<>();
        pair44.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Mareo/mareoSena.m4v");
        pair44.put("word", "Mareo");


        Map<String, Object> exercise8 = new HashMap<>();
        exercise8.put("correctPairs", Arrays.asList(pair11, pair22, pair33, pair44));
        exercise8.put("exerciseType", "matching_videos");
        exercise8.put("points", 5);
        exercise8.put("statement", "Empareja las señas con su palabra correspondiente. (haz click dos veces en el video)" );

        //Ejercicio 9:

        Map<String, Object> exercise9 = new HashMap<>();
        exercise9.put("statement", "Hoy por primera vez vez cambié un ________, eso de ser niñero no es sencillo.");
        exercise9.put("exerciseType", "video_selection");
        exercise9.put("points", 5);
        exercise9.put("correctAnswer",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Panal/panalSena.m4v");
        exercise9.put("videos", Arrays.asList(
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Sida/sidaSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Radiografia/radiografiaSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Panal/panalSena.m4v",
                "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Papel/papelhiguienicoSena.mp4"));

        // Ejercicio 10: Emparejamiento de palabra con imagen
        Map<String, Object> exercise10 = new HashMap<>();
        exercise10.put("statement", "Mi dermatologo me recomendó un jabón especial para el ________, ahora tengo la piel super suave");
        exercise10.put("exerciseType", "selection");
        exercise10.put("points", 5);
        exercise10.put("hint", "Acné");
        exercise10.put("video_url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Acne/acnepalabra.mp4/acnepalabra.mp4");
        exercise10.put("correctAnswer", "Acné");
        exercise10.put("words", Arrays.asList("Acné", "Cuerpo", "Rostro", "Pie"));

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
        lesson.put("title", "Lección 5: Aseo personal y salud");
        lesson.put("exercises", exercises);

        // Guardar la lección en Firestore
        db.collection("lessons").document("lesson5")
                .set(lesson)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firestore", "Lección 5 añadida con éxito!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firestore", "Error añadiendo la lección 5", e);
                    }
                });
    }
}