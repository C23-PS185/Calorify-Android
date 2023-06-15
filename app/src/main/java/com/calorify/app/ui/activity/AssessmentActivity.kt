package com.calorify.app.ui.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.calorify.app.R
import com.calorify.app.data.remote.request.AssessmentRequest
import com.calorify.app.databinding.ActivityAssessmentBinding
import com.calorify.app.helper.NetworkManager
import com.calorify.app.helper.Result
import com.calorify.app.viewmodel.AssessmentViewModel
import com.calorify.app.viewmodel.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.Calendar

class AssessmentActivity : AppCompatActivity() {
    private var _binding: ActivityAssessmentBinding? = null
    private val binding get() = _binding!!

    private val assessmentViewModel by viewModels<AssessmentViewModel> {
        ViewModelFactory.getInstance(application)
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    private lateinit var userid: String

    private var gender: String? = null

    private var activityIndex: Int? = null
    private var stressorIndex: Int? = null
    private var weightIndex: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        if (NetworkManager.isConnectedToNetwork(this)) {
            super.onCreate(savedInstanceState)
            _binding = ActivityAssessmentBinding.inflate(layoutInflater)
            setContentView(binding.root)

            auth = Firebase.auth
            currentUser = auth.currentUser!!
            userid = currentUser.uid

            editTextCalendar()

            dropDownActivity()
            dropDownStressor()
            dropDownKesehatan()

            uploadAssessment()
        } else {
            val i = Intent(this, NoConnectionActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
            finish()
        }
    }

    private fun uploadAssessment() {
        binding.buttonConfirm.setOnClickListener {
            binding.apply {
                val etName = etName.text.toString()
                val etBirth = etBirth.text.toString()
                val radioGroup = radioGroup
                val etWeight = etWeight.text.toString()
                val etHeight = etHeight.text.toString()
                val dropDownHeight = activityIndex
                val dropDownStressor = stressorIndex
                val dropDownKesehatan = weightIndex
                val isValid =
                    binding.etName.error == null && binding.etBirth.error == null && binding.etWeight.error == null && binding.etHeight.error == null && binding.dropDownActivity.error == null && binding.dropDownStressor.error == null && binding.dropDownKesehatan.error == null

                when {
                    etName.isEmpty() -> {
                        binding.etName.error = resources.getString(R.string.name_et_invalid)
                    }

                    etBirth.isEmpty() -> {
                        binding.etBirth.error = resources.getString(R.string.birth_et_invalid)
                    }

                    radioGroup.checkedRadioButtonId == -1 -> {
                        Toast.makeText(
                            this@AssessmentActivity,
                            "Mohon isi jenis kelamin",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    etWeight.isEmpty() -> {
                        binding.etWeight.error = resources.getString(R.string.weight_et_invalid)
                    }

                    etHeight.isEmpty() -> {
                        binding.etHeight.error = resources.getString(R.string.height_et_invalid)
                    }

                    dropDownHeight == null -> {
                        binding.dropDownActivity.error =
                            resources.getString(R.string.dd_height_et_invalid)
                    }

                    dropDownStressor == null -> {
                        binding.dropDownStressor.error =
                            resources.getString(R.string.dd_stressor_et_invalid)
                    }

                    dropDownKesehatan == null -> {
                        binding.dropDownKesehatan.error =
                            resources.getString(R.string.dd_helth_et_invalid)
                    }

                    isValid -> {
                        val body = AssessmentRequest(
                            userId = userid,
                            fullName = etName,
                            birthDate = etBirth,
                            gender = gender,
                            userHeight = etHeight,
                            userWeight = etWeight,
                            activityLevel = dropDownHeight,
                            stressLevel = dropDownStressor,
                            weightGoal = dropDownKesehatan
                        )

                        assessmentViewModel.setAssessmentData(body)
                        assessmentViewModel.uploadAssessment()
                            .observe(this@AssessmentActivity) { result ->
                                when (result) {
                                    is Result.Loading -> {
                                        progressBar.visibility = View.VISIBLE
                                        buttonConfirm.isEnabled = false
                                    }

                                    is Result.Success -> {
                                        progressBar.visibility = View.GONE
                                        buttonConfirm.isEnabled = false

                                        Toast.makeText(
                                            this@AssessmentActivity,
                                            "Success",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        intent = Intent(
                                            this@AssessmentActivity,
                                            AssessmentResultActivity::class.java
                                        )
                                        intent.putExtra(
                                            AssessmentResultActivity.EXTRA_USER_ID,
                                            userid
                                        )
                                        startActivity(intent)
                                        finish()
                                    }

                                    is Result.Error -> {
                                        progressBar.visibility = View.GONE
                                        buttonConfirm.isEnabled = true
                                        Toast.makeText(
                                            this@AssessmentActivity,
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

    private fun editTextCalendar() {
        binding.etBirth.setOnClickListener {
            val calendar = Calendar.getInstance()

            val years = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->
                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    binding.etBirth.setText(dat)
                }, years, month, day
            )
            datePickerDialog.show()
        }
    }

    private fun dropDownActivity() {
        val statusActivity = resources.getStringArray(R.array.status_ativitas)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, statusActivity)
        binding.dropDownActivity.setAdapter(arrayAdapter)

        binding.dropDownActivity.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            val statusActivityValue = resources.getStringArray(R.array.status_ativitas)
            activityIndex = when (selectedItem) {
                statusActivityValue[0] -> 0
                statusActivityValue[1] -> 1
                statusActivityValue[2] -> 2
                else -> null
            }
        }
    }

    private fun dropDownStressor() {
        val statusStressor = resources.getStringArray(R.array.status_stressor)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, statusStressor)
        binding.dropDownStressor.setAdapter(arrayAdapter)

        binding.dropDownStressor.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            val statusStressorValue = resources.getStringArray(R.array.status_stressor)
            stressorIndex = when (selectedItem) {
                statusStressorValue[0] -> 0
                statusStressorValue[1] -> 1
                statusStressorValue[2] -> 2
                statusStressorValue[3] -> 3
                statusStressorValue[4] -> 4
                else -> null
            }
        }
    }

    private fun dropDownKesehatan() {
        val statusKesehatan = resources.getStringArray(R.array.tujuan_kesehatan)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, statusKesehatan)
        binding.dropDownKesehatan.setAdapter(arrayAdapter)

        binding.dropDownKesehatan.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            val statusKesehatanValue = resources.getStringArray(R.array.tujuan_kesehatan)
            weightIndex = when (selectedItem) {
                statusKesehatanValue[0] -> 0
                statusKesehatanValue[1] -> 1
                statusKesehatanValue[2] -> 2
                statusKesehatanValue[3] -> 3
                statusKesehatanValue[4] -> 4
                else -> null
            }
        }
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked

            when (view.id) {
                R.id.radio_female ->
                    if (checked) {
                        gender = resources.getString(R.string.perempuan)
                    }

                R.id.radio_male ->
                    if (checked) {
                        gender = resources.getString(R.string.laki_laki)
                    }

                else -> {
                    gender = null
                }
            }
        }
    }
}