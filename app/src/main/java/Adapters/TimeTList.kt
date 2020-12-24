package Adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import Model.UploadPdf
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.bunk_o_meter.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlin.math.acos

class TimeTList (val context: Context,var list: MutableList<UploadPdf>,var Name:String):RecyclerView.Adapter<TimeTList.DashBoardViewHolder>() {

    class DashBoardViewHolder(view:View):RecyclerView.ViewHolder(view) {
        var name=view.findViewById<TextView>(R.id.rowName)
        var delete=view.findViewById<ImageView>(R.id.rowDelete)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashBoardViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)

        return DashBoardViewHolder(view)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: DashBoardViewHolder, position: Int) {
        val item=list[position]
        holder.name.text=Name
        holder.delete.setOnClickListener {
            val storageReference=FirebaseStorage.getInstance()
            val databaseReference=FirebaseDatabase.getInstance()
            if(storageReference.getReferenceFromUrl(item.GetUrl()!!).delete().isSuccessful &&
                databaseReference.getReferenceFromUrl(item.GetUrl()!!).removeValue().isSuccessful){
                Toast.makeText(context,"deleted",Toast.LENGTH_SHORT).show()
            }
        }
    }

}