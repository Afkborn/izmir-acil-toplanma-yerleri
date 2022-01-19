package com.bilgehankalay.izmirapi.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bilgehankalay.izmirapi.Model.ToplanmaYeri
import com.bilgehankalay.izmirapi.R
import com.bilgehankalay.izmirapi.databinding.ToplanmaYerCardTasarimBinding

class ToplanmaYeriRecyclerViewAdapter(private var toplanmaYeriListe : List<ToplanmaYeri?>) : RecyclerView.Adapter<ToplanmaYeriRecyclerViewAdapter.ToplanmaYeriCardTasarim>(){

    var onImageViewClick : (ToplanmaYeri) -> Unit = {}
    var onRootClick : (ToplanmaYeri) ->  Unit = {}

    class ToplanmaYeriCardTasarim(val toplanmaYeriCardTasarim : ToplanmaYerCardTasarimBinding) : RecyclerView.ViewHolder(toplanmaYeriCardTasarim.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToplanmaYeriCardTasarim {
        val layoutInflater = LayoutInflater.from(parent.context)
        val toplanmaYeriCardTasarimBinding = ToplanmaYerCardTasarimBinding.inflate(layoutInflater,parent,false)
        return ToplanmaYeriCardTasarim(toplanmaYeriCardTasarimBinding)
    }

    override fun onBindViewHolder(holder: ToplanmaYeriCardTasarim, position: Int) {
        val onemliYer = toplanmaYeriListe[position]
        if (onemliYer != null){
            holder.toplanmaYeriCardTasarim.toplanmaYeriAdi.text = onemliYer.adi
            holder.toplanmaYeriCardTasarim.ilceAdi.text = onemliYer.ilce
            holder.toplanmaYeriCardTasarim.mahalleAdi.text= onemliYer.mahalle

            //val yuvarlananEnlem = String.format("%.4f", onemliYer.enlem)
            //val yuvarlananBoylam = String.format("%.4f",onemliYer.boylam)
            //holder.toplanmaYeriCardTasarim.enlemBoylamAdi.text = "E${yuvarlananEnlem} Boylam ${yuvarlananBoylam}"

            when(onemliYer.type){
                0 ->  holder.toplanmaYeriCardTasarim.toplanmaYeriImageView.setImageResource(R.drawable.toplanma_noktasi)
                1 ->  holder.toplanmaYeriCardTasarim.toplanmaYeriImageView.setImageResource(R.drawable.ic_baseline_home_24)
            }

            holder.toplanmaYeriCardTasarim.toplanmaYeriImageView.setOnClickListener {
                onImageViewClick(onemliYer)
            }
            holder.toplanmaYeriCardTasarim.root.setOnClickListener {
                onRootClick(onemliYer)
            }
        }

    }

    override fun getItemCount(): Int {
        return toplanmaYeriListe.size

    }



    fun updateList(yeniToplanmaYeriListe: List<ToplanmaYeri?>){
        toplanmaYeriListe = yeniToplanmaYeriListe
        this.notifyDataSetChanged()
    }


}