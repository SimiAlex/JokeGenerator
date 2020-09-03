package simialex.com.github.jokegenerator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
{

    //fields
    private static final String PATH = "https://icanhazdadjoke.com";
    private TextView jokeTextView ;
    private Button jokeGeneratorButton ;
    private static final String NO_INTERNET_MESSAGE = "No internet connection";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jokeTextView = findViewById(R.id.textView);
        jokeGeneratorButton = findViewById(R.id.button);

        jokeGeneratorButton.setOnClickListener((v)-> onButtonClicked());
    }

    public void onButtonClicked()
    {
        Thread jokeThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {

                JSONObject myJoke = Utils.getJSON(PATH);
                final String processedJoke = Utils.processJSON(myJoke);
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if(processedJoke != null)
                        {
                            jokeTextView.setText(processedJoke);
                        }
                        else
                        {
                            jokeTextView.setText(NO_INTERNET_MESSAGE);
                        }
                    }
                });
            }
        });
        jokeThread.start();
    }
}