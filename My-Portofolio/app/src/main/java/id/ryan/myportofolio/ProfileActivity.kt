package id.ryan.myportofolio

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class ProfileActivity : AppCompatActivity(), View.OnClickListener {
	private lateinit var backBtn: RelativeLayout
	private lateinit var connectBtn: AppCompatButton
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_profile)
		
		backBtn = findViewById(R.id.btn_back)
		backBtn.setOnClickListener(this)
		
		connectBtn = findViewById(R.id.button)
		connectBtn.setOnClickListener(this)
	}
	
	override fun onClick(v: View?) {
		when (v?.id) {
			R.id.btn_back -> {
				finish()
			}
			R.id.button -> {
				val uri = Uri.parse("https://www.linkedin.com/in/ryan-aprianto-04b83212b/")
				
				val intent = Intent(Intent.ACTION_VIEW, uri)
				startActivity(intent)
			}
		}
	}
}