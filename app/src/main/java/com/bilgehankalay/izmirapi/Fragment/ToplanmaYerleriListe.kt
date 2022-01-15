package com.bilgehankalay.izmirapi.Fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bilgehankalay.izmirapi.Adapter.ToplanmaYeriRecyclerViewAdapter
import com.bilgehankalay.izmirapi.Database.ToplanmaYeriDatabase
import com.bilgehankalay.izmirapi.Model.ToplanmaYeri
import com.bilgehankalay.izmirapi.Network.ApiUtils
import com.bilgehankalay.izmirapi.R
import com.bilgehankalay.izmirapi.Response.ExchangeResponse
import com.bilgehankalay.izmirapi.databinding.FragmentToplanmaYerleriListeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ToplanmaYerleriListe : Fragment() {
    private  lateinit var binding : FragmentToplanmaYerleriListeBinding //view binding
    var onemliYerList : List<ToplanmaYeri?> = arrayListOf()
    private lateinit var toplanmaYeriDB : ToplanmaYeriDatabase
    val toplanmaYeriAdapter = ToplanmaYeriRecyclerViewAdapter(onemliYerList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toplanmaYeriDB = ToplanmaYeriDatabase.getirToplanmaYeriDatabase(requireContext())!!
        onemliYerList = toplanmaYeriDB.toplanmaYeriDAO().tumToplanmaYerleriGetir()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentToplanmaYerleriListeBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.aramaText.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 != null && p0.isNotEmpty()) {
                    //aramayı yap
                    val aranan_onemli_yer_list: List<ToplanmaYeri?>
                    aranan_onemli_yer_list = toplanmaYeriDB.toplanmaYeriDAO().getToplanmaYeriWithName(p0.toString())
                    println("Gelen Sayi : ${aranan_onemli_yer_list.size}")
                    toplanmaYeriAdapter.updateList(aranan_onemli_yer_list)

                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        toplanmaYeriAdapter.updateList(onemliYerList)

        toplanmaYeriAdapter.onRootClick = ::secilenRootToplanmaYeriOnClick
        toplanmaYeriAdapter.onImageViewClick = ::secilenImageViewToplanmaYeriOnClick
        Toast.makeText(requireContext(),"Önemli yer sayı: ${onemliYerList.size}",Toast.LENGTH_LONG).show()
        binding.toplanmaYeriListeRecyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.toplanmaYeriListeRecyclerView.adapter = toplanmaYeriAdapter
        binding.toplanmaYeriListeRecyclerView.setHasFixedSize(true)

        onemliYerGetir()

    }

    private fun onemliYerGetir(){
        ApiUtils.ToplanmaYeriDAOInterfaceGetir().onemliYerAl().enqueue(
            object : Callback<ExchangeResponse> {
                override fun onResponse(
                    call: Call<ExchangeResponse>,
                    response: Response<ExchangeResponse>
                ) {

                    val tempList = response.body()?.onemliyerler
                    tempList?.let {
                        onemliYerList = it
                    }

                    println("Liste boyutu : ${onemliYerList.size}")

                    Toast.makeText(requireContext(),"Toplanma yerleri başarılı bir şekilde güncellendi! (${onemliYerList.size} kayıt)",Toast.LENGTH_LONG).show()
                    toplanmaYeriAdapter.updateList(onemliYerList)


                    for (onemliYer in onemliYerList){
                        if (onemliYer != null){
                            //val returnValueOfDB = toplanmaYeriDB.toplanmaYeriDAO().getToplanmaYeriWithName(onemliYer.adi)
                            val returnValueOfDB = toplanmaYeriDB.toplanmaYeriDAO().getToplanmaYeriWithLL(onemliYer.enlem,onemliYer.boylam)
                            if (returnValueOfDB == null){
                                /*
                                val eklenecekOnemliYer = ToplanmaYeri(ilce = onemliYer.ilce, kapino = onemliYer.kapino, enlem = onemliYer.enlem, aciklama = onemliYer.aciklama, ilce_id = onemliYer.ilce_id, mahalle = onemliYer.mahalle, mahalle_id = onemliYer.mahalle_id, adi = onemliYer.adi, yol = onemliYer.yol, boylam = onemliYer.boylam)
                                 */
                                toplanmaYeriDB.toplanmaYeriDAO().toplanmaYeriEkle(onemliYer)
                            }
                        }
                    }

                }

                override fun onFailure(call: Call<ExchangeResponse>, t: Throwable) {
                    println("ERROR ${t.localizedMessage}")
                    Toast.makeText(requireContext(),"API HATA ",Toast.LENGTH_LONG).show()
                }

            }
        )
    }


    private fun secilenImageViewToplanmaYeriOnClick(gelenToplanmaYeri : ToplanmaYeri){
        val gmmIntentUri = Uri.parse("http://maps.google.com/maps?daddr=" + gelenToplanmaYeri.enlem + "," + gelenToplanmaYeri.boylam)
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }
    private fun secilenRootToplanmaYeriOnClick(gelenToplanmaYeri : ToplanmaYeri){
        //gelen veriyi diğer sekmeye gönlendir
        val gecisAction = ToplanmaYerleriListeDirections.listeToDetay(gelenToplanmaYeri)
        findNavController().navigate(gecisAction)

    }
}