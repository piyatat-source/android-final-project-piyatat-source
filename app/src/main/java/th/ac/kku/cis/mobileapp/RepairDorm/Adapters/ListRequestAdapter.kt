package th.ac.kku.cis.mobileapp.RepairDorm.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
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

            return view
        }
}

