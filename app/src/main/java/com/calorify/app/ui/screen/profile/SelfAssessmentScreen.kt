package com.calorify.app.ui.screen.profile

import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.calorify.app.R
import com.calorify.app.data.remote.request.AssessmentUpdateRequest
import com.calorify.app.data.remote.response.DataUser
import com.calorify.app.databinding.AssessmentScreenBinding
import com.calorify.app.helper.Result
import com.calorify.app.viewmodel.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SelfAssessmentScreen(
    lifecycleOwner: LifecycleOwner,
    userId: String,
    profileViewModel: ProfileViewModel,
    getString: (Int) -> String,
    getStringArr: (Int) -> Array<String>,
    arrAdapter: (Int, Array<String>) -> ArrayAdapter<String>,
    onSuccess: (DataUser) -> Unit,
    moveToResult: () -> Unit,
) {
    var activityIndex: Int? by remember { mutableStateOf(null) }
    var stressorIndex: Int? by remember { mutableStateOf(null) }
    var weightIndex: Int? by remember { mutableStateOf(null) }

    AndroidView(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        factory = { context ->
            val binding = AssessmentScreenBinding.inflate(LayoutInflater.from(context))

            val statusActivity = getStringArr(R.array.status_ativitas)
            val arrayAdapterActivity = arrAdapter(R.layout.dropdown_item, statusActivity)
            binding.dropDownActivity.setAdapter(arrayAdapterActivity)

            binding.dropDownActivity.setOnItemClickListener { parent, _, position, _ ->
                val selectedItem = parent.getItemAtPosition(position).toString()
                val statusActivityValue = getStringArr(R.array.status_ativitas)
                activityIndex = when (selectedItem) {
                    statusActivityValue[0] -> 0
                    statusActivityValue[1] -> 1
                    statusActivityValue[2] -> 2
                    else -> null
                }
            }

            val statusStressor = getStringArr(R.array.status_stressor)
            val arrayAdapterStressor = arrAdapter(R.layout.dropdown_item, statusStressor)
            binding.dropDownStressor.setAdapter(arrayAdapterStressor)

            binding.dropDownStressor.setOnItemClickListener { parent, _, position, _ ->
                val selectedItem = parent.getItemAtPosition(position).toString()
                val statusStressorValue = getStringArr(R.array.status_stressor)
                stressorIndex = when (selectedItem) {
                    statusStressorValue[0] -> 0
                    statusStressorValue[1] -> 1
                    statusStressorValue[2] -> 2
                    statusStressorValue[3] -> 3
                    statusStressorValue[4] -> 4
                    else -> null
                }
            }

            val statusKesehatan = getStringArr(R.array.tujuan_kesehatan)
            val arrayAdapterKesehatan = arrAdapter(R.layout.dropdown_item, statusKesehatan)
            binding.dropDownKesehatan.setAdapter(arrayAdapterKesehatan)

            binding.dropDownKesehatan.setOnItemClickListener { parent, _, position, _ ->
                val selectedItem = parent.getItemAtPosition(position).toString()
                val statusKesehatanValue = getStringArr(R.array.tujuan_kesehatan)
                weightIndex = when (selectedItem) {
                    statusKesehatanValue[0] -> 0
                    statusKesehatanValue[1] -> 1
                    statusKesehatanValue[2] -> 2
                    statusKesehatanValue[3] -> 3
                    statusKesehatanValue[4] -> 4
                    else -> null
                }
            }

            binding.buttonConfirm.setOnClickListener {
                binding.apply {
                    val etWeight = etWeight.text.toString()
                    val etHeight = etHeight.text.toString()
                    val dropDownHeight = activityIndex
                    val dropDownStressor = stressorIndex
                    val dropDownKesehatan = weightIndex
                    val isValid =
                        binding.etWeight.error == null && binding.etHeight.error == null && binding.dropDownActivity.error == null && binding.dropDownStressor.error == null && binding.dropDownKesehatan.error == null

                    when {
                        etWeight.isEmpty() -> {
                            binding.etWeight.error = getString(R.string.weight_et_invalid)
                        }

                        etHeight.isEmpty() -> {
                            binding.etHeight.error = getString(R.string.height_et_invalid)
                        }

                        dropDownHeight == null -> {
                            binding.dropDownActivity.error =
                                getString(R.string.dd_height_et_invalid)
                        }

                        dropDownStressor == null -> {
                            binding.dropDownStressor.error =
                                getString(R.string.dd_stressor_et_invalid)
                        }

                        dropDownKesehatan == null -> {
                            binding.dropDownKesehatan.error =
                                getString(R.string.dd_helth_et_invalid)
                        }

                        isValid -> {
                            val body = AssessmentUpdateRequest(
                                userHeight = etHeight.toIntOrNull(),
                                userWeight = etWeight.toIntOrNull(),
                                activityLevel = dropDownHeight,
                                stressLevel = dropDownStressor,
                                weightGoal = dropDownKesehatan
                            )

                            profileViewModel.uploadUpdateAssessment(userId, body)
                                .observe(lifecycleOwner) { result ->
                                    when (result) {
                                        is Result.Loading -> {
                                            progressBar.visibility = View.VISIBLE
                                            buttonConfirm.isEnabled = false
                                        }

                                        is Result.Success -> {
                                            progressBar.visibility = View.GONE
                                            buttonConfirm.isEnabled = true

                                            onSuccess(result.data.data!!)
                                            moveToResult()
                                        }

                                        is Result.Error -> {
                                            progressBar.visibility = View.GONE
                                            buttonConfirm.isEnabled = true
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
        })
}