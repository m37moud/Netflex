package com.example.netflex.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData


fun <T> Fragment.setLifecycleObserver(liveData: LiveData<T>, function: (it: T) -> Unit){
    liveData.observe(this.viewLifecycleOwner){
        function(it)
    }
}