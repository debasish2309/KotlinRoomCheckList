package com.debasish.kotlinroomchecklist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.debasish.kotlinroomchecklist.databinding.SingleItemLayoutBinding

class ChecklistAdapter(
    private val checklistList  : ArrayList<Checklist>,
    private val onItemClicked: OnItemClicked,
    private val onItemChecked: OnItemChecked,
    private val onCheckedChange: OnCheckedChange)
    :RecyclerView.Adapter<ChecklistAdapter.ChecklistViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChecklistViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : SingleItemLayoutBinding = DataBindingUtil.inflate(layoutInflater,R.layout.single_item_layout,parent,false)
        return ChecklistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChecklistViewHolder, position: Int) {
        holder.bind(checklistList[position])

    }

    override fun getItemCount(): Int {
        return checklistList.size
    }

    interface OnItemClicked {
        fun itemClicked(position : Int)
    }

    interface OnItemChecked {
        fun itemChecked(position: Int, completed : Boolean)
    }

    interface OnCheckedChange {
        fun checkedChange(position: Int)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }



    inner class ChecklistViewHolder(val binding: SingleItemLayoutBinding) :RecyclerView.ViewHolder(binding.root) {

        fun bind(checklist: Checklist){

            binding.apply {
                noteTextView.text = checklist.note
                dateTextView.text = checklist.noteCreated
                checkBox.isChecked = checklist.completed
//                itemView.setOnClickListener {
//                    onItemClicked.itemClicked(adapterPosition)
//                }
                checkBox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener{
                    compoundButton, b ->
                    if(b) {
                        onItemChecked.itemChecked(adapterPosition,b)
                        onCheckedChange.checkedChange(adapterPosition)
                    } else {
                        onItemChecked.itemChecked(adapterPosition,b)
                        onCheckedChange.checkedChange(adapterPosition)

                    }
                } )
            }
        }

    }



}