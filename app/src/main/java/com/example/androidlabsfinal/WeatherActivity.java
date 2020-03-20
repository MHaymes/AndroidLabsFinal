package com.example.androidlabsfinal;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherActivity extends AppCompatActivity {

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
        private String UV;
        private String minTemp;
        private String maxTemp;
        private String currentTemp;
        private String icon;
        private Bitmap weatherPic = null;




        protected String doInBackground(String ... args)
        {
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
                int eventType = xpp.getEventType();

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

                //download the icon for the weather type.
                if(!icon.equals(""))
                {
                    String urlString = "http://openweathermap.org/img/w/" + icon + ".png";
                    try {
                        URL url2 = new URL(urlString);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.connect();

                    int responseCode = connection.getResponseCode();

                    if (responseCode == 200) {
                        this.weatherPic = BitmapFactory.decodeStream(connection.getInputStream());

                        //store the bitmap to local device with the filename.
                        FileOutputStream outputStream = openFileOutput( icon + ".png", Context.MODE_PRIVATE);
                        this.weatherPic.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                        outputStream.flush();
                        outputStream.close();
                        publishProgress(100);

                    }
                }
            }
            catch (Exception e)
            {

            }
            //TODO:  Add the UV call as well.  URL is : http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389





            return "Done";
        }



        public void onProgressUpdate(Integer ... args){
            ProgressBar pBar = (ProgressBar)findViewById(R.id.weatherProgressBar);
            pBar.setProgress(args[0]);
        }


    }
}

