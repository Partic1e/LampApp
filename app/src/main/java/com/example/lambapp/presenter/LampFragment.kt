package com.example.lambapp.presenter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.lambapp.R
import com.example.lambapp.UiState
import com.example.lambapp.app.App
import com.example.lambapp.data.model.Lamp
import com.example.lambapp.databinding.FragmentLampBinding
import com.example.lambapp.di.viewModel.ViewModelFactory
import javax.inject.Inject

class LampFragment : Fragment(R.layout.fragment_lamp) {

    private val binding: FragmentLampBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val lampViewModel: LampViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        (requireContext().applicationContext as App).appComponent.inject(this)
        super.onAttach(context)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSpinner()
        initSeekBar()
        initButton()

        lampViewModel.colorList.observe(viewLifecycleOwner) {
            onColorsReceived(it)
        }
        lampViewModel.loadAllColors()

        lampViewModel.lampLiveData.observe(viewLifecycleOwner) {
            onDataReceived(it)
        }
        lampViewModel.loadLampData()
    }

    private fun onDataReceived(lampData: UiState<Lamp?>?) {
        when(lampData) {
            is UiState.Failure -> {
                Log.e("LampApp", "UI Error: ${lampData.message}")
                binding.state.visibility = View.GONE
                binding.color.visibility = View.GONE
                binding.brightness.visibility = View.GONE
                binding.loading.visibility = View.GONE
                binding.errorImage.visibility = View.VISIBLE
                binding.errorText.visibility = View.VISIBLE
                binding.errorText.text = lampData.message
            }
            is UiState.Loading -> {
                Log.d("LampApp", "Loading data...")
                binding.state.visibility = View.GONE
                binding.color.visibility = View.GONE
                binding.brightness.visibility = View.GONE
                binding.loading.visibility = View.VISIBLE
                binding.errorImage.visibility = View.GONE
                binding.errorText.visibility = View.GONE
            }
            is UiState.Success -> {
                Log.d("LampApp", "Lamp data loaded: ${lampData.value}")
                binding.state.visibility = View.VISIBLE
                binding.color.visibility = View.VISIBLE
                binding.brightness.visibility = View.VISIBLE
                binding.loading.visibility = View.GONE
                binding.errorImage.visibility = View.GONE
                binding.errorText.visibility = View.GONE
                binding.state.text = lampData.value?.state?.action
                binding.color.text = lampData.value?.color
                binding.brightness.text = lampData.value?.brightness?.toString()
            }
            else -> {
                Log.w("LampApp", "Unexpected state")
            }
        }
    }

    private fun onColorsReceived(colors: UiState<List<String>?>) {
        when (colors) {
            is UiState.Loading -> {
                setupSpinner(listOf("Загрузка цветов..."), false)
            }
            is UiState.Success -> {
                setupSpinner(colors.value ?: emptyList(), true)
            }
            is UiState.Failure -> {
                setupSpinner(emptyList(), enabled = false)
                Toast.makeText(context, "Ошибка загрузки цветов", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupSpinner(items: List<String>, enabled: Boolean) {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            items).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinner.adapter = adapter
        binding.spinner.isEnabled = enabled
    }

    private fun initSpinner() {
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedColor = parent.getItemAtPosition(position) as String
                if (selectedColor == "Загрузка цветов..." || selectedColor.isEmpty()) {
                    return
                }
                lampViewModel.updateLampColor(selectedColor)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun initSeekBar() {
        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.brightnessText.text = "$progress"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                lampViewModel.updateLampBrightness(seekBar?.progress ?: 0)
            }
        })
    }

    private fun initButton() {
        binding.button.setOnClickListener {
            lampViewModel.updateLampState(binding.state.text.toString())
        }
    }
}
