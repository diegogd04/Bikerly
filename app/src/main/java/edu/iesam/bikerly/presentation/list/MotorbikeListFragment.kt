package edu.iesam.bikerly.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import edu.iesam.bikerly.R
import edu.iesam.bikerly.databinding.FragmentMotorbikeListBinding
import edu.iesam.bikerly.domain.Motorbike
import edu.iesam.bikerly.presentation.filters.FiltersDialogFragment
import edu.iesam.bikerly.presentation.list.adapter.MotorbikeAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MotorbikeListFragment : Fragment() {

    private var _binding: FragmentMotorbikeListBinding? = null
    val binding get() = _binding!!
    private val motorbikeAdapter = MotorbikeAdapter { motorbikeId ->
        findNavController().navigate(
            MotorbikeListFragmentDirections.actionFromMotorbikeListToMotorbikeDetail(motorbikeId)
        )
    }
    private val viewModel: MotorbikeListViewModel by viewModel()
    private lateinit var skeleton: Skeleton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMotorbikeListBinding.inflate(inflater, container, false)
        setUpView()
        return binding.root
    }

    private fun setUpView() {
        toolbarEdit()
        binding.apply {
            listItem.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = motorbikeAdapter
                skeleton = applySkeleton(R.layout.view_motorbike_item, 8)
            }
        }
    }

    private fun toolbarEdit() {
        binding.apply {
            toolbar.apply {
                topAppBar.navigationIcon = null
                buttonFavoriteTrue.visibility = View.GONE
                buttonFavoriteFalse.visibility = View.VISIBLE
                val favoriteClickListener = View.OnClickListener {
                    viewModel.toggleFavoriteMotorbikeList()
                }
                buttonFavoriteTrue.setOnClickListener(favoriteClickListener)
                buttonFavoriteFalse.setOnClickListener(favoriteClickListener)
                searchInput.addTextChangedListener { text ->
                    viewModel.onSearchFilterChanged(text?.toString().orEmpty())
                }
                buttonSearch.setOnClickListener {
                    buttonSearch.visibility = View.GONE
                    searchBar.visibility = View.VISIBLE
                }
                buttonFilters.setOnClickListener {
                    val dialog = FiltersDialogFragment.newInstance(
                        viewModel.getSelectedMakes(),
                        viewModel.getSelectedTypes(),
                        viewModel.getMinDisplacement(),
                        viewModel.getMaxDisplacement(),
                        viewModel.getMinYear(),
                        viewModel.getMaxYear()
                    )
                    dialog.show(parentFragmentManager, "FiltersDialog")
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObserver()
        viewModel.loadInitialList()

        parentFragmentManager.setFragmentResultListener(
            "filters_result",
            viewLifecycleOwner
        ) { _, bundle ->
            val makes = getStringArray(bundle, "makes")
            val types = getStringArray(bundle, "types")
            val minDisplacement = getInt(bundle, "minDisplacement")
            val maxDisplacement = getInt(bundle, "maxDisplacement")
            val minYear = getInt(bundle, "minYear")
            val maxYear = getInt(bundle, "maxYear")

            filtersListener(makes, types, minDisplacement, maxDisplacement, minYear, maxYear)

            updateFiltersIcon()
        }
    }

    private fun getStringArray(bundle: Bundle, key: String): List<String> {
        return bundle.getStringArray(key)?.toList().orEmpty()
    }

    private fun getInt(bundle: Bundle, key: String): Int? {
        return bundle.getInt(key, -1).takeIf { it >= 0 }
    }

    private fun filtersListener(
        makes: List<String>,
        types: List<String>,
        minDisplacement: Int?,
        maxDisplacement: Int?,
        minYear: Int?,
        maxYear: Int?
    ) {
        viewModel.apply {
            onMakeFilterChanged(makes)
            onTypeFilterChanged(types)
            onDisplacementFilterChanged(minDisplacement, maxDisplacement)
            onYearFilterChanged(minYear, maxYear)
        }
    }

    private fun updateFiltersIcon() {
        val activeFilters = viewModel.hasActiveFilters()
        binding.toolbar.apply {
            if (activeFilters) {
                buttonFiltersTrue.visibility = View.VISIBLE
                buttonFiltersFalse.visibility = View.GONE
            } else {
                buttonFiltersTrue.visibility = View.GONE
                buttonFiltersFalse.visibility = View.VISIBLE
            }
        }
    }

    private fun setUpObserver() {
        val observer = Observer<MotorbikeListViewModel.UiState> { uiState ->
            bindLoading(uiState.isLoading)
            bindToolbar(uiState.showFavorites)

            val showFavoriteIcon = !uiState.showFavorites
            motorbikeAdapter.setShowFavoriteIcon(showFavoriteIcon)
            motorbikeAdapter.setFavoriteIdList(uiState.favoriteIdList)
            bindData(uiState.motorbikeList, uiState.favoriteIdList, showFavoriteIcon)
        }
        viewModel.uiState.observe(viewLifecycleOwner, observer)
    }

    private fun bindData(
        motorbikeList: List<Motorbike>,
        favoriteIdList: Set<Int>,
        showFavoriteIcon: Boolean
    ) {
        motorbikeAdapter.setShowFavoriteIcon(showFavoriteIcon)
        motorbikeAdapter.setFavoriteIdList(favoriteIdList)
        motorbikeAdapter.submitList(motorbikeList) {
            binding.listItem.scrollToPosition(0)
        }
    }

    private fun bindLoading(loading: Boolean) {
        if (loading) {
            skeleton.showSkeleton()
        } else {
            skeleton.showOriginal()
        }
    }

    private fun bindToolbar(showFavorites: Boolean) {
        binding.toolbar.apply {
            if (showFavorites) {
                buttonFavoriteTrue.visibility = View.VISIBLE
                buttonFavoriteFalse.visibility = View.GONE
            } else {
                buttonFavoriteTrue.visibility = View.GONE
                buttonFavoriteFalse.visibility = View.VISIBLE
            }
        }
    }
}