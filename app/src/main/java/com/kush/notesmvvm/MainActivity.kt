package com.kush.notesmvvm

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Query
import androidx.room.RoomDatabase
import com.kush.notesmvvm.Adapter.NotesAdapter
import com.kush.notesmvvm.Database.NoteDatabase
import com.kush.notesmvvm.Models.Note
import com.kush.notesmvvm.Models.NoteViewModel
import com.kush.notesmvvm.databinding.ActivityAddNoteBinding
import com.kush.notesmvvm.databinding.ActivityMainBinding

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), NotesAdapter.NotesitemClickListner ,PopupMenu.OnMenuItemClickListener{
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: NoteDatabase
    lateinit var viewModel: NoteViewModel
    lateinit var  adapter : NotesAdapter
    lateinit var selectedNote :Note
    private  val updateNote = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
        if (result.resultCode==Activity.RESULT_OK){
            val note = result.data?.getSerializableExtra("note")as? Note
            if (note != null){
                viewModel.UpdateNote(note)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        viewModel = ViewModelProvider(this,
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)


        viewModel.allnotes.observe(this) { list ->
            list?.let {
                adapter.UpdateList(list)
            }
        }
        database = NoteDatabase.getDatabase(this)

    }

    @SuppressLint("SuspiciousIndentation")
    private fun initUI() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager= StaggeredGridLayoutManager(2,LinearLayout.VERTICAL)
        adapter = NotesAdapter(this,this)
        binding.recyclerView.adapter =adapter
        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
            if(result.resultCode == Activity.RESULT_OK){
                val note = result.data?.getSerializableExtra("note") as?Note
                if (note != null){
                    viewModel.insertNote(note )
                }
            }
        }
        binding.floatingActionButton.setOnClickListener{
          val intent = Intent(this, Add_Note::class.java)
            getContent.launch(intent)
        }
        binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null){
                    adapter.filterList(newText)

                }

                TODO("Not yet implemented")
            }

        })



    }

    override fun onItemClicked(note: Note) {
        val intent = Intent(this@MainActivity, Add_Note::class.java)
        intent.putExtra("currentNote",note)
        updateNote.launch(intent)
        TODO("Not yet implemented")
    }

    override fun onLongItemClicked(note: Note, cardView: CardView) {
        selectedNote = note
        popUpDisplay(cardView)
        TODO("Not yet implemented")
    }

    private fun popUpDisplay(cardView: CardView) {
        val popup = PopupMenu(this, cardView)
        popup.setOnMenuItemClickListener (this@MainActivity)
        popup.inflate(R.menu.pop_up_menu)
        popup.show()

    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item?.itemId== R.id.delete_note){
            viewModel.deleteNote(selectedNote)
            return true
        }
        return false
        TODO("Not yet implemented")
    }
}