package com.example.emailsenderapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*
import javax.activation.DataHandler
import javax.activation.FileDataSource
import javax.mail.*
import javax.mail.internet.*

object MailSender {

    suspend fun sendEmail(
        fromEmail: String,
        password: String,
        toEmail: String,
        subject: String,
        bodyHtml: String,
        attachments: List<File>
    ): Result<String> = withContext(Dispatchers.IO) {
        return@withContext try {
            val props = Properties().apply {
                put("mail.smtp.auth", "true")
                put("mail.smtp.starttls.enable", "true")
                put("mail.smtp.host", "smtp.gmail.com")
                put("mail.smtp.port", "587")
            }

            val session = Session.getInstance(props, object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(fromEmail, password)
                }
            })

            val message = MimeMessage(session).apply {
                setFrom(InternetAddress(fromEmail))
                setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail))
                setSubject(subject)

                val multipart = MimeMultipart()

                // Body part
                val bodyPart = MimeBodyPart()
                bodyPart.setContent(bodyHtml, "text/html; charset=utf-8")
                multipart.addBodyPart(bodyPart)

                // Attachments
                for (file in attachments) {
                    val attachPart = MimeBodyPart()
                    val source = FileDataSource(file)
                    attachPart.dataHandler = DataHandler(source)
                    attachPart.fileName = file.name
                    multipart.addBodyPart(attachPart)
                }

                setContent(multipart)
            }

            Transport.send(message)
            Result.success("Email sent successfully.")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
