package com.example.gpa_salcedoj6_calculator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private TextView GPAText;
    private Button popupOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button submitBtn = (Button) findViewById(R.id.submitBtn);
        Button clearBtn = (Button) findViewById(R.id.clearBtn);

        EditText[] gpaEntry;
        gpaEntry = new EditText[5];

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int[] gpa;
                gpa = new int[5];

                boolean success = false;

                TextView GPAResults = (TextView) findViewById(R.id.gpaDisplay);
                TextView GPABackground = (TextView) findViewById(R.id.emptyBG);

                //Sets the EditText value to an array for easy processing
                gpaEntry[0] = (EditText) findViewById(R.id.gpaEntry1);
                gpaEntry[1] = (EditText) findViewById(R.id.gpaEntry2);
                gpaEntry[2] = (EditText) findViewById(R.id.gpaEntry3);
                gpaEntry[3] = (EditText) findViewById(R.id.gpaEntry4);
                gpaEntry[4] = (EditText) findViewById(R.id.gpaEntry5);

                if(submitBtn.getText().toString().equals(R.string.clearForm)){
                    submitBtn.setText(R.string.computeGPA);

                }

                for (int x = 0; x < 5; x++){
                    //checks to see if the text from the EditText array is empty
                    if (gpaEntry[x].getText().toString().equals("")){
                        //if empty report error in dialog popup, breaks loop
                        //success is false, will not calculate
                        success = false;
                        createPopup(getString(R.string.process_error));
                        break;
                    }else {
                        gpa[x] = Integer.parseInt(gpaEntry[x].getText().toString());
                        //checks to see if the string is out of bounds, if it is it will break the loop
                        if (gpa[x] < 0 || gpa[x] > 100){
                            //success is false, will not calculate
                            success = false;
                            createPopup(getString(R.string.outOfBounds));
                            break;
                        }
                        success = true;
                    }//if-else
                }//for loop

                //calculates
                if(success){
                    int totalGPA = 0;
                    for(int x = 0; x < 5; x++) {
                        totalGPA += gpa[x];
                    }

                    totalGPA /= 5;

                    if(totalGPA < 60){
                        GPABackground.setBackgroundResource(R.color.failed);
                        //change background to red
                    } else if (totalGPA < 80) {
                        GPABackground.setBackgroundResource(R.color.passing);
                        //change background to yellow
                    } else if (totalGPA < 100) {
                        GPABackground.setBackgroundResource(R.color.excellent);
                        //change background to green
                    }
                    GPAResults.setText(getString(R.string.results) + totalGPA);

                    submitBtn.setVisibility(View.INVISIBLE);
                    clearBtn.setVisibility(View.VISIBLE);

                }
            }//onClick
        });//setOnClickListener

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int x = 0; x < 5; x++) {
                    gpaEntry[x].setText("");
                }
                clearBtn.setVisibility(View.INVISIBLE);
                submitBtn.setVisibility(View.VISIBLE);
            }
        });

    }//onCreate

    public void createPopup(String RESULT){
        dialogBuilder = new AlertDialog.Builder(this);
        final View popupView = getLayoutInflater().inflate(R.layout.popup, null);

        GPAText = (TextView) popupView.findViewById(R.id.popupMsg);
        popupOK = (Button) popupView.findViewById(R.id.OKbtn);

        dialogBuilder.setView(popupView);
        dialog = dialogBuilder.create();
        dialog.show();

        GPAText.setText(RESULT);

        popupOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}