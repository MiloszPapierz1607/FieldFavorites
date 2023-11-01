package com.example.fieldfavorites.ui.screens.leagues

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fieldfavorites.model.League
import com.example.fieldfavorites.network.FootballApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface LeagueUiState {
    data class Success(val leagues: League) : LeagueUiState
    object Error : LeagueUiState
    object Loading : LeagueUiState
}

class LeagueViewModel : ViewModel() {
    var leagueUiState: LeagueUiState by mutableStateOf(LeagueUiState.Loading)
        private set

    init {
        getLeagues()
    }
    private fun getLeagues() {
        viewModelScope.launch {
            leagueUiState =  try {
                val listResult = FootballApi.retrofitService.getLeagues()
                println(listResult)
                LeagueUiState.Success(listResult.response.get(0).league)
            } catch(e: IOException) {
                Log.v("LeagueViewModel",e.message.toString())
                println(e.message.toString())
                LeagueUiState.Error
            }
            catch (e : HttpException) {
                Log.v("LeagueViewModel2",e.message.toString())
                println(e.message.toString())
                LeagueUiState.Error
            }
            catch (e : Error) {
                Log.v("LeagueViewModel3",e.message.toString())
                println(e.message.toString())
                LeagueUiState.Error
            }
        }
    }
}