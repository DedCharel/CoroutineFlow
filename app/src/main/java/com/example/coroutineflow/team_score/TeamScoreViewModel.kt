package com.example.coroutineflow.team_score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TeamScoreViewModel: ViewModel() {

    private val _state = MutableLiveData<TeamScoreState>(TeamScoreState.Game(0, 0))
    val state: LiveData<TeamScoreState> = _state

    fun increaseScore(team: Team){
        val currentState = _state.value
        if (currentState is TeamScoreState.Game) {
            if (team == Team.Team1) {
                val oldValue = currentState.score1
                val newValue = oldValue + 1
                _state.value = currentState.copy(score1 = newValue)
                if (newValue >= WINNER_SCORE) {
                    _state.value = TeamScoreState.Winner(
                        winnerTeam = Team.Team1,
                        score1 = newValue,
                        score2 = currentState.score2
                    )
                }
            } else {
                val oldValue = currentState.score2
                val newValue = oldValue + 1
                _state.value = currentState.copy(score2 = newValue)
                if (newValue >= WINNER_SCORE) {
                    _state.value = TeamScoreState.Winner(
                        winnerTeam = Team.Team2,
                        score1 = currentState.score1,
                        score2 = newValue
                    )
                }
            }
        }
    }

    companion object {
        private const val WINNER_SCORE = 7
    }
}