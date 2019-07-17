package com.letbyte.core.w3vpn.ui

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.VpnService
import android.util.Log
import android.view.View
import com.letbyte.core.w3vpn.R
import com.letbyte.core.w3vpn.databinding.ActivityMainBinding
import com.w3engineers.ext.strom.application.ui.base.BaseActivity
import com.w3engineers.vpn.LocalVPNService
import java.net.NetworkInterface
import java.util.*

class MainActivity : BaseActivity() {

    private val TAG = javaClass.simpleName
    lateinit var activityMainBinding : ActivityMainBinding

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun startUI() {

        activityMainBinding = viewDataBinding as ActivityMainBinding
        activityMainBinding.setLifecycleOwner(this)
        activityMainBinding.vpnStateModel = VpnStateModel()
    }

    fun onClickStartVpn(view : View) {
        if (isConnectedToInternet())
            startVPN()
        else {
            showInfoDialog(
                resources.getString(R.string.app_name),
                resources.getString(R.string.no_network_information)
            )
        }
    }

    /**
     * @param title Title in Dialog
     * @param message Message in Dialog
     */
    private fun showInfoDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(getString(android.R.string.ok)) { dialog, which ->
                //finish();
            }
            .show()
    }

    /**
     * Launch intent for user approval of VPN connection
     */
    private fun startVPN() {
        // check for VPN already running
        try {
            if (!checkForActiveInterface("tun0")) {

                // get user permission for VPN
                val intent = VpnService.prepare(this)
                if (intent != null) {
                    Log.d(TAG, "ask user for VPN permission")
                    startActivityForResult(intent, 0)
                } else {
                    Log.d(TAG, "already have VPN permission")
                    onActivityResult(0, RESULT_OK, null)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception checking network interfaces :" + e.message)
            e.printStackTrace()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i(TAG, "onActivityResult(resultCode:  $resultCode)")
        if (resultCode == RESULT_OK) {
            val vpnServiceIntent = Intent(applicationContext, LocalVPNService::class.java)
            startService(vpnServiceIntent)
            activityMainBinding.vpnStateModel?.setIsVpnServiceRunningLiveData(true)
        } else if (resultCode == RESULT_CANCELED) {
            showVPNRefusedDialog()
        }
    }

    /**
     * check a network interface by name
     *
     * @param networkInterfaceName Network interface Name on Linux, for example tun0
     * @return true if interface exists and is active
     * @throws Exception throws Exception
     */
    @Throws(Exception::class)
    private fun checkForActiveInterface(networkInterfaceName: String): Boolean {
        val interfaces = Collections.list(NetworkInterface.getNetworkInterfaces())
        for (networkInterface in interfaces) {
            if (networkInterface.name == networkInterfaceName) {
                return networkInterface.isUp
            }
        }
        return false
    }

    /** check whether network is connected or not
     * @return boolean
     */
    private fun isConnectedToInternet(): Boolean {
        val connectivity = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivity.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            return true
        }
        return false
    }

    /**
     * Show dialog to educate the user about VPN trust
     * abort app if user chooses to quit
     * otherwise relaunch the startVPN()
     */
    private fun showVPNRefusedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Usage Alert")
            .setMessage("You must trust the ToyShark in order to run a VPN based trace.")
            .setPositiveButton(
                getString(R.string.try_again)
            ) { dialog, which -> startVPN() }
            .setNegativeButton(
                getString(R.string.quit)
            ) { dialog, which -> finish() }
            .show()

    }
}
