package com.ibrahim.kotlindemo.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.ibrahim.kotlindemo.model.DogBreed
import com.ibrahim.kotlindemo.model.DogDatabase
import com.ibrahim.kotlindemo.model.DogsApiService
import com.ibrahim.kotlindemo.util.NotificationHelper
import com.ibrahim.kotlindemo.util.SharedPreferenceHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.lang.NumberFormatException

class ListViewModel(application: Application) : BaseViewModel(application) {
    private var preferenceHelper = SharedPreferenceHelper()
    val dogsService = DogsApiService()
    private var refreshTime = 5* 60 * 1000 * 1000 * 1000L

    val dogs = MutableLiveData<List<DogBreed>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()
    private val disposable = CompositeDisposable()
    fun refresh() {
       checkCacheDuration()
val updateTime=preferenceHelper.getUpdateTime()

        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            fetchFromDataBase()
        } else {
            fetchRemote()
        }

    }

    private fun checkCacheDuration() {
      val checkPreference=preferenceHelper.getCacheDuration()
        try {
      val cachePreferenceInt=checkPreference?.toInt() ?:5*60
            refreshTime=cachePreferenceInt.times(1000*1000*1000L)
            Log.d("prfTest","$cachePreferenceInt")
            Log.d("prfTest","$refreshTime")
        }catch (e:NumberFormatException){
            e.printStackTrace()
        }
    }

    fun refreshBypassCache(){
    fetchRemote()
}
    private fun fetchFromDataBase() {
        isLoading.value=true
        launch {
            val dogs=DogDatabase(getApplication()).dogDao().getAllDogs()
            dogsRetrieved(dogs)

        }
    }


    private fun fetchRemote() {
        isLoading.value = true
        disposable.add(
            dogsService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                    object : DisposableSingleObserver<List<DogBreed>>() {

                        override fun onSuccess(dogsList: List<DogBreed>) {



                            storeDogsLocally(dogsList)
                           NotificationHelper(getApplication()).createNotification()
                        }


                        override fun onError(e: Throwable) {
                            dogsLoadError.value = true
                            isLoading.value = false

                            e.printStackTrace()
                        }

                    }
                )

        )

    }

    private fun dogsRetrieved(dogsList: List<DogBreed>) {
        dogs.value = dogsList
        dogsLoadError.value = false
        isLoading.value = false

    }

    private fun storeDogsLocally(list: List<DogBreed>) {
        launch {
            val dao = DogDatabase(getApplication()).dogDao()
            dao.deleteAllDogs()

            val result = dao.insertAll(*list.toTypedArray())
            var  i=0
            while (i<list.size){
                list[i].uuId=result[i].toInt()
                i++
            }

            dogsRetrieved(list)
        }
        preferenceHelper.saveUpdateTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}


