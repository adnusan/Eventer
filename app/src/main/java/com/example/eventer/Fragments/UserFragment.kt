package com.example.eventer.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
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
 * Use the [UserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

private lateinit var viewModel: UsersViewModel
private lateinit var userRecyclerView: RecyclerView
lateinit var adapterU: UserAdapter

class UserFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.users_fragment, container, false)

        // Inflate the layout for this fragment


        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //view.findViewById<Button>(R.id.addBtn).setOnClickListener {
        //    findNavController().navigate(R.id.action_userFragment_to_profileFragment)
        //}

        userRecyclerView = view.findViewById(R.id.recyclerView)
        userRecyclerView.layoutManager = LinearLayoutManager(context)
        userRecyclerView.setHasFixedSize(true)
        adapterU = UserAdapter()
        userRecyclerView.adapter = adapterU

        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)

        viewModel.allUsers.observe(viewLifecycleOwner, Observer {

            adapterU.updateList(it)
        })
    }


//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//
//        userRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
//        userRecyclerView.layoutManager = LinearLayoutManager(context)
//        userRecyclerView.setHasFixedSize(true)
//        adapter = UserAdapter()
//        userRecyclerView.adapter = adapter
//
//        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
//
//        (viewModel as UsersViewModel).allUsers.observe(viewLifecycleOwner) {
//            adapter.updateList(it)
//        }
//
//    }
}