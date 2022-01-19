package com.bilgehankalay.izmirapi.Fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.bilgehankalay.izmirapi.R
import com.bilgehankalay.izmirapi.databinding.FragmentIzmirApiMenuBinding
import java.util.*
import kotlin.concurrent.schedule


class IzmirApi_Menu : Fragment() {


    private lateinit var binding : FragmentIzmirApiMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentIzmirApiMenuBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity?)!!.supportActionBar!!.hide()
        binding.buttonAcilToplanmaYerleri.setOnClickListener {
            val gecisAction = IzmirApi_MenuDirections.menuToToplanmaYerleriListe(0)
            findNavController().navigate(gecisAction)


        }
        binding.buttonMuhtarliklar.setOnClickListener {
            val gecisAction = IzmirApi_MenuDirections.menuToToplanmaYerleriListe(1)
            findNavController().navigate(gecisAction)
        }
        binding.buttonOtobusHatlari.setOnClickListener {
            val gecisAction = IzmirApi_MenuDirections.menuToOtobusHatlari()
            findNavController().navigate(gecisAction)

        }
        binding.imageViewIzmirLogo.alpha = 0f
        binding.imageViewIzmirLogo.animate().apply {
            interpolator = LinearInterpolator()
            duration = 1500
            alpha(1f)
            startDelay = 0
            start()
            this.setListener(object : Animator.AnimatorListener{
                override fun onAnimationStart(p0: Animator?) {

                }

                override fun onAnimationEnd(p0: Animator?) {
                    binding.imageViewIzmirLogo.visibility = View.GONE
                    binding.menuDesign.visibility = View.VISIBLE

                }

                override fun onAnimationCancel(p0: Animator?) {

                }

                override fun onAnimationRepeat(p0: Animator?) {

                }

            })


        }



    }




}