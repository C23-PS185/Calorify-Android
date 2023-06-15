package com.calorify.app.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.calorify.app.R
import com.calorify.app.data.remote.response.AssessmentResultResponse
import com.calorify.app.databinding.ActivityAssessmentResultBinding
import com.calorify.app.helper.NetworkManager
import com.calorify.app.helper.Result
import com.calorify.app.viewmodel.AssessmentResultViewModel
import com.calorify.app.viewmodel.ViewModelFactory

class AssessmentResultActivity : AppCompatActivity() {
    private var _binding: ActivityAssessmentResultBinding? = null
    private val binding get() = _binding!!

    private val assessmentResultViewModel by viewModels<AssessmentResultViewModel> {
        ViewModelFactory.getInstance(application)
    }

    private lateinit var userID: String
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        if (NetworkManager.isConnectedToNetwork(this)) {
            super.onCreate(savedInstanceState)
            _binding = ActivityAssessmentResultBinding.inflate(layoutInflater)
            setContentView(binding.root)

            userID = intent.getStringExtra(EXTRA_USER_ID).toString()

            showAssessmentResult()

            binding.buttonUpgrade.setOnClickListener {
                Toast.makeText(
                    this@AssessmentResultActivity,
                    "Tunggu fitur ini pada pengembangan selanjutnya",
                    Toast.LENGTH_SHORT
                ).show()
            }

            binding.buttonBeranda.setOnClickListener {
                intent = Intent(this@AssessmentResultActivity, HomeActivity::class.java)
                startActivity(intent)
            }
        } else {
            val i = Intent(this, NoConnectionActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
            finish()
        }
    }

    private fun showAssessmentResult() {
        assessmentResultViewModel.getUserResult(userID)
            .observe(this@AssessmentResultActivity) { result ->
                binding.apply {
                    if (result != null) {
                        when (result) {
                            is Result.Loading -> {
                                progressBar.visibility = View.VISIBLE
                            }

                            is Result.Success -> {
                                progressBar.visibility = View.GONE
                                setAssessmentResult(result.data)
                            }

                            is Result.Error -> {
                                progressBar.visibility = View.GONE
                                Toast.makeText(
                                    this@AssessmentResultActivity,
                                    "Error",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
    }

    private fun setAssessmentResult(result: AssessmentResultResponse) {
        val statusKesehatanValue = resources.getStringArray(R.array.tujuan_kesehatan)
        binding.apply {
            tvWeightValue.text =
                getString(R.string.weight_value, result.data?.userWeight.toString())
            tvHeightValue.text =
                getString(R.string.height_value, result.data?.userHeight.toString())
            tvIndexBmiValue.text = result.data?.userBMI.toString()
            tvCaloryValue.text = result.data?.userCalorieIntake.toString()

            when (result.data?.weightGoal) {
                0 -> tvTujuanValue.text = statusKesehatanValue[0]
                1 -> tvTujuanValue.text = statusKesehatanValue[1]
                2 -> tvTujuanValue.text = statusKesehatanValue[2]
                3 -> tvTujuanValue.text = statusKesehatanValue[3]
                4 -> tvTujuanValue.text = statusKesehatanValue[4]
            }

            when (result.data?.userBMI!!.toFloat()) {
                in Float.MIN_VALUE..18.4f -> indicatorUnderweight.visibility = View.VISIBLE
                in 18.5f..24.9f -> indicatorNormal.visibility = View.VISIBLE
                in 25.0f..29.9f -> indicatorOverweight.visibility = View.VISIBLE
                in 30.0f..Float.MAX_VALUE -> indicatorObesitas.visibility = View.VISIBLE
                else -> indicatorObesitas.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        const val EXTRA_USER_ID = "extra_user_id"
    }
}