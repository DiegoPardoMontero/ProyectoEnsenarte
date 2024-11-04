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

public class CrearLeccion3 extends AppCompatActivity {

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
        exercise1.put("statement", "¿Cuál de estos videos muestra la seña de “cama”?");
        exercise1.put("exerciseType", "video_selection");
        exercise1.put("points", 5);
        exercise1.put("correctAnswer", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Cama/camaSena.mp4");
        exercise1.put("videos", Arrays.asList("https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Cama/camaSena.mp4", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Jugar/jugarSena.m4v", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Cuidar/cuidarSena.m4v","https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Cocina/cocinaSena.mp4"));

        // Ejercicio 2: Selección de video
        Map<String, Object> exercise2 = new HashMap<>();
        exercise2.put("statement", "¿Cuál de estos videos muestra la seña de “gustar”?");
        exercise2.put("exerciseType", "video_selection");
        exercise2.put("points", 5);
        exercise2.put("correctAnswer", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Gustar/gustarSena.m4v");
        exercise2.put("videos", Arrays.asList("https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Cocina/cocinaSena.mp4", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Cama/camaSena.mp4", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Gustar/gustarSena.m4v", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Ducha/duchaSena.mp4"));


        // Ejercicio 3: Ordenar letras
        Map<String, Object> exercise3 = new HashMap<>();
        exercise3.put("statement", "Ordena las señas para deletrear la palabra “cocina”");
        exercise3.put("exerciseType", "ordering");
        exercise3.put("points", 10);
        exercise3.put("maxLetter", 6);
        exercise3.put("correctAnswer", Arrays.asList("https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_c.png?alt=media&token=566aeb9a-0520-4c1f-bdfc-d9951a94e150", "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_o.png?alt=media&token=93d2d9c9-7f70-47da-9f0e-9fd1f24fbbc2", "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_c.png?alt=media&token=566aeb9a-0520-4c1f-bdfc-d9951a94e150", "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_i.png?alt=media&token=61564936-c49b-4919-828b-e9271ac12b79", "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_n.png?alt=media&token=f3bc7221-673b-4380-b7bd-64d5e2657cba","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_a.png?alt=media&token=6110acd0-c4ea-4911-b97f-0d8c9a0814da"));
        exercise3.put("videos", Arrays.asList("https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_n.png?alt=media&token=f3bc7221-673b-4380-b7bd-64d5e2657cba","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_f.png?alt=media&token=ddd8e114-9625-4320-aca3-9594dad77bbe","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_a.png?alt=media&token=6110acd0-c4ea-4911-b97f-0d8c9a0814da","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_c.png?alt=media&token=566aeb9a-0520-4c1f-bdfc-d9951a94e150","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_n%CC%83.png?alt=media&token=eb2d7fb9-efd1-4c29-9100-7be5637cb402","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_w.png?alt=media&token=53a1f0cf-b30d-4d98-92ed-0682bdd9bad9","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_i.png?alt=media&token=61564936-c49b-4919-828b-e9271ac12b79","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_c.png?alt=media&token=566aeb9a-0520-4c1f-bdfc-d9951a94e150","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_o.png?alt=media&token=93d2d9c9-7f70-47da-9f0e-9fd1f24fbbc2"));


        // Ejercicio 4: Emparejamiento de palabra con imagen
        Map<String, Object> pair1 = new HashMap<>();
        pair1.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Escoba/escobaSena.mp4");
        pair1.put("word", "Barrer");

        Map<String, Object> pair2 = new HashMap<>();
        pair2.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Cuchillo/cuchilloSena.mp4");
        pair2.put("word", "Cortar");

        Map<String, Object> pair3 = new HashMap<>();
        pair3.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Aspiradora/aspiradoraSena.mp4");
        pair3.put("word", "Aspirar");

        Map<String, Object> pair4 = new HashMap<>();
        pair4.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Espejo/espejoSena.mp4");
        pair4.put("word", "Reflejar");


        Map<String, Object> exercise4 = new HashMap<>();
        exercise4.put("correctPairs", Arrays.asList(pair1, pair2, pair3, pair4));
        exercise4.put("exerciseType", "matching_videos");
        exercise4.put("points", 5);
        exercise4.put("statement", "Empareja el objeto con la acción correcta. Ej: Escoba con barrer (haz click dos veces en el video)" );

        // Ejercicio 2: Selección de video
        Map<String, Object> exercise5 = new HashMap<>();
        exercise5.put("statement", "Al recibir la noticia de bancarrota de la empresa , Juan  sintió ___________ únicamente en su padre");
        exercise5.put("exerciseType", "video_selection");
        exercise5.put("points", 5);
        exercise5.put("correctAnswer", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Confianza/confianzaSena.m4v");
        exercise5.put("videos", Arrays.asList("https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Cama/camaSena.mp4", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Gustar/gustarSena.m4v","https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Television/televisionSena.mp4" ,"https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Confianza/confianzaSena.m4v"));


        // Ejercicio 5: Selección de palabra
        Map<String, Object> exercise6 = new HashMap<>();
        exercise6.put("statement", "Por la mañana, Ana se levanta y va a __________ sus llaves para salir de casa ");
        exercise6.put("exerciseType", "selection");
        exercise6.put("points", 5);
        exercise6.put("hint", "Buscar");
        exercise6.put("video_url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Buscar/buscarSena.m4v");
        exercise6.put("correctAnswer", "Buscar");
        exercise6.put("words", Arrays.asList("Duplicar", "Guardar", "Buscar", "Arreglar"));

        // Ejercicio 6: Selección de palabra
        Map<String, Object> exercise7 = new HashMap<>();
        exercise7.put("statement", "Por la tarde, Pedro invitó  a su mamá a __________ la casa");
        exercise7.put("exerciseType", "selection");
        exercise7.put("points", 5);
        exercise7.put("video_url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Entrar/entrarSena.m4v");
        exercise7.put("correctAnswer", "Entrar");
        exercise7.put("words", Arrays.asList("Entrar", "Buscar", "Respetar", "Querer"));

        Map<String, Object> exercise8 = new HashMap<>();
        exercise8.put("statement", "¿Cuál de estos videos muestra la seña de “Escoba”?");
        exercise8.put("exerciseType", "video_selection");
        exercise8.put("points", 5);
        exercise8.put("correctAnswer", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Escoba/escobaSena.mp4");
        exercise8.put("videos", Arrays.asList("https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Jugar/jugarSena.m4v", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Cuchillo/cuchilloSena.mp4", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Television/televisionSena.mp4","https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Escoba/escobaSena.mp4"));

        // Ejercicio 7: Ordenar letras
        Map<String, Object> exercise9 = new HashMap<>();
        exercise9.put("statement", "Ordena las señas para deletrear la palabra Cuidar");
        exercise9.put("exerciseType", "ordering");
        exercise9.put("points", 10);
        exercise9.put("maxLetter", 6);
        exercise9.put("correctAnswer", Arrays.asList("https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_c.png?alt=media&token=566aeb9a-0520-4c1f-bdfc-d9951a94e150","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_u.png?alt=media&token=93ed77d9-7df6-4f7f-b416-3ec67fc763cd","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_i.png?alt=media&token=61564936-c49b-4919-828b-e9271ac12b79","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_d.png?alt=media&token=46edad3f-9cc1-4871-866c-e4cb4ed311d3","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_a.png?alt=media&token=6110acd0-c4ea-4911-b97f-0d8c9a0814da","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_r.png?alt=media&token=37b15bb5-5ecd-4f77-a900-300a932ae426"));
        exercise9.put("videos", Arrays.asList("https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_i.png?alt=media&token=61564936-c49b-4919-828b-e9271ac12b79","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_u.png?alt=media&token=93ed77d9-7df6-4f7f-b416-3ec67fc763cd","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_a.png?alt=media&token=6110acd0-c4ea-4911-b97f-0d8c9a0814da","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_d.png?alt=media&token=46edad3f-9cc1-4871-866c-e4cb4ed311d3","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_p.png?alt=media&token=b6c6fdd1-1575-45f0-9e3b-488a59fdca7d","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_c.png?alt=media&token=566aeb9a-0520-4c1f-bdfc-d9951a94e150","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_j.png?alt=media&token=8ccec022-077a-449e-be74-4f59e45d740b","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_r.png?alt=media&token=37b15bb5-5ecd-4f77-a900-300a932ae426","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_k.png?alt=media&token=6bc99266-87de-438b-aa58-65e033a94d2f"));


        // Ejercicio 9: Emparejamiento de palabra con imagen
        Map<String, Object> pair11 = new HashMap<>();
        pair11.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Television/televisionSena.mp4");
        pair11.put("word", "Televisión");

        Map<String, Object> pair22 = new HashMap<>();
        pair22.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Computador/computadorSena.mp4");
        pair22.put("word", "Computador");

        Map<String, Object> pair33 = new HashMap<>();
        pair33.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Cama/camaSena.mp4");
        pair33.put("word", "Cama");

        Map<String, Object> pair44 = new HashMap<>();
        pair44.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Ducha/duchaSena.mp4");
        pair44.put("word", "Ducha");

        Map<String, Object> exercise10 = new HashMap<>();
        exercise10.put("correctPairs", Arrays.asList(pair11, pair22, pair33, pair44));
        exercise10.put("exerciseType", "matching_videos");
        exercise10.put("points", 5);
        exercise10.put("statement", "Une la palabra con su seña correspondiente (haz click dos veces en el video)");

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
        lesson.put("title", "Lección 3: Rutinas Diarias y Tareas del Hogar");
        lesson.put("exercises", exercises);

        // Guardar la lección en Firestore
        db.collection("lessons").document("lesson3")
                .set(lesson)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firestore", "Lección 3 añadida con éxito!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firestore", "Error añadiendo la lección 2", e);
                    }
                });
    }
}