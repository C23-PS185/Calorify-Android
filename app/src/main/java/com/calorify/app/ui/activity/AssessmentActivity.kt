package com.calorify.app.ui.activity

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.calorify.app.R
import com.calorify.app.databinding.ActivityAssessmentBinding
import java.util.Calendar

class AssessmentActivity : AppCompatActivity() {
    private var _binding: ActivityAssessmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAssessmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editTextCalendar()
        dropDownHeight()
        dropDownStressor()
        dropDownKesehatan()

    }

    private fun editTextCalendar() {
        binding.etBirth.setOnClickListener {
            val calendar = Calendar.getInstance()

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    binding.etBirth.setText(dat)
                }, year, month, day
            )
            datePickerDialog.show()
        }
    }

    private fun dropDownHeight() {
        val statusFisik = resources.getStringArray(R.array.status_fisik)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, statusFisik)
        binding.dropDownHeight.setAdapter(arrayAdapter)

        binding.dropDownHeight.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            Toast.makeText(this, selectedItem, Toast.LENGTH_SHORT).show()
        }
    }

    private fun dropDownStressor() {
        val statusStressor = resources.getStringArray(R.array.status_stressor)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, statusStressor)
        binding.dropDownStressor.setAdapter(arrayAdapter)

        binding.dropDownStressor.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            Toast.makeText(this, selectedItem, Toast.LENGTH_SHORT).show()
        }
    }

    private fun dropDownKesehatan() {
        val statusKesehatan = resources.getStringArray(R.array.tujuan_kesehatan)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, statusKesehatan)
        binding.dropDownKesehatan.setAdapter(arrayAdapter)

        binding.dropDownKesehatan.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            Toast.makeText(this, selectedItem, Toast.LENGTH_SHORT).show()
        }
    }

    fun onRadioButtonClicked(view: View) {}
}