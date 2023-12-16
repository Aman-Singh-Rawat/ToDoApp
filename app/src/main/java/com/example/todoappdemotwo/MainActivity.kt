package com.example.todoappdemotwo

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val arrayList = ArrayList<DataContainer>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val mainFloatButton: FloatingActionButton = findViewById(R.id.mainFloatButton)

        val customRecycler = CustomRecycler(this,arrayList)
        recyclerView.adapter = customRecycler

        mainFloatButton.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.custom_dialog)

            val window: Window? = dialog.window
            val layoutParams: WindowManager.LayoutParams? = window?.attributes
            layoutParams?.width = WindowManager.LayoutParams.MATCH_PARENT
            layoutParams?.height = WindowManager.LayoutParams.WRAP_CONTENT
            window?.attributes = layoutParams

            /* X====================X Finding the ids of custom_dialog.xml X====================X */
            val dataAddingTask: EditText = dialog.findViewById(R.id.dataAddingTask)
            val dataAddingDescription: EditText = dialog.findViewById(R.id.dataAddingDescription)
            val dataAddingButton: FloatingActionButton = dialog.findViewById(R.id.dataAddingButton)

            /* X====================X Finding the ids of custom_dialog.xml X====================X */

            /* X====================X ==================================== X====================X */
            dataAddingButton.setOnClickListener {
                if (dataAddingTask.text.isNotEmpty() && dataAddingDescription.text.isNotEmpty()){
                    arrayList.add(DataContainer(
                        dataAddingTask.text.toString(),
                        dataAddingDescription.text.toString()))

                    customRecycler.notifyItemInserted(arrayList.size-1)
                    recyclerView.scrollToPosition(arrayList.size-1)
                    dialog.dismiss()

                }else{
                    Toast.makeText(this,"NaNa",Toast.LENGTH_LONG).show()
                }
            }
            dialog.show()
        }
    }
}