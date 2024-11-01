package com.puj.proyectoensenarte.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.gpu.CompatibilityList
import org.tensorflow.lite.gpu.GpuDelegate
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import kotlin.math.min

class SignLanguageVideoProcessor(private val context: Context) {
    private var interpreter: Interpreter? = null
    private val imageSize = 224 // El tamaño que espera tu modelo
    private val numFrames = 30  // Número de frames a procesar
    private var gpuDelegate: GpuDelegate? = null

    init {
        initializeInterpreter()
    }

    private fun initializeInterpreter() {
        try {
            val modelFile = loadModelFile()
            val options = Interpreter.Options()

            // Verificar si podemos usar GPU
            val compatList = CompatibilityList()
            if (compatList.isDelegateSupportedOnThisDevice) {
                gpuDelegate = GpuDelegate()
                options.addDelegate(gpuDelegate)
            }

            interpreter = Interpreter(modelFile, options)
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing interpreter", e)
        }
    }

    @Throws(IOException::class)
    private fun loadModelFile(): MappedByteBuffer {
        val modelPath = "model/activity.model.tflite" // Ajusta la ruta según tu estructura
        val assetFileDescriptor = context.assets.openFd(modelPath)
        val fileInputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel = fileInputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    suspend fun processVideo(videoUri: Uri): ProcessingResult = withContext(Dispatchers.IO) {
        try {
            val frames = extractFrames(videoUri)
            if (frames.isEmpty()) {
                return@withContext ProcessingResult.Error(Exception("No frames extracted"))
            }

            val inputBuffer = prepareInputBuffer(frames)
            val outputBuffer = Array(1) { FloatArray(NUM_CLASSES) } // Ajusta según tu modelo

            // Realizar la inferencia
            interpreter?.run(inputBuffer, outputBuffer)

            // Obtener la clase predicha
            val predictedClass = getPredictedClass(outputBuffer[0])
            ProcessingResult.Success(predictedClass)
        } catch (e: Exception) {
            Log.e(TAG, "Error processing video", e)
            ProcessingResult.Error(e)
        }
    }

    private fun extractFrames(videoUri: Uri): List<Bitmap> {
        val frames = mutableListOf<Bitmap>()
        val retriever = MediaMetadataRetriever()

        try {
            retriever.setDataSource(context, videoUri)
            val duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toLong() ?: 0
            val interval = duration / numFrames

            for (i in 0 until numFrames) {
                val timeUs = i * interval * 1000 // Convertir a microsegundos
                val frame = retriever.getFrameAtTime(timeUs, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
                frame?.let {
                    // Redimensionar y preprocesar el frame
                    val scaledFrame = preprocessFrame(it)
                    frames.add(scaledFrame)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error extracting frames", e)
        } finally {
            retriever.release()
        }

        return frames
    }

    private fun preprocessFrame(frame: Bitmap): Bitmap {
        // Redimensionar la imagen al tamaño requerido
        val scaledBitmap = Bitmap.createScaledBitmap(frame, imageSize, imageSize, true)

        // Normalizar los valores de píxeles
        return scaledBitmap
    }

    private fun prepareInputBuffer(frames: List<Bitmap>): ByteBuffer {
        // Ajusta estos valores según los requerimientos de tu modelo
        val channelSize = 3 // RGB
        val totalSize = 1 * numFrames * imageSize * imageSize * channelSize * 4 // 4 bytes por float

        val inputBuffer = ByteBuffer.allocateDirect(totalSize)
        inputBuffer.order(ByteOrder.nativeOrder())

        for (frame in frames) {
            addFrameToBuffer(frame, inputBuffer)
        }

        inputBuffer.rewind()
        return inputBuffer
    }

    private fun addFrameToBuffer(bitmap: Bitmap, buffer: ByteBuffer) {
        val pixels = IntArray(imageSize * imageSize)
        bitmap.getPixels(pixels, 0, imageSize, 0, 0, imageSize, imageSize)

        for (pixel in pixels) {
            // Normalizar y agregar cada canal RGB
            buffer.putFloat(((pixel shr 16) and 0xFF) / 255.0f) // R
            buffer.putFloat(((pixel shr 8) and 0xFF) / 255.0f)  // G
            buffer.putFloat((pixel and 0xFF) / 255.0f)          // B
        }
    }
    sealed class ProcessingResult {
        data class Success(val predictedLabel: String) : ProcessingResult()
        data class Error(val exception: Exception) : ProcessingResult()
    }

    private lateinit var labelMap: Map<Int, String>

    init {
        initializeInterpreter()
        initializeLabelMap()
    }

    private fun initializeLabelMap() {
        try {
            // Leer el archivo de etiquetas desde assets
            context.assets.open("labels.txt").bufferedReader().use { reader ->
                labelMap = reader.readLines().mapIndexed { index, label ->
                    index to label.trim()
                }.toMap()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error loading labels", e)
        }
    }

    private fun getPredictedClass(outputArray: FloatArray): String {
        // Encontrar el índice con la mayor probabilidad
        var maxIndex = 0
        var maxValue = outputArray[0]

        for (i in 1 until outputArray.size) {
            if (outputArray[i] > maxValue) {
                maxValue = outputArray[i]
                maxIndex = i
            }
        }

        // Convertir el índice a etiqueta usando el mapa
        return labelMap[maxIndex] ?: "Unknown"
    }


    companion object {
        private const val TAG = "SignLanguageProcessor"
        private const val NUM_CLASSES = 10 // Ajusta según tu modelo
    }
}