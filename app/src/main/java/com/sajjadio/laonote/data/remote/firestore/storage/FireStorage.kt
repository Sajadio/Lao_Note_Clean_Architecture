package com.sajjadio.laonote.data.remote.firestore.storage

import android.net.Uri
import com.google.firebase.storage.UploadTask

interface FireStorage {
    fun manageImageStorage(uri: Uri): UploadTask
}