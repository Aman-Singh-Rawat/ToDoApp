package com.example.todoappdemotwo

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CustomRecycler(private val context: Context, private val arrayList: ArrayList<DataContainer>):
    RecyclerView.Adapter<CustomRecycler.CustomHolder>() {
        private var lastPosition = -1
    class CustomHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val titleText: TextView = itemView.findViewById(R.id.titleText)
        val titleDescription: TextView = itemView.findViewById(R.id.titleDescription)
        val linearLayout: LinearLayout = itemView.findViewById(R.id.linearLayout)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.adapter_layout, parent, false)
        return CustomHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: CustomHolder, position: Int) {
        holder.titleText.text = arrayList[position].task
        holder.titleDescription.text = arrayList[position].description

        setAnimation(holder.itemView, position)
        /* ======================================== Editing Task ========================== */

        holder.linearLayout.setOnClickListener {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.custom_dialog)

            /* Increasing width of custom_dialog.xml */

            val window: Window? = dialog.window
            val layoutParams: WindowManager.LayoutParams? = window?.attributes
            layoutParams?.width = WindowManager.LayoutParams.MATCH_PARENT
            layoutParams?.height = WindowManager.LayoutParams.WRAP_CONTENT
            window?.attributes = layoutParams

            /* ================================================================== */

            /* =============== finding the id of custom_dialog.xml =============== */

            val dataAddingTask: EditText = dialog.findViewById(R.id.dataAddingTask)
            val dataAddingDescription: EditText = dialog.findViewById(R.id.dataAddingDescription)
            val dataAddingButton: FloatingActionButton = dialog.findViewById(R.id.dataAddingButton)

            /* ==================================================================== */
            dataAddingTask.setText(arrayList[position].task)
            dataAddingDescription.setText(arrayList[position].description)

            dataAddingButton.setOnClickListener {
                if (dataAddingTask.text.isNotEmpty() && dataAddingDescription.text.isNotEmpty()){
                    arrayList[position] = DataContainer(
                        dataAddingTask.text.toString(),
                        dataAddingDescription.text.toString()
                    )

                    notifyItemChanged(position)
                    dialog.dismiss()

                }else{
                    Toast.makeText(context,"NaNa", Toast.LENGTH_LONG).show()
                }
            }
            dialog.show()
        }
        /* ================================================================================= */

        /* ============================= Deleting Task ================================== */

        if (position < arrayList.size) {
            holder.linearLayout.setOnLongClickListener {
                val alertDialog = AlertDialog.Builder(context)
                    .setTitle("Deleting Task")
                    .setIcon(R.drawable.delete)
                    .setMessage("Are your sure you want to do delete the task")
                    .setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->
                        if (position < arrayList.size) {
                            arrayList.removeAt(position)
                            notifyItemRemoved(position)

                            notifyItemRangeChanged(position, arrayList.size - position)
                        }
                    })
                alertDialog.show()
                true
            }
        }
        /* ============================================================================ */

        /* ============================ CheckBox ======================== */
        holder.checkBox.setOnClickListener {
            if (holder.checkBox.isChecked){

            }
        }
        /* ============================================================================ */
    }
    private fun setAnimation(view: View, position: Int){
        if (lastPosition < position){
            val slideIn = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)

            view.startAnimation(slideIn)
            lastPosition = position
        }
    }
}