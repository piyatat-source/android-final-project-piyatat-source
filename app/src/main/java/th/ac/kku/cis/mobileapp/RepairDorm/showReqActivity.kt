package th.ac.kku.cis.mobileapp.RepairDorm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_showreq.*
import th.ac.kku.cis.mobileapp.RepairDorm.Models.infoStudent

class showReqActivity : AppCompatActivity() {

    private val TAG: String = "showReq"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showreq)
        var i = intent
        var Topic = i.getStringExtra("topic")
        var Date = i.getStringExtra("date")
        var Status = i.getStringExtra("status")
        var Jobreq = i.getStringExtra("job")
        var Descript = i.getStringExtra("descript")

        var Stdid = i.getStringExtra("stdid")
        //Log.d(TAG, "=========" + Stdid)

        txt_topic.text = Topic
        txt_date.text = Date
        txt_status.text = Status
        txt_job.text = "(" + Jobreq + ")"
        txt_descript.text = Descript



        FirebaseDatabase.getInstance().reference.child("students").child(Stdid.toString())
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
        //var Firstname = i.getStringExtra("firstname")
        //var Lastname = i.getStringExtra("lastname")
    }
}//Log.d(TAG, "Your fullname is " + Firstname)




