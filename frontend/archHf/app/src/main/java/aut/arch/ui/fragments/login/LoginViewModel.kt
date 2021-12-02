package aut.arch.ui.fragments.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import aut.arch.databinding.FragmentLoginBinding
import aut.arch.local_data.dao.ArchDao
import aut.arch.local_data.models.User
import aut.arch.networking.RetrofitFactory
import aut.arch.networking.RetrofitFactory.ownUserId
import aut.arch.networking.SessionManager
import aut.arch.networking.enums.EmailProblems
import aut.arch.networking.interactors.UserInteractor
import aut.arch.networking.models.UserModel
import aut.arch.ui.fragments.SpinnerRemote
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController
    private lateinit var dao: ArchDao
    private lateinit var userInteractor: UserInteractor
    private lateinit var sessionManager: SessionManager
    var user: ArrayList<User> = ArrayList()
    val emailError = MutableLiveData<EmailProblems>()
    val error = MutableLiveData<Boolean>()

    init {
        SpinnerRemote.startSpinner()
    }

    fun login() {
        emailError.value = EmailProblems.NONE
        val email = binding.loginEmailText.text
        if (email.isNullOrBlank()) {
            emailError.value = EmailProblems.MISSING
            binding.loginPasswordText.text = null
            return
        } else if (!email.contains(Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$"))) {
            emailError.value = EmailProblems.FORM
            binding.loginPasswordText.text = null
            return
        }

        if (binding.loginPasswordText.text.isNullOrBlank()) {
            error.value = true
            binding.loginPasswordText.text = null
            return
        }

        auth(binding.loginEmailText.text.toString(), binding.loginPasswordText.text.toString())
    }

    private fun auth(email: String, password: String) {
        val normalEmail = email.lowercase()
        SpinnerRemote.startSpinner()
        val auth = userInteractor.authUser(
            UserModel(
                email = normalEmail,
                password = password
            )
        ).subscribe({ model ->
            model.token?.let {
                sessionManager.saveAuthToken(it)
                ownUserId = model.userId
                viewModelScope.launch {
                    dao.logout()
                    dao.insertUser(
                        User(
                            userId = ownUserId,
                            email = email,
                            password = password,
                            isOwnUser = true
                        )
                    )
                }
                SpinnerRemote.stopSpinner()
                navController.navigate(LoginFragmentDirections.actionLoginFragmentToLandingFragment())
            }
            if (model.token.isNullOrBlank()) {
                SpinnerRemote.stopSpinner()
                binding.loginEmail.error =
                    "Nem várt hiba, valószínűleg a szerverrel van a gond."
            }
        }, {
            SpinnerRemote.stopSpinner()
            it.printStackTrace()
            binding.loginPassword.error = "Téves jelszó, vagy nincs internet."
        })
    }

    fun navigateToRegister() {
        SpinnerRemote.stopSpinner()
        navController.navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
    }

    fun setNav(navController: NavController) {
        this.navController = navController
    }

    fun checkUser() {
        SpinnerRemote.startSpinner()
        viewModelScope.launch {
            user.addAll(dao.getOwnUser())
            if (user.isNotEmpty()) {
                auth(user[0].email, user[0].password)
            } else {
                SpinnerRemote.stopSpinner()
            }
        }
    }

    fun setDao(archDao: ArchDao) {
        dao = archDao
    }

    fun setBinding(binding: FragmentLoginBinding) {
        this.binding = binding
    }

    fun setTrainingInteractor(interactor: UserInteractor) {
        userInteractor = interactor
    }

    fun setSessionManager(sessionManager: SessionManager) {
        this.sessionManager = sessionManager
    }
}
