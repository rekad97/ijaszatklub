package aut.arch.ui.fragments.register

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import aut.arch.databinding.FragmentRegisterBinding
import aut.arch.local_data.dao.ArchDao
import aut.arch.local_data.models.User
import aut.arch.networking.RetrofitFactory
import aut.arch.networking.SessionManager
import aut.arch.networking.enums.EmailProblems
import aut.arch.networking.enums.PasswordProblems
import aut.arch.networking.interactors.UserInteractor
import aut.arch.networking.models.UserModel
import aut.arch.ui.fragments.SpinnerRemote
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private lateinit var dao: ArchDao
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var navController: NavController
    private lateinit var userInteractor: UserInteractor
    private lateinit var userModel: UserModel
    private lateinit var sessionManager: SessionManager

    val emailError = MutableLiveData<EmailProblems>()
    val passwordError = MutableLiveData<PasswordProblems>()

    fun register() {
        emailError.value = EmailProblems.NONE
        passwordError.value = PasswordProblems.NONE
        val email = binding.registerEmailText.text
        val passWord = binding.registerPasswordText.text
        if (email.isNullOrBlank()) {
            emailError.value = EmailProblems.MISSING
            return
        } else if (!email.contains(Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$"))) {
            emailError.value = EmailProblems.FORM
            return
        }

        if (passWord.isNullOrBlank()) {
            passwordError.value = PasswordProblems.EMPTY
            return
        }

        if (passWord.toString() != binding.registerPasswordAgainText.text.toString()) {
            passwordError.value = PasswordProblems.NOT_SAME
            return
        }

        if (!passWord.contains(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$"))) {
            passwordError.value = PasswordProblems.SIMPLE
            return
        }

        userModel = UserModel(
                email = binding.registerEmailText.text.toString().lowercase(),
                password = binding.registerPasswordText.text.toString()
        )

        SpinnerRemote.startSpinner()

        val disposable = userInteractor.registerUser(
            userModel
        ).subscribe({
            RetrofitFactory.ownUserId = it
            viewModelScope.launch {
                dao.insertUser(
                    User(
                        userId = it,
                        email = binding.registerEmailText.text.toString().lowercase(),
                        password = binding.registerPasswordText.text.toString(),
                        isOwnUser = true
                    )
                )
            }
            val auth = userInteractor.authUser(userModel).subscribe ({ model ->
                model.token?.let {
                    sessionManager.saveAuthToken(it)
                }
                if(model.token.isNullOrBlank())
                    binding.registerEmailText.error = "Nem várt hiba, valószínűleg a szerverrel van a gond."
            },{
                SpinnerRemote.stopSpinner()
                it.printStackTrace()
                binding.registerEmailText.error = "Nem várt hiba, sajnos nem tudjuk mi a gond."
            })
            SpinnerRemote.stopSpinner()
            navController.navigate(RegisterFragmentDirections.actionRegisterFragmentToLandingFragment())
        },{
            SpinnerRemote.stopSpinner()
            it.printStackTrace()
            binding.registerEmailText.error = "Ezzel az email címmel már regisztráltak."
        })


    }

    fun back() {
        navController.popBackStack()
    }

    fun setNav(navController: NavController) {
        this.navController = navController
    }

    fun setBinding(binding: FragmentRegisterBinding) {
        this.binding = binding
    }

    fun setUserInteractor(userInteractor: UserInteractor) {
        this.userInteractor = userInteractor
    }

    fun setDao(dao: ArchDao) {
        this.dao = dao
    }

    fun setSessionManager(sessionManager: SessionManager) {
        this.sessionManager = sessionManager
    }
}