package com.example.fabricio.graffs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AssignActivity extends AppCompatActivity {
    // ARRAYS DE NODOS Y ARISTA ( SUJETO A CAMBIO )
    ArrayList<Nodo> nodes = new ArrayList<Nodo>();
    ArrayList<Nodo> nodesOr = new ArrayList<Nodo>();
    ArrayList<Nodo> nodesTar = new ArrayList<Nodo>();
    ArrayList<Arista> edges = new ArrayList<Arista>();
    ArrayList<Arista> edgesD = new ArrayList<Arista>();


    // LAYOUTS Y VIEW CREADOS

    LinearLayout parent;
    TextView dialogo;
    EditText editDialog;
    View MyView;
    Dialog dialog;
    private String result = "";


    // VARIABLES GLOBALES ( NO SE SI ESTO ESTA BIEN :V )

    // Matriz de adyacencia
    int [][] matrix;

    int selected;

    // Creacion de botones
    public Button bt1,bt2,bt3,bt4,bt5,bt6,bt7,bt8,bt9,btmax,btmin;

    // Boleanos para saber si un boton esa siendo usado o no
    public boolean  nodo,
            arista,
            crear,
            seleccionar,
            dirigido,
            ndirigido,
            nodoTar;


    // PROCESOS DE INICIALIZACION DEL PROYECTO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign);

        //  AGREGAMOS EL CUSTOM VIEW AL LINEARLAYOUT CREADO EN EL XML
        parent = (LinearLayout)findViewById(R.id.parent);
        MyView = new MyView(this);
        parent.addView(MyView);

        // Creacion de botones
        bt1=(Button)findViewById(R.id.bt1);
        bt2=(Button)findViewById(R.id.bt2);
        bt3=(Button)findViewById(R.id.bt3);
        bt4=(Button)findViewById(R.id.bt4);
        bt5=(Button)findViewById(R.id.bt5);
        bt6=(Button)findViewById(R.id.bt6);
        bt7=(Button)findViewById(R.id.bt7);
        bt8=(Button)findViewById(R.id.bt8);
        bt9=(Button)findViewById(R.id.bt9);
        btmax=(Button)findViewById(R.id.btmax);
        btmin=(Button)findViewById(R.id.btmin);

        nodo = true;
        arista = false;
        crear = true;
        seleccionar = false;
        dirigido = false;
        ndirigido = true;
        nodoTar = false;

        selected = 0;

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialogo = (TextView) dialog.findViewById(R.id.dialog);


        bt1.setTextColor(Color.DKGRAY);
        bt2.setTextColor(Color.WHITE);
        bt7.setVisibility(View.VISIBLE);
        bt6.setVisibility(View.VISIBLE);
        if(bt8.getVisibility() == View.VISIBLE){
            bt8.setVisibility(View.INVISIBLE);
            bt9.setVisibility(View.INVISIBLE);
        }
    }

    // <---  FUNCIONES DE LOS BOTONES  ---
    public void buttonMax(View view)
    {
        try
        {
            int alfa[][]=new int[nodesOr.size()][nodesTar.size()];
            int maycols[]=new int[nodesTar.size()];
            int alfamenass[][]=new int[nodesOr.size()][nodesTar.size()];
            for(int i=0;i<nodesTar.size();i++)
            {
                maycols[i]=(-1);
            }
            for(int i=0;i<nodesOr.size();i++) {
                for(int j=0;j<nodesTar.size();j++)
                {
                    if(maycols[i]<matrix[j][i])
                    {
                        maycols[i]=matrix[j][i];
                    }
                }
            }
            for(int i=0;i<nodesOr.size();i++)
            {
                for(int j=0;j<nodesTar.size();j++)
                {
                    alfa[j][i]=maycols[i];
                }
            }
            for(int i=0;i<nodesOr.size();i++)
            {
                for(int j=0;j<nodesTar.size();j++)
                {
                    alfamenass[i][j]=matrix[i][j]-alfa[i][j];
                }
            }
            int beta[][]=new int[nodesOr.size()][nodesTar.size()];
            int mayfilas[]=new int[nodesOr.size()];

            for(int i=0;i<nodesOr.size();i++) {
                mayfilas[i]=alfamenass[i][0];
                for(int j=0;j<nodesTar.size();j++)
                {
                    if(mayfilas[i]<alfamenass[i][j])
                    {
                        mayfilas[i]=alfamenass[i][j];
                    }
                }
            }
            for(int i=0;i<nodesOr.size();i++)
            {
                for(int j=0;j<nodesTar.size();j++)
                {
                    beta[i][j]=mayfilas[i];
                }
            }
            int todomenbeta[][] = new int[nodesOr.size()][nodesTar.size()];
            for(int i=0;i<nodesOr.size();i++)
            {
                for(int j=0;j<nodesTar.size();j++)
                {
                    todomenbeta[i][j]=alfamenass[i][j]-beta[i][j];
                }
            }
            String aux="";
            for(int i=0;i<nodesOr.size();i++)
            {
                aux=aux+"\n";
                for(int j=0;j<nodesTar.size();j++)
                {
                    aux=aux+todomenbeta[i][j]+"\t";
                }
            }
            Log.e("TodoMenBeta",""+aux);
            aux="";

        }
        catch(Exception e)
        {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void buttonNode(View view) {
        if(!nodo) {
            nodo = true;
            nodoTar=false;
            arista = false;
            changeColor();
        }

        else {
            nodo = false;
            changeColor();
        }
    }

    public void buttonTarget(View view) {
        if(!nodoTar) {
            nodoTar = true;
            nodo=false;
            arista = false;
            changeColor();
        }

        else {
            nodoTar = false;
            changeColor();
        }
    }

    public void buttonEdge(View view) {
        if(!arista) {
            arista = true;
            nodo = false;
            nodoTar = false;
            changeColor();
        }
        else {
            arista = false;
            changeColor();
        }
    }

    public void start(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        finish();
    }

    public void buttonMatrix(View view){
        String aux="Matriz Adyacente\n";
        matrix();

        for(int i=0;i<nodesOr.size();i++) {
            aux = aux+"\n\n\t\t";
            for(int j=0;j<nodesTar.size();j++)
            {
                aux = aux + matrix[i][j] +"\t\t";
            }
        }

        dialogo.setTextSize(30);
        dialogo.setText(aux);
        dialog.show();
    }


    public void buttonDirigido(View view){
        showChangeLangDialog();
        edges.clear();
        dirigido = true;
        MyView.invalidate();
    }


    public void buttonCreate(View view){
        crear = true;
        seleccionar = false;

    }

    public void buttonSelect(View view){
        crear = false;
        seleccionar = true;
    }

    public void changeColor(){
        if(!nodo){
            bt1.setTextColor(Color.WHITE);
        }
        if(!arista){
            bt2.setTextColor(Color.WHITE);
        }
        if(nodo){
            bt1.setTextColor(Color.DKGRAY);
            bt2.setTextColor(Color.WHITE);
            bt7.setVisibility(View.VISIBLE);
            bt6.setVisibility(View.VISIBLE);
            if(bt8.getVisibility() == View.VISIBLE){
                bt8.setVisibility(View.INVISIBLE);
                bt9.setVisibility(View.INVISIBLE);
            }
        }
        if(arista){
            bt2.setTextColor(Color.DKGRAY);
            bt1.setTextColor(Color.WHITE);
            bt8.setVisibility(View.VISIBLE);
            bt9.setVisibility(View.VISIBLE);
            if(bt6.getVisibility() == View.VISIBLE){
                bt6.setVisibility(View.INVISIBLE);
                bt7.setVisibility(View.INVISIBLE);
            }

        }
    }

    public void matrix(){
        matrix = new int [nodesOr.size()][nodesTar.size()];
        for(int g=0;g < nodes.size();g++) {
            for(int h=0;h < nodes.size();h++) {
                for(int k=0;k < edges.size();k++) {
                    if(g == edges.get(k).getUno().getId() && h == edges.get(k).getDos().getId()){
                        matrix[g][h]+= 1;
                        matrix[h][g]+= 1;
                    }
                }

                for(int k=0;k < edgesD.size();k++) {
                    if(g == edgesD.get(k).getUno().getId() && h == edgesD.get(k).getDos().getId()){
                        matrix[g][h] = edgesD.get(k).getPeso();
                    }
                }
            }
        }
    }

    public void showChangeLangDialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(parms);

        layout.setGravity(Gravity.CLIP_VERTICAL);
        layout.setPadding(2, 2, 2, 2);

        TextView tv = new TextView(this);
        tv.setText("Valor");
        tv.setPadding(40, 40, 40, 40);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(20);

        final EditText et = new EditText(this);

        TextView tv1 = new TextView(this);
        tv1.setText("Peso de la arista");

        LinearLayout.LayoutParams tv1Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tv1Params.bottomMargin = 5;
        layout.addView(tv1,tv1Params);
        layout.addView(et, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        alertDialogBuilder.setView(layout);
        // alertDialogBuilder.setMessage("Input Student ID");
        alertDialogBuilder.setCustomTitle(tv);
        alertDialogBuilder.setCancelable(false);

        // Setting Negative "Cancel" Button
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });

        // Setting Positive "OK" Button
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                result = et.getText().toString();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        try {
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // <---  END BOTONES  --->


    // <---  CUSTOM VIEW  --->

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
            paint.setColor(Color.WHITE);
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
                paint.setColor(Color.BLACK);
                canvas.drawText(String.valueOf(edges.get(i).getPeso()),(edges.get(i).getX2()+edges.get(i).getX1())/2,(edges.get(i).getY2()+edges.get(i).getY1())/2,paint);
                paint.setColor(Color.WHITE);
            }

            for(int i = 0; i<edgesD.size();i++){
                if(edgesD.get(i).getHolgura()==0)
                    paint.setColor(Color.YELLOW);
                else
                    paint.setColor(Color.BLACK);
                canvas.drawLine(edgesD.get(i).getX1(),edgesD.get(i).getY1(),edgesD.get(i).getX2(),edgesD.get(i).getY2(),paint);
                drawTriangle(canvas,paint,(edgesD.get(i).getX2()+edgesD.get(i).getX1())/2,(edgesD.get(i).getY2()+edgesD.get(i).getY1())/2,edgesD.get(i));
                paint.setColor(Color.RED);
                canvas.drawText(String.valueOf(edgesD.get(i).getPeso()),((edgesD.get(i).getX2()+edgesD.get(i).getX1())/2)-20,((edgesD.get(i).getY2()+edgesD.get(i).getY1())/2),paint);
                paint.setColor(Color.WHITE);
            }


            for(int i = 0; i < nodes.size();i++){
                int su=0;
                paint.setColor(Color.parseColor(nodes.get(i).getColor()));
                canvas.drawCircle(nodes.get(i).getX(),nodes.get(i).getY(),radio,paint);
                paint.setColor(Color.WHITE);
                if(nodes.get(i).getFeed()==999999)
                    su=0;
                else
                    su=nodes.get(i).getFeed();
                canvas.drawText(String.valueOf(nodes.get(i).getStart()+"|"+su),nodes.get(i).getX()-70,nodes.get(i).getY()+20,paint);
                paint.setColor(Color.WHITE);
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent e) {
            _x = e.getX();
            _y = e.getY();
            switch (e.getAction()){
                case MotionEvent.ACTION_DOWN:
                    if(nodo && crear){ createNode(_x,_y,true); invalidate(); }
                    else if(nodoTar && crear){ createNode(_x,_y,false); invalidate();}
                    else if(arista && ndirigido){
                        for(int i = 0; i < nodes.size();i++) {
                            double cenx = _x - nodes.get(i).getX();
                            double ceny = _y - nodes.get(i).getY();
                            float distancia = (float) Math.sqrt(cenx * cenx + ceny * ceny);
                            circulo = i;
                            if (distancia <= 100) {
                                if(selected == 0){
                                    if(nodes.get(i).getSelected()== false){
                                        selected++;
                                        nodes.get(i).setSelected(true);
                                        nodes.get(i).setColor("#0277BD");
                                        _x1 = nodes.get(i).getX();
                                        _y1 = nodes.get(i).getY();
                                    }else{
                                        selected--;
                                        nodes.get(i).setSelected(false);
                                        nodes.get(i).setColor("#E53935");
                                    }
                                }else if(selected == 1){
                                    if(nodes.get(i).getSelected()== false){
                                        selected++;
                                        nodes.get(i).setSelected(true);
                                        _x2 = nodes.get(i).getX();
                                        _y2 = nodes.get(i).getY();
                                        createAristaND(_x1,_x2,_y1,_y2);
                                    }
                                }
                            }
                        }
                    }
                    break;

                case MotionEvent.ACTION_MOVE:
                    if(nodo && seleccionar){
                        for(int i = 0; i < nodes.size();i++) {
                            double cenx = _x - nodes.get(i).getX();
                            double ceny = _y - nodes.get(i).getY();
                            float distancia = (float) Math.sqrt(cenx * cenx + ceny * ceny);
                            if (distancia <= 100) {
                                circulo = i;
                                nodes.get(i).setX(_x);
                                nodes.get(i).setY(_y);
                                if(!edges.isEmpty()){
                                    for(int j = 0;j<edges.size();j++){
                                        if(edges.get(j).getUno() == nodes.get(i)){
                                            edges.get(j).setX1(nodes.get(i).getX());
                                            edges.get(j).setY1(nodes.get(i).getY());

                                        }if(edges.get(j).getDos() == nodes.get(i)){
                                            edges.get(j).setX2(nodes.get(i).getX());
                                            edges.get(j).setY2(nodes.get(i).getY());
                                        }
                                    }
                                }
                                else if(!edgesD.isEmpty()){
                                    for(int j = 0;j<edgesD.size();j++){
                                        if(edgesD.get(j).getUno() == nodes.get(i)){
                                            edgesD.get(j).setX1(nodes.get(i).getX());
                                            edgesD.get(j).setY1(nodes.get(i).getY());

                                        }if(edgesD.get(j).getDos() == nodes.get(i)){
                                            edgesD.get(j).setX2(nodes.get(i).getX());
                                            edgesD.get(j).setY2(nodes.get(i).getY());
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    invalidate();
                default:
                    break;
            }
            return true;
        }

        public void createNode(float x, float y,boolean choose) {
            int id=0;
            if(choose)
                id = nodesOr.size();
            else
                id=nodesTar.size();
            Nodo a = new Nodo(x,y,id,"#E53935",0,999999);
            nodes.add(a);
            if(choose)
                nodesOr.add(a);
            else
                nodesTar.add(a);
        }

        public void createAristaND(float x1,float x2,float y1, float y2) {
            Arista a = null;
            Nodo _uno = null;
            Nodo _dos = null;
            selected = 0;
            int resultado = Integer.parseInt(result);


            for(int i = 0;i < nodes.size(); i++){
                if( x1 == nodes.get(i).getX() && y1 == nodes.get(i).getY()){
                    _uno = nodes.get(i);
                }
                if( x2 == nodes.get(i).getX() && y2 == nodes.get(i).getY()){
                    _dos = nodes.get(i);
                }
            }

            if(dirigido == false){
                a = new Arista(x1,y1,x2,y2,false,_uno,_dos,resultado,0);
                edges.add(a);
            }else if(dirigido == true){
                String cadena="";
                cadena=cadena+_uno.getId()+_dos.getId();
                Log.e("IdArista: ",""+cadena);
                int id=Integer.parseInt(cadena);
                a = new Arista(x1,y1,x2,y2,true,_uno,_dos,resultado,id);
                edgesD.add(a);
            }

            for(int i = 0;i<nodes.size();i++){
                nodes.get(i).setSelected(false);
                nodes.get(i).setColor("#E53935");
            }
        }

        public void drawTriangle(Canvas canvas, Paint paint, float x, float y, Arista angulo) {

            paint.setColor(Color.parseColor("#1976D2"));
            int halfWidth = 50;
            Path path = new Path();
            path.moveTo(x, y - halfWidth); // Top
            path.lineTo(x - halfWidth, y + halfWidth); // Bottom left
            path.lineTo(x + halfWidth, y + halfWidth); // Bottom right
            path.lineTo(x, y - halfWidth); // Back to Top
            path.close();

            canvas.save();
            canvas.rotate((float)angulo.pendiente(),x,y);

            canvas.drawPath(path, paint);
            canvas.restore();
        }

        public void drawRhombus(Canvas canvas, Paint paint, float x, float y, Arista angulo) {
            int halfWidth = 100;

            Path path = new Path();
            path.moveTo(x, y + halfWidth); // Top
            path.lineTo(x - halfWidth, y); // Left
            path.lineTo(x, y - halfWidth); // Bottom
            path.lineTo(x + halfWidth, y); // Right
            path.lineTo(x, y + halfWidth); // Back to Top
            path.close();

            canvas.save();
            canvas.rotate((float)angulo.pendiente(),x,y);

            canvas.drawPath(path, paint);
            canvas.restore();
        }

    }

    // <---  END VIEW  --->
}
