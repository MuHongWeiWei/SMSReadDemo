package com.fly.smsreaddemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fly.smsreaddemo.databinding.SmsItemBinding

class SMSAdapter(private var smsList: List<SMS>) : RecyclerView.Adapter<SMSAdapter.SMSHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SMSHolder {
        return SMSHolder(SmsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SMSHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount() = smsList.size

    inner class SMSHolder(private val smsItemBinding: SmsItemBinding) : RecyclerView.ViewHolder(smsItemBinding.root) {
        fun onBind(position: Int) {
            smsItemBinding.sender.text = smsList[position].sender
            smsItemBinding.body.text = smsList[position].body
            smsItemBinding.date.text = smsList[position].dateTime
        }
    }

}