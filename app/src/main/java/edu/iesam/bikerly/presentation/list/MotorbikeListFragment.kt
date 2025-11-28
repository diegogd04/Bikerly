package edu.iesam.bikerly.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import edu.iesam.bikerly.R
import edu.iesam.bikerly.databinding.FragmentMotorbikeListBinding
import edu.iesam.bikerly.domain.Motorbike
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
        binding.apply {
            toolbar.topAppBar.navigationIcon = null
            listItem.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = motorbikeAdapter
                skeleton = applySkeleton(R.layout.view_motorbike_item, 8)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObserver()
        viewModel.loadMotorbikeList()
    }

    private fun setUpObserver() {
        val observer = Observer<MotorbikeListViewModel.UiState> { uiState ->
            bindLoading(uiState.isLoading)
            bindData(uiState.motorbikeList)
        }
        viewModel.uiState.observe(viewLifecycleOwner, observer)
    }

    private fun bindData(motorbikeList: List<Motorbike>) {
        motorbikeAdapter.submitList(motorbikeList)
    }

    private fun bindLoading(loading: Boolean) {
        if (loading) {
            skeleton.showSkeleton()
        } else {
            skeleton.showOriginal()
        }
    }
}