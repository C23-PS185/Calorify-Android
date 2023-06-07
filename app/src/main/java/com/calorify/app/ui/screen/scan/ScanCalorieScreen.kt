package com.calorify.app.ui.screen.scan

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.calorify.app.R
import com.calorify.app.databinding.ScanCalorieScreenBinding
import com.calorify.app.helper.Commons.REQUIRED_PERMISSIONS
import com.calorify.app.helper.createCustomTempFile
import com.calorify.app.helper.uriToFile
import com.calorify.app.ui.navigation.Screen
import kotlinx.coroutines.delay
import java.io.File
import java.io.IOException

private lateinit var currentPhotoPath: String
private var getFile: File? = null

var photoBitmap by mutableStateOf<Bitmap?>(null)

@Composable
fun ScanCalorieScreen(
    context: Context,
    onScanResultClick: () -> Unit,
) {
    var hasCamPermission by remember {
        mutableStateOf(
            REQUIRED_PERMISSIONS.all {
                ContextCompat.checkSelfPermission(context, it) ==
                        PackageManager.PERMISSION_GRANTED
            })
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { granted ->
            hasCamPermission = granted.size == 2
        }
    )

    val launcherIntentCamera = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val myFile = File(currentPhotoPath)

            myFile.let { file ->
                getFile = file
                photoBitmap = BitmapFactory.decodeFile(file.path)
            }
        }
    }

    val launcherIntentGallery = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, context)
                getFile = myFile
                photoBitmap = BitmapFactory.decodeFile(myFile.path)
            }
        }
    }

    LaunchedEffect(key1 = true) {
        launcher.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        )
    }

    LaunchedEffect(photoBitmap) {
        if (photoBitmap != null) {
            onScanResultClick()
        }
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val binding = ScanCalorieScreenBinding.inflate(LayoutInflater.from(context))

            binding.buttonScan.setOnClickListener {
                if (hasCamPermission) {
                    startCamera(context, launcherIntentCamera)
                }
            }
            binding.buttonGallery.setOnClickListener {
                startGallery(launcherIntentGallery)
            }
            binding.root
        },
    )
}

private fun startCamera(
    context: Context,
    launcherIntentCamera: ManagedActivityResultLauncher<Intent, ActivityResult>
) {
    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    // Create a file to save the image
    val photoFile: File? = try {
        createCustomTempFile(context)
    } catch (ex: IOException) {
        Log.e("image", "Error creating image file: ${ex.message}", ex)
        null
    }
    // Continue if the file was successfully created
    photoFile?.let {
        val photoUri: Uri = FileProvider.getUriForFile(
            context,
            "com.calorify.app",
            it
        )
        currentPhotoPath = it.absolutePath
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        launcherIntentCamera.launch(takePictureIntent)
    }
}

private fun startGallery(
    launcherIntentGallery: ManagedActivityResultLauncher<Intent, ActivityResult>
) {
    val intent = Intent(Intent.ACTION_PICK)
    intent.type = "image/*"
    launcherIntentGallery.launch(intent)
}