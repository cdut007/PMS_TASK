package com.example.puree;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cookpad.puree.Puree;
import com.example.puree.logs.ClickLog;
import com.example.puree.logs.PvLog;
import com.example.puree.logs.plugins.OutBufferedDisplay;
import com.example.puree.logs.plugins.OutDisplay;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends ActionBarActivity {
    private TextView logDisplayTextView;
    private Button button1;
    private Button button2;

    private final OutDisplay.Callback outDisplayCallback = new OutDisplay.Callback() {
        @Override
        public void onEmit(JSONObject jsonLog) {
            preprendOutput(jsonLog.toString());
        }
    };

    private final OutBufferedDisplay.Callback outBufferedDisplayCallback = new OutBufferedDisplay.Callback() {
        @Override
        public void onEmit(JSONArray jsonLogs) {
            preprendOutput(jsonLogs.toString());
        }
    };

    private void preprendOutput(String text) {
        logDisplayTextView.setText(new StringBuilder()
                .append(text)
                .append(System.getProperty("line.separator"))
                .append(logDisplayTextView.getText())
                .toString());
    }

    private void clear() {
        Puree.clear();
        logDisplayTextView.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        OutDisplay.register(outDisplayCallback);
        OutBufferedDisplay.register(outBufferedDisplayCallback);
        Puree.send(new PvLog(this));
        setupViews();
    }

    @Override
    protected void onDestroy() {
        OutDisplay.unregister();
        OutBufferedDisplay.unregister();
        super.onDestroy();
    }

    private void findViews() {
        logDisplayTextView = (TextView) findViewById(R.id.log_display);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
    }

    private void setupViews() {
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Puree.send(new ClickLog("MainActivity", "BUTTON 1"));
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Puree.send(new ClickLog("MainActivity", "BUTTON 2"));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.action_dump:
                Puree.dump();
                return true;
            case R.id.action_clear:
                clear();
                return true;
            case R.id.action_settings:
                return true;
            default:
                // do nothing
        }
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
