package com.example.coroutineflow.team_score

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.coroutineflow.databinding.ActivityTeamScoreBinding

class TeamScoreActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityTeamScoreBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[TeamScoreViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observeViewModel()
        setupListener()

    }

    private fun observeViewModel() {
        viewModel.state.observe(this) {
            when (it) {
                is TeamScoreState.Game -> {
                    binding.team1Score.text = it.score1.toString()
                    binding.team2Score.text = it.score2.toString()
                }

                is TeamScoreState.Winner -> {
                    binding.team1Score.text = it.score1.toString()
                    binding.team2Score.text = it.score2.toString()
                    Toast.makeText(
                        this,
                        "Winner ${it.winnerTeam}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }
    }

    private fun setupListener(){
        binding.team1Logo.setOnClickListener {
            viewModel.increaseScore(Team.Team1)
        }
        binding.team2Logo.setOnClickListener {
            viewModel.increaseScore(Team.Team2)
        }
    }

    companion object {

        fun newIntent(context: Context) = Intent(context, TeamScoreActivity::class.java)
    }
}