package aut.arch.ui.fragments.setups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import aut.arch.databinding.FragmentSetupsBinding
import aut.arch.local_data.database.ArchDatabase
import aut.arch.networking.RetrofitFactory
import aut.arch.ui.fragments.setups.setups_recyclerview.SetupsPreview
import aut.arch.ui.fragments.setups.setups_recyclerview.SetupsPreviewAdapter
import aut.arch.ui.interfaces.Navigator

class SetupsFragment : Fragment(), Navigator {

    private lateinit var viewModel: SetupsViewModel
    private lateinit var binding: FragmentSetupsBinding
    private lateinit var navController: NavController
    private lateinit var topRecyclerView: RecyclerView
    private lateinit var topAdapter: SetupsPreviewAdapter
    private lateinit var bottomRecyclerView: RecyclerView
    private lateinit var bottomAdapter: SetupsPreviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSetupsBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SetupsViewModel::class.java)

        binding.viewModel = viewModel

        topRecyclerView = binding.setupsFirstRecyclerview
        topAdapter = SetupsPreviewAdapter(this)
        bottomRecyclerView = binding.setupsSecondRecyclerview
        bottomAdapter = SetupsPreviewAdapter(this)


        navController = findNavController()
        viewModel.setNav(navController)
        context?.let { context ->
            viewModel.setDao(ArchDatabase.getInstance(context).archDao())
            viewModel.setTrainingInteractor(RetrofitFactory.getTrainingInteractor(context))
        }

        val topLayoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        val bottomLayoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(topRecyclerView)
        topRecyclerView.layoutManager = topLayoutManager

        snapHelper.attachToRecyclerView(bottomRecyclerView)
        bottomRecyclerView.layoutManager = bottomLayoutManager

        binding.executePendingBindings()

        topRecyclerView.adapter = topAdapter
        bottomRecyclerView.adapter = bottomAdapter

        initListeners()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSetups()
    }

    private fun initListeners() {
        viewModel.setups.observe(viewLifecycleOwner, { setups ->
            topAdapter.clear()
            bottomAdapter.clear()
            for (i in setups.indices) {
                val currentSetup = setups[i]
                if (i % 2 == 0) {
                    topAdapter.addItem(
                        SetupsPreview(
                            setupId = currentSetup.setupId,
                            name = currentSetup.setupName,
                            type = currentSetup.bowType
                        )
                    )
                } else {
                    bottomAdapter.addItem(
                        SetupsPreview(
                            setupId = currentSetup.setupId,
                            name = currentSetup.setupName,
                            type = currentSetup.bowType
                        )
                    )
                }
            }
            topRecyclerView.adapter = topAdapter
            bottomRecyclerView.adapter = bottomAdapter
            binding.executePendingBindings()
        })
    }

    override fun navigateTo(id: String) {
        navController.navigate(
            SetupsFragmentDirections.actionSetupsFragmentToSetupDetailsFragment(id)
        )
    }
}
