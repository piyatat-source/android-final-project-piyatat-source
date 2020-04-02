package th.ac.kku.cis.mobileapp.RepairDorm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.activity_showreqforstaff.*
import kotlinx.android.synthetic.main.activity_showreqforstaff.txt_date
import kotlinx.android.synthetic.main.activity_showreqforstaff.txt_descript
import kotlinx.android.synthetic.main.activity_showreqforstaff.txt_fullname
import kotlinx.android.synthetic.main.activity_showreqforstaff.txt_job
import kotlinx.android.synthetic.main.activity_showreqforstaff.txt_roomNo
import kotlinx.android.synthetic.main.activity_showreqforstaff.txt_status
import kotlinx.android.synthetic.main.activity_showreqforstaff.txt_topic
import th.ac.kku.cis.mobileapp.RepairDorm.Models.infoStudent

class showReqForStaffActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showreqforstaff)

        var i = intent
        var date = i.getStringExtra("date")
        var description = i.getStringExtra("descript")
        var job = i.getStringExtra("job")
        var status = i.getStringExtra("status")
        var topic = i.getStringExtra("topic")
        var studentid = i.getStringExtra("stdid")
        var Key = i.getStringExtra("key")

        txt_date.text = date
        txt_descript.text = description
        txt_job.text = job
        txt_status.text = status
        txt_topic.text = topic

        FirebaseDatabase.getInstance().reference.child("students").child(studentid)
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val info = dataSnapshot.getValue<infoStudent>()
                    txt_fullname.text = info!!.Firstname + " " + info.Lastname.toString()
                    txt_roomNo.text = info!!.roomNo.toString()
                }

                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })

        btn_success.setOnClickListener {
            repairSuccess(status,studentid,Key)
        }
    }

    private fun repairSuccess(status: String, stdid: String, key: String) {
        val builder = AlertDialog.Builder(this@showReqForStaffActivity)

        builder.setTitle("ยืนยันการซ่อมแซม")
        builder.setMessage("คุณต้องการที่จะยืนยันการซ่อมแซม ?")

        builder.setPositiveButton("ยืนยัน") { dialog, which ->

            val myDataBase = FirebaseDatabase.getInstance().getReference("repairlists").child(stdid).child(key)
            txt_status.text = "ดำเนินการเสร็จสิ้น"
            myDataBase.child("status").setValue("ดำเนินการเสร็จสิ้น").addOnCompleteListener{
                Toast.makeText(this, "ซ่อมแซมเสร็จสิ้น", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("ยกเลิก"){dialog,which ->
            Toast.makeText(this,"ยกเลิกการซ่อมแซม",Toast.LENGTH_SHORT).show()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
