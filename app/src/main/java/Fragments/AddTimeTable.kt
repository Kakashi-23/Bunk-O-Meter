package Fragments

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bunk_o_meter.R
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.lang.NullPointerException
import java.lang.Void as Void1


class AddTimeTable : Fragment() {
    lateinit var   storageReference:StorageReference
    lateinit var databaseReference:DatabaseReference

    lateinit var filename: TextView
    lateinit var delete: ImageView
    lateinit var sharedPreferences: SharedPreferences
    lateinit var progressBar: ProgressBar
    lateinit var progressBarLayout:RelativeLayout
    lateinit var noFile:RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
val view=inflater.inflate(R.layout.fragment_add_time_table, container, false)
        filename=view.findViewById(R.id.timeTName)
        delete=view.findViewById(R.id.timeTDelete)
        progressBar=view.findViewById(R.id.timeTProgressBar)
        progressBarLayout=view.findViewById(R.id.timeTProgressLayout)
        progressBarLayout.visibility=View.GONE
        progressBar.visibility=View.GONE
        storageReference= FirebaseStorage.getInstance().reference
        databaseReference=FirebaseDatabase.getInstance().reference
        noFile=view.findViewById(R.id.timeTNoFile)
        noFile.visibility=View.GONE
        sharedPreferences=this.activity!!.getSharedPreferences(getString(R.string.PDFdata), Context.MODE_PRIVATE)
        val isAdded=sharedPreferences.getBoolean("isAdded",false)
        setHasOptionsMenu(true)


        if (!isAdded){
            filename.visibility=View.GONE
            delete.visibility=View.GONE
            AddFile()


        }
        else
        {
     DisplayData()
        }
        return view
    }
    fun AddFile() {
        val intent=Intent()
        intent.type = "application/pdf"
        intent.action = ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"ADD PDF"),1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode==1){
            if (resultCode== RESULT_OK && data!!.data!=null){
                val check=DBAsync(data, activity as Context).execute().get()
                if (check){
                    sharedPreferences.edit().putBoolean("isAdded",true).apply()
                    DisplayData()
                }
                else{
                    Toast.makeText(activity as Context,"Some Error Occured",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    fun DisplayData(){
        filename.visibility=View.VISIBLE
        delete.visibility=View.VISIBLE
        progressBarLayout.visibility=View.VISIBLE
        progressBar.visibility=View.VISIBLE
        noFile.visibility=View.GONE

        val databaseRef=FirebaseDatabase.getInstance().reference.child("Timetable")
        databaseRef.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                try {
                    val url=p0.getValue(String::class.java)!!
                    println("response is $url")
                    val fileName = url.substring(url.lastIndexOf('%') + 3, url.length)
                    filename.text=fileName.substring(0, fileName.lastIndexOf('.'))
                    progressBar.visibility=View.GONE
                    progressBarLayout.visibility=View.GONE
                }
                catch (e: NullPointerException){

                }
            }
        })
        delete.setOnClickListener {
            val storageValueDelete=FirebaseStorage.getInstance().reference.child("TimeTable").delete().isSuccessful
            val databaseValueDelete=FirebaseDatabase.getInstance().reference.child("Timetable").removeValue().isSuccessful
            if (databaseValueDelete && storageValueDelete){
                Toast.makeText(context,"File Deleted",Toast.LENGTH_SHORT).show()
                noFile.visibility=View.VISIBLE
                sharedPreferences.edit().putBoolean("isAdded",false).apply()
            }
        }
    }
    // menu for adding file
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_time_table,menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId ==R.id.menuADD){
            AddFile()
        }
        return super.onOptionsItemSelected(item)
    }


// Async task class for uploading
    class DBAsync(val data: Intent?,val context:Context):AsyncTask<Void1, Void1,Boolean>(){
        lateinit var   progressDialog:ProgressDialog
        lateinit var storageRef:StorageReference
        lateinit var databaseRef:DatabaseReference
        var check=false
        override fun doInBackground(vararg p0: Void1?): Boolean {
            UploadingFile(data,context)
            return check
        }



    override fun onPreExecute() {
            storageRef=FirebaseStorage.getInstance().reference
        databaseRef=FirebaseDatabase.getInstance().reference
             progressDialog=ProgressDialog(context)
            progressDialog.setMessage("Uploading..")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()
        }

        override fun onPostExecute(result: Boolean?){
            progressDialog.dismiss()
        Toast.makeText(context,"File Uploaded",Toast.LENGTH_SHORT).show()
    }

        fun UploadingFile(data: Intent?,context: Context){
            val ref= storageRef.child("TimeTable/"+System.currentTimeMillis()+(".pdf"))
            ref.putFile(data!!.data!!).addOnSuccessListener {
                val uri1=it.storage.downloadUrl
                while (!uri1.isComplete);
                val url=uri1.result!!
               databaseRef.child("Timetable").setValue(url.toString())
                Toast.makeText(context,"File Uploaded",Toast.LENGTH_SHORT).show()
                check=true
            }.addOnProgressListener {
                val progress=(100.0*it.bytesTransferred)/it.totalByteCount
                progressDialog.setMessage("Uploading..${progress.toInt()}%")

            }.addOnFailureListener{
                    storageRef.child("TimeTable/").delete().addOnSuccessListener {
                        Toast.makeText(context,"$it",Toast.LENGTH_SHORT).show()
                        check = false
                    }
            }
        }
    }

    // Start the operation
    fun Start(){
        
    }



}







