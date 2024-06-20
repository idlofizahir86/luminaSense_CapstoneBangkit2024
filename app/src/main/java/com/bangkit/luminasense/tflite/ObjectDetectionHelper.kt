package com.bangkit.luminasense.tflite

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.SystemClock
import android.util.Log
import com.google.android.gms.tflite.client.TfLiteInitializationOptions
import com.google.android.gms.tflite.gpu.support.TfLiteGpu
import org.tensorflow.lite.gpu.CompatibilityList
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.gms.vision.TfLiteVision
import org.tensorflow.lite.task.gms.vision.detector.Detection
import org.tensorflow.lite.task.gms.vision.detector.ObjectDetector

class ObjectDetectionHelper(
    val context: Context,
    val modelName: String,
    val detectorListener: DetectorListener?
) {
    private var objectDetector: ObjectDetector? = null

    init {
        TfLiteGpu.isGpuDelegateAvailable(context).onSuccessTask { gpuAvailable ->
            val optionsBuilder = TfLiteInitializationOptions.builder()
            if (gpuAvailable) {
                optionsBuilder.setEnableGpuDelegateSupport(true)
            }
            TfLiteVision.initialize(context, optionsBuilder.build())
        }.addOnSuccessListener {
            setupObjectDetector()
        }.addOnFailureListener {
            detectorListener?.onError("TfLiteVision is not initialized yet")
        }
    }

    private fun setupObjectDetector() {
        val optionsBuilder = ObjectDetector.ObjectDetectorOptions.builder()
        val baseOptionsBuilder = BaseOptions.builder()
        if (CompatibilityList().isDelegateSupportedOnThisDevice) {
            baseOptionsBuilder.useGpu()
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            baseOptionsBuilder.useNnapi()
        } else {
            baseOptionsBuilder.setNumThreads(4)
        }
        optionsBuilder.setBaseOptions(baseOptionsBuilder.build())

        try {
            objectDetector = ObjectDetector.createFromFileAndOptions(
                context,
                modelName,
                optionsBuilder.build()
            )
        } catch (e: IllegalStateException) {
            detectorListener?.onError("Image classifier failed to initialize")
            Log.e(TAG, e.message.toString())
        }
    }

    fun detectObject(bitmap: Bitmap) {
        if (!TfLiteVision.isInitialized()) {
            val errorMessage = "TfLiteVision is not initialized yet"
            Log.e(TAG, errorMessage)
            detectorListener?.onError(errorMessage)
            return
        }

        if (objectDetector == null) {
            setupObjectDetector()
        }

        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(640, 640, ResizeOp.ResizeMethod.BILINEAR))
            .build()

        val tensorImage = imageProcessor.process(TensorImage.fromBitmap(bitmap))

        var inferenceTime = SystemClock.uptimeMillis()
        val results = objectDetector?.detect(tensorImage)
        inferenceTime = SystemClock.uptimeMillis() - inferenceTime

        val bitmapWithBoxes = drawBoundingBoxes(bitmap, results)

        detectorListener?.onResults(
            results,
            inferenceTime,
            tensorImage.height,
            tensorImage.width,
            bitmapWithBoxes
        )

        // Logging detection results for debugging
        if (results != null) {
            for (result in results) {
                val boundingBox = result.boundingBox
                val label = result.categories.firstOrNull()?.label ?: "Unknown"
                val score = result.categories.firstOrNull()?.score ?: 0f
                Log.d(TAG, "Detected object: $label with score: $score at $boundingBox")
            }
        }
    }

    private fun drawBoundingBoxes(bitmap: Bitmap, results: MutableList<Detection>?): Bitmap {
        val mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(mutableBitmap)
        val paint = Paint()
        paint.color = Color.RED
        paint.strokeWidth = 8f
        paint.style = Paint.Style.STROKE

        results?.forEach { result ->
            val boundingBox = result.boundingBox
            canvas.drawRect(
                boundingBox.left,
                boundingBox.top,
                boundingBox.right,
                boundingBox.bottom,
                paint
            )
        }

        Log.d(TAG, "Bitmap with bounding boxes drawn successfully.")
        return mutableBitmap
    }


    interface DetectorListener {
        fun onError(error: String)
        fun onResults(
            results: MutableList<Detection>?,
            inferenceTime: Long,
            imageHeight: Int,
            imageWidth: Int,
            bitmapWithBoxes: Bitmap
        )
    }

    companion object {
        private const val TAG = "ObjectDetectionHelper"
    }
}

