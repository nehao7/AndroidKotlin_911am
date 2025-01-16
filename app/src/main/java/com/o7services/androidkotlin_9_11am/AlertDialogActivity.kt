package com.o7services.androidkotlin_9_11am

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.o7services.androidkotlin_9_11am.databinding.ActivityAlertDialogBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AlertDialogActivity : AppCompatActivity() {
    lateinit var binding: ActivityAlertDialogBinding
    var list = arrayOf("one", "two", "three", "four")
    var checklist = booleanArrayOf(true, false, true, true)
    var simpleDateFormat = SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault())
    var timeFormat = SimpleDateFormat("HH:mm:ss a", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAlertDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.button.setOnClickListener {
            Toast.makeText(this, "fcghjk", Toast.LENGTH_SHORT).show()

        }
        binding.button.setOnClickListener {
            Snackbar.make(it, "This is a simple Snackbar", Snackbar.LENGTH_LONG)
                .setAction("Undo", {
                    Toast.makeText(
                        this, "Toast is invoked from snackbar",
                        Toast.LENGTH_SHORT
                    ).show()
                }).show()
        }

        binding.btnDialog.setOnClickListener {
           AlertDialog.Builder(this) .apply {
                setTitle("This is Alert Dialog")
               setMessage("This is my Message")
           setNegativeButton("No") { _, _ ->
                }
            setPositiveButton("yes") { _, _ -> }
               setNeutralButton("Cancel"){_,_->}
            setCancelable(true)
          show()
            }
        }


        binding.btnSingleChoice.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle("My List")
                setItems(list) { _, which ->
                    Toast.makeText(
                        this@AlertDialogActivity,
                        list[which].toString(), Toast.LENGTH_LONG
                    ).show()
                }
                setNegativeButton("No") { _, _ ->
                }
                setPositiveButton("yes") { _, _ -> }
                setCancelable(false)
                show()

            }
        }

        binding.btnMultipleChoice.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle("This is Alert Dialog")
                setMultiChoiceItems(list,checklist){_,which,isChecked->
                    checklist.set(which,isChecked)
                    Toast.makeText(
                        this@AlertDialogActivity,
                        list[which].toString(), Toast.LENGTH_LONG
                    ).show()

                }
                setNegativeButton("No") { _, _ ->
                }
                setPositiveButton("yes") { _, _ -> }
                setCancelable(true)
                show()
            }
        }

        binding.btnCustomDialog.setOnClickListener {
            var dialog = Dialog(this)
            dialog.setContentView(R.layout.custom_layout)
            var btn=dialog.findViewById<Button>(R.id.btnCancel)

            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            )
            dialog.show()
            btn.setOnClickListener{
                dialog.dismiss()
            }
        }

        binding.btnDatePicker.setOnClickListener {

            DatePickerDialog(
                this,
                { _, year, month, dateOfMonth ->
                    Log.e("date", "picked Date is $year $month $dateOfMonth")
                    val calendar = Calendar.getInstance()
                    calendar.set(year, month, dateOfMonth)
                    var formattedDate = simpleDateFormat.format(calendar.time)
                    binding.btnDatePicker.setText(formattedDate)
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DATE),
            ).show()
        }

    }
}