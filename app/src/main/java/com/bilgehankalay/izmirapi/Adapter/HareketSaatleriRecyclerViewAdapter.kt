package com.bilgehankalay.izmirapi.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bilgehankalay.izmirapi.Model.HareketSaati
import com.bilgehankalay.izmirapi.databinding.HareketSaatleriCardTasarimBinding

class HareketSaatleriRecyclerViewAdapter(private var hareketSaatleriList : List<HareketSaati?>) : RecyclerView.Adapter<HareketSaatleriRecyclerViewAdapter.HareketSaatleriCardTasarim>() {
    class HareketSaatleriCardTasarim(val hareketSaatleriCardBinding : HareketSaatleriCardTasarimBinding) : RecyclerView.ViewHolder(hareketSaatleriCardBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HareketSaatleriCardTasarim {
        val layoutInflater = LayoutInflater.from(parent.context)
        val hareketSaatleriCardBinding = HareketSaatleriCardTasarimBinding.inflate(layoutInflater,parent,false)
        return HareketSaatleriCardTasarim(hareketSaatleriCardBinding)
    }

    override fun onBindViewHolder(holder: HareketSaatleriCardTasarim, position: Int) {
        val hareketSaati = hareketSaatleriList[position]
        if (hareketSaati != null){
            holder.hareketSaatleriCardBinding.let {
                //06:15         06:35

                it.textViewGidisDonus.text = "${hareketSaati.GidisSaat}         ${hareketSaati.DonusSaat}"

                if(hareketSaati.BisikletliMiGidis)
                    it.ivBisikletGidis.visibility = View.VISIBLE
                else
                    it.ivBisikletGidis.visibility = View.INVISIBLE

                if(hareketSaati.EngelliMiGidis)
                    it.ivEngelliGidis.visibility = View.VISIBLE
                else
                    it.ivEngelliGidis.visibility = View.INVISIBLE

                if(hareketSaati.ElektrikliMiGidis)
                    it.ivElektrikGidis.visibility = View.VISIBLE
                else
                    it.ivElektrikGidis.visibility = View.INVISIBLE



                if(hareketSaati.BisikletliMiDonus)
                    it.ivBisikletDonus.visibility = View.VISIBLE
                else
                    it.ivBisikletDonus.visibility = View.INVISIBLE

                if(hareketSaati.EngelliMiDonus)
                    it.ivEngelliDonus.visibility = View.VISIBLE
                else
                    it.ivEngelliDonus.visibility = View.INVISIBLE

                if(hareketSaati.ElektrikliMiDonus)
                    it.ivElektrikDonus.visibility = View.VISIBLE
                else
                    it.ivElektrikDonus.visibility = View.INVISIBLE


                when(hareketSaati.TarifeId){
                    1->{
                        //haftaiçi
                        it.textViewNeZaman.text = "Hafta İçi"
                    }
                    2->{
                        //cumartesi
                        it.textViewNeZaman.text = "Cumartesi"
                    }
                    3->{
                        //pazar
                        it.textViewNeZaman.text = "Pazar"
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return hareketSaatleriList.size
    }

    fun updateList(yeniHareketSaatleriList : List<HareketSaati?>){
        hareketSaatleriList = yeniHareketSaatleriList
        this.notifyDataSetChanged()
    }
}