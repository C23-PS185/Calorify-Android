package com.calorify.app.ui.screen.scan

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import com.calorify.app.R
import com.calorify.app.databinding.ScanCalorieScreenBinding
import com.calorify.app.helper.Commons.REQUIRED_PERMISSIONS
import com.calorify.app.helper.createCustomTempFile
import java.io.File
import java.io.IOException

private lateinit var currentPhotoPath: String
private var getFile: File? = null

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
    LaunchedEffect(key1 = true) {
        launcher.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        )
    }

    var photoBitmap by remember { mutableStateOf<Bitmap?>(null) }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val binding = ScanCalorieScreenBinding.inflate(LayoutInflater.from(context))

            binding.buttonScan.setOnClickListener {
                if (hasCamPermission) {
                    val result = launchCameraIntent(context)
                    if (result != null) {
                        photoBitmap = result
                    }
                }
            }
            binding.root
        },
           update = { view ->
                val imageView = view.findViewById<ImageView>(R.id.imageView)
                if (photoBitmap != null) {
                    imageView.setImageBitmap(photoBitmap)
                }
//               val binding = ScanCalorieScreenBinding.inflate(LayoutInflater.from(context))
//               binding.icScan.setImageBitmap(photoBitmap)
            }
    )
}

private fun launchCameraIntent(context: Context): Bitmap? {
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
        // Set the output file URI for the camera intent
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        // Launch the camera intent
        context.startActivity(takePictureIntent)

        currentPhotoPath = it.absolutePath
        val myFile = File(currentPhotoPath)
        getFile = myFile
        return BitmapFactory.decodeFile(myFile.path)
    }
    return null
}