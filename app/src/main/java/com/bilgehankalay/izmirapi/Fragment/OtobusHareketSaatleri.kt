package com.bilgehankalay.izmirapi.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bilgehankalay.izmirapi.Adapter.HareketSaatleriRecyclerViewAdapter
import com.bilgehankalay.izmirapi.Model.HareketSaati
import com.bilgehankalay.izmirapi.Network.ApiUtils
import com.bilgehankalay.izmirapi.R
import com.bilgehankalay.izmirapi.Response.HareketSaatleriResponse
import com.bilgehankalay.izmirapi.databinding.FragmentOtobusHareketSaatleriBinding
import com.bilgehankalay.izmirapi.databinding.FragmentOtobusHatlariBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OtobusHareketSaatleri : Fragment() {
    private lateinit var binding : FragmentOtobusHareketSaatleriBinding
    private val args : OtobusHareketSaatleriArgs by navArgs()

    private var hatnumarasi  = 0
    private var hatAdi = ""

    private var hafta_ici_hareket_saatleri : List<HareketSaati> = arrayListOf()
    private var cumartesi_hareket_saatleri : List<HareketSaati> = arrayListOf()
    private var pazar_hareket_saatleri: List<HareketSaati> = arrayListOf()

    private var spinner_secili_item = 0

    val hareketSaatleriAdapter = HareketSaatleriRecyclerViewAdapter(hafta_ici_hareket_saatleri)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOtobusHareketSaatleriBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hatnumarasi = args.hatNo
        hatAdi = args.adi
        binding.textViewHatBilgi.text = "${hatnumarasi} ${hatAdi}"
        hareket_saatleri_getir()

        binding.recyclerViewHareketSaatleri.adapter = hareketSaatleriAdapter
        binding.recyclerViewHareketSaatleri.layoutManager =  LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,false)
        binding.recyclerViewHareketSaatleri.setHasFixedSize(true)

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.hareketSaatleriSecekeler,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerHareketSaatleri.adapter = adapter
        }


        binding.spinnerHareketSaatleri.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinner_secili_item = p2
                spinner_hareket_saatleri_yukle(spinner_secili_item)

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    private fun hareket_saatleri_getir(){
        ApiUtils.ToplanmaYeriDAOInterfaceGetir().hareket_saatleri_al(hatnumarasi).enqueue(object : Callback<HareketSaatleriResponse>{
            override fun onResponse(
                call: Call<HareketSaatleriResponse>,
                response: Response<HareketSaatleriResponse>
            ) {
                val hafta_ici = response.body()?.HareketSaatlerHici
                val cumartesi = response.body()?.HareketSaatleriCtesi
                val pazar = response.body()?.HareketSaatleriPazar
                hafta_ici?.let {
                    hafta_ici_hareket_saatleri = it
                }
                cumartesi?.let {
                    cumartesi_hareket_saatleri = it
                }
                pazar?.let {
                    pazar_hareket_saatleri = it
                }
                spinner_hareket_saatleri_yukle(spinner_secili_item)



            }

            override fun onFailure(call: Call<HareketSaatleriResponse>, t: Throwable) {
                println("Error ${t.localizedMessage}")
                Toast.makeText(requireContext(),"API Error, daha sonra tekrar deneyin.", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun spinner_hareket_saatleri_yukle(spinner_secili_item : Int){
        when(spinner_secili_item){
            0 -> {
                //hafta ici
                hareketSaatleriAdapter.updateList(hafta_ici_hareket_saatleri)
            }
            1 -> {
                //cumartesi
                hareketSaatleriAdapter.updateList(cumartesi_hareket_saatleri)
            }
            2 -> {
                //pazar
                hareketSaatleriAdapter.updateList(pazar_hareket_saatleri)
            }


        }
    }


}