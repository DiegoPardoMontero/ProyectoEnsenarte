package com.puj.proyectoensenarte.utils;

import android.content.Context
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer
import java.nio.ByteOrder
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import java.io.FileInputStream
import java.nio.channels.FileChannel

class VideoClassifier(private val context: Context) {
    private var interpreter: Interpreter
    private var labelList: List<String>

    init {
        interpreter = Interpreter(loadModelFile())
        labelList = loadLabelList()
    }

    private fun loadModelFile(): ByteBuffer {
        val assetManager = context.assets
        val modelFile = assetManager.openFd("modelEnseniarte.tflite")
        val inputStream = FileInputStream(modelFile.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = modelFile.startOffset
        val declaredLength = modelFile.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    private fun loadLabelList(): List<String> {
        return context.assets.open("etiquetasEnseniarte.txt").bufferedReader().useLines { it.toList() }
    }

    private fun preprocessFrame(bitmap: Bitmap): ByteBuffer {
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
        val byteBuffer = ByteBuffer.allocateDirect(4 * 224 * 224 * 3)
        byteBuffer.order(ByteOrder.nativeOrder())

        val intValues = IntArray(224 * 224)
        resizedBitmap.getPixels(intValues, 0, resizedBitmap.width, 0, 0, resizedBitmap.width, resizedBitmap.height)

        for (pixelValue in intValues) {
            byteBuffer.putFloat(((pixelValue shr 16 and 0xFF) / 255f))
            byteBuffer.putFloat(((pixelValue shr 8 and 0xFF) / 255f))
            byteBuffer.putFloat((pixelValue and 0xFF) / 255f)
        }

        return byteBuffer
    }

    fun classifyVideo(videoUri: Uri): Pair<String, Float> {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(context, videoUri)

        val frameCount = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_FRAME_COUNT)?.toInt() ?: 0
        val classVotes = mutableMapOf<String, Float>()

        for (i in 0 until frameCount step 5) {
            val bitmap = retriever.getFrameAtTime((i * 1000000).toLong(), MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
            bitmap?.let {
                val inputBuffer = preprocessFrame(it)
                val outputBuffer = Array(1) { FloatArray(labelList.size) }
                interpreter.run(inputBuffer, outputBuffer)

                val maxIndex = outputBuffer[0].indices.maxByOrNull { outputBuffer[0][it] } ?: 0
                val className = labelList[maxIndex]
                val confidence = outputBuffer[0][maxIndex]

                classVotes[className] = (classVotes[className] ?: 0f) + confidence
            }
        }

        retriever.release()

        val totalVotes = classVotes.values.sum()
        classVotes.forEach { (className, votes) -> classVotes[className] = votes / totalVotes }

        val finalClass = classVotes.maxByOrNull { it.value }
        return if (finalClass != null) {
            Pair(finalClass.key, finalClass.value)
        } else {
            Pair("No se pudo clasificar", 0f)
        }
    }
}