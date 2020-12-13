package com.debasish.kotlinroomchecklist

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.debasish.kotlinroomchecklist.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), ChecklistAdapter.OnItemClicked,ChecklistAdapter.OnItemChecked,ChecklistAdapter.OnCheckedChange {
    private lateinit var binding: ActivityMainBinding
    private lateinit var checklistViewModel: ChecklistViewModel
    private lateinit var checklistAdapter: ChecklistAdapter
    private val checklist = ArrayList<Checklist>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = CheckListDatabase.getInstance(application).checklistDao
        val repository = ChecklistRepository(dao)
        val factory = ChecklistViewModelFactory(repository)
        checklistViewModel = ViewModelProvider(this, factory).get(ChecklistViewModel::class.java)

        binding.lifecycleOwner = this
        initRecyclerView()

        val timeStamp: String = SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Date())

        binding.floatingActionButton.setOnClickListener {
            val noteEdittext: EditText = EditText(this);

            val alertDialog: AlertDialog = this.let {
                val builder = AlertDialog.Builder(it)
                builder.apply {
                    setView(noteEdittext)
                    setPositiveButton("Add", DialogInterface.OnClickListener { dialogInterface, i ->
                        CoroutineScope(IO).launch {
                            dao.insertNote(
                                Checklist(
                                    0,
                                    noteEdittext.text.toString(),
                                    "enjoy",
                                    timeStamp,
                                    false
                                )
                            )
                        }
                    })
                    setNegativeButton(
                        "cancel",
                        DialogInterface.OnClickListener { dialogInterface, i ->
                            dialogInterface.cancel()
                        })

                }
                builder.create()
            }
            alertDialog.show()

        }

        binding.floatingActionButton2.setOnClickListener {

            CoroutineScope(IO).launch {
                val someString = dao.getOnlyNotes().joinToString(separator = ",")

                Log.d("!!!String", someString)

                val secondString = someString.split(",").toTypedArray()

                for(str in secondString){
                    Log.d("!!!str",str)
                }
            }


        }
    }


    override fun itemClicked(position: Int) {
        val dao = CheckListDatabase.getInstance(application).checklistDao
        val updatetext: EditText = EditText(this)

        val alertDialog: AlertDialog = this.let {
            val builder = AlertDialog.Builder(this)
            builder.apply {
                setView(updatetext)
                setPositiveButton("Update", DialogInterface.OnClickListener { dialogInterface, i ->

                //    updatetext?.setText(checklist[position].note)
                    CoroutineScope(IO).launch {
                        dao.updateNewNote(updatetext.text.toString(),
                        checklist[position].id)

                        Log.d("!!!test", updatetext.text.toString() + checklist[position].id)

                    }
                })
                setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.cancel()
                })
            }
            builder.create()
        }
        alertDialog.show()

    }

    override fun itemChecked(position: Int, completed: Boolean) {
        val dao = CheckListDatabase.getInstance(application).checklistDao
        CoroutineScope(IO).launch {
            dao.updateStatus(completed,checklist[position].id)
        }


    }

    private fun initRecyclerView() {
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        checklistViewModel.checklist.observe(this, Observer {
            checklist.clear()
            checklist.addAll(it)
            checklistAdapter = ChecklistAdapter(checklist,this,this,this)
            binding.recyclerview.adapter = checklistAdapter
            checklistAdapter.notifyDataSetChanged()

            val itemTouchHelper :ItemTouchHelper = ItemTouchHelper(simpleCallback)
            itemTouchHelper.attachToRecyclerView(binding.recyclerview)
        })

    }

    val simpleCallback =
        object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteSingleItem(viewHolder.adapterPosition)

            }

        }

    private fun deleteSingleItem(position: Int){
        val dao = CheckListDatabase.getInstance(application).checklistDao
        CoroutineScope(IO).launch {
            dao.deleteSingleNote(checklist[position].id)
        }
    }

    override fun checkedChange(position: Int) {
        val dao = CheckListDatabase.getInstance(application).checklistDao
        dao.getAllNotes()

    }

}