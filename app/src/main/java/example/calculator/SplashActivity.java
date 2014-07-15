package example.calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends Activity
{
    private static final String	TAG	= "SplashActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
        finish();
    }
}
