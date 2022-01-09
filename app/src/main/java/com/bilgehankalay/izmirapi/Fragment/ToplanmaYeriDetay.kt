package com.bilgehankalay.izmirapi.Fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bilgehankalay.izmirapi.R
import com.bilgehankalay.izmirapi.databinding.FragmentToplanmaYeriDetayBinding


class ToplanmaYeriDetay : Fragment() {
    private val args : ToplanmaYeriDetayArgs by navArgs()
    private lateinit var binding : FragmentToplanmaYeriDetayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentToplanmaYeriDetayBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gelenToplanmaYeri = args.toplanmaYeri

        binding.let {
            it.detayAciklama.text = gelenToplanmaYeri.aciklama
            it.detayAdi.text = gelenToplanmaYeri.adi
            it.detayBoylam.text = gelenToplanmaYeri.boylam.toString()
            it.detayEnlem.text = gelenToplanmaYeri.enlem.toString()
            it.detayIlceAdi.text = gelenToplanmaYeri.ilce
            it.detayMahalleAdi.text = gelenToplanmaYeri.mahalle


            it.detayGeri.setOnClickListener {
                val gecisAction = ToplanmaYeriDetayDirections.detayToListe()
                findNavController().navigate(gecisAction)

            }

            it.detayShowMap.setOnClickListener {
                val gmmIntentUri = Uri.parse("http://maps.google.com/maps?daddr=" + gelenToplanmaYeri.enlem + "," + gelenToplanmaYeri.boylam)
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }
        }
    }
}