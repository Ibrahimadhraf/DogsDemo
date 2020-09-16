package com.ibrahim.kotlindemo.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData

import com.ibrahim.kotlindemo.model.DogBreed
import com.ibrahim.kotlindemo.model.DogDatabase

import kotlinx.coroutines.launch


class DetailViewModel(application: Application) : BaseViewModel(application) {

    val dogDetails = MutableLiveData<DogBreed>()
    val dogeLoadErrors = MutableLiveData<Boolean>()
    fun fetch(uuid: Int) {
        launch {
            val dog2 = DogDatabase(getApplication()).dogDao().getDog(uuid)
            dogDetails.value = dog2

        }


    }
}