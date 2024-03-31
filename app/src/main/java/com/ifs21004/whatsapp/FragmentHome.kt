package com.ifs21004.whatsapp

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ifs21004.whatsapp.databinding.HomeFragmentBinding

class HomeFragment : Fragment() {
    private lateinit var binding: HomeFragmentBinding
    private val dataEmail: ArrayList<Email> = ArrayList()
    private lateinit var menu_btn: FloatingActionButton // tambahkan ini di atas onCreateView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        // Initialize views
        menu_btn = view.findViewById<FloatingActionButton>(R.id.menu_btn)

        menu_btn.setOnClickListener {
            Toast.makeText(context, "Kamu memilih menu Create New Email", Toast.LENGTH_SHORT).show()
        }

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding = FragmentHomeBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        arguments?.let {
            val message = it.getString(EXTRA_MESSAGE)
            binding.tvFragmentHomeMessage.text = message
        }

        binding.apply {
            svFragmentHome.setupWithSearchBar(sbFragmentHome)
            svFragmentHome
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    val keyword = svFragmentHome.text.toString().toLowerCase() // Ambil keyword pencarian
                    val filteredEmails = dataEmail.filter { // Filter email sesuai keyword
                        it.name.toLowerCase().contains(keyword) ||
                                it.subject.toLowerCase().contains(keyword) ||
                                it.text.toLowerCase().contains(keyword)
                    }.toMutableList()
                    showRecyclerList(ArrayList(filteredEmails)) // Convert MutableList to ArrayList
                    svFragmentHome.hide()
                    false
                }
        }



        dataEmail.addAll(getListEmail())
        showRecyclerList()
        binding.rvEmail.setHasFixedSize(false)
    }


    companion object {
        const val EXTRA_MESSAGE = "extra_message"
    }

    @SuppressLint("Recycle")
    private fun getListEmail(): ArrayList<Email> {
        val dataSender =
            resources.getStringArray(R.array.email_sender)
        val dataIcon =
            resources.obtainTypedArray(R.array.email_icon)
        val dataSubject =
            resources.getStringArray(R.array.email_subject)
        val dataText =
            resources.getStringArray(R.array.email_text)

        val listEmail = ArrayList<Email>()
        for (i in dataSender.indices) {
            val email = Email(
                dataSender[i],
                dataIcon.getResourceId(i, -1),
                dataSubject[i],
                dataText[i])
            listEmail.add (email)
        }
        return listEmail
    }

    private fun showRecyclerList(emails: ArrayList<Email> = dataEmail) {
        val layoutManager = if (requireActivity().resources.configuration.orientation ==
            Configuration.ORIENTATION_LANDSCAPE) {
            GridLayoutManager(requireContext(), 2)
        } else {
            LinearLayoutManager(requireContext())
        }
        binding.rvEmail.layoutManager = layoutManager
        val listEmailAdapter = ListEmailAdapter(emails)
        binding.rvEmail.adapter = listEmailAdapter
    }



}