package com.kush.notesmvvm.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.kush.notesmvvm.Models.Note
import com.kush.notesmvvm.R
import kotlin.random.Random

class NotesAdapter(private val context: Context,val listner: NotesitemClickListner):
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>()

{
    private val NotesList =ArrayList<Note>()
    private val fullList = ArrayList<Note>()




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item,parent,false)
        )
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentNote = NotesList[position]
        holder.title.text = currentNote.Title
        holder.note.text = currentNote.Note
        holder.date.text = currentNote.Date
        holder.title.isSelected = true
        holder.date.isSelected = true
        holder.notes_layout.setCardBackgroundColor(holder.itemView.resources.getColor(RandomColor(),null))
        holder.notes_layout.setOnClickListener(){
            listner.onItemClicked(NotesList[holder.adapterPosition])
        }
        holder.notes_layout.setOnLongClickListener(){
            listner.onLongItemClicked(NotesList[holder.adapterPosition],holder.notes_layout)
            true
        }
    }
    fun filterList(search :String){
        NotesList.clear()
        for (item in fullList){
            if(item.Title?.lowercase()?.contains(search.lowercase())==true||
                        item.Note?.lowercase()?.contains(search.lowercase()) == true){
                            NotesList.add(item)
                        }
        }
        notifyDataSetChanged()
    }
    fun UpdateList(newList: List<Note>){
        fullList.clear()
        fullList.addAll(newList)
        notifyDataSetChanged()
    }
    fun RandomColor(): Int{
        val list = ArrayList<Int>()
        list.add(R.color.NoteColor1)
        list.add(R.color.NoteColor2)
        list.add(R.color.NoteColor3)
        list.add(R.color.NoteColor4)
        list.add(R.color.NoteColor5)
        list.add(R.color.NoteColor6)
        val seed = System.currentTimeMillis().toInt()
        val randomINdex = Random(seed).nextInt(list.size)
        return  list [randomINdex]

    }


    override fun getItemCount(): Int {
        return NotesList.size
        TODO("Not yet implemented")
    }
    inner class NotesViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val notes_layout = itemView.findViewById<CardView>(R.id.card_layout)
        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val note = itemView.findViewById<TextView>(R.id.tv_note)
        val date = itemView.findViewById<TextView>(R.id.tv_date)

    }
    interface  NotesitemClickListner {
        fun onItemClicked(note: Note)
        fun onLongItemClicked(note:Note,cardView: CardView )
    }

}