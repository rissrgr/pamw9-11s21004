package com.ifs21004.whatsapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ifs21004.whatsapp.databinding.ItemRowEmailBinding

class ListEmailAdapter(private val listEmail: ArrayList<Email>) :
    RecyclerView.Adapter<ListEmailAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowEmailBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val email = listEmail[position]
        holder.binding.ivItemEmail.setImageResource(email.icon)
        holder.binding.tvItemEmail.text = email.name
        holder.binding.tvItemSubject.text = email.subject
        holder.binding.tvItemtext.text = email.text
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listEmail[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listEmail.size

    class ListViewHolder(var binding: ItemRowEmailBinding) : RecyclerView.ViewHolder(binding.root)
    interface OnItemClickCallback {
        fun onItemClicked(data: Email)
    }

}