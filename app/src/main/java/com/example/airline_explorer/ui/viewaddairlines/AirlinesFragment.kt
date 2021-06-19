package com.example.airline_explorer.ui.viewaddairlines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.airline_explorer.R
import com.example.airline_explorer.databinding.FragmentAirlinesBinding
import com.google.android.material.snackbar.Snackbar

/**
 * A [Fragment] showing a list of airlines that can be filtered by airline name, airline ID
 * or country. Also, it allow adding a new airline to the existing list.
 */
class AirlinesFragment : Fragment() {

    private var _binding: FragmentAirlinesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<AirlinesViewModel>()

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

        binding.fabAddAirline.setOnClickListener { v ->
            Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}