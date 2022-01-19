package com.bilgehankalay.izmirapi.Fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bilgehankalay.izmirapi.Adapter.ToplanmaYeriRecyclerViewAdapter
import com.bilgehankalay.izmirapi.Database.ToplanmaYeriDatabase
import com.bilgehankalay.izmirapi.Model.ToplanmaYeri
import com.bilgehankalay.izmirapi.Network.ApiUtils
import com.bilgehankalay.izmirapi.R
import com.bilgehankalay.izmirapi.Response.OnemliYerResponse
import com.bilgehankalay.izmirapi.databinding.FragmentToplanmaYerleriListeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class ToplanmaYerleriListe : Fragment() {
    private  lateinit var binding : FragmentToplanmaYerleriListeBinding //view binding
    private  val args : ToplanmaYerleriListeArgs by navArgs()
    var onemliYerList : List<ToplanmaYeri?> = arrayListOf()

    private lateinit var toplanmaYeriDB : ToplanmaYeriDatabase
    val toplanmaYeriAdapter = ToplanmaYeriRecyclerViewAdapter(onemliYerList)

    var isVisibleSearch = false
    var isVisibleFilter = false

    val ilceler_listesi : ArrayList<String> = arrayListOf()
    val mahalleler_listesi : ArrayList<String> = arrayListOf()

    var seciliİlce : String = ""

    private var data_type : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toplanmaYeriDB = ToplanmaYeriDatabase.getirToplanmaYeriDatabase(requireContext())!!
        data_type = args.dataType
        when (data_type){
            0 -> onemliYerList = toplanmaYeriDB.toplanmaYeriDAO().tumAcilToplanmaYerleriGetir()
            1 -> onemliYerList = toplanmaYeriDB.toplanmaYeriDAO().tumMuhtarlikYerleriGetir()
        }

        setHasOptionsMenu(true)



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
        (requireActivity() as AppCompatActivity?)!!.supportActionBar!!.show()

        binding.ilceSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                println("seçili olanın adı ${ilceler_listesi[p2]}")
                seciliİlce = ilceler_listesi[p2]
                spinner_yukle_mahalle(ilceler_listesi[p2])

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                println("hiç bir şey seçili değil.")
            }

        }

        binding.mahalleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            var tempArrayList : ArrayList<ToplanmaYeri> = arrayListOf()
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                println("seçili olanın adı ${mahalleler_listesi[p2]}")
                tempArrayList.clear()
                if (mahalleler_listesi[p2] == "BÜTÜN İLÇE"){
                    onemliYerList.forEach {
                        if (it!!.ilce == seciliİlce){
                            tempArrayList.add(it)
                        }
                    }
                }
                else{
                    onemliYerList.forEach {
                        if (it!!.mahalle == mahalleler_listesi[p2] && it.ilce == seciliİlce){
                            tempArrayList.add(it)
                        }
                    }
                }
                toplanmaYeriAdapter.updateList(tempArrayList)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                println("hiç bir şey seçili değil")
            }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            var tempArrayList : ArrayList<ToplanmaYeri> = arrayListOf()
            override fun onQueryTextSubmit(p0: String?): Boolean {
                tempArrayList.clear()
                if (p0!= null) {
                    onemliYerList.forEach {
                        if (it!!.adi.lowercase(Locale.getDefault()).contains(p0.toString().lowercase(Locale.getDefault()))){
                            tempArrayList.add(it)
                        }
                    }

                    toplanmaYeriAdapter.updateList(tempArrayList)
                }
                else{
                    toplanmaYeriAdapter.updateList(onemliYerList)
                }

                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                tempArrayList.clear()
                if (p0!= null) {
                    onemliYerList.forEach {
                        if (it!!.adi.lowercase(Locale.getDefault()).contains(p0.toString().lowercase(Locale.getDefault()))){
                            tempArrayList.add(it)
                        }
                    }

                    toplanmaYeriAdapter.updateList(tempArrayList)
                }
                else{
                    toplanmaYeriAdapter.updateList(onemliYerList)
                }

                return false

            }

        })

        if (onemliYerList.isEmpty()){
            Toast.makeText(requireContext(),"Veriler internetten güncelleniyor.",Toast.LENGTH_LONG).show()
            get_data_with_data_type()

        }
        else{
            toplanmaYeriAdapter.updateList(onemliYerList)
            Toast.makeText(requireContext(),"Toplam Acil Toplanma Yeri sayısı ${onemliYerList.size}",Toast.LENGTH_LONG).show()
        }



        toplanmaYeriAdapter.onRootClick = ::secilenRootToplanmaYeriOnClick
        toplanmaYeriAdapter.onImageViewClick = ::secilenImageViewToplanmaYeriOnClick

        binding.toplanmaYeriListeRecyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.toplanmaYeriListeRecyclerView.adapter = toplanmaYeriAdapter
        binding.toplanmaYeriListeRecyclerView.setHasFixedSize(true)



    }

    private fun spinner_yukle_ilce(){
        ilceler_listesi.clear()
        onemliYerList.forEach {
            if ( !ilceler_listesi.contains(it!!.ilce)){
                ilceler_listesi.add(it.ilce)
            }
        }

        val adapter: ArrayAdapter<*>

        adapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            ilceler_listesi
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.ilceSpinner.adapter = adapter

        adapter.notifyDataSetChanged()

    }

    private fun spinner_yukle_mahalle(ilceAdi:String){
        mahalleler_listesi.clear()
        mahalleler_listesi.add("BÜTÜN İLÇE")
        onemliYerList.forEach {
            if (it!!.ilce == ilceAdi){
                if (!mahalleler_listesi.contains(it.mahalle)){
                    mahalleler_listesi.add(it.mahalle)
                }
            }
        }
        val adapter : ArrayAdapter<*>
        adapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            mahalleler_listesi
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.mahalleSpinner.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun get_data_with_data_type(){
        when (data_type){
            0 -> acil_toplanma_yerleri_getir()
            1 -> muhtarliklar_getir()
        }
    }

    // datatype 0
    private fun acil_toplanma_yerleri_getir(){
        ApiUtils.ToplanmaYeriDAOInterfaceGetir().toplanma_yerleri_al().enqueue(
            object : Callback<OnemliYerResponse> {
                override fun onResponse(
                    call: Call<OnemliYerResponse>,
                    response: Response<OnemliYerResponse>
                ) {
                    val gelenKayitSayisi = response.body()?.kayit_sayisi
                    val tempList = response.body()?.onemliyerler
                    tempList?.let {
                        onemliYerList = it
                    }
                    onemliYerList.forEach { onemliYer ->
                        if (onemliYer != null) {
                            onemliYer.type = data_type
                        }
                    }
                    println("Liste boyutu : ${onemliYerList.size}")
                    if (gelenKayitSayisi != null && onemliYerList.size == gelenKayitSayisi){
                        println("Veri eşleşiyor.")
                    }

                    Toast.makeText(requireContext(),"Toplanma yerleri başarılı bir şekilde güncellendi! (${onemliYerList.size} kayıt)",Toast.LENGTH_LONG).show()
                    toplanmaYeriAdapter.updateList(onemliYerList)


                    onemli_yer_liste_kaydet(onemliYerList)

                }

                override fun onFailure(call: Call<OnemliYerResponse>, t: Throwable) {
                    println("ERROR ${t.localizedMessage}")
                    Toast.makeText(requireContext(),"API hatası, daha sonra tekrar deneyin!",Toast.LENGTH_LONG).show()

                }

            }
        )
    }

    //datatype 1
    private fun muhtarliklar_getir(){
        ApiUtils.ToplanmaYeriDAOInterfaceGetir().muhtarliklar_al().enqueue(object : Callback<OnemliYerResponse>{
            override fun onResponse(
                call: Call<OnemliYerResponse>,
                response: Response<OnemliYerResponse>
            ) {
                val gelenKayitSayisi = response.body()?.kayit_sayisi
                val tempList = response?.body()?.onemliyerler
                tempList?.let {
                    onemliYerList = it
                }

                onemliYerList.forEach { onemliYer ->
                    if (onemliYer != null) {
                        onemliYer.type = data_type
                    }
                }

                if (gelenKayitSayisi != null && onemliYerList.size == gelenKayitSayisi){
                    println("Veri eşleşiyor.")
                }

                println("Liste boyutu : ${onemliYerList.size}")

                Toast.makeText(requireContext(),"Toplanma yerleri başarılı bir şekilde güncellendi! (${onemliYerList.size} kayıt)", Toast.LENGTH_LONG).show()

                toplanmaYeriAdapter.updateList(onemliYerList)

                onemli_yer_liste_kaydet(onemliYerList)

            }

            override fun onFailure(call: Call<OnemliYerResponse>, t: Throwable) {
                println("ERROR ${t.localizedMessage}")
                Toast.makeText(requireContext(),"API hatası, daha sonra tekrar deneyin!",Toast.LENGTH_LONG).show()
            }

        })

    }

    private fun onemli_yer_liste_kaydet(onemliYerList : List<ToplanmaYeri?>){
        for (onemliYer in onemliYerList){
            if (onemliYer != null){
                val returnValueOfDB = toplanmaYeriDB.toplanmaYeriDAO().getOnemliYerWithLL(onemliYer.enlem,onemliYer.boylam)
                if (returnValueOfDB == null){
                    toplanmaYeriDB.toplanmaYeriDAO().onemliYerEkle(onemliYer)
                }
            }
        }
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.filtrele -> {
                if (isVisibleSearch){
                    binding.searchView.visibility = View.GONE
                    isVisibleSearch = !isVisibleSearch
                }
                if (isVisibleFilter){
                    //hide
                    toplanmaYeriAdapter.updateList(onemliYerList)
                    binding.ilceSpinner.visibility = View.GONE
                    binding.ilceText.visibility = View.GONE
                    binding.mahalleSpinner.visibility = View.GONE
                    binding.mahalleText.visibility = View.GONE
                }
                else{//show
                    spinner_yukle_ilce()
                    binding.ilceSpinner.visibility = View.VISIBLE
                    binding.ilceText.visibility = View.VISIBLE
                    binding.mahalleSpinner.visibility = View.VISIBLE
                    binding.mahalleText.visibility = View.VISIBLE
                }
                isVisibleFilter = !isVisibleFilter

            }
            R.id.ara ->{
                if (isVisibleFilter){
                    //hide filter
                    binding.ilceSpinner.visibility = View.GONE
                    binding.ilceText.visibility = View.GONE
                    binding.mahalleSpinner.visibility = View.GONE
                    binding.mahalleText.visibility = View.GONE
                    isVisibleFilter = !isVisibleFilter
                }

                if (isVisibleSearch){
                    //hide
                    toplanmaYeriAdapter.updateList(onemliYerList)
                    binding.searchView.visibility = View.GONE
                }
                else{
                    //show
                    binding.searchView.visibility = View.VISIBLE
                }
                isVisibleSearch = !isVisibleSearch
            }
            R.id.yenile->{
                get_data_with_data_type()
            }
        }
        return super.onOptionsItemSelected(item)
    }




}