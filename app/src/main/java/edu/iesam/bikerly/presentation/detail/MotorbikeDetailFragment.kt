package edu.iesam.bikerly.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.faltenreich.skeletonlayout.Skeleton
import edu.iesam.bikerly.app.presentation.loadUrl
import edu.iesam.bikerly.databinding.FragmentMotorbikeDetailBinding
import edu.iesam.bikerly.domain.Motorbike
import org.koin.androidx.viewmodel.ext.android.viewModel

class MotorbikeDetailFragment : Fragment() {

    private var _binding: FragmentMotorbikeDetailBinding? = null
    val binding get() = _binding!!
    private val viewModel: MotorbikeDetailViewModel by viewModel()
    private val args: MotorbikeDetailFragmentArgs by navArgs()
    private lateinit var skeleton: Skeleton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMotorbikeDetailBinding.inflate(inflater, container, false)
        setUpView()
        return binding.root
    }

    private fun setUpView() {
        toolbarEdit()
        skeleton = binding.skeleton
    }

    private fun toolbarEdit() {
        binding.toolbar.topAppBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObserver()
        viewModel.loadMotorbikeById(args.motorbikeId)
    }

    private fun setUpObserver() {
        val observer = Observer<MotorbikeDetailViewModel.UiState> { uiState ->
            bindLoading(uiState.isLoading)
            bindData(uiState.motorbike)
        }
        viewModel.uiState.observe(viewLifecycleOwner, observer)
    }

    private fun bindData(motorbike: Motorbike) {
        binding.apply {
            make.text = motorbike.make
            model.text = motorbike.model
            year.text = motorbike.year
            type.text = motorbike.type
            displacement.text = motorbike.displacement
            image.loadUrl(motorbike.img)
        }
    }

    private fun bindLoading(loading: Boolean) {
        if (loading) {
            skeleton.showSkeleton()
        } else {
            skeleton.showOriginal()
        }
    }
}