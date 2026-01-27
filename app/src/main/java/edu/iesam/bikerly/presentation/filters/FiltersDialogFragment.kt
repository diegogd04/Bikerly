package edu.iesam.bikerly.presentation.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import edu.iesam.bikerly.databinding.ViewFiltersDialogBinding

class FiltersDialogFragment : DialogFragment() {

    private var _binding: ViewFiltersDialogBinding? = null
    private val binding get() = _binding!!
    private val selectedMakes = mutableSetOf<String>()
    private val selectedTypes = mutableSetOf<String>()
    private var minDisplacement: Int? = null
    private var maxDisplacement: Int? = null
    private var minYear: Int? = null
    private var maxYear: Int? = null

    companion object {
        fun newInstance(
            currentMakes: List<String>,
            currentTypes: List<String>,
            currentMinDisplacement: Int?,
            currentMaxDisplacement: Int?,
            currentMinYear: Int?,
            currentMaxYear: Int?
        ): FiltersDialogFragment {
            return FiltersDialogFragment().apply {
                selectedMakes.addAll(currentMakes)
                selectedTypes.addAll(currentTypes)
                this.minDisplacement = currentMinDisplacement
                this.maxDisplacement = currentMaxDisplacement
                this.minYear = currentMinYear
                this.maxYear = currentMaxYear
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ViewFiltersDialogBinding.inflate(inflater, container, false)
        setUpView()
        return binding.root
    }

    private fun setUpView() {
        setUpFilters()

        binding.apply {
            submitButton.setOnClickListener {
                val minDisplacement = binding.minDisplacementEditText.text.toString().toIntOrNull()
                val maxDisplacement = binding.maxDisplacementEditText.text.toString().toIntOrNull()
                val minYear = binding.minYearEditText.text.toString().toIntOrNull()
                val maxYear = binding.maxYearEditText.text.toString().toIntOrNull()
                parentFragmentManager.setFragmentResult(
                    "filters_result",
                    bundleOf(
                        "makes" to selectedMakes.toTypedArray(),
                        "types" to selectedTypes.toTypedArray(),
                        "minDisplacement" to minDisplacement,
                        "maxDisplacement" to maxDisplacement,
                        "minYear" to minYear,
                        "maxYear" to maxYear
                    )
                )
                dismiss()
            }
            toolbar.apply {
                cleanButton.setOnClickListener {
                    cleanFilters()
                    resetFilters()
                }
                topAppBar.setNavigationOnClickListener {
                    dismiss()
                }
            }
        }
    }

    private fun setUpFilters() {
        setUpMakeCheckboxes()
        setUpTypeCheckboxes()
        setUpDisplacementInputs()
        setUpYearInputs()
    }

    private fun setUpMakeCheckboxes() {
        binding.apply {
            checkboxBenelli.isChecked = selectedMakes.contains("Benelli")
            checkboxBmw.isChecked = selectedMakes.contains("BMW")
            checkboxDucati.isChecked = selectedMakes.contains("Ducati")
            checkboxKawasaki.isChecked = selectedMakes.contains("Kawasaki")
            checkboxKtm.isChecked = selectedMakes.contains("KTM")

            checkboxBenelli.setOnCheckedChangeListener { _, checked ->
                if (checked) selectedMakes.add("Benelli") else selectedMakes.remove("Benelli")
            }
            checkboxBmw.setOnCheckedChangeListener { _, checked ->
                if (checked) selectedMakes.add("BMW") else selectedMakes.remove("BMW")
            }
            checkboxDucati.setOnCheckedChangeListener { _, checked ->
                if (checked) selectedMakes.add("Ducati") else selectedMakes.remove("Ducati")
            }
            checkboxKawasaki.setOnCheckedChangeListener { _, checked ->
                if (checked) selectedMakes.add("Kawasaki") else selectedMakes.remove("Kawasaki")
            }
            checkboxKtm.setOnCheckedChangeListener { _, checked ->
                if (checked) selectedMakes.add("KTM") else selectedMakes.remove("KTM")
            }
        }
    }

    private fun setUpTypeCheckboxes() {
        binding.apply {
            checkboxSport.isChecked = selectedTypes.contains("Sport")
            checkboxCrossMotocross.isChecked = selectedTypes.contains("CrossMotocross")
            checkboxEnduroOffroad.isChecked = selectedTypes.contains("EnduroOffroad")
            checkboxNaked.isChecked = selectedTypes.contains("Naked")
            checkboxTrail.isChecked = selectedTypes.contains("Trail")

            checkboxSport.setOnCheckedChangeListener { _, checked ->
                if (checked) selectedTypes.add("Sport") else selectedTypes.remove("Sport")
            }
            checkboxCrossMotocross.setOnCheckedChangeListener { _, checked ->
                if (checked) selectedTypes.add("CrossMotocross") else selectedTypes.remove("CrossMotocross")
            }
            checkboxEnduroOffroad.setOnCheckedChangeListener { _, checked ->
                if (checked) selectedTypes.add("EnduroOffroad") else selectedTypes.remove("EnduroOffroad")
            }
            checkboxNaked.setOnCheckedChangeListener { _, checked ->
                if (checked) selectedTypes.add("Naked") else selectedTypes.remove("Naked")
            }
            checkboxTrail.setOnCheckedChangeListener { _, checked ->
                if (checked) selectedTypes.add("Trail") else selectedTypes.remove("Trail")
            }
        }
    }

    private fun setUpDisplacementInputs() {
        binding.apply {
            minDisplacementEditText.setText(minDisplacement?.toString())
            maxDisplacementEditText.setText(maxDisplacement?.toString())

            minDisplacementEditText.addTextChangedListener { text ->
                minDisplacement = text?.toString()?.toIntOrNull()
            }
            maxDisplacementEditText.addTextChangedListener { text ->
                maxDisplacement = text?.toString()?.toIntOrNull()
            }
        }
    }

    private fun setUpYearInputs() {
        binding.apply {
            minYearEditText.setText(minYear?.toString())
            maxYearEditText.setText(maxYear?.toString())

            minYearEditText.addTextChangedListener { text ->
                minYear = text?.toString()?.toIntOrNull()
            }
            maxYearEditText.addTextChangedListener { text ->
                maxYear = text?.toString()?.toIntOrNull()
            }
        }
    }

    private fun resetFilters() {
        resetMakeCheckboxes()
        resetTypeCheckboxes()
        resetDisplacementInput()
        resetYearInput()
    }

    private fun resetMakeCheckboxes() {
        binding.apply {
            checkboxBenelli.isChecked = false
            checkboxBmw.isChecked = false
            checkboxDucati.isChecked = false
            checkboxKawasaki.isChecked = false
            checkboxKtm.isChecked = false
        }
    }

    private fun resetTypeCheckboxes() {
        binding.apply {
            checkboxSport.isChecked = false
            checkboxCrossMotocross.isChecked = false
            checkboxEnduroOffroad.isChecked = false
            checkboxNaked.isChecked = false
            checkboxTrail.isChecked = false
        }
    }

    private fun resetDisplacementInput() {
        binding.apply {
            minDisplacementEditText.setText(minDisplacement?.toString() ?: "")
            maxDisplacementEditText.setText(maxDisplacement?.toString() ?: "")
        }
    }

    private fun resetYearInput() {
        binding.apply {
            minYearEditText.setText(minYear?.toString() ?: "")
            maxYearEditText.setText(maxYear?.toString() ?: "")
        }
    }

    private fun cleanFilters() {
        selectedMakes.clear()
        selectedTypes.clear()
        minDisplacement = null
        maxDisplacement = null
        minYear = null
        maxYear = null
    }
}