package th.ac.kku.cis.mobileapp.RepairDorm.Adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.FirebaseDatabase
import th.ac.kku.cis.mobileapp.RepairDorm.Models.infoRequest
import th.ac.kku.cis.mobileapp.RepairDorm.R
import java.text.FieldPosition

public class ListRequestAdapter(var mCtx: Context, var resource:Int, var item:List<infoRequest>)
    : ArrayAdapter<infoRequest>( mCtx , resource , item) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View{
            val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)

            val view: View = layoutInflater.inflate(resource,null)
            var tvTopic : TextView = view.findViewById(R.id.txt_Topic)
            var tvDate : TextView = view.findViewById(R.id.txt_Date)
            var tvStatus : TextView = view.findViewById(R.id.txt_Status)

            var infoReq: infoRequest = item[position]
            tvTopic.text = infoReq.Topic
            tvDate.text = infoReq.Date
            tvStatus.text = infoReq.Status
            var key =  infoReq.key
            var Stdid =  infoReq.stdid


        var del : TextView = view.findViewById(R.id.btn_del)

        del.setOnClickListener{
            val mDatabase = FirebaseDatabase.getInstance().getReference("repairlists").child(Stdid)
            val builder = AlertDialog.Builder(context)

            builder.setTitle("ยืนยันการลบ?")
            builder.setMessage("ยืนยันการลบรายการแจ้งซ่อมนี้")

            builder.setPositiveButton("ยืนยัน") { dialog, which ->
                mDatabase.child(key).removeValue().addOnSuccessListener {
                    Toast.makeText(context, "ลบรายการแจ้งซ่อมนี้แล้ว", Toast.LENGTH_SHORT).show()
                }
            }

            builder.setNegativeButton("ยกเลิก") { dialog, which ->
                Toast.makeText(context, "ยกเลิกการการลบแล้ว", Toast.LENGTH_SHORT).show()
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        return view
    }
}

