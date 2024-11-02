package com.puj.proyectoensenarte.utils

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.gpu.CompatibilityList
import org.tensorflow.lite.gpu.GpuDelegate
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class SignLanguageVideoProcessor(private val context: Context) {
    private var interpreter: Interpreter? = null
    private val imageSize = 224 // El tamaño que espera tu modelo
    private val numFrames = 30  // Número de frames a procesar
    private var gpuDelegate: GpuDelegate? = null
    private lateinit var labelMap: Map<Int, String>

    init {
        initializeInterpreter()
        initializeLabelMap()
        logModelInfo() // Añadimos logging para debug
    }

    private fun logModelInfo() {
        try {
            val inputTensor = interpreter?.getInputTensor(0)
            val outputTensor = interpreter?.getOutputTensor(0)

            Log.d(TAG, "Input Tensor Shape: ${inputTensor?.shape()?.contentToString()}")
            Log.d(TAG, "Output Tensor Shape: ${outputTensor?.shape()?.contentToString()}")
        } catch (e: Exception) {
            Log.e(TAG, "Error getting tensor info", e)
        }
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

    private fun initializeLabelMap() {
        try {
            // Leer el archivo de etiquetas desde assets
            context.assets.open("model/labels.txt").bufferedReader().use { reader ->
                labelMap = reader.readLines().mapIndexed { index, label ->
                    index to label.trim()
                }.toMap()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error loading labels", e)
        }
    }

    @Throws(IOException::class)
    private fun loadModelFile(): MappedByteBuffer {
        val modelPath = "model/activity.model.tflite"
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

            // Procesar frame por frame
            val frame = frames.firstOrNull() ?:
            return@withContext ProcessingResult.Error(Exception("No frames available"))

            val inputBuffer = prepareInputBuffer(frame)
            val outputBuffer = Array(1) { FloatArray(NUM_CLASSES) }

            interpreter?.run(inputBuffer, outputBuffer)

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
        return Bitmap.createScaledBitmap(frame, imageSize, imageSize, true)
    }

    private fun prepareInputBuffer(bitmap: Bitmap): ByteBuffer {
        val inputBuffer = ByteBuffer.allocateDirect(1 * imageSize * imageSize * 3 * 4)
        inputBuffer.order(ByteOrder.nativeOrder())

        val pixels = IntArray(imageSize * imageSize)
        bitmap.getPixels(pixels, 0, imageSize, 0, 0, imageSize, imageSize)

        var pixel = 0
        for (i in 0 until imageSize) {
            for (j in 0 until imageSize) {
                val value = pixels[pixel++]
                // Normalizar a [-1, 1]
                inputBuffer.putFloat(((value shr 16 and 0xFF) - 128f) / 128f)
                inputBuffer.putFloat(((value shr 8 and 0xFF) - 128f) / 128f)
                inputBuffer.putFloat(((value and 0xFF) - 128) / 128.0f)
            }
        }

        inputBuffer.rewind()
        return inputBuffer
    }

    private fun getPredictedClass(outputArray: FloatArray): String {
        var maxIndex = 0
        var maxValue = outputArray[0]

        for (i in 1 until outputArray.size) {
            if (outputArray[i] > maxValue) {
                maxValue = outputArray[i]
                maxIndex = i
            }
        }

        return labelMap[maxIndex] ?: "Unknown"
    }

    sealed class ProcessingResult {
        data class Success(val predictedLabel: String) : ProcessingResult()
        data class Error(val exception: Exception) : ProcessingResult()
    }

    companion object {
        private const val TAG = "SignLanguageProcessor"
        private const val NUM_CLASSES = 5 // Actualizado a 5 clases según el modelo
    }
}