package sg.edu.rp.c346.mysecondbmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText edWe;
    EditText edHe;
    TextView tvCal;
    TextView tvDate;
    TextView tvInfo;
    Button btnCalculate;
    Button btnReset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edHe=findViewById(R.id.editText);
        edWe=findViewById(R.id.editText2);
        tvCal=findViewById(R.id.textView2);
        tvDate=findViewById(R.id.textView);
        btnCalculate=findViewById(R.id.button);
        btnReset=findViewById(R.id.button2);
        tvInfo=findViewById(R.id.textView3);
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                String oc;
                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);
                if((edWe.getText().toString().isEmpty())||(edHe.getText().toString().isEmpty())) {}
                else{
                    float fBmi = (Float.parseFloat(edWe.getText().toString())) / ((Float.parseFloat(edHe.getText().toString())) * (Float.parseFloat(edHe.getText().toString())));
                    // Code for the action
                    if (fBmi < 18.5) {
                        oc = "You are underweight";
                    } else if (fBmi < 25) {
                        oc = "Your BMI is normal";
                    } else if (fBmi < 30) {
                        oc = "You are overweight";
                    } else {
                        oc = "You are obese";
                    }

                    //Step1a Obtain an instance of the SharedPreferences
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

                    //step1b obtain an instance of the SharedPreference Editor
                    SharedPreferences.Editor prefEdit = pref.edit();
                    //step1c Add the key-value pair

                    prefEdit.putString("outcome", oc);
                    prefEdit.putString("date", datetime);
                    prefEdit.putFloat("bmi", fBmi);

                    //step1d: Call commit to save changes into sharedPreferences
                    prefEdit.commit();
                    edHe.setText("");
                    edWe.setText("");
                    tvCal.setText("Last Calculated BMI:" + fBmi);
                    tvDate.setText("Last Calculated Date:" + datetime);
                    tvInfo.setText(oc);

                }
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                // Code for the action

                //Step1a Obtain an instance of the SharedPreferences
                SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

                //step1b obtain an instance of the SharedPreference Editor
                SharedPreferences.Editor prefEdit=pref.edit();
                //step1c Add the key-value pair

                prefEdit.putString("outcome","");
                prefEdit.putString("date","");
                prefEdit.putFloat("bmi",0);

                //step1d: Call commit to save changes into sharedPreferences
                prefEdit.commit();
                edHe.setText("");
                edWe.setText("");
                tvCal.setText("Last Calculated BMI:");
                tvDate.setText("Last Calculated Date:");
                tvInfo.setText("");


            }
        });


    }
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String date1=prefs.getString("date"," ");
        String oc1=prefs.getString("outcome"," ");
        float bmi1=prefs.getFloat("bmi",Float.parseFloat("0"));

        if(bmi1>0){
            tvCal.setText("Last Calculated BMI:"+bmi1);
        }else{
            tvCal.setText("Last Calculated BMI:");
        }

        tvDate.setText("Last Calculated Date:"+date1);
        tvInfo.setText(oc1);
    }

}
