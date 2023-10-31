package com.example.firebase_login_signup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase_login_signup.databinding.NotesItemBinding

class noteAdapter(private val notes: List<NoteItem>) : RecyclerView.Adapter<noteAdapter.NoteViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): noteAdapter.NoteViewHolder {
        var binding = NotesItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: noteAdapter.NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
    }
    class NoteViewHolder(private var binding : NotesItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(note: NoteItem) {
            binding.titleID.text = note.title
            binding.DescriptionID.text = note.description
        }

    }

    override fun getItemCount(): Int {
        return notes.size
    }

}


