package Model

import android.net.Uri
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UploadPdf(var url: String?=" "){
    fun GetUrl(): String? {
        return url
    }
}