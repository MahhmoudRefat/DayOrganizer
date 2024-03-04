package com.example.dayorganizer.Ui.home.tasksLis

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dayorganizer.database.model.Task
import com.example.dayorganizer.databinding.TaskitemBinding

class TaskAdapter(var tasks: MutableList<Task>? = null) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {


    class ViewHolder(val binding: TaskitemBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(task: Task) {
            binding.tvTaskName.text = task.title
            binding.tvDescription.text = task.content

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itembinding: TaskitemBinding =
            TaskitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itembinding)

    }

    override fun getItemCount(): Int = tasks?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var task = tasks!![position]
        holder.bind(task)

        if (onDeleteClickListener != null) {
            holder.binding.btnDeleteTask.setOnClickListener {
                onDeleteClickListener?.onItemClick(task, position)
            }

        }
        if(onItemClickListener != null ){
            holder.itemView.setOnClickListener {
                onItemClickListener?.onItemClick(task,position)
            }
        }
    }

    fun changeData(alltasks: List<Task>) {
        if (tasks == null) {
            tasks = mutableListOf()
        }
        tasks?.clear()
        tasks?.addAll(alltasks)
        notifyDataSetChanged()

    }

    var onDeleteClickListener: OnItemClickListener? = null
    var onItemClickListener: OnItemClickListener? = null


    fun interface OnItemClickListener {
        fun onItemClick(item: Task, id: Int)
    }

}