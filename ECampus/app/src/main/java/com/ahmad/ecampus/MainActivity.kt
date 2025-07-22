package com.ahmad.ecampus

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.ahmad.ecampus.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupWindowAppearance()
        setupClickListeners()
    }

    private fun setupWindowAppearance() {
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true
    }

    private fun setupClickListeners() {
        binding.apply {
            btnViewData.setOnClickListener {
                navigateTo(ViewDataActivity::class.java)
            }
            btnInputData.setOnClickListener {
                navigateTo(InputDataActivity::class.java)
            }
            btnInfo.setOnClickListener {
                navigateTo(InfoActivity::class.java)
            }
        }
    }

    private fun navigateTo(target: Class<*>) {
        startActivity(Intent(this, target))
    }
}
