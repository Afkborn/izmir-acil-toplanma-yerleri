package com.bilgehankalay.izmirapi.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bilgehankalay.izmirapi.Adapter.HareketSaatleriRecyclerViewAdapter
import com.bilgehankalay.izmirapi.Database.HatlarDatabase
import com.bilgehankalay.izmirapi.Model.Hat
import com.bilgehankalay.izmirapi.Network.ApiUtils
import com.bilgehankalay.izmirapi.R
import com.bilgehankalay.izmirapi.Response.HatlarResponse
import com.bilgehankalay.izmirapi.databinding.FragmentOtobusHatlariBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtobusHatlari : Fragment() {
    private lateinit var binding : FragmentOtobusHatlariBinding
    private lateinit var hatlarDb : HatlarDatabase


    var otobusHatlariList : List<Hat?> = arrayListOf()
    lateinit var secilenHat : Hat



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hatlarDb = HatlarDatabase.getirHatlarDatabase(requireContext())!!
        otobusHatlariList = hatlarDb.hatlarDAO().tum_hatlari_getir()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOtobusHatlariBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonHareketSaatleri.setOnClickListener {
            if (secilenHat != null){
                val gecisAction = OtobusHatlariDirections.otobusHatlariToHareketSaatleri(secilenHat.HatNo,secilenHat.Adi)
                findNavController().navigate(gecisAction)
            }

        }
        binding.spinnerHatNumaralar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                secilenHat = otobusHatlariList[p2]!!
                binding.let {
                    if (secilenHat != null){
                        if (secilenHat.Aciklama == ""){
                            it.textViewAciklama.text = "Herhangi bir açıklama girilmemiş."
                        }
                        else
                            it.textViewAciklama.text = secilenHat.Aciklama

                        it.textViewBaslangicYeri.text = secilenHat.HatBaslangic
                        it.textViewBitisYeri.text = secilenHat.HatBitis
                        it.textViewCalismaSaatiDonus.text = secilenHat.CalismaSaatiDonus
                        it.textViewCalismaSaatiGidis.text = secilenHat.CalismaSaatiGidis
                        it.textViewTarife.text = secilenHat.Tarife


                    }

                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        if (otobusHatlariList.isEmpty()){
            hatlari_getir()
        }
        else{
            hatlari_spinnera_yukle()
            hatlari_getir()
        }



    }

    private fun hatlari_getir(){
        val baslangic_hat_sayisi = otobusHatlariList.size
        ApiUtils.ToplanmaYeriDAOInterfaceGetir().hatlari_al().enqueue(object : Callback<HatlarResponse>{
            override fun onResponse(
                call: Call<HatlarResponse>,
                response: Response<HatlarResponse>
            ) {
                val hataMesaji = response.body()?.hataMesaj
                val hataVarMi = response.body()?.hataVarMi
                if (hataVarMi == true){
                    println("Hata: ${hataMesaji}")
                    Toast.makeText(requireContext(),"Error ${hataMesaji}",Toast.LENGTH_LONG).show()
                    return
                }

                val tempList = response?.body()?.hatlar
                tempList?.let {
                    otobusHatlariList = it
                }

                /*
                if (otobusHatlariList.size == baslangic_hat_sayisi){
                    // database'dan gelen baslangıc hat sayısı ile aynı ise diğerlerini yapmana gerek yok
                    return
                }

                 */

                hatlari_spinnera_yukle()
                otobus_hatlari_kaydet()

            }

            override fun onFailure(call: Call<HatlarResponse>, t: Throwable) {
                println("Error ${t.localizedMessage}")
                Toast.makeText(requireContext(),"API Error, daha sonra tekrar deneyin.",Toast.LENGTH_LONG).show()

            }

        })
    }

    private fun hatlari_spinnera_yukle(){
        val gozukur_hat_numaralari_listesi : ArrayList<String> = arrayListOf()
        otobusHatlariList.forEach {
            if (it !=null){
                gozukur_hat_numaralari_listesi.add("${it.HatNo} ${it.Adi}")
            }
        }
        val adapter : ArrayAdapter<*>
        adapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            gozukur_hat_numaralari_listesi
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerHatNumaralar.adapter = adapter
        adapter.notifyDataSetChanged()


    }

    private fun otobus_hatlari_kaydet(){
        otobusHatlariList.forEach {
            if (it !=null){
                val returnValue = hatlarDb.hatlarDAO().hat_getir_with_hatno(it.HatNo)
                if (returnValue == null){
                    hatlarDb.hatlarDAO().HatEkle(it)

                }
            }
        }
    }






}