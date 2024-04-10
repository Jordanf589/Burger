package com.example.firebaserestaurantapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.Executors

class CustomerAdapter :ListAdapter<Customer,CustomerAdapter.CustomerAdapter>(UserViewHolder())
{
    class CustomerAdapter(view : View): RecyclerView.ViewHolder(view)
    {
    }
    override fun onCreateViewHolder(parent : ViewGroup, viewType:Int):CustomerAdapter
    {
        val inflater = LayoutInflater.from(parent.context)
        return CustomerAdapter(inflater.inflate(
            R.layout.users,parent,false
        ))
    }

    override fun onBindViewHolder(holder: CustomerAdapter,position: Int)
    {
        val user = getItem(position)
        holder.itemView.findViewById<TextView>(R.id.lblCustName).text = user.Name
        holder.itemView.findViewById<TextView>(R.id.lblItem).text = user.Item
    }
}
class UserViewHolder :DiffUtil.ItemCallback<Customer>()
{
    override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean {
        return oldItem.Name == newItem.Name
    }

    override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean {
        return areItemsTheSame(oldItem,newItem)
    }
}