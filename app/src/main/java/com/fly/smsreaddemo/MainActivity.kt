package com.fly.smsreaddemo

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fly.smsreaddemo.databinding.ActivityMainBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val readPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        readPermission.launch(
            arrayOf(
                android.Manifest.permission.RECEIVE_SMS,
                android.Manifest.permission.READ_SMS
            )
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = SMSAdapter(querySMS())

            val divider = DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
            addItemDecoration(divider)
        }
    }

    private fun querySMS(): MutableList<SMS> {
        val uri = Uri.parse("content://sms/inbox")
        val projection = arrayOf("_id", "address", "body", "date")
        val smsList = mutableListOf<SMS>()

        contentResolver.query(uri, projection, null, null, null)?.apply {
            if (moveToFirst()) {
                do {
                    val id = getInt(getColumnIndexOrThrow("_id"))
                    val sender = getString(getColumnIndexOrThrow("address"))
                    val body = getString(getColumnIndexOrThrow("body"))
                    val timestamp = getLong(getColumnIndexOrThrow("date"))
                    val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.TAIWAN)
                    val dateTime = dateFormat.format(Date(timestamp))

                    smsList.add(SMS(id, sender, body, dateTime))
                } while (moveToNext())
            }
            close()
        }

        return smsList
    }

}