package com.example.coroutineflow.team_score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TeamScoreViewModel : ViewModel() {

    private var cashedState: TeamScoreState = TeamScoreState.Game(0, 0)

    private val _state = MutableSharedFlow<TeamScoreState>(replay = 1)
    val state = _state.asSharedFlow()
        .onEach { cashedState = it }


    fun increaseScore(team: Team) {
        viewModelScope.launch {
            val currentState = cashedState
            if (currentState is TeamScoreState.Game) {
                if (team == Team.Team1) {
                    val oldValue = currentState.score1
                    val newValue = oldValue + 1
                    _state.emit(currentState.copy(score1 = newValue))
                    if (newValue >= WINNER_SCORE) {
                        _state.emit(
                            TeamScoreState.Winner(
                                winnerTeam = Team.Team1,
                                score1 = newValue,
                                score2 = currentState.score2
                            )
                        )
                    }
                } else {
                    val oldValue = currentState.score2
                    val newValue = oldValue + 1
                    _state.emit(currentState.copy(score2 = newValue))
                    if (newValue >= WINNER_SCORE) {
                        _state.emit(
                            TeamScoreState.Winner(
                                winnerTeam = Team.Team2,
                                score1 = currentState.score1,
                                score2 = newValue
                            )
                        )
                    }
                }
            }
        }

    }

    companion object {
        private const val WINNER_SCORE = 7
    }
}