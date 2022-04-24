package com.astar.astarradio.presentation.radiolist

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.astar.astarradio.R
import com.astar.astarradio.databinding.FragmentRadioListBinding
import com.astar.astarradio.presentation.RadiosAdapter
import com.astar.astarradio.core.Result
import com.astar.astarradio.domain.RadioStation
import com.astar.astarradio.presentation.radiolist.RadioListResult.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class RadioListFragment: Fragment(), RadiosAdapter.Callback {

    private var _binding: FragmentRadioListBinding? = null
    private val binding: FragmentRadioListBinding get() = _binding!!

    private val viewModel: RadioListViewModel by viewModels()

    private var adapter: RadiosAdapter by Delegates.notNull()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRadioListBinding.inflate(inflater, container, false)

        adapter = RadiosAdapter(this)

        val divider = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        divider.setDrawable(ColorDrawable(ContextCompat.getColor(requireContext(), R.color.colorDivider)))
        binding.radiosRecycler.addItemDecoration(divider)
        binding.radiosRecycler.adapter = adapter
        viewModel.radioStations.observe(viewLifecycleOwner, Observer { data ->
            when (data) {
                is Loading -> binding.progress.isVisible = true
                is EmptyResult -> {}
                is SuccessResult -> {
                    binding.progress.isVisible = false
                    adapter.radios = data.result
                }
                is ErrorResult -> {
                    binding.progress.isVisible = false
                    toast(data.error.message!!)
                }
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickItem(station: RadioStation) {
        Snackbar.make(requireView(), "Item click - ${station.name}", Snackbar.LENGTH_SHORT).show()
    }

    override fun onClickFavorite(station: RadioStation) {
        Snackbar.make(requireView(), "Click favorite - ${station.name}", Snackbar.LENGTH_SHORT).show()
    }

    private fun toast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}