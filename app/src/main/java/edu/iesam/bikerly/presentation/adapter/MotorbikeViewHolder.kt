package edu.iesam.bikerly.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import edu.iesam.bikerly.databinding.ViewMotorbikeItemBinding
import edu.iesam.bikerly.domain.Motorbike

class MotorbikeViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private lateinit var binding: ViewMotorbikeItemBinding

    fun bind(item: Motorbike) {
        binding = ViewMotorbikeItemBinding.bind(view)

        binding.apply {
            make.text = item.make
            model.text = item.model
            year.text = item.year
            type.text = item.type
            displacement.text = item.displacement
        }
    }
}