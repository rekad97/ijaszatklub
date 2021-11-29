package aut.arch.ui.fragments.teams.teams_recyclerview

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import aut.arch.databinding.ItemTeamPreviewBinding
import aut.arch.networking.RetrofitFactory
import aut.arch.networking.RetrofitFactory.ownUserId
import aut.arch.networking.interactors.TrainingInteractor
import aut.arch.ui.interfaces.Navigator

class TeamsPreviewViewHolder(
    val binding: ItemTeamPreviewBinding,
    val context: Context
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var item: TeamPreview
    private lateinit var trainingInteractor: TrainingInteractor


    fun bind(item: TeamPreview) {
        this.item = item

        trainingInteractor = RetrofitFactory.getTrainingInteractor(context)
        binding.teamPreviewName.text = item.name
        binding.teamPreviewJoin.text = if (item.isUserMember)
            "Kilépés"
        else
            "Jelentkezés"

        binding.executePendingBindings()

        initListeners()
    }

    private fun initListeners() {
        binding.teamPreviewJoin.setOnClickListener {
            binding.teamPreviewJoin.text = null
            if (item.isUserMember) {
                val exit = trainingInteractor.removeUserFromTeam(item.teamId, ownUserId).subscribe({
                    binding.teamPreviewJoin.text = "Jelentkezés"
                }, {
                    it.printStackTrace()
                })
            } else {
                val join = trainingInteractor.addUserToTeam(item.teamId, ownUserId).subscribe({
                    binding.teamPreviewJoin.text = "Kilépés"
                }, {
                    it.printStackTrace()
                })
            }
        }
    }

}
