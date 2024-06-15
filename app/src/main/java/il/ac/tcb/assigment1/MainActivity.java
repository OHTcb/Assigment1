package il.ac.tcb.assigment1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText editTextNumber1, editTextNumber2;
    private TextView res;
    private Button calc;
    private Spinner spinner;

    private String operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNumber1 = findViewById(R.id.editTextNumber1);
        editTextNumber2 = findViewById(R.id.editTextNumber2);
        res = findViewById(R.id.res);
        calc = findViewById(R.id.calc);
        spinner = findViewById(R.id.spinner);

        setupSpinner();
        setupCalcButton();
    }

    private void setupSpinner() {
        ArrayList<String> operatorsList = new ArrayList<>();
        operatorsList.add("+");
        operatorsList.add("-");
        operatorsList.add("*");
        operatorsList.add("/");
        operatorsList.add("^");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, operatorsList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                operator = operatorsList.get(position);
                Toast.makeText(MainActivity.this, operator, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void setupCalcButton() {
        calc.setOnClickListener(v -> calculate());
    }

    private void calculate() {
        double num1 = Double.parseDouble(editTextNumber1.getText().toString());
        double num2 = Double.parseDouble(editTextNumber2.getText().toString());

        try {
            double result = performOperation(num1, num2);
            res.setText(String.format("%.2f", result));

        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show(); // Use MainActivity.this for context
        }
    }

    private double performOperation(double num1, double num2) throws ArithmeticException {
        switch (operator) {
            case "+": return num1 + num2;
            case "-": return num1 - num2;
            case "*": return num1 * num2;
            case "/":
                if (num2 == 0) throw new ArithmeticException("Division by zero");
                return num1 / num2;
            case "^": return Math.pow(num1, num2);
            default:
                throw new IllegalArgumentException("Invalid operator");
        }
    }
}
