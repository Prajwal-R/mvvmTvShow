package com.example.hilttvshowlister

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.hilttvshowlister.fragment.FirstFragment
import com.example.hilttvshowlister.fragment.SecondFragment
import com.example.hilttvshowlister.utility.CallBackInterface
import android.util.Log
import androidx.fragment.app.FragmentManager


class MainActivity : AppCompatActivity() , CallBackInterface {

    private val firstFragment = FirstFragment().apply {
        callBackInterface = this@MainActivity
    }
    private val secondFragment = SecondFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayoutFragment, firstFragment)
            commit()
        }
    }

    override fun onCallBack(tvShowId: Int) {
        val bundle = Bundle()
        bundle.putInt("tvShowId", tvShowId)

        secondFragment.arguments = bundle
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayoutFragment, secondFragment)
            //Log.d("TAG", "onCallBack: main class check")
            addToBackStack("second page")
            commit()
        }
    }
}