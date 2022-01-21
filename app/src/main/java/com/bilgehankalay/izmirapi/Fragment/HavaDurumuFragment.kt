package com.bilgehankalay.izmirapi.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.bilgehankalay.izmirapi.Model.HavaDurumu
import com.bilgehankalay.izmirapi.Network.ApiUtils
import com.bilgehankalay.izmirapi.R
import com.bilgehankalay.izmirapi.Response.WeatherResponse
import com.bilgehankalay.izmirapi.databinding.FragmentHavaDurumuBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.util.*


class HavaDurumuFragment : Fragment() {

    private lateinit var binding : FragmentHavaDurumuBinding
    private var havaDurumuListe : List<HavaDurumu?> = arrayListOf()
    private lateinit var bugun_hava_durum : HavaDurumu
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hava_durum_al()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHavaDurumuBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun hava_durum_al(){
        ApiUtils.collectApiDaoInterfaceGetir().hava_durumu_al("tr","izmir").enqueue(object  :
            Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                val basariDurumu = response.body()?.success
                if(basariDurumu != null && basariDurumu == true){
                    val sehir = response.body()?.city

                    val tempList = response.body()?.result

                    tempList?.let {
                        havaDurumuListe = it
                    }
                    havaDurumuListe.forEach {
                        if (it != null && sehir != null){
                            it.sehir = sehir
                        }
                    }

                    bugun_hava_durum = havaDurumuListe[0]!!
                    binding.textViewSehirAdi.text  = bugun_hava_durum.sehir.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.getDefault()
                        ) else it.toString()
                    }
                    binding.textViewHavaDurumAciklama.text = bugun_hava_durum.description
                    binding.textViewHavaDurumDate.text = "${bugun_hava_durum.date} ${bugun_hava_durum.day}"
                    binding.textViewHavaDurumMaxMin.text = "En yüksek ${bugun_hava_durum.max}°C ve en düşük ${bugun_hava_durum.min}°C"
                    Picasso.get().load(bugun_hava_durum.icon).into(binding.imageViewHavaIcon)

                    val bugunHavaDurumDegree = bugun_hava_durum!!.degree.toDouble().toInt()
                    binding.textViewDerece.text = bugunHavaDurumDegree.toString()




                }
                else{
                    println("Error basariDurumu ${basariDurumu}")
                    Toast.makeText(requireContext(),"Api hatası. ${basariDurumu}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                println("Error ${t.localizedMessage}")
                Toast.makeText(requireContext(),"Api hatası.", Toast.LENGTH_LONG).show()
            }

        })
    }


}