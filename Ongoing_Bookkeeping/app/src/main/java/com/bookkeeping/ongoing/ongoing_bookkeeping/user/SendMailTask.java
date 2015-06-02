package com.bookkeeping.ongoing.ongoing_bookkeeping.user;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

/**
 * Created by Seulki_Lee on 5/7/2015.
 */
public class SendMailTask  extends AsyncTask<Message, Void, Void> {
    private ProgressDialog progressDialog;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       // progressDialog = ProgressDialog.show(forgetActivity.this, "Please wait", "Sending mail", true, false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
    }

    @Override
    protected Void doInBackground(Message... messages) {
        try {
            Transport.send(messages[0]);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
