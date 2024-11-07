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

public class CrearLeccion2 extends AppCompatActivity {

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
        exercise1.put("statement", "¿Cuál de estos videos muestra la seña de “feliz”?");
        exercise1.put("exerciseType", "video_selection");
        exercise1.put("points", 5);
        exercise1.put("correctAnswer", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Feliz/felizSena.m4v");
        exercise1.put("videos", Arrays.asList("https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Amor/amorSena.m4v", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Feliz/felizSena.m4v", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Deprimido/deprimidoSena.m4v", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Cuidar/cuidarSena.m4v"));

        // Ejercicio 2: Selección de video
        Map<String, Object> exercise2 = new HashMap<>();
        exercise2.put("statement", "¿Cuál de estos videos muestra la seña de “fiesta”??");
        exercise2.put("exerciseType", "video_selection");
        exercise2.put("points", 5);
        exercise2.put("correctAnswer", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Fiesta/fiestaSena.m4v");
        exercise2.put("videos", Arrays.asList("https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Feliz/felizSena.m4v", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Respetar/respetarSena.m4v", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Fiesta/fiestaSena.m4v", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Bonito/bonitoSena.m4v"));


        // Ejercicio 3: Ordenar letras@
        Map<String, Object> exercise3 = new HashMap<>();
        exercise3.put("statement", "Ordena las señas para deletrear la palabra bailar");
        exercise3.put("exerciseType", "ordering");
        exercise3.put("points", 10);
        exercise3.put("maxLetter", 6);
        exercise3.put("correctAnswer", Arrays.asList("https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_b.png?alt=media&token=a8939166-a7db-40ee-aabe-67b1bb03d8be", "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_a.png?alt=media&token=6110acd0-c4ea-4911-b97f-0d8c9a0814da", "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_i.png?alt=media&token=61564936-c49b-4919-828b-e9271ac12b79", "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_l.png?alt=media&token=86e65dd1-3d7d-46d5-99a2-b3eeeed694c1", "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_a.png?alt=media&token=6110acd0-c4ea-4911-b97f-0d8c9a0814da","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_r.png?alt=media&token=37b15bb5-5ecd-4f77-a900-300a932ae426"));
        exercise3.put("videos", Arrays.asList("https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_a.png?alt=media&token=6110acd0-c4ea-4911-b97f-0d8c9a0814da", "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_b.png?alt=media&token=a8939166-a7db-40ee-aabe-67b1bb03d8be", "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_r.png?alt=media&token=37b15bb5-5ecd-4f77-a900-300a932ae426", "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_j.png?alt=media&token=8ccec022-077a-449e-be74-4f59e45d740b", "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_a.png?alt=media&token=6110acd0-c4ea-4911-b97f-0d8c9a0814da", "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_l.png?alt=media&token=86e65dd1-3d7d-46d5-99a2-b3eeeed694c1", "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_i.png?alt=media&token=61564936-c49b-4919-828b-e9271ac12b79", "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_g.png?alt=media&token=3bc02d22-4379-4de7-8d1c-2433c843535e", "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_n.png?alt=media&token=f3bc7221-673b-4380-b7bd-64d5e2657cba"));


        // Ejercicio 4: Emparejamiento de palabra con imagen
        Map<String, Object> pair1 = new HashMap<>();
        pair1.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Mama/mamaSena.m4v");
        pair1.put("word", "Mamá");

        Map<String, Object> pair2 = new HashMap<>();
        pair2.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias/Papá/01-Papa-Palabra-1.m4v");
        pair2.put("word", "Papá");

        Map<String, Object> pair3 = new HashMap<>();
        pair3.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Hermano/hermanoSena.m4v");
        pair3.put("word", "Hermano");

        Map<String, Object> pair4 = new HashMap<>();
        pair4.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Hijo/hijoSena.m4v");
        pair4.put("word", "Hijo");

        Map<String, Object> exercise4 = new HashMap<>();
        exercise4.put("correctPairs", Arrays.asList(pair1, pair2, pair3, pair4));
        exercise4.put("exerciseType", "matching_videos");
        exercise4.put("points", 5);
        exercise4.put("statement", "Empareja las señas con los nombres correctos (haz click dos veces en el video)");

        // Ejercicio 2: Selección de video
        Map<String, Object> exercise5 = new HashMap<>();
        exercise5.put("statement", "Al recibir la noticia del falleciemiento de su perrito, Juan se sintió ___________ ");
        exercise5.put("exerciseType", "ordering");
        exercise5.put("points", 10);
        exercise5.put("hint", "triste");
        exercise5.put("maxLetter", 6);
        exercise5.put("correctAnswer", Arrays.asList("https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_t.png?alt=media&token=43719ad0-23c9-44c0-b5f9-465781b2d544","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_r.png?alt=media&token=37b15bb5-5ecd-4f77-a900-300a932ae426","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_i.png?alt=media&token=61564936-c49b-4919-828b-e9271ac12b79","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_s.png?alt=media&token=f24656a4-bf62-49b2-b3d2-50ed5e39d2c8","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_t.png?alt=media&token=43719ad0-23c9-44c0-b5f9-465781b2d544","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_e.png?alt=media&token=95f64fa2-2a41-40f9-937c-b94072327578"));
        exercise5.put("videos", Arrays.asList("https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_r.png?alt=media&token=37b15bb5-5ecd-4f77-a900-300a932ae426","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_e.png?alt=media&token=95f64fa2-2a41-40f9-937c-b94072327578","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_t.png?alt=media&token=43719ad0-23c9-44c0-b5f9-465781b2d544","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_t.png?alt=media&token=43719ad0-23c9-44c0-b5f9-465781b2d544","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_i.png?alt=media&token=61564936-c49b-4919-828b-e9271ac12b79","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_o.png?alt=media&token=93d2d9c9-7f70-47da-9f0e-9fd1f24fbbc2","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_s.png?alt=media&token=f24656a4-bf62-49b2-b3d2-50ed5e39d2c8","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_t.png?alt=media&token=43719ad0-23c9-44c0-b5f9-465781b2d544","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_b.png?alt=media&token=a8939166-a7db-40ee-aabe-67b1bb03d8be"));
        // Ejercicio 5: Selección de palabra
        Map<String, Object> exercise6 = new HashMap<>();
        exercise6.put("statement", "¿Qué significa la seña mostrada en el video?");
        exercise6.put("exerciseType", "selection");
        exercise6.put("points", 5);
        exercise6.put("hint", "Fiesta");
        exercise6.put("video_url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Fiesta/fiestaSena.m4v");
        exercise6.put("correctAnswer", "Fiesta");
        exercise6.put("words", Arrays.asList("Fiesta", "Comer", "Concurso", "Defender"));

        // Ejercicio 6: Selección de palabra
        Map<String, Object> exercise7 = new HashMap<>();
        exercise7.put("statement", "Después de perder el juego, Ana se sintió ___________");
        exercise7.put("exerciseType", "selection");
        exercise7.put("points", 5);
        exercise7.put("video_url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Deprimido/deprimidoSena.m4v");
        exercise7.put("correctAnswer", "Deprimida");
        exercise7.put("words", Arrays.asList("Deprimida", "Timida", "Rabiosa", "Feliz"));

        Map<String, Object> exercise8 = new HashMap<>();
        exercise8.put("statement", "“Mi __________ viene de visita este fin de semana, porque soy su nieta favorita”");
        exercise8.put("exerciseType", "video_selection");
        exercise8.put("points", 5);
        exercise8.put("correctAnswer", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Abuelo/abueloSena.m4v");
        exercise8.put("videos", Arrays.asList("https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Mama/mamaSena.m4v", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Hermano/hermanoSena.m4v", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Abuelo/abueloSena.m4v", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Hijo/hijoSena.m4v"));

        // Ejercicio 7: Ordenar letras
        Map<String, Object> exercise9 = new HashMap<>();
        exercise9.put("statement", "Ordena las señas para deletrear la palabra abuelo");
        exercise9.put("exerciseType", "ordering");
        exercise9.put("points", 10);
        exercise9.put("maxLetter", 6);
        exercise9.put("correctAnswer", Arrays.asList("https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_a.png?alt=media&token=6110acd0-c4ea-4911-b97f-0d8c9a0814da","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_b.png?alt=media&token=a8939166-a7db-40ee-aabe-67b1bb03d8be","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_u.png?alt=media&token=93ed77d9-7df6-4f7f-b416-3ec67fc763cd","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_e.png?alt=media&token=95f64fa2-2a41-40f9-937c-b94072327578","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_l.png?alt=media&token=86e65dd1-3d7d-46d5-99a2-b3eeeed694c1","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_o.png?alt=media&token=93d2d9c9-7f70-47da-9f0e-9fd1f24fbbc2"));
        exercise9.put("videos", Arrays.asList("https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_b.png?alt=media&token=a8939166-a7db-40ee-aabe-67b1bb03d8be","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_l.png?alt=media&token=86e65dd1-3d7d-46d5-99a2-b3eeeed694c1","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_u.png?alt=media&token=93ed77d9-7df6-4f7f-b416-3ec67fc763cd","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_o.png?alt=media&token=93d2d9c9-7f70-47da-9f0e-9fd1f24fbbc2","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_r.png?alt=media&token=37b15bb5-5ecd-4f77-a900-300a932ae426","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_a.png?alt=media&token=6110acd0-c4ea-4911-b97f-0d8c9a0814da","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_v.png?alt=media&token=7f7292b9-e9a3-4fb1-9d12-feefeb974a69","https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_e.png?alt=media&token=95f64fa2-2a41-40f9-937c-b94072327578", "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/abecedarioJuegos%2Fletra_j.png?alt=media&token=8ccec022-077a-449e-be74-4f59e45d740b"));


        // Ejercicio 9: Emparejamiento de palabra con imagen
        Map<String, Object> pair11 = new HashMap<>();
        pair11.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Almohada/almohadaSena.mp4");
        pair11.put("word", "Almohada");

        Map<String, Object> pair22 = new HashMap<>();
        pair22.put("url", "https://storage.googleapis.com/proyectoensenarte-d4dd2.appspot.com/senias2/Apartamento/apartamentoSena.mp4");
        pair22.put("word", "Apartamento");

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
        lesson.put("title", "Lección 2: Emociones, Relaciones, Hogar y Actividades Sociales");
        lesson.put("exercises", exercises);

        // Guardar la lección en Firestore
        db.collection("lessons").document("lesson2")
                .set(lesson)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firestore", "Lección 2 añadida con éxito!");
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