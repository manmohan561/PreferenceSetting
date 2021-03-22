package edu.nitt.cse.preferencesetting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText name,mail,mobile;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button storeinformation = (Button) findViewById(R.id.storeinformation);
        Button showinformation = (Button) findViewById(R.id.showinformation);
        name=findViewById(R.id.editTextTextPersonName);
        mail=findViewById(R.id.editTextTextPersonName2);
        mobile=findViewById(R.id.editTextTextPersonName3);
        textView = (TextView) findViewById(R.id.txtPrefs);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.storeinformation:
                        insertsharedpreference();
                        break;
                    case R.id.showinformation:
                        displaySharedPreferences();
                        break;
                    default:
                        break;
                }
            }
        };
        storeinformation.setOnClickListener(listener);
        showinformation.setOnClickListener(listener);
    }
    private void insertsharedpreference()
    {
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String s1=name.getText().toString();
        String s2=mail.getText().toString();
        String s3=mobile.getText().toString();
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("name",s1);
        editor.putString("mail",s2);
        editor.putString("mobile",s3);
        editor.commit();

    }

    private void displaySharedPreferences() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String username = prefs.getString("name", "Default NickName");
        String passw = prefs.getString("mail", "Default xyz@gmail.com");
        String mobile=prefs.getString("mobile","Default 100");


        StringBuilder builder = new StringBuilder();
        builder.append("Name: " + username + "\n");
        builder.append("Mail: " + passw + "\n");
        builder.append("Mobile: "+mobile);
        textView.setText(builder.toString());

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.help:
                Intent browsehelp=new Intent(Intent.ACTION_VIEW, Uri.parse("https://developer.android.com/studio/intro"));
                startActivity(browsehelp);
                break;
            case R.id.settings:
                startActivityForResult(new Intent(Settings.ACTION_SETTINGS),0);
            case R.id.exit:
                finish();
                break;
            case R.id.control:
                Intent i=new Intent(this,Preference.class);
                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}