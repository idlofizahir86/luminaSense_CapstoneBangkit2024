package com.bangkit.luminasense.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bangkit.luminasense.tflite.ObjectDetectionHelper
import com.bangkit.luminasense.backend.preferences.SharedPrefHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.task.gms.vision.detector.Detection
import java.net.HttpURLConnection
import java.net.URL
import java.nio.ByteBuffer
import java.nio.ByteOrder


@Composable
fun LivePortraitScreen(navController: NavController, context: Context) {
    Log.d("ObjectDetection", "ObjectDetection Composable called")
    val model = remember { ObjectDetectionModel(context) }
    var detectedPeopleCount by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    var webView: WebView? by remember { mutableStateOf(null) }

    val sharedPrefHelper = SharedPrefHelper(context)
    val ipAddress = sharedPrefHelper.getIpAddress()

    Scaffold(
        content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background
            ) {
                if (ipAddress != null) {


                    LaunchedEffect(Unit) {
                        webView = WebView(context).apply {
                            settings.javaScriptEnabled = true
                            settings.loadWithOverviewMode = true
                            settings.useWideViewPort = true
                            loadUrl("http://$ipAddress:81/stream")
                        }

                        scope.launch(Dispatchers.IO) {
                            while (true) {
                                try {
                                    withContext(Dispatchers.Main) {
                                        if (webView != null) {
                                            // Setelah memperoleh gambar dari WebView
                                            webView?.capturePicture()?.let { picture ->
                                                // Mendapatkan gambar asli dari WebView
                                                val originalBitmap = Bitmap.createBitmap(picture.width, picture.height, Bitmap.Config.ARGB_8888)
                                                Canvas(originalBitmap).apply { drawPicture(picture) }

                                                Log.d("ObjectDetection", "Original image obtained: Width = ${originalBitmap.width}, Height = ${originalBitmap.height}")

                                                // Menyesuaikan gambar dengan resolusi yang diharapkan (640x640)
                                                val scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, 640, 640, true)

                                                Log.d("ObjectDetection", "Image resized to: Width = ${scaledBitmap.width}, Height = ${scaledBitmap.height}")

                                                // Konversi Bitmap ke ByteBuffer untuk input model
                                                val inputBuffer = ByteBuffer.allocateDirect(1 * 640 * 640 * 3 * 4).apply {
                                                    order(ByteOrder.nativeOrder())
                                                    for (y in 0 until 640) {
                                                        for (x in 0 until 640) {
                                                            val pixel = scaledBitmap.getPixel(x, y)

                                                            // Normalisasi nilai piksel ke rentang 0 hingga 1
                                                            val red = (pixel shr 16 and 0xFF) / 255.0f
                                                            val green = (pixel shr 8 and 0xFF) / 255.0f
                                                            val blue = (pixel and 0xFF) / 255.0f

                                                            // Setiap saluran warna (R, G, B) ditambahkan ke buffer
                                                            putFloat(red)
                                                            putFloat(green)
                                                            putFloat(blue)
                                                        }
                                                    }
                                                }

                                                Log.d("ObjectDetection", "ByteBuffer created: Capacity = ${inputBuffer.capacity()}, Remaining = ${inputBuffer.remaining()}")


                                                // Jalankan inferensi dengan model
                                                val output = model.runInference(inputBuffer)

                                                Log.d("ObjectDetection", "Inference completed, processing output")

                                                // Proses output model
                                                val peopleCount = processModelOutput(output)

                                                // Update UI dengan jumlah orang yang terdeteksi
                                                detectedPeopleCount = peopleCount
                                                Log.d("ObjectDetection", "People detected: $detectedPeopleCount")
                                            }


                                        }
                                    }
                                } catch (e: Exception) {
                                    Log.e("ObjectDetection", "Error during object detection", e)
                                }
                                delay(1000)  // Tambahkan delay untuk memberikan waktu antar frame
                            }
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        if (webView != null) {
                            AndroidView(factory = { webView!! })
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "LuminaSense",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "IP Address: $ipAddress",
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Jumlah Orang yang Terdeteksi: ${detectedPeopleCount}",
                            fontSize = 16.sp
                        )

                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Tidak ada IP Address yang tersimpan",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Button(onClick = {
                            navController.navigate("ipInput")
                        }) {
                            Text("Set IP Address")
                        }
                    }
                }
            }
        }
    )
}



class ObjectDetectionModel(context: Context) {
    private val interpreter: Interpreter

    init {
        Log.d("ObjectDetectionModel", "Loading TFLite model")
        val modelFile = context.assets.open("best_float32.tflite").use { it.readBytes() }
        val buffer = ByteBuffer.allocateDirect(modelFile.size).apply {
            order(ByteOrder.nativeOrder())
            put(modelFile)
        }
        interpreter = Interpreter(buffer)
        Log.d("ObjectDetectionModel", "Model loaded successfully")
    }

    fun runInference(inputImage: ByteBuffer): Array<Array<Array<Float>>> {
        val output = Array(1) { Array(5) { Array(8400) { 0.0f } } }
        Log.d("ObjectDetectionModel", "Running inference")
        interpreter.run(inputImage, output)
        Log.d("ObjectDetectionModel", "Inference completed")
        return output
    }
}

fun processModelOutput(output: Array<Array<Array<Float>>>): Int {
    // Mengasumsikan bahwa output[0][0..4][...] berisi informasi yang relevan
    // Sesuaikan logika ini berdasarkan output model Anda yang tepat
    var personCount = 0
    for (i in 0 until output[0][0].size) {
        val classId = output[0][4][i]
        Log.d("ObjectDetection", "Detected class ID: $classId")
        if (classId == 0.0f) {  // Asumsikan "0.0f" adalah ID kelas untuk "person"
            personCount++
        }
    }
    Log.d("ObjectDetection", "Total people detected: $personCount")
    return personCount
}

//
//@Composable
//fun LivePortraitScreen(navController: NavController) {
//    val CONFIDENCE_THRESHOLD = 0.1 // atau nilai yang sesuai dengan kebutuhan Anda
//    val context = LocalContext.current
//    val sharedPrefHelper = SharedPrefHelper(context)
//    val ipAddress = sharedPrefHelper.getIpAddress()
//    val detectionResults = remember { mutableStateListOf<Detection>() }
//    val coroutineScope = rememberCoroutineScope()
//    var bitmapWithBoxes by remember { mutableStateOf<Bitmap?>(null) }
//
//    Scaffold(
//        content = { paddingValues ->
//            Surface(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(paddingValues),
//                color = MaterialTheme.colorScheme.background
//            ) {
//                if (ipAddress != null) {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(16.dp)
//                    ) {
//                        AndroidView(
//                            factory = {
//                                WebView(it).apply {
//                                    webViewClient = object : WebViewClient() {
//                                        override fun onPageFinished(view: WebView?, url: String?) {
//                                            super.onPageFinished(view, url)
//                                            view?.evaluateJavascript(
//                                                "javascript:(function() {" +
//                                                        "var img = document.getElementsByTagName('img')[0];" +
//                                                        "img.style.width = '100%';" +
//                                                        "img.style.height = 'auto';" +
//                                                        "})()",
//                                                null
//                                            )
//                                        }
//                                    }
//                                    webChromeClient = WebChromeClient()
//                                    settings.javaScriptEnabled = true
//                                    settings.loadWithOverviewMode = true
//                                    settings.useWideViewPort = true
//                                    loadUrl("http://$ipAddress:81/stream")
//                                }
//                            },
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .fillMaxHeight(0.27f)
//                                .clip(RoundedCornerShape(10.dp))
//                        )
//
//                        Spacer(modifier = Modifier.height(8.dp))
//
//                        Text(
//                            text = "LuminaSense",
//                            fontSize = 24.sp,
//                            fontWeight = FontWeight.Bold,
//                            modifier = Modifier.align(Alignment.CenterHorizontally)
//                        )
//
//                        Spacer(modifier = Modifier.height(16.dp))
//
//                        Text(
//                            text = "IP Address: $ipAddress",
//                            fontSize = 16.sp
//                        )
//
//                        Spacer(modifier = Modifier.height(8.dp))
//
//                        Text(
//                            text = "Jumlah Orang yang Terdeteksi: ${detectionResults.size}",
//                            fontSize = 16.sp
//                        )
//
//                        // Display detection results
//                        detectionResults.forEach { result ->
//                            Text(
//
//                                text = "Label: ${result.categories.first().label}, Score: ${result.categories.first().score}",
//                                fontSize = 14.sp
//                            )
//                        }
//
//                        Spacer(modifier = Modifier.height(16.dp))
//
//                        bitmapWithBoxes?.let {
//                            Image(
//                                bitmap = it.asImageBitmap(),
//                                contentDescription = null,
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .clip(RoundedCornerShape(10.dp))
//                            )
//                        }
//                    }
//
//                    // Schedule detection
//                    LaunchedEffect(key1 = Unit) {
//                        while (true) {
//                            coroutineScope.launch {
//                                val bitmap = captureBitmapFromStream("http://$ipAddress:81/stream")
//                                if (bitmap != null) {
//                                    val detectionHelper = ObjectDetectionHelper(
//                                        context = context,
//                                        modelName = "yolov8m_float32.tflite",
//                                        detectorListener = object : ObjectDetectionHelper.DetectorListener {
//                                            override fun onError(error: String) {
//                                                Log.e("ObjectDetection", error)
//                                            }
//
//                                            override fun onResults(
//                                                results: MutableList<Detection>?,
//                                                inferenceTime: Long,
//                                                imageHeight: Int,
//                                                imageWidth: Int,
//                                                bitmapWithBoxesResult: Bitmap
//                                            ) {
//                                                detectionResults.clear()
//                                                results?.forEach { result ->
//                                                    result.categories.forEach { category ->
//                                                        val label = category.label
//                                                        val score = category.score
//                                                        if (score > CONFIDENCE_THRESHOLD) {
//                                                            detectionResults.add(result)
//                                                            Log.d("ObjectDetection", "Label: $label, Score: $score")
//                                                        }
//                                                    }
//                                                }
//                                                bitmapWithBoxes = bitmapWithBoxesResult
//                                            }
//                                        }
//                                    )
//
//                                    detectionHelper.detectObject(bitmap)
//                                } else {
//                                    Log.e("LivePortraitScreen", "Bitmap capture failed")
//                                }
//                            }
//                            kotlinx.coroutines.delay(1000) // Delay for 1 second before next detection
//                        }
//                    }
//                } else {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(16.dp),
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Text(
//                            text = "Tidak ada IP Address yang tersimpan",
//                            color = MaterialTheme.colorScheme.error,
//                            modifier = Modifier.padding(bottom = 16.dp)
//                        )
//                        Button(onClick = {
//                            navController.navigate("ipInput")
//                        }) {
//                            Text("Set IP Address")
//                        }
//                    }
//                }
//            }
//        }
//    )
//}
//
//// Function to capture Bitmap from the ESP32CAM stream
//suspend fun captureBitmapFromStream(url: String): Bitmap? = withContext(Dispatchers.IO) {
//    try {
//        val connection = URL(url).openConnection() as HttpURLConnection
//        connection.doInput = true
//        connection.connect()
//        val inputStream = connection.inputStream
//        BitmapFactory.decodeStream(inputStream)
//    } catch (e: Exception) {
//        e.printStackTrace()
//        null
//    }
//}
//]