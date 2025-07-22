package com.ahmad.ecampus

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.ahmad.ecampus.databinding.ActivityEditDataBinding
import com.ahmad.ecampus.helper.StudentDbHelper
import com.ahmad.ecampus.model.Student
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class EditDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditDataBinding
    private lateinit var dbHelper: StudentDbHelper
    private var studentId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = StudentDbHelper(this)
        studentId = intent.getIntExtra("STUDENT_ID", -1)

        setupStatusBar()
        setupToolbar()
        setupDatePicker()
        loadStudentData()
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

    private fun loadStudentData() {
        val student = dbHelper.getById(studentId)
        student?.let {
            binding.etNim.setText(it.studentId)
            binding.etName.setText(it.name)
            binding.etBirthDate.setText(it.birthDate)
            binding.etAddress.setText(it.address)
            when (it.gender) {
                getString(R.string.male) -> binding.rbMale.isChecked = true
                getString(R.string.female) -> binding.rbFemale.isChecked = true
            }
            binding.btnUpdate.text = getString(R.string.update)
        }
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
        binding.btnUpdate.setOnClickListener {
            val updatedStudent = Student(
                id = studentId,
                studentId = binding.etNim.text.toString().trim(),
                name = binding.etName.text.toString().trim(),
                birthDate = binding.etBirthDate.text.toString().trim(),
                gender = if (binding.rbMale.isChecked) getString(R.string.male) else getString(R.string.female),
                address = binding.etAddress.text.toString().trim()
            )

            val result = dbHelper.update(updatedStudent)
            if (result > 0) {
                Toast.makeText(this, "Data updated successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to update data", Toast.LENGTH_SHORT).show()
            }
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
