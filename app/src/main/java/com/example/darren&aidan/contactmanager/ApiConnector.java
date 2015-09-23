package com.example.darren&aidan.contactmanager;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Darren & Aidan on 3/31/2015.
 */
public class ApiConnector {
    private String theUrl = "http://ec2-52-5-244-215.compute-1.amazonaws.com/";

    public JSONArray SearchItems(String search){
        String url =  theUrl +"searchTitleDescription.php?Search="+search;

        //Get HttpResponse Object from url
        //Get HttpEntitiy from Http response object

        HttpEntity httpEntity = null;

        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpGet);

            httpEntity = httpResponse.getEntity();
        } catch(ClientProtocolException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }


        //Convert HttpEntity into JSON Array
        JSONArray jsonArray = null;

        if (httpEntity != null){
            try{
                String entityResponse = EntityUtils.toString(httpEntity);
                Log.e("Entity Response : ", entityResponse);

                jsonArray = new JSONArray(entityResponse);
            } catch(JSONException e){
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            }
        }

        return jsonArray;
    }


    public JSONArray GetAllItems(){
        String url = theUrl +"getAllItems.php";

        //Get HttpResponse Object from url
        //Get HttpEntitiy from Http response object

        HttpEntity httpEntity = null;

        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpGet);

            httpEntity = httpResponse.getEntity();
        } catch(ClientProtocolException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }


        //Convert HttpEntity into JSON Array
        JSONArray jsonArray = null;

        if (httpEntity != null){
            try{
                String entityResponse = EntityUtils.toString(httpEntity);
                Log.e("Entity Response : ", entityResponse);

                jsonArray = new JSONArray(entityResponse);
            } catch(JSONException e){
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            }
        }

        return jsonArray;
    }


    public JSONArray GetMyItems(String phone){
        String url = theUrl +"getMyItems.php?Phone="+phone;

        //Get HttpResponse Object from url
        //Get HttpEntitiy from Http response object


        HttpEntity httpEntity = null;

        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpGet);

            httpEntity = httpResponse.getEntity();
        } catch(ClientProtocolException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }

        //Convert HttpEntity into JSON Array
        JSONArray jsonArray = null;

        if (httpEntity != null){
            try{
                String entityResponse = EntityUtils.toString(httpEntity);
                Log.e("Entity Response : ", entityResponse);

                jsonArray = new JSONArray(entityResponse);
            } catch(JSONException e){
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            }
        }

        return jsonArray;
    }

    public JSONArray GetLikedItems(String phone){
        String url = theUrl +"getLikedItems.php?Phone="+phone;

        //Get HttpResponse Object from url
        //Get HttpEntitiy from Http response object


        HttpEntity httpEntity = null;

        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpGet);

            httpEntity = httpResponse.getEntity();
        } catch(ClientProtocolException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }

        //Convert HttpEntity into JSON Array
        JSONArray jsonArray = null;

        if (httpEntity != null){
            try{
                String entityResponse = EntityUtils.toString(httpEntity);
                Log.e("Entity Response : ", entityResponse);

                jsonArray = new JSONArray(entityResponse);
            } catch(JSONException e){
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            }
        }

        return jsonArray;
    }

    public JSONArray GetItemDetails(String phone, String time){
        String finalTime = time.replace(" ", "%20");
        String url = theUrl + "getItemDetails.php?PhoneNumber="+phone+"&Time="+finalTime;

        //Get HttpResponse Object from url
        //Get HttpEntitiy from Http response object

        HttpEntity httpEntity = null;

        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpGet);

            httpEntity = httpResponse.getEntity();
        } catch(ClientProtocolException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }


        //Convert HttpEntity into JSON Array
        JSONArray jsonArray = null;

        if (httpEntity != null){
            try{
                String entityResponse = EntityUtils.toString(httpEntity);
                Log.e("Entity Response : ", entityResponse);

                jsonArray = new JSONArray(entityResponse);
            } catch(JSONException e){
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            }
        }

        return jsonArray;
    }




    public void InsertItem(String phone, String title, String price, String keywords, String description, String latitude, String longitude, String imageName){
        title = title.replace(" ", "%20");
        price = price.replace(" ", "%20");
        description = description.replace(" ", "%20");
        keywords = keywords.replace(", ", "%20");



        String url = theUrl + "createItem.php?Phone="+phone+"&Title="+title+"&Price="+price+"&Keywords="+keywords+"&Description="+description+"&Latitude="+latitude+"&Longitude="+longitude+"&Image="+imageName;

        //Get HttpResponse Object from url
        //Get HttpEntitiy from Http response object

        HttpEntity httpEntity = null;

        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            //there is no response so i think...
            httpClient.execute(httpGet);
            //HttpResponse httpResponse = httpClient.execute(httpGet);

            //httpEntity = httpResponse.getEntity();
        } catch(ClientProtocolException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public String InsertImage(String sourceFileUri){
        String url = theUrl + "upload.php";


        //upload my image hopefully...
        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

//        if (!sourceFile.isFile()) {
//
//            //dialog.dismiss();
//
//            Log.e("uploadFile", "Source File not exist :"+imagepath);
//
//            runOnUiThread(new Runnable() {
//                public void run() {
//                    messageText.setText("Source File not exist :"+ imagepath);
//                }
//            });
//
//            return 0;
//
//        }
//        else
//        {
        try {

            // open a URL connection to the Servlet
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            URL uploadUrl = new URL(url);

            // Open a HTTP  connection to  the URL
            conn = (HttpURLConnection) uploadUrl.openConnection();
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("uploaded_file", fileName);

            dos = new DataOutputStream(conn.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                    + fileName + "\"" + lineEnd);

            dos.writeBytes(lineEnd);

            // create a buffer of  maximum size
            bytesAvailable = fileInputStream.available();

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {

                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // Responses from the server (code and message)

            String serverResponseMessage = conn.getResponseMessage();




//            Log.i("uploadFile", "HTTP Response is : "
//                    + serverResponseMessage + ": " + serverResponseCode);

//            if(serverResponseCode == 200){
//
//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
//                                +" F:/wamp/wamp/www/uploads";
//                        messageText.setText(msg);
//                        Toast.makeText(MainActivity.this, "File Upload Complete.", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }

            //close the streams //
            fileInputStream.close();
            dos.flush();
            dos.close();
            System.out.println("######################################");
            int lastindex = fileName.lastIndexOf('/');

            return fileName.substring(lastindex+1);

        } catch (MalformedURLException ex) {

//            dialog.dismiss();
//            ex.printStackTrace();
//
//            runOnUiThread(new Runnable() {
//                public void run() {
//                    messageText.setText("MalformedURLException Exception : check script url.");
//                    Toast.makeText(MainActivity.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
//                }
//            });

            Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
        } catch (Exception e) {

//            dialog.dismiss();
//            e.printStackTrace();
//
//            runOnUiThread(new Runnable() {
//                public void run() {
//                    messageText.setText("Got Exception : see logcat ");
//                    Toast.makeText(MainActivity.this, "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
//                }
//            });

            Log.e("Upload file to server Exception", "Exception : "  + e.getMessage(), e);
        }
//        dialog.dismiss();
//        return serverResponseCode;

        // End else block
        return "";
    }



    public void InsertKeyword(String phone, String keyword){

        String url = theUrl+"createKeyword.php?Phone="+phone+"&Keyword="+keyword;

        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            httpClient.execute(httpGet);
        } catch(ClientProtocolException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void DeleteKeyword(String phone, String keyword){

        String url = theUrl+"deleteKeyword.php?Phone="+phone+"&Keyword="+keyword;

        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            httpClient.execute(httpGet);
        } catch(ClientProtocolException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public JSONArray FindKeyword(String searchKey){
        String finalKey = searchKey.replace(", ", "%20");
        String url =  theUrl +"searchKeyword.php?Search="+finalKey;

        //Get HttpResponse Object from url
        //Get HttpEntitiy from Http response object

        HttpEntity httpEntity = null;

        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpGet);

            httpEntity = httpResponse.getEntity();
        } catch(ClientProtocolException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }


        //Convert HttpEntity into JSON Array
        JSONArray jsonArray = null;

        if (httpEntity != null){
            try{
                String entityResponse = EntityUtils.toString(httpEntity);
                Log.e("Entity Response : ", entityResponse);

                jsonArray = new JSONArray(entityResponse);
            } catch(JSONException e){
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            }
        }

        return jsonArray;
    }

    public JSONArray FindMyKeywords(String phone){
        String url =  theUrl +"findMyKeywords.php?Phone="+phone;

        //Get HttpResponse Object from url
        //Get HttpEntitiy from Http response object

        HttpEntity httpEntity = null;

        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpGet);

            httpEntity = httpResponse.getEntity();
        } catch(ClientProtocolException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }


        //Convert HttpEntity into JSON Array
        JSONArray jsonArray = null;

        if (httpEntity != null){
            try{
                String entityResponse = EntityUtils.toString(httpEntity);
                Log.e("Entity Response : ", entityResponse);

                jsonArray = new JSONArray(entityResponse);
            } catch(JSONException e){
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            }
        }

        return jsonArray;
    }

    public void DeleteItem(String phone, String time){
        String finalTime = time.replace(" ", "%20");
        String url = theUrl +"deleteItem.php?PhoneNumber="+phone+"&Time="+finalTime;

        //Get HttpResponse Object from url
        //Get HttpEntitiy from Http response object

        HttpEntity httpEntity = null;

        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            //there is no response so i think...
            httpClient.execute(httpGet);
            //HttpResponse httpResponse = httpClient.execute(httpGet);

            //httpEntity = httpResponse.getEntity();
        } catch(ClientProtocolException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }


//
    }

    public Boolean uploadImageToServer(List<NameValuePair> params){
        String url = theUrl + "uploadImage.php";

        //Get HttpResponse Object from url
        //Get HttpEntitiy from Http response object

        HttpEntity httpEntity = null;

        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse httpResponse = httpClient.execute(httpPost);

            httpEntity = httpResponse.getEntity();

            String entityResponse = EntityUtils.toString(httpEntity);

            Log.e("Entity Response : ", entityResponse);
            return true;
        } catch(ClientProtocolException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
        return false;
    }



}
