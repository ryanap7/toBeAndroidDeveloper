package com.ahmad.intent

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ahmad.intent.databinding.ActivityMainBinding
import androidx.core.net.toUri

class MainActivity : AppCompatActivity() {
    companion object {
        private const val WEBSITE_URL = "https://thamrin.ac.id"
        private const val REQUEST_CALL_PHONE = 1
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Call
        binding.btnCall.setOnClickListener {
            val phone = binding.etPhone.text.toString()
            if (phone.isNotEmpty()) {
                makeCall()
            } else {
                Toast.makeText(this, getString(R.string.phone_required), Toast.LENGTH_SHORT).show()
            }
        }

        // Open Browser
        binding.btnBrowser.setOnClickListener {
            val url = WEBSITE_URL
            openBrowser(url)
        }

        // Go to Profile Activity
        binding.btnGo.setOnClickListener {
            goToProfileActivity()
        }
    }


    private fun makeCall() {
        val phone = binding.etPhone.text.toString()

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.CALL_PHONE),
                REQUEST_CALL_PHONE)

        } else {
            val callIntent = Intent(Intent.ACTION_CALL).apply {
                data = "tel:$phone".toUri()
            }
            startActivity(callIntent)
        }
    }

    private fun openBrowser(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, url.toUri())
        startActivity(browserIntent)
    }

    private fun goToProfileActivity() {
        val profileIntent = Intent(this, ProfileActivity::class.java)
        startActivity(profileIntent)
    }
}