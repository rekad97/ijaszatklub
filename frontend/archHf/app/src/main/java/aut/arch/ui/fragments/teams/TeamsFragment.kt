package aut.arch.ui.fragments.teams

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import aut.arch.databinding.FragmentTeamsBinding
import aut.arch.local_data.database.ArchDatabase
import aut.arch.networking.RetrofitFactory
import aut.arch.ui.fragments.teams.teams_recyclerview.TeamsPreviewAdapter
import aut.arch.ui.interfaces.Navigator
import com.google.android.material.bottomsheet.BottomSheetBehavior

class TeamsFragment : Fragment() {

    private lateinit var viewModel: TeamsViewModel
    private lateinit var binding: FragmentTeamsBinding
    private lateinit var navController: NavController
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>
    private lateinit var topRecyclerView: RecyclerView
    private lateinit var topAdapter: TeamsPreviewAdapter
    private lateinit var bottomRecyclerView: RecyclerView
    private lateinit var bottomAdapter: TeamsPreviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeamsBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        bottomSheetBehavior = BottomSheetBehavior.from(binding.trainingBottomSheet).apply {
            peekHeight = 180
            this.state = BottomSheetBehavior.STATE_COLLAPSED
            this.isDraggable = false
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(TeamsViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.setBinding(binding)

        topRecyclerView = binding.teamsOwnRecyclerview
        bottomRecyclerView = binding.teamsEveryRecyclerview

        navController = findNavController()
        viewModel.setNav(navController)
        context?.let { context ->
            viewModel.setDao(ArchDatabase.getInstance(context).archDao())
            viewModel.setTrainingInteractor(RetrofitFactory.getTrainingInteractor(context))
            bottomAdapter = TeamsPreviewAdapter( context)
            topAdapter = TeamsPreviewAdapter( context)
        }

        val topLayoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.VERTICAL,
            false
        )

        val bottomLayoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.VERTICAL,
            false
        )

        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(topRecyclerView)
        topRecyclerView.layoutManager = topLayoutManager

        snapHelper.attachToRecyclerView(bottomRecyclerView)
        bottomRecyclerView.layoutManager = bottomLayoutManager

        binding.executePendingBindings()

        viewModel.getTeams(true)

        topRecyclerView.adapter = topAdapter
        bottomRecyclerView.adapter = bottomAdapter

        initListeners()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListeners() {
        viewModel.ownTeams.observe(viewLifecycleOwner, {
            topAdapter.clear()
            topAdapter.addAll(it)
            topRecyclerView.adapter = topAdapter
            binding.executePendingBindings()
        })
        viewModel.otherTeams.observe(viewLifecycleOwner, {
            bottomAdapter.clear()
            bottomAdapter.addAll(it)
            bottomRecyclerView.adapter = bottomAdapter
            binding.executePendingBindings()
        })
        binding.trainingBottomSheet.setOnTouchListener { _, motionEvent ->
            when (motionEvent.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                    } else {
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    }
                }
            }
            true
        }
        viewModel.newTeamCreated.observe(viewLifecycleOwner,{
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        })
    }
}
