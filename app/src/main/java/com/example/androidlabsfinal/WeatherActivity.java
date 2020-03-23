package com.example.androidlabsfinal;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherActivity extends AppCompatActivity {
    final static String ACTIVITY_NAME = "WeatherActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        //Set progress bar to visible.
        ProgressBar pBar = (ProgressBar)findViewById(R.id.weatherProgressBar);
        pBar.setVisibility(View.VISIBLE);

        ForecastQuery query = new ForecastQuery();
        query.execute();
    }




    public class ForecastQuery extends AsyncTask<String, Integer, String>{

        static final String MAIN_URL = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric";
        static final String ICON_BASE_URL = "http://openweathermap.org/img/w/";
        static final String UV_URL = "http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389";
        private String UV;
        private String minTemp;
        private String maxTemp;
        private String currentTemp;
        private String icon;
        private Bitmap weatherPic = null;

        //check if a file exists.
        public boolean fileExistance(String fname){

            File file = getBaseContext().getFileStreamPath(fname);
            if (file.exists()) {
                Log.i(ACTIVITY_NAME, "file " + fname + " exists");
            } else {
                Log.i(ACTIVITY_NAME, "file " + fname + " not found");
            }
            return file.exists(); }


        protected String doInBackground(String ... args)
        {
            int eventType;
            try {

                //create a URL object of what server to contact:
//            URL url = new URL(args[0]);

                URL url = new URL(MAIN_URL);


                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                InputStream response = urlConnection.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(response, "UTF-8");

                //start parsing the XML.
                //example tag of interest <temperature value="-3.11" min="-3.89" max="-2.78" unit="celsius"/>
                //<weather number="804" value="overcast clouds" icon="04d"/>
                eventType = xpp.getEventType();

                String icon = "";
                while(eventType != XmlPullParser.END_DOCUMENT)
                {


                    if(eventType == XmlPullParser.START_TAG)
                    {
                        //find a temperature tag.
                        if(xpp.getName().equals("temperature"))
                        {
                            //assign it to the attributes
                            this.currentTemp = xpp.getAttributeValue(null,    "value");
                            publishProgress(25);
                            this.minTemp = xpp.getAttributeValue(null, "min");
                            publishProgress(50);
                            this.maxTemp = xpp.getAttributeValue(null, "max");
                            publishProgress(75);
                        }
                        else if(xpp.getName().equals("weather"))
                        {
                            icon = xpp.getAttributeValue(null,    "icon");
                        }

                    }
                    eventType = xpp.next(); //move to the next xml event
                }


                //download the icon for the weather type, only if necessary.
                String iconFileName = icon + ".png";

                //download file case.
                if(!icon.equals("") & !fileExistance(iconFileName))
                {
                    String urlString = ICON_BASE_URL + icon + ".png";
                    URL bitmapURL = null;
                    try {
                        bitmapURL = new URL(urlString);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    HttpURLConnection connection = (HttpURLConnection) bitmapURL.openConnection();

                    connection.connect();

                    int responseCode = connection.getResponseCode();

                    if (responseCode == 200) {

                        weatherPic = BitmapFactory.decodeStream(connection.getInputStream());

                        //store the bitmap to local device with the filename.
                        FileOutputStream outputStream = openFileOutput( icon + ".png", Context.MODE_PRIVATE);
                        this.weatherPic.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                        outputStream.flush();
                        outputStream.close();
                    }
                }

                //pull from local case.
                if(!icon.equals("") & fileExistance(iconFileName)){
                    FileInputStream fis = null;
                    try { fis = openFileInput(iconFileName); }
                    catch (FileNotFoundException e) { e.printStackTrace(); }
                    this.weatherPic = BitmapFactory.decodeStream(fis);

                }
                publishProgress(100);


            }
            catch (Exception e)
            {

            }

            //Call for the UV
            try {
                URL uvURL = new URL(UV_URL);
                //open the connection
                HttpURLConnection urlConnection = null;
                urlConnection = (HttpURLConnection) uvURL.openConnection();
                InputStream response = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line=reader.readLine()) != null){
                    sb.append(line + "\n");
                }
                String result = sb.toString();

                JSONObject jObject = new JSONObject(result);
                this.UV = Double.toString(jObject.getDouble("value"));
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }





            return "Done";
        }



        public void onProgressUpdate(Integer ... args){
            ProgressBar pBar = (ProgressBar)findViewById(R.id.weatherProgressBar);
            pBar.setProgress(args[0]);
        }

        public void onPostExecute(String arg){
            ImageView wPic = (ImageView)findViewById(R.id.currentWeatherImg);
            TextView curTemp = (TextView)findViewById(R.id.currentTempTV);
            TextView mxTemp = (TextView)findViewById(R.id.maxTempTV);
            TextView mnTemp = (TextView)findViewById(R.id.minTempTV);
            TextView uvTemp = (TextView)findViewById(R.id.uvRatingTV);


            wPic.setImageBitmap(this.weatherPic);
            curTemp.setText(getString(R.string.currentTemp) + this.currentTemp);
            mxTemp.setText(getString(R.string.maxTemp) + this.maxTemp);
            mnTemp.setText(getString(R.string.minTemp) + this.minTemp);
            uvTemp.setText(getString(R.string.UV) + this.UV);

            //make the progress bar invisible.
            ProgressBar pBar = (ProgressBar)findViewById(R.id.weatherProgressBar);
            pBar.setVisibility(View.INVISIBLE);

        }
    }
}

