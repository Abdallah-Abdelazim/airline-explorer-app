package com.example.airline_explorer.ui.viewaddairlines

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.airline_explorer.R
import com.example.airline_explorer.data.model.Airline
import com.example.airline_explorer.data.source.AirlinesRepository
import com.example.airline_explorer.data.source.remote.AirlinesRemoteDataSource
import com.example.airline_explorer.data.source.remote.RetrofitClient
import com.example.airline_explorer.databinding.FragmentAirlinesBinding
import com.example.airline_explorer.util.NetworkConnectivityCheckerImpl
import com.example.airline_explorer.util.showSnackbar
import com.google.android.material.snackbar.Snackbar

/**
 * A [Fragment] showing a list of airlines that can be filtered by airline name, airline ID
 * or country. Also, it allow adding a new airline to the existing list.
 */
class AirlinesFragment : Fragment(), AirlinesAdapter.AirlineItemClickListener {

    private var _binding: FragmentAirlinesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<AirlinesViewModel> {
        AirlinesViewModelFactory(
            AirlinesRepository(
                airlinesRemoteDataSource = AirlinesRemoteDataSource(RetrofitClient.airlinesService),
                networkConnectivityChecker = NetworkConnectivityCheckerImpl(requireContext())
            )
        )
    }

    private lateinit var airlinesAdapter: AirlinesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_airlines,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupAirlinesRecyclerView();

        viewModel.airlinesData.observe(viewLifecycleOwner, { airlinesList ->
            airlinesAdapter.updateAirlines(airlinesList)
            binding.progressBar.visibility = View.GONE
        })

        viewModel.messageEvent.observe(viewLifecycleOwner, { msgStrResId ->
            showSnackbar(msgStrResId)
        })

        binding.fabAddAirline.setOnClickListener { v ->
            Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun setupAirlinesRecyclerView() {
        binding.rvAirlines.setHasFixedSize(true)

        airlinesAdapter = AirlinesAdapter(itemClickListener = this)
        binding.rvAirlines.adapter = airlinesAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAirlineItemClick(airline: Airline) {
        Log.d(TAG, "onAirlineItemClick: airline = $airline")
    }

    companion object {
        private val TAG = AirlinesFragment::class.simpleName
    }
}