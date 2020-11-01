package com.ar.cartonclouddemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.ar.cartonclouddemo.R
import com.ar.cartonclouddemo.model.WeatherData
import com.ar.cartonclouddemo.utils.DateUtil
import com.ar.cartonclouddemo.view.WeatherDialogFragment
import kotlinx.android.synthetic.main.rv_weather_item.view.*
import kotlin.math.roundToInt

class WeatherAdaptor (var context: Context): RecyclerView.Adapter<WeatherAdaptor.WeatherAdapterViewHolder>(){
    private var list:List<WeatherData> = mutableListOf()
    fun setData(list:MutableList<WeatherData>){
        this.list=list
        notifyDataSetChanged()
    }

    inner class WeatherAdapterViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherAdaptor.WeatherAdapterViewHolder {
        val  view=
            LayoutInflater.from(parent.context).inflate(R.layout.rv_weather_item,parent,false)
        return WeatherAdapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: WeatherAdaptor.WeatherAdapterViewHolder, position: Int) {
        val item=list[position]
        holder.itemView.tv_clock_time.setText(DateUtil.formatDateToTime(item.created))
        holder.itemView.tv_date.setText(DateUtil.formatDateToDay(item.created))
        holder.itemView.tv_temp.setText(item.the_temp.roundToInt().toString()+" \u2103")
        holder.itemView.root.setOnClickListener {
            WeatherDialogFragment.newInstance(item.id).show((context as FragmentActivity).supportFragmentManager,WeatherDialogFragment.TAG)
        }
    }
}