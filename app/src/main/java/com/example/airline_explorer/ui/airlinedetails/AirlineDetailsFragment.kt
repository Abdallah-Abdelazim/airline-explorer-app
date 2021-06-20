package com.example.airline_explorer.ui.airlinedetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.airline_explorer.R
import com.example.airline_explorer.data.source.AirlinesRepository
import com.example.airline_explorer.data.source.remote.AirlinesRemoteDataSource
import com.example.airline_explorer.data.source.remote.RetrofitClient
import com.example.airline_explorer.databinding.FragmentAirlineDetailsBinding
import com.example.airline_explorer.util.NetworkConnectivityCheckerImpl
import com.example.airline_explorer.util.showSnackbar

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AirlineDetailsFragment : Fragment() {

    val args: AirlineDetailsFragmentArgs by navArgs()

    private var _binding: FragmentAirlineDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<AirlinesDetailsViewModel> {
        AirlineDetailsViewModelFactory(
            args.airlineId,
            AirlinesRepository(
                airlinesRemoteDataSource = AirlinesRemoteDataSource(RetrofitClient.airlinesService),
                networkConnectivityChecker = NetworkConnectivityCheckerImpl(requireContext())
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAirlineDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.messageEvent.observe(viewLifecycleOwner, { msgStrResId ->
            showSnackbar(msgStrResId)
        })

        viewModel.openWebsiteEvent.observe(viewLifecycleOwner, { website ->
            val url = if (!website.contains("http://") || !website.contains("https://"))
                "http://$website"
            else website

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            activity?.packageManager?.let {
                if (browserIntent.resolveActivity(it) != null) {
                    startActivity(browserIntent)
                } else {
                    showSnackbar(R.string.error_no_installed_browser)
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}