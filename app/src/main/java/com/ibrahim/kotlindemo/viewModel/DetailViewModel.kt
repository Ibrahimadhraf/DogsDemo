package com.ibrahim.kotlindemo.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ibrahim.kotlindemo.model.DogBreed
import com.ibrahim.kotlindemo.view.DetailsFragmentArgs

class DetailViewModel : ViewModel() {

    val dogDetails = MutableLiveData<DogBreed>()
    val dogeLoadErrors=MutableLiveData<Boolean>()
    fun fetch() {

        val dog2 = DogBreed("1", "corgi", "15 years", "breadGroup", "bredFor", "temperament", "")

        dogDetails.value=dog2
        dogeLoadErrors.value=false

    }
}