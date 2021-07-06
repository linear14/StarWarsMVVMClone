package com.ldh.starwars_mvvm_clone.adapters

import android.view.View
import com.ldh.starwars_mvvm_clone.databinding.ItemSearchBinding
import com.ldh.starwars_mvvm_clone.models.CharacterPresentation
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolder

internal class SearchedCharacterViewHolder(view: View): RecyclerViewHolder<CharacterPresentation>(view) {

    val binding = ItemSearchBinding.bind(view)

    override fun bind(position: Int, item: CharacterPresentation) {
        super.bind(position, item)
        binding.executePendingBindings()
    }
}