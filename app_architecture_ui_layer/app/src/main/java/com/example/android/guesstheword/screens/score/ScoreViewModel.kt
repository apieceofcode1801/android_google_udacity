package com.example.android.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore: Int) : ViewModel() {
    private var _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score

    private var _eventPlayAgain = MutableLiveData<Boolean>()
    val eventPlayAgain: LiveData<Boolean> get() = _eventPlayAgain

    fun onPlayAgain() {
        _eventPlayAgain.value = true
    }

    fun onPlayAgainComplete() {
        _eventPlayAgain.value = false
    }

    init {
        Log.i("ScoreViewModel", "Final Score $finalScore")
        _score.value = finalScore
        _eventPlayAgain.value = false
    }
}