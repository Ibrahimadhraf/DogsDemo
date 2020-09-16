package com.ibrahim.kotlindemo.view

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.ibrahim.kotlindemo.R


class SettingFragment : PreferenceFragmentCompat(){



    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
       setPreferencesFromResource(R.xml.preferences ,rootKey)
    }


}