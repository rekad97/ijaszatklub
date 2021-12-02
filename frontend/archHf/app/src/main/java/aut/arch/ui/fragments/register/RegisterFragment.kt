package aut.arch.ui.fragments.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import aut.arch.databinding.FragmentRegisterBinding
import aut.arch.local_data.database.ArchDatabase
import aut.arch.networking.RetrofitFactory
import aut.arch.networking.SessionManager
import aut.arch.networking.enums.EmailProblems
import aut.arch.networking.enums.PasswordProblems
import aut.arch.ui.fragments.login.LoginViewModel

class RegisterFragment : Fragment() {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        binding.viewModel = viewModel

        navController = findNavController()
        viewModel.setNav(navController)
        viewModel.setBinding(binding)
        context?.let { context ->
            viewModel.setDao(ArchDatabase.getInstance(context).archDao())
            viewModel.setUserInteractor(RetrofitFactory.getUserInteractor(context))
            viewModel.setSessionManager(SessionManager(context))
        }

        initListeners()
    }

    private fun initListeners() {
        viewModel.emailError.observe(viewLifecycleOwner, {
            when (it) {
                EmailProblems.MISSING -> {
                    binding.registerEmail.error = "Kérlek add meg az email címed!"
                }
                EmailProblems.FORM -> {
                    binding.registerEmail.error = "Ez nem egy email cím."
                }
                EmailProblems.IN_USE -> {
                    binding.registerEmail.error = "Ez az email cím már foglalt."
                }
                EmailProblems.NONE -> {
                    binding.registerEmail.error = null
                }
                else -> {
                    binding.registerEmail.error = null
                }
            }
        })
        viewModel.passwordError.observe(viewLifecycleOwner, {
            when (it) {
                PasswordProblems.NOT_SAME -> {
                    binding.registerPasswordAgain.error = "A két jelszó nem egyezik meg."
                }
                PasswordProblems.SIMPLE -> {
                    binding.registerPassword.error = "A jelszavaknak tartalmaznia kell kis és nagy betűt, számot, és legalább 6 karakter hosszúnak kell lenniük."
                }
                PasswordProblems.EMPTY -> {
                    binding.registerPassword.error = "Nincs jeszó megadva."
                }
                PasswordProblems.NONE -> {
                    binding.registerPassword.error = null
                    binding.registerPasswordAgain.error = null
                }
                else -> {
                    binding.registerPassword.error = null
                    binding.registerPasswordAgain.error = null
                }
            }
        })
    }
}
