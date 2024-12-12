package com.capstone.diacare.presentation.home

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.capstone.diacare.databinding.FragmentDetectionBinding
import java.io.File
import java.time.Instant
import java.time.format.DateTimeFormatter


class DetectionActivity : AppCompatActivity() {
    companion object {
        const val SECOND_ACTIVITY_RESULT_CODE = 1012
        const val SECOND_ACTIVITY_BUNDLE_ID = "THIS_MUST_BE_URI!"
    }

    private lateinit var binding: FragmentDetectionBinding
    private lateinit var imageCapture: ImageCapture
    private var mUri: Uri? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDetectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imageCapture = ImageCapture.Builder().build()
        startCamera()
        binding.btnPicture.setOnClickListener {
            capturePhoto()
        }
        binding.btnDetect.setOnClickListener {
            val intent = Intent(
                this,
                MainActivity::class.java
            )
            intent.putExtra(SECOND_ACTIVITY_BUNDLE_ID, mUri.toString())

            setResult(SECOND_ACTIVITY_RESULT_CODE, intent)
            finish()
        }
        binding.btnCancel.setOnClickListener {
            mUri = null
            binding.previewView.visibility = View.VISIBLE
            binding.btnCancelDetect.visibility = View.GONE
            binding.btnPicture.visibility = View.VISIBLE
            binding.imageViewDetection.visibility = View.GONE
        }

    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.surfaceProvider = binding.previewView.surfaceProvider
                }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )

            } catch (exc: Exception) {
                Log.e("CameraX", "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun displayImage(uri: Uri) {
        binding.imageViewDetection.setImageURI(uri)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun capturePhoto() {

        val file = File(
            filesDir,
            "photo-${Instant.now()}.jpg"
        )
        val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val uri = Uri.fromFile(file)
                    mUri = uri
                    binding.previewView.visibility = View.GONE
                    binding.btnCancelDetect.visibility = View.VISIBLE
                    binding.btnPicture.visibility = View.GONE
                    binding.imageViewDetection.visibility = View.VISIBLE
                    displayImage(uri)
                }

                override fun onError(exception: ImageCaptureException) {
                    exception.printStackTrace()
                }
            }
        )
    }

}


