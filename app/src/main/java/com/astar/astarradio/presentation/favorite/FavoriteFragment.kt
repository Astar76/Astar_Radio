package com.astar.astarradio.presentation.favorite

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.astar.astarradio.R
import com.astar.astarradio.databinding.FragmentFavoriteBinding
import com.astar.astarradio.presentation.radiolist.RadioAdapter
import com.astar.astarradio.presentation.radiolist.RadioListResult
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class FavoriteFragment: Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding: FragmentFavoriteBinding get() = _binding!!

    private var adapter: RadioAdapter by Delegates.notNull()

    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        adapter = RadioAdapter(viewModel)

        binding.favorite.adapter = adapter
        val divider = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        divider.setDrawable(ColorDrawable(ContextCompat.getColor(requireContext(), R.color.colorDivider)))
        binding.favorite.addItemDecoration(divider)

        viewModel.radioListResult.observe(viewLifecycleOwner, Observer {
            when(it) {
                is RadioListResult.Loading -> {}
                is RadioListResult.EmptyResult -> {}
                is RadioListResult.SuccessResult -> {
                    adapter.radios = it.result
                }
                is RadioListResult.ErrorResult -> {}
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFavorites()
    }

}