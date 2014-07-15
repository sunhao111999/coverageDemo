package example.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class HomeActivity extends Activity {

    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        final EditText firstValue = (EditText) findViewById(R.id.EditText01);
        final EditText secondValue = (EditText) findViewById(R.id.EditText02);

        result = (TextView)findViewById(R.id.TextView01);
        result.setText("0.00");

        Button calculate = (Button)findViewById(R.id.Button01);
        calculate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showResult(firstValue.getText(), secondValue.getText());
            }
        });
    }

    protected void showResult(Editable first, Editable second)
    {
        float num1 = Float.parseFloat(first.toString());
        float num2 = Float.parseFloat(second.toString());
        result.setText(String.valueOf(num1 * num2));
    }
}
