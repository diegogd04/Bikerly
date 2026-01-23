package edu.iesam.bikerly.presentation.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import edu.iesam.bikerly.R
import edu.iesam.bikerly.domain.Motorbike

class MotorbikeAdapter(private val onClick: (Int) -> Unit) :
    ListAdapter<Motorbike, MotorbikeViewHolder>(MotorbikeDiffUtil()) {

    private var favoriteIdList: Set<Int> = emptySet()
    private var showFavoriteIcon: Boolean = true

    fun setFavoriteIdList(idList: Set<Int>) {
        favoriteIdList = idList
        notifyDataSetChanged()
    }

    fun setShowFavoriteIcon(show: Boolean) {
        showFavoriteIcon = show
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MotorbikeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_motorbike_item, parent, false)

        return MotorbikeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(
        holder: MotorbikeViewHolder,
        position: Int
    ) {
        val motorbike = currentList[position]
        val isFavorite = favoriteIdList.contains(motorbike.id) && showFavoriteIcon
        holder.bind(motorbike, onClick, isFavorite)
    }
}