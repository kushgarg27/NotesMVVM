package com.kush.notesmvvm

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Note
import android.widget.ImageView
import android.widget.Toast
import com.kush.notesmvvm.databinding.ActivityAddNoteBinding
import com.kush.notesmvvm.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter


@Suppress("CAST_NEVER_SUCCEEDS", "DEPRECATION")
class Add_Note : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var note: com.kush.notesmvvm.Models.Note
    private lateinit var old_note:com.kush.notesmvvm.Models.Note
    var isUpdate= false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        try {
            old_note = intent.getSerializableExtra("currentNote")as com.kush.notesmvvm.Models.Note
            binding.etTitle.setText(old_note.title)
            binding.etNote.setText(old_note.note)
            isUpdate = true
        }
        catch (e : java.lang.Exception){
            e.printStackTrace()
        }
        binding.imgCheck.setOnClickListener{
            val title = binding.etTitle.text.toString()
            val note_desc = binding.etNote.text.toString()
            if(title.isNotEmpty()||note_desc.isNotEmpty()){
                val formatter = SimpleDateFormat("EEE, d MMM yyy HH:mm a")
                if(isUpdate){
                    note = com.kush.notesmvvm.Models.Note(
                        old_note.id, title, note_desc, formatter.format(Date())
                    )

                }
                else{
                    note = com.kush.notesmvvm.Models.Note(
                        null,title,note_desc,formatter.format(Date())
                    )
                }
                val intent = Intent()
                intent.putExtra("note",note)
                setResult(Activity.RESULT_OK,intent)
                finish()
            }
            else{
                Toast.makeText(this@Add_Note,"Please enter some data",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

        }
        binding.imgBack.setOnClickListener{
            onBackPressed()

        }


    }
}


