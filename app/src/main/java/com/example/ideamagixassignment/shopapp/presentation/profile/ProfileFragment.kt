package com.example.ideamagixassignment.shopapp.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import com.example.ideamagixassignment.shopapp.presentation.profile.auth.AuthViewModel
import com.example.ideamagixassignment.shopapp.redux.ApplicationState
import com.example.ideamagixassignment.shopapp.utils.ResourceProvider
import com.example.namespace.R
import com.example.namespace.databinding.FragmentProfileBinding
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var profilerItemGenerator: UserProfilerItemGenerator

    @Inject
    lateinit var resourceProvider: ResourceProvider

    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uiAction = ProfileUiAction(authViewModel)
        val epoxyController =
            ProfileEpoxyController(profilerItemGenerator, uiAction, resourceProvider)
        binding.epoxyRecyclerView.setController(epoxyController)


        authViewModel.store.stateFlow.map {
            it.authState
        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner) { authState ->
            epoxyController.setData(authState)
            binding.headerTextView.text = if (authState is ApplicationState.AuthState.UnAuth) {
                getString(R.string.sign_in)
            } else {
                getString(R.string.welcome_message, authState.getGreetingMessage())
            }
            binding.infoTextView.text = authState.getEmail()
        }

        authViewModel.intentFlow.filterNotNull().asLiveData()
            .observe(viewLifecycleOwner) { intent ->
                startActivity(intent)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
