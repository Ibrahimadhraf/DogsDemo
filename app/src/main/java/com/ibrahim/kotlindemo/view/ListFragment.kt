package com.ibrahim.kotlindemo.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager


import com.ibrahim.kotlindemo.R
import com.ibrahim.kotlindemo.viewModel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : Fragment() {

    lateinit var listViewModel: ListViewModel
    private val dogListAdapter = DogListAdapter(arrayListOf())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listViewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        listViewModel.refresh()
        dogsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dogListAdapter
        }
        refreshLayout.setOnRefreshListener {
            errorText.visibility = View.GONE
            dogsList.visibility = View.GONE
            loadigView.visibility=View.GONE
            listViewModel.refreshBypassCache()
            refreshLayout.isRefreshing=false
        }

        observeViewModel(view)

    }

    fun observeViewModel( view: View) {
        listViewModel.dogs.observe(viewLifecycleOwner, Observer {dogds->
            dogds?.let {
                view.dogsList.visibility = View.VISIBLE
                Log.d("Respose" , " aftergetis${dogds}")
                dogsList.visibility = View.VISIBLE
                dogListAdapter.updateDogList(dogds)

            } })

            listViewModel.dogsLoadError.observe(viewLifecycleOwner , Observer {isError ->
                isError?.let { it ->
                    Log.d("Respose" , " aftergetis=${isError}")
                   errorText.visibility= if(it) View.VISIBLE else View.GONE


                } })
          listViewModel.isLoading.observe(viewLifecycleOwner , Observer { isLoading->
              isLoading?.let {
                  Log.d("Respose" , " aftergetis=${isLoading}")
                  loadigView.visibility = if(it) View.VISIBLE else View.GONE
                  if(it) {
                      errorText.visibility = View.GONE
                      dogsList.visibility = View.GONE


                  }
              }

          })


    }
}