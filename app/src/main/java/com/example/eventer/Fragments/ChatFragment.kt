package com.example.eventer.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventer.Adapter.UserAdapter
import com.example.eventer.R
import com.example.eventer.model.UsersViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
//
//private lateinit var viewModel: ViewModel
//private lateinit var userRecyclerView: RecyclerView
//lateinit var adapter: UserAdapter

class ChatFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        view?.findViewById<Button>(R.id.addBtn)?.setOnClickListener {
            //val fragment = ProfileFragment()
            Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
             replaceFragment(ProfileFragment())


        }
    }

        //get view from fragment_chat.xml


        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment

            return inflater.inflate(R.layout.fragment_chat, container, false)
        }



    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()

    }

}
