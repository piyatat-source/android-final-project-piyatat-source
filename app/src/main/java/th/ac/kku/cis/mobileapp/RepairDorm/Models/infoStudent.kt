package th.ac.kku.cis.mobileapp.RepairDorm.Models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties

data class infoStudent(
    var Email: String? = "",
    var Firstname: String? = "",
    var Lastname: String? = "",
    var numID: String? = "",
    var roomNo: String? = ""
)