package com.o7services.androidkotlin_9_11am

import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.btnPay).setOnClickListener {
            startPayment()
        }
    }

    private fun startPayment(){

        Checkout.preload(this)

        val co = Checkout()
        // apart from setting it in AndroidManifest.xml, keyId can also be set
        // programmatically during runtime
        co.setKeyID("rzp_test_c51XgNNFUQLpLP")

        try {
            val options = JSONObject()
            options.put("name","Neha")
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
            prefill.put("email","abc@gmail.com")
            prefill.put("contact","1234567890")

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