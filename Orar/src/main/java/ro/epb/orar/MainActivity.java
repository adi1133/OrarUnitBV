package ro.epb.orar;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView xlsPath = (TextView) findViewById(R.id.xlsPath);
        xlsPath.setText("test");

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Document doc = Jsoup.connect("http://www.unitbv.ro/iesc/Studenti/Orar.aspx").get();
                    final Elements orars = doc.select("a[href~=(?i)\\.xls$]:matches((?i)orar)");
                    String text = "";
                    for(Element element : orars)
                    {
                        String href =element.attr("abs:href");

                                URL url = new URL(href);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("HEAD");
                        con.connect();
                        long lastModified = con.getLastModified();
                        con.disconnect();
                        if (lastModified != 0) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeZone(TimeZone.getDefault());
                            calendar.setTimeInMillis(lastModified);
                            Log.i("Date",calendar.toString());
                        } else {
                            Log.i("Date","Last-Modified not returned");
                        }




                        String elemStr = href + " --- " + element.text();
                        Log.i("Element",elemStr);
                        text+=elemStr + '\n';

                    }
                    final String finalText = text;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            xlsPath.setText(finalText);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
