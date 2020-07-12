package com.example.gameguess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements IButtonListener {

    TextView textView;
    SeekBar seekBar;
    public int numberGuess;
    int point = 0;
    TextView points1;
    int number;
    TextView Number50;

    final int win = 1;
    final int lose = -1;
    Animation animation;

    final String numberTrue = "Вы угадали число";
    final String numberFalse = "Вы не угадали число";

    ArrayList <Integer> halfNumbers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        points1 = findViewById(R.id.tv_pointer);
        textView = findViewById(R.id.tv_number);
        seekBar = findViewById(R.id.seek_bar);
        Number50 = findViewById(R.id.tv_half_numbers);
        registerForContextMenu(points1);
        animation = AnimationUtils.loadAnimation(this,R.anim.myalpha);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(String.valueOf(progress));
                numberGuess = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        switch (v.getId()){
            case R.id.tv_pointer:
        menu.add(0,1,0,"alpha");
        break;
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

   /* @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.myalpha);
        points1.startAnimation(animation);
        return super.onContextItemSelected(item);

    }*/

    public void check(String s, int point){
        Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
        this.point += point;
        points1.setText(String.valueOf(this.point));
    }

    @Override
    public void onGuessClick() {
        if (number == 0){
        Random r = new Random();
        number = r.nextInt(11);}
        if (number == numberGuess){
            points1.startAnimation(animation);
            check(numberTrue,win);
        } else {
            points1.startAnimation(animation);
            check(numberFalse,lose);
        }
        number = 0;
        halfNumbers.clear();
        Number50.setText("");
    }

    @Override
    public void Help() {
        Intent intent = new Intent();
        intent.setAction(intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(intent.EXTRA_TEXT, "Тут у меня просьба на миллион. Выбери любое число от 0 - 10.");
        startActivity(intent);
    }

    @Override
    public void Half() {
        HalfNumber();
        for (int i = 0; i < 1000 ; i++) {
            Random r = new Random();
            number = r.nextInt(11);
            if (halfNumbers.get(0) != number && halfNumbers.get(1) != number && halfNumbers.get(2) != number && halfNumbers.get(3) != number && halfNumbers.get(4) != number ){
                break;
            }
        }
        Number50.setText("!= " + halfNumbers.get(0)+ ";" + halfNumbers.get(1)+ ";" +halfNumbers.get(2)+ ";" +halfNumbers.get(3)+ ";" +halfNumbers.get(4));
    }

    public void HalfNumber () {
        for (int i = 0; i < 5 ; i++) {
            Random r1 = new Random();
            halfNumbers.add(r1.nextInt(11));
        }
        for (int i = 0; i <halfNumbers.size() ; i++) {
            for (int j = 0; j < halfNumbers.size() ; j++) {
                if (halfNumbers.get(i) == halfNumbers.get(j)){
                    Random r2 = new Random();
                    halfNumbers.set(j,r2.nextInt(11));
                }
            }
        }
    }
}