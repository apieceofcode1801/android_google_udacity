package com.example.colormyview

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.colormyview.databinding.ActivityMainBinding
import androidx.databinding.DataBindingUtil

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setListeners()
    }

    private fun setListeners() {
        val clickableViews: List<View> = listOf(
            binding.boxOneText, binding.boxTwoText, binding.boxThreeText, binding.boxFourText, binding.boxFiveText, binding.constraintLayout,
            binding.redButton, binding.yellowButton, binding.greenButton
        )

        for (item in clickableViews) {
            item.setOnClickListener {
                makeColored(it)
            }
        }
    }

    private fun makeColored(view: View) {
        when (view.id) {
            R.id.box_one_text -> view.setBackgroundColor(Color.DKGRAY)
            R.id.box_two_text -> view.setBackgroundColor(Color.GRAY)

            R.id.box_three_text -> view.setBackgroundResource(R.color.purple_200)
            R.id.box_four_text -> view.setBackgroundResource(R.color.purple_700)
            R.id.box_five_text -> view.setBackgroundResource(R.color.teal_700)

            R.id.red_button -> binding.boxOneText.setBackgroundColor(Color.RED)
            R.id.yellow_button -> binding.boxFourText.setBackgroundColor(Color.YELLOW)
            R.id.green_button -> binding.boxFiveText.setBackgroundColor(Color.GREEN)
            else -> view.setBackgroundColor(Color.LTGRAY)
        }
    }
}