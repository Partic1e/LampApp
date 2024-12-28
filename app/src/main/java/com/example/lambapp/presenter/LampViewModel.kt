package com.example.lambapp.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lambapp.UiState
import com.example.lambapp.data.model.Lamp
import com.example.lambapp.data.model.LampState
import com.example.lambapp.domain.usecase.ChangeBrightnessLampUseCase
import com.example.lambapp.domain.usecase.ChangeColorLampUseCase
import com.example.lambapp.domain.usecase.ChangeStateLampUseCase
import com.example.lambapp.domain.usecase.GetLampOptionsUseCase
import com.example.lambapp.domain.usecase.GetListOfColorsUseCase
import com.example.lambapp.toUiState
import kotlinx.coroutines.launch
import javax.inject.Inject

class LampViewModel @Inject constructor(
    private val getLampOptionsUseCase: GetLampOptionsUseCase,
    private val getListOfColorsUseCase: GetListOfColorsUseCase,
    private val changeStateLampUseCase: ChangeStateLampUseCase,
    private val changeColorLampUseCase: ChangeColorLampUseCase,
    private val changeBrightnessLampUseCase: ChangeBrightnessLampUseCase
) : ViewModel() {

    private val _lampLiveData = MutableLiveData<UiState<Lamp?>>(UiState.Loading)
    val lampLiveData: LiveData<UiState<Lamp?>>
        get() = _lampLiveData

    private val _colorList = MutableLiveData<UiState<List<String>?>>(UiState.Loading)
    val colorList: LiveData<UiState<List<String>?>>
        get() = _colorList

    private val _optionsStatus = MutableLiveData<UiState<Boolean?>>(UiState.Loading)
    val lampOptions: LiveData<UiState<Boolean?>>
        get() = _optionsStatus

    fun loadLampData() {
        viewModelScope.launch {
            _lampLiveData.postValue(UiState.Loading)
            val lampOptionsResult = getLampOptionsUseCase()
            _lampLiveData.postValue(lampOptionsResult.toUiState())
        }
    }

    fun loadAllColors() {
        viewModelScope.launch {
            val colors = getListOfColorsUseCase()
            _colorList.postValue(colors.toUiState())
        }
    }

    fun updateLampState(state: String) {
        viewModelScope.launch {
            val lamp = (_lampLiveData.value as? UiState.Success)?.value
            lamp?.state = if (state == "on") LampState.OFF else LampState.ON
            val result = lamp?.let { changeStateLampUseCase(it) }
            result?.let {
                _optionsStatus.postValue(it.toUiState())
            }
        }
        loadLampData()
    }

    fun updateLampColor(color: String) {
        viewModelScope.launch {
            val lamp = (_lampLiveData.value as? UiState.Success)?.value
            lamp?.color = color
            val result = lamp?.let { changeColorLampUseCase(it) }
            if (result != null) {
                _optionsStatus.postValue(result.toUiState())
            }
        }
        loadLampData()
    }

    fun updateLampBrightness(brightness: Int) {
        viewModelScope.launch {
            val lamp = (_lampLiveData.value as? UiState.Success)?.value
            lamp?.brightness = brightness
            val result = lamp?.let { changeBrightnessLampUseCase(it) }
            if (result != null) {
                _optionsStatus.postValue(result.toUiState())
            }
        }
        loadLampData()
    }

    private fun applyСhanges() {}
}
