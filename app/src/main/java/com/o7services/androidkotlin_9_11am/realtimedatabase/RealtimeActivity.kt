package com.o7services.androidkotlin_9_11am.realtimedatabase

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidkt_2to4_6m.Realtimedatabase.firebase.ClickInterface
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.o7services.androidkotlin_9_11am.R
import com.o7services.androidkotlin_9_11am.databinding.ActivityRealtimeBinding
import com.o7services.androidkotlin_9_11am.databinding.DialogAddUpdateMenuBinding

class RealtimeActivity : AppCompatActivity(), ClickInterface {
    lateinit var binding: ActivityRealtimeBinding
    lateinit var menuAdapter: MenuAdapter
    var dbReference: DatabaseReference = FirebaseDatabase.getInstance().reference
    var userList=ArrayList<MenuModel>()
    val TAG="logs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityRealtimeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbReference.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                Log.e(TAG," snapshot before ${snapshot.value}")
                val menuModel: MenuModel? = snapshot.getValue(MenuModel::class.java)
                menuModel?.id = snapshot.key
                Log.e(TAG," snapshot ${snapshot.value} id:${snapshot.key}")

                if (menuModel != null) {
                    userList.add(menuModel)
                    menuAdapter.notifyDataSetChanged()
                }
                Log.e(TAG,"menu model ${menuModel?.name}")
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                Log.e(TAG," on child changed")
                val menuModel:MenuModel?= snapshot.getValue(MenuModel::class.java)
                menuModel?.id=snapshot.key
                if (menuModel!=null) {
                    userList.forEachIndexed { index, menuModelData ->
                        if(menuModelData.id == menuModel.id) {
                            userList[index] = menuModel
                            return@forEachIndexed
                        }
                    }
                    menuAdapter.notifyDataSetChanged()
                }
            }
            override fun onChildRemoved(snapshot: DataSnapshot) {
                Log.e(TAG," on child removed")
                val menuModel:MenuModel?= snapshot.getValue(MenuModel::class.java)
                menuModel?.id=snapshot.key
                if(menuModel!=null){
                    userList.remove(menuModel)
                    menuAdapter.notifyDataSetChanged()
                }
            }
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                Log.e(TAG," on child moved")
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

        menuAdapter = MenuAdapter(userList,this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter=menuAdapter
        binding.fcbAdd.setOnClickListener {
            val binding = DialogAddUpdateMenuBinding.inflate(layoutInflater)
            val addDialog = Dialog(this)
            addDialog.setContentView(binding.root)
            binding.btnAdd.setOnClickListener {
                if (binding.etMenuName.text.isEmpty()) {
                    binding.etMenuName.setError("Enter Name")
                } else if (binding.etMenuPrice.text.isEmpty()) {
                    binding.etMenuPrice.setError("Enter Price")
                } else {
                    val menuData = MenuModel("",binding.etMenuName.text.toString(), binding.etMenuPrice.text.toString())

                    dbReference.push().setValue(menuData)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Menu Add", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener { err ->
                            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                        }
                    addDialog.dismiss()
                }
            }
            binding.btnCancel.setOnClickListener {
                addDialog.dismiss()
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
            }
            addDialog.create()
            addDialog.show()
        }
    }

    override fun editClick(menuModel: MenuModel, position: Int) {
        val binding = DialogAddUpdateMenuBinding.inflate(layoutInflater)
        val editDialog = Dialog(this)
        binding.tvText.setText("Update Item")
        binding.btnAdd.setText("Update")
        binding.etMenuName.setText(menuModel.name)
        binding.etMenuPrice.setText(menuModel.price)
        editDialog.setContentView(binding.root)
        editDialog.create()
        editDialog.show()
        binding.btnAdd.setOnClickListener {
            if (binding.etMenuName.text.isEmpty()) {
                binding.etMenuName.setError("Enter Name")
            } else if (binding.etMenuPrice.text.isEmpty()) {
                binding.etMenuPrice.setError("Enter Price")
            } else {
                val key = menuModel.id
                val post = MenuModel("",binding.etMenuName.text.toString(), binding.etMenuPrice.text.toString())
//                val postValues = post.toMap()
                val childUpdates = hashMapOf<String, Any>(
                    "$key" to post,
                )
                dbReference.updateChildren(childUpdates)
                editDialog.dismiss()
            }
        }
        binding.btnCancel.setOnClickListener {
            editDialog.dismiss()
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
        }
    }

    override fun deleteClick(menuModel: MenuModel, position: Int) {
        val dialog= AlertDialog.Builder(this)
        dialog.setTitle("Delete")
        dialog.setMessage("Are you sure...")
        dialog.setPositiveButton("Yes"){addDialog, _ ->
            dbReference.child(menuModel.id ?: "").removeValue()
        }
        dialog.setNegativeButton("No"){addDialog, _ ->
            addDialog.dismiss()
        }
        dialog.create()
        dialog.show()
    }
}
