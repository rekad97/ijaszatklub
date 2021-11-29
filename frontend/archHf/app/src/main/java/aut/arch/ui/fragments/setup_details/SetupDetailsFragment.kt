package aut.arch.ui.fragments.setup_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import aut.arch.databinding.FragmentSetupDetailsBinding
import aut.arch.local_data.database.ArchDatabase
import aut.arch.local_data.models.Setup
import aut.arch.networking.RetrofitFactory

class SetupDetailsFragment : Fragment() {

    private lateinit var viewModel: SetupDetailsViewModel
    private lateinit var binding: FragmentSetupDetailsBinding
    private val args: SetupDetailsFragmentArgs by navArgs()
    var setupId = "-1"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSetupDetailsBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SetupDetailsViewModel::class.java)

        binding.viewModel = viewModel

        val navController = findNavController()
        viewModel.setNav(navController)
        context?.let { context ->
            viewModel.setDao(ArchDatabase.getInstance(context).archDao())
            viewModel.setTrainingInteractor(RetrofitFactory.getTrainingInteractor(context))
        }

        viewModel.initSetup(args.setupId)
        if(args.setupId != "-1") {
            setupId = args.setupId
            binding.setupSaveButton.text = "Letöltés"
            binding.setupNameText.isEnabled = false
            binding.setupMiddlePartText.isEnabled = false
            binding.setupArmText.isEnabled = false
            binding.setupReticeText.isEnabled = false
            binding.setupStabilizatorText.isEnabled = false
            binding.setupArrowText.isEnabled = false
        }

        initListeners()
    }

    private fun initListeners() {
        viewModel.save.observe(viewLifecycleOwner, {
            val setupName = binding.setupNameText.text.toString()
            val middlePart = binding.setupMiddlePartText.text.toString()
            val arm = binding.setupArmText.text.toString()
            val retice = binding.setupReticeText.text.toString()
            val stabilizator = binding.setupStabilizatorText.text.toString()
            val bowType = viewModel.activeBow.value
            val Arrow = binding.setupArrowText.text.toString()
            val id = setupId

            if (setupName.isEmpty()) {
                binding.setupName.error = "Nincs megadott név."
                binding.executePendingBindings()
                return@observe
            }

            if (bowType == null) {
                viewModel.activeTypeText.value = "Kötelező kiválasztani az íj fajtáját."
                binding.executePendingBindings()
                return@observe
            }

            viewModel.addNewSetup(Setup(
                setupId = setupId,
                setupName = setupName,
                middlePart = middlePart,
                arm = arm,
                retice = retice,
                stabilizator = stabilizator,
                bowType = bowType,
                arrow = Arrow
            ))
        })
        viewModel.updateSetup.observe(viewLifecycleOwner, { currentSetup ->
            if (currentSetup != null) {
                setupId = currentSetup.setupId
                binding.setupNameText.setText(currentSetup.setupName)
                binding.setupMiddlePartText.setText(currentSetup.middlePart)
                binding.setupArmText.setText(currentSetup.arm)
                binding.setupReticeText.setText(currentSetup.retice)
                binding.setupStabilizatorText.setText(currentSetup.stabilizator)
                viewModel.activate(currentSetup.bowType)
                binding.setupArrowText.setText(currentSetup.arrow)

                binding.executePendingBindings()
            }
        })
    }

}
