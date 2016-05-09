package rmuti.openweathermap;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.editText);
                String message = editText.getText().toString();

                try {
                    URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + message + "&APPID=6d8c3db06cd827052dee0e743082fdf1");
                    Scanner sc = new Scanner(url.openStream());
                    StringBuffer buf = new StringBuffer();
                    while (sc.hasNext()) {
                        buf.append(sc.next());
                    }
                    JSONObject jsonObject = new JSONObject(buf.toString());

                    JSONObject sysObj = jsonObject.getJSONObject("sys");
                    String country = sysObj.getString("country");

                    TextView countryText = (TextView) findViewById(R.id.country_text);
                    countryText.setText(country);

                    //pressure
                    JSONObject mainObj = jsonObject.getJSONObject("main");
                    String pressure = mainObj.getString("pressure");

                    TextView pressureText = (TextView) findViewById(R.id.pressure_text);
                    pressureText.setText(pressure);

                    //humidity
                    String humidity = mainObj.getString("humidity");

                    TextView humidityText = (TextView) findViewById(R.id.humidity_text);
                    humidityText.setText(humidity);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
