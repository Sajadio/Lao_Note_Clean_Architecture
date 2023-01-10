package com.sajjadio.laonote.data.remote.firestore

import android.content.Context
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.sajjadio.laonote.utils.NOTES_IMAGES
import com.sajjadio.laonote.utils.extension.getFileExtension
import javax.inject.Inject

class FireStorageImpl @Inject constructor(
    private val fireStorage: FirebaseStorage,
    private val context: Context
) : FireStorage {

    override fun manageImageStorage(uri: Uri): UploadTask {
        val storageRef = fireStorage.reference.child(NOTES_IMAGES)
            .child("${System.currentTimeMillis()}.${getFileExtension(context, uri)}")
        return storageRef.putFile(uri)
    }
}