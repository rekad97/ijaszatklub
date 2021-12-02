package aut.arch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import aut.arch.databinding.ActivityMainBinding
import aut.arch.networking.RetrofitFactory
import aut.arch.ui.fragments.SpinnerRemote

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        SpinnerRemote.setBinding(binding)
        setContentView(binding.root)
        RetrofitFactory.createRetrofit(context = this)
    }
}