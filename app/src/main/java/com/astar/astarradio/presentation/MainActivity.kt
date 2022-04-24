package com.astar.astarradio.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.astar.astarradio.R
import com.astar.astarradio.databinding.ActivityMainBinding
import com.astar.astarradio.presentation.radiolist.RadioListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: RadioListViewModel by viewModels()
    private var adapter: RadiosAdapter by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       /* adapter = RadiosAdapter()

        val divider = DividerItemDecoration(this, RecyclerView.VERTICAL)
        binding.recycler.addItemDecoration(divider)
        binding.recycler.adapter = adapter
        viewModel.users.observe(this, Observer { result ->
            when (result) {
                is Result.Success -> adapter.radios = result.result
                is Result.Error -> snake(result.result.message!!)
            }
        })*/

        val navController = findNavController(R.id.nav_host_fragment)
        binding.bottomNav.setupWithNavController(navController)

    }
}