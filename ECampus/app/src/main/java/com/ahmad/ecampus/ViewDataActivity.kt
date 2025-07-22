package com.ahmad.ecampus

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmad.ecampus.adapter.StudentAdapter
import com.ahmad.ecampus.databinding.ActivityViewDataBinding
import com.ahmad.ecampus.helper.StudentDbHelper
import com.ahmad.ecampus.helper.SwipeHelperCallback
import com.ahmad.ecampus.model.Student

class ViewDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewDataBinding
    private lateinit var dbHelper: StudentDbHelper
    private lateinit var adapter: StudentAdapter
    private var studentList: MutableList<Student> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = StudentDbHelper(this)

        setupStatusBar()
        setupToolbar()
        setupRecyclerView()
        setupFab()
        setupSwipeActions()
    }

    override fun onResume() {
        super.onResume()
        loadStudents()
    }

    private fun setupStatusBar() {
        WindowCompat.getInsetsController(window, window.decorView)
            .isAppearanceLightStatusBars = true
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.show_data)
        }
        val white = ContextCompat.getColor(this, R.color.white)
        binding.toolbar.setTitleTextColor(white)
        binding.toolbar.navigationIcon?.setTint(white)
    }

    private fun setupRecyclerView() {
        adapter = StudentAdapter(studentList) { student ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("STUDENT_ID", student.id)
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun setupSwipeActions() {
        val swipeHelper = SwipeHelperCallback(
            context = this,
            onSwipeLeft = { position ->
                val student = studentList[position]
                AlertDialog.Builder(this)
                    .setTitle("Delete Confirmation")
                    .setMessage("Are you sure you want to delete ${student.name}?")
                    .setPositiveButton("Delete") { _, _ ->
                        dbHelper.delete(student.id)
                        studentList.removeAt(position)
                        adapter.notifyItemRemoved(position)
                        loadStudents()
                    }
                    .setNegativeButton("Cancel") { _, _ ->
                        adapter.notifyItemChanged(position)
                    }
                    .setCancelable(false)
                    .show()
            },
            onSwipeRight = { position ->
                val student = studentList[position]
                val intent = Intent(this, EditDataActivity::class.java)
                intent.putExtra("STUDENT_ID", student.id)
                startActivity(intent)
                adapter.notifyItemChanged(position)
            }
        )
        ItemTouchHelper(swipeHelper).attachToRecyclerView(binding.recyclerView)
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun loadStudents() {
        val data = dbHelper.getAll()
        studentList.clear()
        studentList.addAll(data)
        adapter.notifyDataSetChanged()

        if (studentList.isEmpty()) {
            binding.recyclerView.visibility = View.GONE
            binding.tvEmptyState.visibility = View.VISIBLE
        } else {
            binding.recyclerView.visibility = View.VISIBLE
            binding.tvEmptyState.visibility = View.GONE
        }
    }

    private fun setupFab() {
        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, InputDataActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
