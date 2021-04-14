package com.example.kotlinroom

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinroom.db.UserModel
import com.example.kotlinroom.viewModels.UserViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyAdapter(private val viewModel: UserViewModel, private val context: Context) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private var listData: List<UserModel> = emptyList()

    // 入口
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // 指定了 layout
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item, parent, false)
            .run(::ViewHolder)
    }

    // 綁定資料
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(listData[position])
    }

    // 返回數目
    override fun getItemCount() = listData.size

    // view
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val photo = itemView.findViewById<TextView>(R.id.photo)
        private val name = itemView.findViewById<TextView>(R.id.Name)
        private val time = itemView.findViewById<TextView>(R.id.Time)
        private val edit = itemView.findViewById<ImageButton>(R.id.edit)
        private val delete = itemView.findViewById<ImageButton>(R.id.delete)

        fun bindView(item: UserModel) {
            photo.text = makeAbbreviation(item.firstName, item.lastName)
            name.text = item.firstName + item.lastName
            time.text = item.createdAt
            delete.setOnClickListener { viewModel.delete(item) }
            edit.setOnClickListener { showEditDialog(item) }
//            time.text = item.time
        }
    }

    private fun makeAbbreviation(firstName: String, lastName: String): String? {
        var photoText = ""
        photoText += firstName.substring(0, 1)
        photoText += "."
        photoText += lastName.substring(0, 1)
        return photoText
    }

    fun updateScreen(list: List<UserModel>) {
        listData = list
        notifyDataSetChanged()
    }

    private fun showEditDialog(userModel: UserModel) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog)
        val dialogTitle = dialog.findViewById<TextView>(R.id.dialogTitle)
        val firstName = dialog.findViewById<EditText>(R.id.firstName)
        val lastName = dialog.findViewById<EditText>(R.id.lastName)

        dialogTitle.setText("Edit")
        firstName.setText(userModel.firstName)
        lastName.setText(userModel.lastName)
        val add = dialog.findViewById<Button>(R.id.add)
        add.setText("Edit")
        add.setOnClickListener {
            if(firstName.text.isNotEmpty()&& lastName.text.isNotEmpty()){
                GlobalScope.launch {
                    userModel.id?.let { it1 ->
                        viewModel.update(
                            it1,
                            firstName.text.toString(),
                            lastName.text.toString()
                        )
                    }
                }
                dialog.dismiss()
            }else{
                Toast.makeText(context,"Can Not Be Null", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
    }
}
