package com.example.fabricio.graffs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Nodo> nodes = new ArrayList<Nodo>();
    ArrayList<Arista> edges = new ArrayList<Arista>();
    ArrayList<Arista> edgesD = new ArrayList<Arista>();

    LinearLayout parent;
    TextView dialogo;
    int [][] mat;

    public Button bt1,bt2,bt3,bt4,bt5,bt6,bt7;
    public boolean nodo = false, arista = false,dirigido = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parent = (LinearLayout)findViewById(R.id.parent);
        View MyView = new MyView(this);
        parent.addView(MyView);

        // Creacion de botones
        bt1=(Button)findViewById(R.id.bt1);
        bt2=(Button)findViewById(R.id.bt2);
        bt3=(Button)findViewById(R.id.bt3);
        bt4=(Button)findViewById(R.id.bt4);
        bt5=(Button)findViewById(R.id.bt5);
        bt6=(Button)findViewById(R.id.bt6);
        bt7=(Button)findViewById(R.id.bt7);
    }

    public void buttonNode(View view) {
        if(!nodo) {
            nodo = true;
            arista = false;
            changeColor();
        }

        else {
            nodo = false;
            changeColor();
        }
    }

    public void buttonEdge(View view) {
        if(!arista) {
            arista = true;
            nodo = false;
            changeColor();
        }
        else {
            arista = false;
            changeColor();
        }
    }

    public void buttonClear(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        finish();
    }

    public void buttonMatrix(View view){

        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.custom_dialog);
        dialogo = (TextView) dialog.findViewById(R.id.dialog);
        mat = new int [nodes.size()+1][nodes.size()+1];
        String aux="Matriz Adyacente\n";

        for(int g=0;g < nodes.size();g++) {
            for(int h=0;h < nodes.size();h++) {
                for(int k=0;k < edges.size();k++) {
                    if(g == edges.get(k).getUno().getId()-1 && h == edges.get(k).getDos().getId()-1){
                        mat[g][h]= 1;
                        mat[h][g]= 1;
                    }
                }

                for(int k=0;k < edgesD.size();k++) {
                    if(g == edgesD.get(k).getUno().getId()-1 && h == edgesD.get(k).getDos().getId()-1){
                        mat[h][g]= 1;
                    }
                }
            }
        }

        for(int i=0;i<nodes.size();i++) {
            aux = aux+"\t\t"+nodes.get(i).getId();
        }
        aux=aux+"\n";
        for(int i=0;i<nodes.size();i++){
            aux = aux+"\t\t||";
        }
        for(int i=0;i<nodes.size();i++) {
            aux = aux+"\n\n\t\t";
            for(int j=0;j<nodes.size();j++)
            {
                aux = aux + mat[i][j] +"\t\t";
            }
            aux = aux +" = "+nodes.get(i).getId();
        }

        dialogo.setText(aux);
        dialog.show();
    }

    public void buttonRange(View view){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.custom_dialog);
        dialogo = (TextView) dialog.findViewById(R.id.dialog);
        int s=0,rango=0;
        for(int i=0;i < nodes.size();i++) {
            s=0;
            for(int j=0;j < nodes.size();j++) {
                if(mat[i][j]>0) {
                    s++;
                }
            }
            if(s>rango)
                rango=s;
        }
        dialogo.setTextSize(50);
        dialogo.setText(""+rango);
        dialog.show();

    }

    public void buttonDirigido(View view){
        dirigido = true;
        Log.d("dirigido",""+dirigido);
    }

    public void buttonNoDirigido(View view){
        dirigido = false;
        Log.d("dirigido",""+dirigido);
    }

    public void changeColor(){
        if(!nodo){
            bt1.setTextColor(Color.BLACK);
        }
        if(!arista){
            bt2.setTextColor(Color.BLACK);
        }
        if(nodo){
            bt1.setTextColor(Color.RED);
            bt2.setTextColor(Color.BLACK);
            if(bt6.getVisibility() == View.VISIBLE){
                bt6.setVisibility(View.INVISIBLE);
                bt7.setVisibility(View.INVISIBLE);
            }
        }
        if(arista){
            bt2.setTextColor(Color.RED);
            bt1.setTextColor(Color.BLACK);
            if(bt6.getVisibility() == View.INVISIBLE){
                bt6.setVisibility(View.VISIBLE);
                bt7.setVisibility(View.VISIBLE);
            }
        }
    }


    class MyView extends View {

        float _x;
        float _y;
        float _x1;
        float _x2;
        float _y1;
        float _y2;

        int circulo = -1;
        Paint paint;

        public MyView (Context context) {
            super(context);
            init();
        }

        public void init(){
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.LTGRAY);
            paint.setTextSize(60);
            paint.setStrokeWidth(20);

        }

        @Override
        public void onDraw(Canvas canvas) {
            int radio = 80;

            super.onDraw(canvas);
            canvas.drawPaint(paint);

            for(int i = 0; i<edges.size();i++){
                paint.setColor(Color.BLACK);
                canvas.drawLine(edges.get(i).getX1(),edges.get(i).getY1(),edges.get(i).getX2(),edges.get(i).getY2(),paint);
                paint.setColor(Color.LTGRAY);
            }

            for(int i = 0; i<edgesD.size();i++){
                paint.setColor(Color.BLACK);
                canvas.drawLine(edgesD.get(i).getX1(),edgesD.get(i).getY1(),(((((edgesD.get(i).getX2()-edgesD.get(i).getX1())/10)+edgesD.get(i).getX1())-edgesD.get(i).getX1())*7)+edgesD.get(i).getX1(),(((((edgesD.get(i).getY2()-edgesD.get(i).getY1())/10)+edgesD.get(i).getY1())-edgesD.get(i).getY1())*7)+edgesD.get(i).getY1(),paint);
                canvas.drawCircle((((((edgesD.get(i).getX2()-edgesD.get(i).getX1())/10)+edgesD.get(i).getX1())-edgesD.get(i).getX1())*7)+edgesD.get(i).getX1(),(((((edgesD.get(i).getY2()-edgesD.get(i).getY1())/10)+edgesD.get(i).getY1())-edgesD.get(i).getY1())*7)+edgesD.get(i).getY1(),20,paint);
                paint.setColor(Color.LTGRAY);
            }


            for(int i = 0; i < nodes.size();i++){
                paint.setColor(Color.parseColor(nodes.get(i).getColor()));
                canvas.drawCircle(nodes.get(i).getX(),nodes.get(i).getY(),radio,paint);
                paint.setColor(Color.WHITE);
                canvas.drawText(String.valueOf(nodes.get(i).getId()),nodes.get(i).getX()-15,nodes.get(i).getY()+15,paint);
                paint.setColor(Color.LTGRAY);
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent e) {
            _x = e.getX();
            _y = e.getY();
            switch (e.getAction()){
                case MotionEvent.ACTION_DOWN:
                    if(nodo){ createNode(_x,_y); invalidate(); }
                    else if(arista){
                        for(int i = 0; i < nodes.size();i++) {
                            double cenx = _x - nodes.get(i).getX();
                            double ceny = _y - nodes.get(i).getY();

                            nodes.get(i).setSelected(!nodes.get(i).getSelected());

                            float distancia = (float) Math.sqrt(cenx * cenx + ceny * ceny);

                            if (distancia <= 100) {
                                circulo = i;
                                if (nodes.get(i).getSelected()) {
                                    _x1 = nodes.get(i).getX();
                                    _y1 = nodes.get(i).getY();

                                } else if(!nodes.get(i).getSelected()){
                                    _x2 = nodes.get(i).getX();
                                    _y2 = nodes.get(i).getY();
                                    createAristaND(_x1,_x2,_y1,_y2);


                                }
                            }
                        }
                        invalidate();
                    }
                    break;
                default:
                    break;
            }
            return true;
        }

        public void createNode(float x, float y) {
            int id = nodes.size();
            Nodo a = new Nodo(x,y,id+1,"#CD5C5D");
            nodes.add(a);
        }

        public void createAristaND(float x1,float x2,float y1, float y2) {
            Arista a = null;
            Nodo _uno = null;
            Nodo _dos = null;

            for(int i = 0;i < nodes.size(); i++){
                if( x1 == nodes.get(i).getX() && y1 == nodes.get(i).getY()){
                    _uno = nodes.get(i);
                }
                if( x2 == nodes.get(i).getX() && y2 == nodes.get(i).getY()){
                    _dos = nodes.get(i);
                }
            }

            if(dirigido == false){
                a = new Arista(x1,y1,x2,y2,false,_uno,_dos);
                edges.add(a);
            }else if(dirigido == true){
                a = new Arista(x1,y1,x2,y2,true,_uno,_dos);
                edgesD.add(a);
            }
        }
    }
}
