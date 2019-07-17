package com.letbyte.core.w3vpn.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * ============================================================================
 * Copyright (C) 2019 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br></br>----------------------------------------------------------------------------
 * <br></br>Created by: Ahmed Mohmmad Ullah (Azim) on [2019-06-25 at 11:23 AM].
 * <br></br>----------------------------------------------------------------------------
 * <br></br>Project: W3VPN.
 * <br></br>Code Responsibility: <Purpose of code>
 * <br></br>----------------------------------------------------------------------------
 * <br></br>Edited by :
 * <br></br>1. <First Editor> on [2019-06-25 at 11:23 AM].
 * <br></br>2. <Second Editor>
 * <br></br>----------------------------------------------------------------------------
 * <br></br>Reviewed by :
 * <br></br>1. <First Reviewer> on [2019-06-25 at 11:23 AM].
 * <br></br>2. <Second Reviewer>
 * <br></br>============================================================================
</Second></First></Second></First></Purpose> */
class VpnStateModel {

    private val mIsVpnServiceRunningLiveData: MutableLiveData<Boolean> = MutableLiveData()

    val isVpnServiceRunning: LiveData<Boolean>
        get() = mIsVpnServiceRunningLiveData

    fun setIsVpnServiceRunningLiveData(isServiceOn: Boolean) {
        this.mIsVpnServiceRunningLiveData.value = isServiceOn
    }

}
