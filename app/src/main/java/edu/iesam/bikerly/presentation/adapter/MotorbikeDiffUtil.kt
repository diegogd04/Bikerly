package edu.iesam.bikerly.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import edu.iesam.bikerly.domain.Motorbike

class MotorbikeDiffUtil : DiffUtil.ItemCallback<Motorbike>() {

    override fun areItemsTheSame(
        oldItem: Motorbike,
        newItem: Motorbike
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Motorbike,
        newItem: Motorbike
    ): Boolean {
        return oldItem == newItem
    }
}