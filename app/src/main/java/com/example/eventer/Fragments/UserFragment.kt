package com.example.eventer.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventer.Adapter.UserAdapter
import com.example.eventer.R
import com.example.eventer.model.UsersViewModel



private lateinit var viewModel: UsersViewModel
private lateinit var userRecyclerView: RecyclerView
lateinit var adapterU: UserAdapter

class UserFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.users_fragment, container, false)

        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

}