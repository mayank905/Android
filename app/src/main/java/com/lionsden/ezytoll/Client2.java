package com.lionsden.ezytoll;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client2 extends AsyncTask<Void, Void, String> {

    String chatstring;

    Client2(String addr) {
        chatstring = addr;
    }

    @Override
    protected String doInBackground(Void... arg1) {


        //return (response);
        return null;
    }



    @Override
    protected void onPostExecute(String result) {

        super.onPostExecute(result);
    }

}