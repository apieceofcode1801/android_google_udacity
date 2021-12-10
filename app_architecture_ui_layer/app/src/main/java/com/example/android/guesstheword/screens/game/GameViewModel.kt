package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import android.view.animation.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment

class GameViewModel : ViewModel() {
    enum class BuzzType(val pattern: LongArray) {
        CORRECT(CORRECT_BUZZ_PATTERN),
        GAME_OVER(GAME_OVER_BUZZ_PATTERN),
        COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),
        NO_BUZZ(NO_BUZZ_PATTERN)
    }
    companion object {
        private const val DONE = 0L

        private const val ONE_SECOND = 1000L

        private const val COUNTDOWN_TIME = 10000L

        private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
        private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
        private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
        private val NO_BUZZ_PATTERN = longArrayOf(0)
    }

    // The current word
    private var _word = MutableLiveData<String>()
    val word: LiveData<String> get() = _word

    // The current score
    private var _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score

    private var _time = MutableLiveData<Long>()
    val time: LiveData<Long> get() = _time

    val timeString = Transformations.map(time, {
        DateUtils.formatElapsedTime(it)
    })

    private var _eventBuzz = MutableLiveData<BuzzType>()
    val eventBuzz: LiveData<BuzzType> get() = _eventBuzz

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    private var _gameFinished = MutableLiveData<Boolean>()
    val gameFinished: LiveData<Boolean> get() = _gameFinished

    private val timer: CountDownTimer

    init {
        Log.i("GameViewModel", "GameViewModel created")
        resetList()
        nextWord()
        _score.value = 0
        _word.value = wordList.removeAt(0)
        _gameFinished.value = false
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _time.value = millisUntilFinished / ONE_SECOND

                if (millisUntilFinished / ONE_SECOND <= 3) {
                    _eventBuzz.value = BuzzType.COUNTDOWN_PANIC
                }
            }

            override fun onFinish() {
                _gameFinished.value = true
            }
        }
        timer.start()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "destroyed")
        timer.cancel()
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }

    private fun nextWord() {
        if (wordList.isEmpty()) {
            _eventBuzz.value = BuzzType.GAME_OVER
            resetList()
        }
        _word.value = wordList.removeAt(0)
    }

    fun onSkip() {
        _eventBuzz.value = BuzzType.NO_BUZZ
        _score.value = (_score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _eventBuzz.value = BuzzType.CORRECT
        _score.value = (_score.value)?.plus(1)
        nextWord()
    }

    fun onGameFinishedComplete() {
        _gameFinished.value = false
    }
}