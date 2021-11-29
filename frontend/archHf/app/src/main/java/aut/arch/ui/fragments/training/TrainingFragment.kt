package aut.arch.ui.fragments.training

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.ListPopupWindow
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import aut.arch.R
import aut.arch.databinding.FragmentTrainingBinding
import aut.arch.databinding.ItemSetupPreviewBinding
import aut.arch.local_data.database.ArchDatabase
import aut.arch.local_data.models.Shot
import aut.arch.local_data.models.Training
import aut.arch.local_data.models.enums.BowType
import aut.arch.local_data.models.enums.TargetType
import aut.arch.networking.RetrofitFactory
import aut.arch.networking.RetrofitFactory.ownUserId
import aut.arch.ui.fragments.training.setup_picker_recyclerview.SetupPreview
import aut.arch.ui.fragments.training.setup_picker_recyclerview.SetupPreviewAdapter
import aut.arch.ui.interfaces.SetupPicker
import com.google.android.material.bottomsheet.BottomSheetBehavior

class TrainingFragment : Fragment(), SetupPicker {

    override var lastPickedBinding: ItemSetupPreviewBinding? = null
    override var lastPickedItem: SetupPreview? = null
    private lateinit var viewModel: TrainingViewModel
    private lateinit var binding: FragmentTrainingBinding
    private lateinit var navController: NavController
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>
    private lateinit var listPopupWindow: ListPopupWindow
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SetupPreviewAdapter
    private var trainingId: String = "-1"
    private var currentShot = 0
    private var score = 0
    private var allShot = 15
    private var bottomSheetBehaviorExpendable = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrainingBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        bottomSheetBehavior = BottomSheetBehavior.from(binding.trainingBottomSheet).apply {
            peekHeight = 250
            this.state = BottomSheetBehavior.STATE_COLLAPSED
            this.isDraggable = false
        }

        context?.let {
            listPopupWindow = ListPopupWindow(it, null, R.attr.listPopupWindowStyle)
        }
        listPopupWindow.anchorView = binding.trainingShotCountList

        val items = listOf("15", "30", "Szabad edzés")
        val adapter = ArrayAdapter(requireContext(), R.layout.item_list_element, items)
        listPopupWindow.setAdapter(adapter)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(TrainingViewModel::class.java)
        binding.viewModel = viewModel

        recyclerView = binding.recyclerView
        adapter = SetupPreviewAdapter(this)

        navController = findNavController()
        viewModel.setNav(navController)
        context?.let { context ->
            viewModel.setDao(ArchDatabase.getInstance(context).archDao())
            viewModel.setTrainingInteractor(RetrofitFactory.getTrainingInteractor(context))
        }

        val layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
        recyclerView.layoutManager = layoutManager

        binding.executePendingBindings()

        viewModel.getSetups()

        recyclerView.adapter = adapter


        initListeners()
    }

    @SuppressLint("ClickableViewAccessibility")
    fun initListeners() {
        binding.trainingBottomSheet.setOnTouchListener { _, motionEvent ->
            when (motionEvent.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED
                        && bottomSheetBehaviorExpendable
                    ) {
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                    } else {
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    }
                }
            }
            true
        }
        viewModel.done.observe(viewLifecycleOwner, {

            if (currentShot != allShot && allShot != -1) {
                binding.trainingShotCountList.text =
                    "Még vannak hátra lövések,\nha így szeretnéd menteni, kattints ide, és válaszd a \"szabad edzés\" módot."
                return@observe
            }

            if (lastPickedItem == null)
                binding.trainingShotCountList.text = "Válassz felszerelést!"
            else {
                val newTraining = Training(
                    trainingId = trainingId,
                    userId = ownUserId,
                    setupId = lastPickedItem!!.setupId,
                    isIndoor = binding.trainingIndoorOutdoorSwitch.isActivated,
                    isPublic = binding.trainingPublicSwitch.isActivated,
                    arrowCnt = allShot
                )
                viewModel.addNewTraining(newTraining)
            }
        })
        listPopupWindow.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                allShot = 15
                binding.trainingShotCountList.text = "Lövések száma: 15"
                binding.trainingShotAll.text = "/15 lövés"
            }
            if (position == 1) {
                allShot = 30
                binding.trainingShotCountList.text = "Lövések száma: 30"
                binding.trainingShotAll.text = "/30 lövés"
            }
            if (position == 2) {
                allShot = -1
                binding.trainingShotCountList.text = "Szabad edzés"
                binding.trainingShotAll.text = ". lövés"
            }
            listPopupWindow.dismiss()
            binding.executePendingBindings()
        }
        viewModel.popupNeeded.observe(viewLifecycleOwner, {
            if (bottomSheetBehaviorExpendable) {
                listPopupWindow.show()
            }
        })

        viewModel.shot.observe(viewLifecycleOwner, {
            val distanceText = binding.trainingDistanceText
            if (distanceText.text.isNullOrEmpty())
                distanceText.error = "Nem adtál meg távolságot!"
            else {
                distanceText.error = null
                val text = binding.trainingShotCurrent
                text.text = (++currentShot).toString()

                val scored = binding.trainingShotWidget.calculatePoints(TargetType.FITA)
                score += scored
                binding.trainingSumPointText.text = (score).toString()
                val shot = Shot(
                    trainingId = trainingId,
                    distance = distanceText.text.toString().toLong(),
                    placeX = binding.trainingShotWidget.getXPos(),
                    placeY = binding.trainingShotWidget.getYPos(),
                    score = scored,
                    target = TargetType.FITA
                )

                binding.trainingShotWidget.resetTarget()
                viewModel.addNewShot(shot)

                if (!(currentShot < allShot || allShot == -1)) {
                    bottomSheetBehaviorExpendable = false
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }
            }
        })

        viewModel.setups.observe(viewLifecycleOwner, { setups ->
            setups.forEach { setup ->
                adapter.addItem(SetupPreview(setup.setupId, setup.setupName, setup.bowType))
            }
            recyclerView.adapter = adapter
            binding.executePendingBindings()
        })
    }

    override fun pickSetup(binding: ItemSetupPreviewBinding, setup: SetupPreview) {

        lastPickedBinding?.statisticsPreviewPickedIcon?.background = null
        lastPickedBinding = binding
        lastPickedItem = setup

        when (setup.type) {
            BowType.TRADITIONAL -> binding.statisticsPreviewPickedIcon.setBackgroundResource(
                R.drawable.active_traditional
            )
            BowType.RECURVE -> binding.statisticsPreviewPickedIcon.setBackgroundResource(
                R.drawable.active_recurve
            )
            BowType.COMPOUND -> binding.statisticsPreviewPickedIcon.setBackgroundResource(
                R.drawable.active_compound
            )
        }
    }
}
