import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log

class FirestoreDictionary {

    private val db = FirebaseFirestore.getInstance()

    // Lista de señas específicas a buscar
    private val targetSigns = listOf(
        "Cama", "Lavar", "Cocina", "Escoba", "Cuchillo", "Recogedor",
        "Desayunar", "Cocinar", "Bailar", "Jugar", "Dibujar", "Limpiar",
        "Estudiar", "Television", "Televisión", "Computador", "Ducha", "Gustar", "Aspiradora","Tijeras","Tijera","Bañar","Comer","Estudiar","Buscar","Entrar"
    )

    // Función para obtener las URLs de las señas específicas
    fun fetchSpecificSignUrls(onResult: (Map<String, String?>) -> Unit) {
        val dictRef = db.collection("dict").document("palabras")

        dictRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val wordsMap = document.data
                    val signUrls = mutableMapOf<String, String?>()

                    // Iterar sobre cada entrada en el documento y extraer solo las señas específicas
                    if (wordsMap != null) {
                        for ((key, value) in wordsMap) {
                            if (key in targetSigns) {
                                val signData = value as? Map<*, *>
                                val seniaURL = signData?.get("seniaURL") as? String
                                signUrls[key] = seniaURL
                            }
                        }
                    }

                    Log.d("FirestoreDictionary", "URLs de señas específicas: $signUrls")
                    onResult(signUrls) // Devolver el mapa de nombres y URLs
                } else {
                    Log.d("FirestoreDictionary", "Documento 'palabras' no encontrado.")
                    onResult(emptyMap())
                }
            }
            .addOnFailureListener { exception ->
                Log.e("FirestoreDictionary", "Error al obtener URLs de señas específicas", exception)
                onResult(emptyMap())
            }
    }
}