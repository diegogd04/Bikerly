package edu.iesam.bikerly.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import edu.iesam.bikerly.databinding.FragmentMotorbikeListBinding
import edu.iesam.bikerly.domain.Motorbike
import edu.iesam.bikerly.presentation.adapter.MotorbikeAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MotorbikeListFragment : Fragment() {

    private var _binding: FragmentMotorbikeListBinding? = null
    val binding get() = _binding!!
    private val motorbikeAdapter = MotorbikeAdapter()
    private val viewModel: MotorbikeListViewModel by viewModel()

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
            listItem.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = motorbikeAdapter
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
            bindData(uiState.motorbikeList)
        }
        viewModel.uiState.observe(viewLifecycleOwner, observer)
    }

    private fun bindData(motorbikeList: List<Motorbike>) {
        motorbikeAdapter.submitList(motorbikeList)
    }
}