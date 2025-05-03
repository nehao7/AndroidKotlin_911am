package com.o7services.androidkotlin_9_11am

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.razorpay.Checkout
import com.razorpay.ExternalWalletListener
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import com.razorpay.SmsReceiver
import org.json.JSONObject

class PaymentActivity : AppCompatActivity(),PaymentResultWithDataListener, ExternalWalletListener {

    private lateinit var upiPaymentLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        upiPaymentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK || result.resultCode == 11) {
                val data = result.data
                if (data != null) {
                    val response = data.getStringExtra("response") ?: ""
                    parseUpiResponse(response)
                } else {
                    Toast.makeText(this, "Payment cancelled or failed", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Payment not successful", Toast.LENGTH_SHORT).show()
            }
        }
        findViewById<Button>(R.id.btnPay).setOnClickListener {
            startPayment()
        }
 findViewById<Button>(R.id.btnUpi).setOnClickListener {
     payUsingUpi("Neha Basra", "nehabasra100@okicici", "Monthly Subscription", "1.00")        }

//        val uri = Uri.parse("upi://pay").buildUpon()
//            .appendQueryParameter("pa", "user@upi") // Payee VPA
//            .appendQueryParameter("pn", "Payee Name")
//            .appendQueryParameter("mc", "")
//            .appendQueryParameter("tr", "TxnRef123")
//            .appendQueryParameter("tn", "Note for payment")
//            .appendQueryParameter("am", "100.00")
//            .appendQueryParameter("cu", "INR")
//            .build()
//
//        val intent = Intent(Intent.ACTION_VIEW).apply {
//            data = uri
//        }
//        startActivityForResult(intent, 100)
    }
    private fun parseUpiResponse(response: String) {
        var status = ""
        var approvalRefNo = ""

        val responseList = response.split("&")
        for (pair in responseList) {
            val parts = pair.split("=")
            if (parts.size >= 2) {
                when (parts[0].lowercase()) {
                    "status" -> status = parts[1].lowercase()
                    "approvalrefno", "txnref" -> approvalRefNo = parts[1]
                }
            }
        }

        when (status) {
            "success" -> Toast.makeText(this, "Transaction successful: $approvalRefNo", Toast.LENGTH_LONG).show()
            "failure" -> Toast.makeText(this, "Transaction failed", Toast.LENGTH_LONG).show()
            else -> Toast.makeText(this, "Payment cancelled", Toast.LENGTH_LONG).show()
        }
    }

    fun payUsingUpi(name: String, upiId: String, note: String, amount: String) {
        val uri = Uri.parse("upi://pay").buildUpon()
            .appendQueryParameter("pa", upiId)
            .appendQueryParameter("pn", name)
            .appendQueryParameter("tn", note)
            .appendQueryParameter("am", amount)
            .appendQueryParameter("cu", "INR")
            .build()

        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = uri
        }

        val chooser = Intent.createChooser(intent, "Pay with UPI")
        if (chooser.resolveActivity(packageManager) != null) {
            upiPaymentLauncher.launch(chooser)
        } else {
            Toast.makeText(this, "No UPI app found!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startPayment(){

        Checkout.preload(this)

        val co = Checkout()
        // apart from setting it in AndroidManifest.xml, keyId can also be set
        // programmatically during runtime
        co.setKeyID("rzp_test_IfhAqGdtgoZXyq")

        try {
            val options = JSONObject()
            options.put("name","Neha Basra")
//            options.put("description","Demoing Charges")
            //You can comment the image option to fetch the image from the dashboard
            options.put("image",R.drawable.ic_launcher_background)
            options.put("theme.color", "#3399cc")
            options.put("currency","INR");
//            options.put("order_id", "order_DBJOWzybf0sJbb")
            options.put("amount","1000")//in paise

            val retryObj = JSONObject()
            retryObj.put("enabled", true)
            retryObj.put("max_count", 4)
            options.put("retry", retryObj)

            val prefill = JSONObject()
            prefill.put("email","nehabasra100@gmail.com")
            prefill.put("contact","7717264758")


            options.put("upi", true)  // This opens UPI apps for payment

            options.put("prefill",prefill)
            co.open(this,options)
        }catch (e: Exception){
            Toast.makeText(this,"Error in payment: "+ e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        println("Check Payment Successfully or not: $p0")
        println("Check Payment Data: $p1")
        Toast.makeText(this, "Payment is successful : " + p0, Toast.LENGTH_SHORT).show();
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        println("Check Payment Data: $p1")
        Toast.makeText(this, "Payment Failed due to error : " + p1, Toast.LENGTH_SHORT).show();    }

    override fun onExternalWalletSelected(p0: String?, p1: PaymentData?) {
    }

}