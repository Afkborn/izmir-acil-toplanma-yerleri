package com.bilgehankalay.izmirapi.Fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.bilgehankalay.izmirapi.Model.HavaDurumu
import com.bilgehankalay.izmirapi.Network.ApiUtils
import com.bilgehankalay.izmirapi.R
import com.bilgehankalay.izmirapi.Response.WeatherResponse
import com.bilgehankalay.izmirapi.databinding.FragmentIzmirApiMenuBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.concurrent.schedule


class IzmirApi_Menu : Fragment() {

    private var havaDurumuListe : List<HavaDurumu> = arrayListOf()
    private lateinit var binding : FragmentIzmirApiMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hava_durum_al()

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

        binding.constraintLayoutHavadurumu.setOnClickListener {
            println("tıklandı.")
        }

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

        //hava_durum_al()



    }


    private fun hava_durum_al(){
        ApiUtils.collectApiDaoInterfaceGetir().hava_durumu_al("tr","izmir").enqueue(object  : Callback<WeatherResponse>{
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                val basariDurumu = response.body()?.success
                if(basariDurumu != null && basariDurumu == true){
                    val tempList = response.body()?.result
                    tempList?.let {
                        havaDurumuListe = it
                    }

                    val bugunHavaDurum = havaDurumuListe[0]
                    val bugunHavaDurumDegree = bugunHavaDurum.degree.toDouble().toInt()

                    binding.textViewHavaDurumSicaklik.text  = "${bugunHavaDurumDegree}°"
                    Picasso.get().load(bugunHavaDurum.icon).into(binding.imageViewHavaDurumuIcon)
                }
                else{
                    println("Error basariDurumu ${basariDurumu}")
                    Toast.makeText(requireContext(),"Api hatası. ${basariDurumu}",Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                println("Error ${t.localizedMessage}")
                Toast.makeText(requireContext(),"Api hatası.",Toast.LENGTH_LONG).show()
            }

        })
    }




}