package id.ryan.myportofolio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

class DetailActivity : AppCompatActivity(), View.OnClickListener {
	companion object {
		const val TITLE = "title"
		const val DESCRIPTION = "description"
		const val PHOTO = "photo"
	}
	
	private lateinit var backBtn: RelativeLayout
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_detail)
		
		backBtn = findViewById(R.id.btn_back)
		backBtn.setOnClickListener(this)
		
		val tvTitle: TextView = findViewById(R.id.tv_title)
		val tvDescription : TextView = findViewById(R.id.tv_description)
		val imgPhoto: ImageView = findViewById(R.id.img_photo)
		
		val title = intent.getStringExtra(TITLE)
		val description = intent.getStringExtra(DESCRIPTION)
		val photo = intent.getIntExtra(PHOTO, 0)
		
		tvTitle.text = title
		tvDescription.text = description
		imgPhoto.setImageResource(photo);
	}
	
	override fun onClick(v: View?) {
		finish()
	}
}