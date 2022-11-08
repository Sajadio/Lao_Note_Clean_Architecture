package com.sajjadio.laonote.utils

import android.app.Activity
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.EasyPermissions.hasPermissions
import com.vmadalin.easypermissions.EasyPermissions.requestPermissions
import com.vmadalin.easypermissions.EasyPermissions.somePermissionPermanentlyDenied
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import javax.inject.Inject

class PermissionsHelper @Inject constructor() : EasyPermissions.PermissionCallbacks {
    lateinit var message: String
    lateinit var activity: Activity

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (somePermissionPermanentlyDenied(activity, perms)) {
            SettingsDialog.Builder(activity)
                .rationale(message)
                .build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        //TODO("Not yet implemented")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults,
            activity
        )
    }

    private fun hasStoragePermission(mediaStore: String) =
        hasPermissions(activity, mediaStore)

    fun getRequestPermission(
        requestCode: Int,
        mediaStore: String,
        execute: () -> Unit
    ) {
        if (hasStoragePermission(mediaStore)) {
            execute()
        } else {
            requestPermissions(
                activity,
                message,
                requestCode,
                mediaStore
            )
        }
    }

}