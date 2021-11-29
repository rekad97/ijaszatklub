package aut.arch.ui.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import aut.arch.databinding.FragmentLoginBinding
import aut.arch.local_data.database.ArchDatabase
import aut.arch.networking.RetrofitFactory
import aut.arch.networking.SessionManager
import aut.arch.networking.enums.EmailProblems
import aut.arch.ui.fragments.SpinnerRemote

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.viewModel = viewModel

        navController = findNavController()
        viewModel.setNav(navController)
        context?.let { context ->
            viewModel.setDao(ArchDatabase.getInstance(context).archDao())
            viewModel.setTrainingInteractor(RetrofitFactory.getUserInteractor(context))
            viewModel.setSessionManager(SessionManager(context))
        }
        viewModel.setBinding(binding)
        viewModel.checkUser()

        initListeners()
    }

    private fun initListeners() {
        viewModel.emailError.observe(viewLifecycleOwner, {
            when (it) {
                EmailProblems.FORM -> {
                    binding.loginEmail.error = "Ez nem egy email cím."
                }
                EmailProblems.MISSING -> {
                    binding.loginEmail.error = "Ilyen email cím nincs regisztrálva"
                }
                EmailProblems.NONE -> {
                    binding.loginEmail.error = null
                }
                else -> {
                    binding.loginEmail.error = null
                }
            }
        })
        viewModel.error.observe(viewLifecycleOwner, {
            if (it == true) {
                binding.loginPassword.error = "Nem stimmel a jelszó."
            } else {
                binding.loginPassword.error = null
            }
        })
    }

}