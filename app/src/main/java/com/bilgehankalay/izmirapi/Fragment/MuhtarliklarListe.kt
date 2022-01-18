package com.bilgehankalay.izmirapi.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bilgehankalay.izmirapi.Database.ToplanmaYeriDatabase
import com.bilgehankalay.izmirapi.Model.ToplanmaYeri
import com.bilgehankalay.izmirapi.Network.ApiUtils
import com.bilgehankalay.izmirapi.Response.ExchangeResponse
import com.bilgehankalay.izmirapi.databinding.FragmentMuhtarliklarListeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MuhtarliklarListe : Fragment() {
    private lateinit var binding : FragmentMuhtarliklarListeBinding
    private lateinit var toplanmaYeriDB : ToplanmaYeriDatabase

    var onemliYerList : List<ToplanmaYeri?> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toplanmaYeriDB = ToplanmaYeriDatabase.getirToplanmaYeriDatabase(requireContext())!!
        onemliYerList = toplanmaYeriDB.toplanmaYeriDAO().tumMuhtarlikYerleriGetir()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMuhtarliklarListeBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity?)!!.supportActionBar!!.show()


    }


    private fun muhtarliklar_getir(){
        ApiUtils.ToplanmaYeriDAOInterfaceGetir().muhtarliklar_al().enqueue(object : Callback<ExchangeResponse>{
            override fun onResponse(
                call: Call<ExchangeResponse>,
                response: Response<ExchangeResponse>
            ) {
                val tempList = response?.body()?.onemliyerler
                tempList?.let {
                    onemliYerList = it
                }

                println("Liste boyutu : ${onemliYerList.size}")

                Toast.makeText(requireContext(),"Toplanma yerleri başarılı bir şekilde güncellendi! (${onemliYerList.size} kayıt)",
                    Toast.LENGTH_LONG).show()

                //toplanmaYeriAdapter.updateList(onemliYerList)
                TODO("ADAPTER YAZ, RECYCLER VİEW EKLE")


                for (onemliYer in onemliYerList){
                    if (onemliYer != null){
                        val returnValueOfDB = toplanmaYeriDB.toplanmaYeriDAO().getToplanmaYeriWithLL(onemliYer.enlem,onemliYer.boylam)
                        if (returnValueOfDB == null){
                            onemliYer.type = 1
                            toplanmaYeriDB.toplanmaYeriDAO().toplanmaYeriEkle(onemliYer)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ExchangeResponse>, t: Throwable) {
                println("ERROR ${t.localizedMessage}")
                Toast.makeText(requireContext(),"API hatası, daha sonra tekrar deneyin!",Toast.LENGTH_LONG).show()
            }

        })

    }





}