package com.cl.navicationtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cl.androidstudy.R


/**
 * A simple [Fragment] subclass.
 * Use the [SystemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SystemFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_system, container, false)
    }
}