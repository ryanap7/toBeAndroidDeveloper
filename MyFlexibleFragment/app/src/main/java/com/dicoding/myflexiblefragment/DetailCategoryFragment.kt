package com.dicoding.myflexiblefragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class DetailCategoryFragment : Fragment() {
    private lateinit var tvCategoryName: TextView
    private lateinit var tvCategoryDescription: TextView
    private lateinit var btnProfile: Button
    private lateinit var btnShowDialog: Button

    var description: String? = null
    companion object {
        var EXTRA_NAME = "extra_name"
        var EXTRA_DESCRIPTION = "extra_description"
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvCategoryName = view.findViewById(R.id.tv_category_name)
        tvCategoryDescription = view.findViewById(R.id.tv_category_description)
        btnProfile = view.findViewById(R.id.btn_profile)
        btnShowDialog = view.findViewById(R.id.btn_show_dialog)

        btnShowDialog.setOnClickListener{
            val optionDialogFragment = OptionDialogFragment()
            val fragmentManager = childFragmentManager
            optionDialogFragment.show(fragmentManager, OptionDialogFragment::class.java.simpleName)
        }

        btnProfile.setOnClickListener{
            val intent = Intent(requireActivity(), ProfileActivity::class.java)
            startActivity(intent)
        }

        if (savedInstanceState != null) {
            val descFromBundle = savedInstanceState.getString(EXTRA_DESCRIPTION)
            description = descFromBundle
        }
        if (arguments != null) {
            val categoryName = arguments?.getString(EXTRA_NAME)
            tvCategoryName.text = categoryName
            tvCategoryDescription.text = description
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_category, container, false)
    }

    internal var optionDialogListener: OptionDialogFragment.OnOptionDialogListener = object : OptionDialogFragment.OnOptionDialogListener {
        override fun onOptionChosen(text: String?) {
            Toast.makeText(requireActivity(), text, Toast.LENGTH_SHORT).show()
        }
    }
}