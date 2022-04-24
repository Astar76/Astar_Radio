package com.astar.astarradio.presentation.playradio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.astar.astarradio.databinding.FragmentPlayRadioBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayRadioFragment: Fragment() {

    private var _binding: FragmentPlayRadioBinding? = null
    private val binding: FragmentPlayRadioBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayRadioBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}