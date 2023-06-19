package com.jairorr.samplesocialmediajc

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jairorr.samplesocialmediajc.data.MemberUser
import com.jairorr.samplesocialmediajc.network.GetRandomMembers
import com.jairorr.samplesocialmediajc.network.RetrofitObject
import com.jairorr.samplesocialmediajc.network.SocialMediaResult
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class BodyViewModel : ViewModel() {
    private val getRandomMembers = GetRandomMembers(RetrofitObject.service)
    private var _listMembers: MutableState<List<MemberUser>> = mutableStateOf(emptyList())
    val listMembers: State<List<MemberUser>> = _listMembers

    private var _listHistories: MutableState<List<MemberUser>> = mutableStateOf(emptyList())
    val listHistories: State<List<MemberUser>> = _listHistories

    var directory by mutableStateOf(false)
        private set

    init {
        getNewMembers()
        getNewHistories()
    }

    fun getNewMembers() {
        viewModelScope.launch {
            when(val response = getRandomMembers.invoke()){
                is SocialMediaResult.Error -> {
                    Log.d("SOCIALMEDIA","ERROR: ${response.message.toString()}")
                }
                is SocialMediaResult.Exception -> {
                    Log.d("SOCIALMEDIA","EXCEPTION: ${response.e.toString()}")
                }
                is SocialMediaResult.Success -> {_listMembers.value = response.data}
            }
        }
    }

    fun getNewHistories() {
        viewModelScope.launch {
            when(val response = getRandomMembers.invoke()){
                is SocialMediaResult.Error ->{}
                is SocialMediaResult.Exception -> {}
                is SocialMediaResult.Success -> {_listHistories.value = response.data}
            }
        }
    }

    fun refreshBoth(){
        viewModelScope.launch {
            val responseMembers = async {
                when(val res = getRandomMembers.invoke()){
                    is SocialMediaResult.Error ->{Log.d("SOCIALMEDIA","ERROR: ${res.message.toString()}")
                    null}
                    is SocialMediaResult.Exception -> {Log.d("SOCIALMEDIA","EXCEPTION: ${res.e}")
                        null}
                    is SocialMediaResult.Success -> res.data
                }
            }
            val responseHistories = async {
                when(val res = getRandomMembers.invoke()){
                    is SocialMediaResult.Error ->{Log.d("SOCIALMEDIA","ERROR: ${res.message.toString()}")
                        null}
                    is SocialMediaResult.Exception -> {Log.d("SOCIALMEDIA","EXCEPTION: ${res.e.toString()}")
                        null}
                    is SocialMediaResult.Success -> res.data
                }
            }
            _listHistories.value = responseHistories.await() ?: emptyList()
            _listMembers.value = responseMembers.await() ?: emptyList()

        }
    }


}