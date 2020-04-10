package com.xborg.vendx_service.fragments.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xborg.vendx_service.R
import com.xborg.vendx_service.SharedViewModel
import com.xborg.vendx_service.adapters.RoomSlipAdapter
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModelFactory = HomeViewModelFactory(activity!!.application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        val roomSlipAdapter = RoomSlipAdapter(context!!)

        roomRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = roomSlipAdapter
        }

        viewModel.rooms.observe(viewLifecycleOwner, Observer { rooms ->
            if(rooms != null) {
                roomSlipAdapter.submitList(rooms)
                Log.i("Debug", "rooms : $rooms")
                progressBar.visibility = View.GONE
            }
        })

    }

}
