package com.calorify.app.ui.screen.profile

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import coil.compose.AsyncImage
import com.calorify.app.R
import com.calorify.app.data.remote.response.Profil
import com.calorify.app.databinding.EditProfileScreenBinding
import com.calorify.app.helper.Result
import com.calorify.app.helper.reduceFileImage
import com.calorify.app.helper.uriToFile
import com.calorify.app.ui.theme.Black
import com.calorify.app.ui.theme.White
import com.calorify.app.viewmodel.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.Calendar

private lateinit var currentPhotoPath: String
private var getFile: File? = null

@Composable
fun EditProfileScreen(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    userId: String,
    profileViewModel: ProfileViewModel,
    getString: (Int) -> String,
    onSuccess: (Profil) -> Unit,
    moveToProfile: () -> Unit,
    photoUrl: String,
    name: String,
    birthDate: String,
    gender: String,
) {
    var openGallery by remember { mutableStateOf(false) }
    var newGender by remember { mutableStateOf(gender) }
    var photoBitmap: Bitmap? by remember { mutableStateOf(null) }

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

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Ubah Profil",
            fontFamily = FontFamily(
                Font(resId = R.font.inter_bold),
            ),
            fontSize = 24.sp,
            modifier = Modifier.padding(8.dp)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(8.dp)
                .size(200.dp)
                .clickable {
                    openGallery = true
                }
        ) {
            if (photoBitmap != null) {
                Image(
                    photoBitmap!!.asImageBitmap(),
                    stringResource(R.string.my_photo),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(200.dp)
                        .clip(CircleShape)
                )
            } else {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = stringResource(R.string.my_photo),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(200.dp)
                        .clip(CircleShape)
                )
            }
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = Black.copy(alpha = 0.5f),
                    radius = size.minDimension / 2
                )
            }
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_pencil),
                contentDescription = "Edit Image",
                tint = White,
            )
        }
        if (openGallery) {
            startGallery(launcherIntentGallery)
            openGallery = false
        }
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                val binding = EditProfileScreenBinding.inflate(LayoutInflater.from(context))
                binding.etName.setText(name)
                binding.etBirth.setText(birthDate)

                if (gender == "Perempuan") {
                    binding.radioFemale.isChecked = true
                } else {
                    binding.radioMale.isChecked = true
                }

                val onRadioButtonClicked: (View) -> Unit = { view ->
                    if (view is RadioButton) {
                        val checked = view.isChecked

                        when (view.id) {
                            R.id.radio_female -> {
                                if (checked) {
                                    newGender = "Perempuan"
                                }
                            }

                            R.id.radio_male -> {
                                if (checked) {
                                    newGender = "Laki-Laki"
                                }
                            }

                            else -> {
                                newGender = gender
                            }
                        }
                    }
                }

                binding.radioFemale.setOnClickListener(onRadioButtonClicked)
                binding.radioMale.setOnClickListener(onRadioButtonClicked)

                binding.etBirth.setOnClickListener {
                    val calendar = Calendar.getInstance()

                    val years = calendar.get(Calendar.YEAR)
                    val month = calendar.get(Calendar.MONTH)
                    val day = calendar.get(Calendar.DAY_OF_MONTH)

                    val datePickerDialog = DatePickerDialog(
                        context,
                        { _, year, monthOfYear, dayOfMonth ->
                            val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                            binding.etBirth.setText(dat)
                        }, years, month, day
                    )
                    datePickerDialog.show()
                }

                binding.buttonSave.setOnClickListener {
                    binding.apply {
                        val etName = etName.text.toString()
                        val etBirth = etBirth.text.toString()
                        val isValid = binding.etBirth.error == null && binding.etName.error == null

                        when {
                            etName.isEmpty() -> {
                                binding.etName.error = getString(R.string.name_et_invalid)
                            }

                            etBirth.isEmpty() -> {
                                binding.etBirth.error = getString(R.string.birth_et_invalid)
                            }

                            isValid -> {
                                Log.d("TAG", "EditProfileScreen: VALID")
                                val updatedName =
                                    etName.toRequestBody("text/plain".toMediaType())
                                val updatedBirthDate =
                                    etBirth.toRequestBody("text/plain".toMediaType())
                                val updateGender =
                                    newGender.toRequestBody("text/plain".toMediaType())
                                var imageMultipart: MultipartBody.Part? = null


                                if (getFile != null) {
                                    val file = reduceFileImage(getFile as File)
                                    val requestImageFile =
                                        file.asRequestBody("image/jpeg".toMediaType())
                                    imageMultipart =
                                        MultipartBody.Part.createFormData(
                                            "image",
                                            file.name,
                                            requestImageFile
                                        )
                                }

                                profileViewModel.uploadUpdateData(
                                    userId,
                                    updatedName,
                                    updatedBirthDate,
                                    updateGender,
                                    imageMultipart
                                )
                                    .observe(lifecycleOwner) { result ->
                                        when (result) {
                                            is Result.Loading -> {
                                                progressBar.visibility = View.VISIBLE
                                                buttonSave.isEnabled = false
                                            }

                                            is Result.Success -> {
                                                progressBar.visibility = View.GONE
                                                buttonSave.isEnabled = true

                                                onSuccess(result.data.data!!)
                                                moveToProfile()
                                            }

                                            is Result.Error -> {
                                                progressBar.visibility = View.GONE
                                                buttonSave.isEnabled = true
                                                CoroutineScope(Dispatchers.Main).launch {
                                                    // Show the toast message
                                                    Toast.makeText(
                                                        context,
                                                        result.error,
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                        }
                                    }
                            }
                        }
                    }
                }

                binding.root
            }
        )
    }
}

private fun startGallery(
    launcherIntentGallery: ManagedActivityResultLauncher<Intent, ActivityResult>
) {
    val intent = Intent(Intent.ACTION_PICK)
    intent.type = "image/*"
    launcherIntentGallery.launch(intent)
}