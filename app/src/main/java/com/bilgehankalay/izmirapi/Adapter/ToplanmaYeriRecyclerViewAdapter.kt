package com.bilgehankalay.izmirapi.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bilgehankalay.izmirapi.Model.ToplanmaYeri
import com.bilgehankalay.izmirapi.databinding.ToplanmaYerCardTasarimBinding

class ToplanmaYeriRecyclerViewAdapter(private var toplanmaYeriListe : ArrayList<ToplanmaYeri>) : RecyclerView.Adapter<ToplanmaYeriRecyclerViewAdapter.ToplanmaYeriCardTasarim>(){

    var onImageViewClick : (ToplanmaYeri) -> Unit = {}
    var onRootClick : (ToplanmaYeri) ->  Unit = {}

    class ToplanmaYeriCardTasarim(val toplanmaYeriCardTasarim : ToplanmaYerCardTasarimBinding) : RecyclerView.ViewHolder(toplanmaYeriCardTasarim.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToplanmaYeriCardTasarim {
        val layoutInflater = LayoutInflater.from(parent.context)
        val toplanmaYeriCardTasarimBinding = ToplanmaYerCardTasarimBinding.inflate(layoutInflater,parent,false)
        return ToplanmaYeriCardTasarim(toplanmaYeriCardTasarimBinding)
    }

    override fun onBindViewHolder(holder: ToplanmaYeriCardTasarim, position: Int) {
        val toplanmaYeri = toplanmaYeriListe[position]
        holder.toplanmaYeriCardTasarim.toplanmaYeriAdi.text = toplanmaYeri.adi
        holder.toplanmaYeriCardTasarim.ilceAdi.text = "İlçe: ${toplanmaYeri.ilce}"
        holder.toplanmaYeriCardTasarim.mahalleAdi.text= "Mahalle: ${toplanmaYeri.mahalle}"

        val yuvarlananEnlem = String.format("%.4f", toplanmaYeri.enlem)
        val yuvarlananBoylam = String.format("%.4f",toplanmaYeri.boylam)
        holder.toplanmaYeriCardTasarim.enlemBoylamAdi.text = "Enlem ${yuvarlananEnlem} Boylam ${yuvarlananBoylam}"

        holder.toplanmaYeriCardTasarim.toplanmaYeriImageView.setOnClickListener {
            onImageViewClick(toplanmaYeri)
        }
        holder.toplanmaYeriCardTasarim.root.setOnClickListener {
            onRootClick(toplanmaYeri)
        }

    }

    override fun getItemCount(): Int {
        return toplanmaYeriListe.size

    }

}