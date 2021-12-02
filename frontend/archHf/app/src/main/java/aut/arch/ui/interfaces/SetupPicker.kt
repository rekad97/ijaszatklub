package aut.arch.ui.interfaces

import aut.arch.databinding.ItemSetupPreviewBinding
import aut.arch.ui.fragments.training.setup_picker_recyclerview.SetupPreview

interface SetupPicker {
    var lastPickedBinding: ItemSetupPreviewBinding?
    var lastPickedItem: SetupPreview?

    fun pickSetup(binding: ItemSetupPreviewBinding, setup: SetupPreview)
}
