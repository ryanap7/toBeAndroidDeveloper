package com.ahmad.ecampus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.ahmad.ecampus.databinding.ActivityDetailBinding
import com.ahmad.ecampus.helper.StudentDbHelper

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var dbHelper: StudentDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = StudentDbHelper(this)

        setupWindowAppearance()
        setupToolbar()
        loadStudentDetail()
    }

    private fun setupWindowAppearance() {
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.detail_data)
        }
        val white = ContextCompat.getColor(this, R.color.white)
        binding.toolbar.setTitleTextColor(white)
        binding.toolbar.navigationIcon?.setTint(white)
    }

    private fun loadStudentDetail() {
        val studentId = intent.getIntExtra("STUDENT_ID", -1)
        val student = dbHelper.getById(studentId)

        student?.let {
            binding.tvNim.text = it.studentId
            binding.tvName.text = it.name
            binding.tvBirthDate.text = it.birthDate
            binding.tvGender.text = it.gender
            binding.tvAddress.text = it.address
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
