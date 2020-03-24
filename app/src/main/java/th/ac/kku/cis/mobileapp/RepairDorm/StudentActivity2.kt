package th.ac.kku.cis.mobileapp.RepairDorm

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_student2.*
import kotlinx.android.synthetic.main.content_student2.*
import th.ac.kku.cis.mobileapp.RepairDorm.Adapters.ListRequestAdapter
import th.ac.kku.cis.mobileapp.RepairDorm.Models.infoRequest
import th.ac.kku.cis.mobileapp.RepairDorm.Models.infoStudent

class StudentActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student2)
        setSupportActionBar(toolbar)

        var i = intent
        var Stdid = i.getStringExtra("stdid")
        var Firstname = i.getStringExtra("firstname")
        var Lastname = i.getStringExtra("lastname")
        var numID = i.getStringExtra("numid")
        var roomNo = i.getStringExtra("roomid")


        tvFullname.text = "คุณ " + Firstname + " " + Lastname


        Firebase.database.reference.child("repairlists")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    val listView: ListView = findViewById(R.id.listViewRequest)
                    val dataReq = mutableListOf<infoRequest>()

                    for (postSnapshot in dataSnapshot.children) {
                        dataReq.add(
                            infoRequest(
                                postSnapshot.child("topic").getValue().toString()
                                , postSnapshot.child("date").getValue().toString()
                                , postSnapshot.child("status").getValue().toString()
                            )
                        )
                    }

                    listView.adapter = ListRequestAdapter(
                        this@StudentActivity2, R.layout.activity_requestlist, dataReq
                    )

                    listView.setOnItemClickListener { parent, view, position, id ->
                        val selectItem = parent.getItemAtPosition(position) as infoRequest


                        val l = Intent(this@StudentActivity2, showReqActivity::class.java)
                        l.putExtra("topic", selectItem.Topic)
                        l.putExtra("date", selectItem.Date)
                        l.putExtra("status", selectItem.Status)
                        startActivity(l)
                    }
                }
                override fun onCancelled(p0: DatabaseError) {

                }
            })





        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

            startActivity(Intent(this, AddReqActivity::class.java))
        }
    }
}

