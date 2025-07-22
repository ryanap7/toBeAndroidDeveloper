package com.ahmad.ecampus

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.ahmad.ecampus.databinding.ActivityInputDataBinding
import com.ahmad.ecampus.helper.StudentDbHelper
import com.ahmad.ecampus.model.Student
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class InputDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputDataBinding
    private lateinit var dbHelper: StudentDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = StudentDbHelper(this)

        setupStatusBar()
        setupToolbar()
        setupDatePicker()
        setupSaveButton()
    }

    private fun setupStatusBar() {
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = true
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.input_data)
        }

        val white = ContextCompat.getColor(this, R.color.white)
        binding.toolbar.setTitleTextColor(white)
        binding.toolbar.navigationIcon?.setTint(white)
    }

    private fun setupDatePicker() {
        binding.etBirthDate.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Birth Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

            datePicker.show(supportFragmentManager, "DATE_PICKER")

            datePicker.addOnPositiveButtonClickListener { selection ->
                val date = Date(selection)
                val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                binding.etBirthDate.setText(formatter.format(date))
            }
        }
    }

    private fun setupSaveButton() {
        binding.btnSave.setOnClickListener {
            val student = collectInput()

            if (student == null) {
                Toast.makeText(this, "Please complete the form", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val success = dbHelper.insert(student) != -1L
            if (success) {
                Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun collectInput(): Student? {
        val nim = binding.etNim.text.toString().trim()
        val name = binding.etName.text.toString().trim()
        val birthDate = binding.etBirthDate.text.toString().trim()
        val address = binding.etAddress.text.toString().trim()
        val gender = when {
            binding.rbMale.isChecked -> "Male"
            binding.rbFemale.isChecked -> "Female"
            else -> ""
        }

        return if (nim.isEmpty() || name.isEmpty() || birthDate.isEmpty() || gender.isEmpty() || address.isEmpty()) {
            null
        } else {
            Student(studentId = nim, name = name, birthDate = birthDate, gender = gender, address = address)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            currentFocus!!.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }
}
