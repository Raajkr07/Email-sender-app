package com.example.emailsenderapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var toField: EditText
    private lateinit var subjectField: EditText
    private lateinit var bodyField: EditText
    private lateinit var sendButton: Button
    private lateinit var attachButton: Button

    private val PICK_FILE_REQUEST = 1
    private var selectedFiles: MutableList<File> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toField = findViewById(R.id.toField)
        subjectField = findViewById(R.id.subjectField)
        bodyField = findViewById(R.id.bodyField)
        sendButton = findViewById(R.id.sendButton)
        attachButton = findViewById(R.id.attachButton)

        loadDraft()

        attachButton.setOnClickListener {
            openFilePicker()
        }

        sendButton.setOnClickListener {
            saveDraft(subjectField.text.toString(), bodyField.text.toString())
            sendEmailViaSMTP()
        }
    }

    private fun sendEmailViaSMTP() {
        val to = toField.text.toString()
        val subject = subjectField.text.toString()
        val bodyHtml = bodyField.text.toString()

        val fromEmail = "rk8210032@gmail.com"
        val appPassword = "xftwgyobhuxmnjjd" // No spaces

        if (to.isBlank() || subject.isBlank() || bodyHtml.isBlank()) {
            Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            val result = MailSender.sendEmail(
                fromEmail = fromEmail,
                password = appPassword,
                toEmail = to,
                subject = subject,
                bodyHtml = bodyHtml,
                attachments = selectedFiles
            )

            result.fold(
                onSuccess = {
                    Toast.makeText(this@MainActivity, it, Toast.LENGTH_LONG).show()
                },
                onFailure = {
                    Toast.makeText(this@MainActivity, "Error: ${it.localizedMessage}", Toast.LENGTH_LONG).show()
                    it.printStackTrace()
                }
            )
        }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        startActivityForResult(Intent.createChooser(intent, "Select File(s)"), PICK_FILE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK) {
            selectedFiles.clear()

            data?.clipData?.let { clipData ->
                for (i in 0 until clipData.itemCount) {
                    val uri = clipData.getItemAt(i).uri
                    val path = FileUtils.getPath(this, uri)
                    if (path != null) {
                        selectedFiles.add(File(path))
                    } else {
                        Toast.makeText(this, "Could not get path for attachment", Toast.LENGTH_SHORT).show()
                    }
                }
            } ?: data?.data?.let { uri ->
                val path = FileUtils.getPath(this, uri)
                if (path != null) {
                    selectedFiles.add(File(path))
                } else {
                    Toast.makeText(this, "Could not get path for attachment", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun saveDraft(subject: String, body: String) {
        val prefs = getSharedPreferences("draft", MODE_PRIVATE)
        prefs.edit().apply {
            putString("subject", subject)
            putString("body", body)
            apply()
        }
    }

    private fun loadDraft() {
        val prefs = getSharedPreferences("draft", MODE_PRIVATE)
        subjectField.setText(prefs.getString("subject", ""))
        bodyField.setText(prefs.getString("body", ""))
    }
}
