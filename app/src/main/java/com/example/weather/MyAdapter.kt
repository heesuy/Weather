package com.example.weather

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R

class MyAdapter(val viewModel: MyViewModel) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    inner class MyViewHolder(val view : View) : RecyclerView.ViewHolder(view){
            fun setContents(position: Int){
              // val data = viewModel.getData()
               // val data1 = viewModel.weatherList
                //val data2 = viewModel.weatherData
                val data1 = viewModel.hourlyData.value

                if( data1 != null){
            with(data1!!.get(position)){
                val str = StringBuilder().also{

                        //it.append("현재시각 ")
                        it.append(this.time)


                    }
                .toString()

                val str1 = StringBuilder().also {
                    //it.append(" 서울의 온도는 : ")
                    it.append(this.temperature)
                }.toString()
                Log.d("getWeather", "getWeather  MyAdaptor ${str}")

                view.findViewById<TextView>(R.id.textView).text = str
                view.findViewById<TextView>(R.id.textView2).text = str1

            }

            }

    }}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_layout,parent,false)
        val viewHolder = MyViewHolder(view)

        view.setOnClickListener {
            viewModel._itemClickEvent.value = viewHolder.adapterPosition
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {
        holder.setContents(position)

    }

    override fun getItemCount(): Int {

        //Log.d("getWeather", "getWeather  item count ${viewModel.weatherList.value?.size}")
        Log.d("getWeather", "getWeather  Adapter item count ${viewModel.hourlyData.value?.size}")

        //println("getItemCOunt ${viewModel..value?.size?:0}")

       return viewModel.hourlyData.value?.size?:0
    }
}