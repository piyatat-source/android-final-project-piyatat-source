package th.ac.kku.cis.mobileapp.RepairDorm.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import th.ac.kku.cis.mobileapp.RepairDorm.Models.listRequestFB
import th.ac.kku.cis.mobileapp.RepairDorm.R

public class listReqForStaff(var mCtx: Context, var resource:Int, var item:List<listRequestFB>)
    : ArrayAdapter<listRequestFB>( mCtx , resource , item) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)

        val view: View = layoutInflater.inflate(resource,null)
        var tvTopic : TextView = view.findViewById(R.id.TopicText)
        var tvDate : TextView = view.findViewById(R.id.DateText)
        var tvStatus : TextView = view.findViewById(R.id.StatusText)

        var dataReq: listRequestFB = item[position]
        tvTopic.text = dataReq.topic
        tvDate.text = dataReq.date
        tvStatus.text = dataReq.status

        return view
    }
}
