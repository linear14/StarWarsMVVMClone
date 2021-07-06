package com.ldh.starwars_mvvm_clone.adapters

import com.ldh.starwars_mvvm_clone.R
import com.ldh.starwars_mvvm_clone.models.CharacterPresentation
import me.ibrahimyilmaz.kiel.adapterOf

// inline, noinline, crossinline
internal inline fun createSearchResultAdapter(noinline onClick: (CharacterPresentation) -> Unit) =
    adapterOf<CharacterPresentation> {
        diff(
            areItemsTheSame = { old, new -> old === new },
            areContentsTheSame = { old, new -> old.url == new.url }
        )
        register(
            layoutResource = R.layout.item_search,
            viewHolder = ::SearchedCharacterViewHolder,
            onBindViewHolder = { viewHolder, i, character ->
                viewHolder.binding.searchedCharacter = character
                viewHolder.binding.moreInfoArrowImageButton.setOnClickListener {
                    onClick(character)
                }
            }
        )
    }