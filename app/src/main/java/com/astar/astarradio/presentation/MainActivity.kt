package com.astar.astarradio.presentation

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.astar.astarradio.core.Result
import com.astar.astarradio.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()
    private var adapter: RadiosAdapter by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RadiosAdapter()

        val divider = DividerItemDecoration(this, RecyclerView.VERTICAL)
        binding.recycler.addItemDecoration(divider)
        binding.recycler.adapter = adapter
        viewModel.users.observe(this, Observer { result ->
            when (result) {
                is Result.Success -> adapter.radios = result.result
                is Result.Error -> snake(result.result.message!!)
            }
        })
    }

    private fun Activity.snake(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}