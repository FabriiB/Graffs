package com.example.fabricio.graffs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // ARRAYS DE NODOS Y ARISTA ( SUJETO A CAMBIO )

    ArrayList<Nodo> nodes = new ArrayList<Nodo>();
    ArrayList<Arista> edges = new ArrayList<Arista>();
    ArrayList<Arista> edgesD = new ArrayList<Arista>();


    // LAYOUTS Y VIEW CREADOS

    LinearLayout parent;
    TextView dialogo;
    View MyView;
    Dialog dialog;


    // VARIABLES GLOBALES ( NO SE SI ESTO ESTA BIEN :V )

    // Matriz de adyacencia
    int [][] matrix;

    int selected;

    // Creacion de botones
    public Button bt1,bt2,bt3,bt4,bt5,bt6,bt7,bt8,bt9;

    // Boleanos para saber si un boton esa siendo usado o no
    public boolean  nodo,
                    arista,
                    crear,
                    seleccionar,
                    dirigido,
                    ndirigido;


    // PROCESOS DE INICIALIZACION DEL PROYECTO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        nodo = true;
        arista = false;
        crear = true;
        seleccionar = false;
        dirigido = false;
        ndirigido = true;

        selected = 0;

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
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

    // <---  FUNCIONES DE LOS BOTONES  --->

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
        matrix = new int [nodes.size()+1][nodes.size()+1];
        String aux="Matriz Adyacente\n";

        for(int g=0;g < nodes.size();g++) {
            for(int h=0;h < nodes.size();h++) {
                for(int k=0;k < edges.size();k++) {
                    if(g == edges.get(k).getUno().getId()-1 && h == edges.get(k).getDos().getId()-1){
                        matrix[g][h]= 1;
                        matrix[h][g]= 1;
                    }
                }

                for(int k=0;k < edgesD.size();k++) {
                    if(g == edgesD.get(k).getUno().getId()-1 && h == edgesD.get(k).getDos().getId()-1){
                        matrix[h][g]= 1;
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
                aux = aux + matrix[i][j] +"\t\t";
            }
            aux = aux +" = "+nodes.get(i).getId();
        }

        dialogo.setText(aux);
        dialog.show();
    }

    public void buttonRange(View view){
        dialogo = (TextView) dialog.findViewById(R.id.dialog);
        int s=0,rango=0;
        for(int i=0;i < nodes.size();i++) {
            s=0;
            for(int j=0;j < nodes.size();j++) {
                if(matrix[i][j]>0) {
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
        edges.clear();
        dirigido = true;
        MyView.invalidate();
    }

    public void buttonNoDirigido(View view){
        edgesD.clear();
        dirigido = false;
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
                paint.setColor(Color.WHITE);
            }

            for(int i = 0; i<edgesD.size();i++){
                double angulo = angule(edgesD.get(i).getX1(),edgesD.get(i).getY1(),edgesD.get(i).getX2(),edgesD.get(i).getY2());
                paint.setColor(Color.BLACK);
                canvas.drawLine(edgesD.get(i).getX1(),edgesD.get(i).getY1(),edgesD.get(i).getX2(),edgesD.get(i).getY2(),paint);
                drawTriangle(canvas,paint,(edgesD.get(i).getX2()+edgesD.get(i).getX1())/2,(edgesD.get(i).getY2()+edgesD.get(i).getY1())/2,angulo);
                paint.setColor(Color.WHITE);
            }


            for(int i = 0; i < nodes.size();i++){
                paint.setColor(Color.parseColor(nodes.get(i).getColor()));
                canvas.drawCircle(nodes.get(i).getX(),nodes.get(i).getY(),radio,paint);
                paint.setColor(Color.WHITE);
                canvas.drawText(String.valueOf(nodes.get(i).getId()),nodes.get(i).getX()-15,nodes.get(i).getY()+15,paint);
                paint.setColor(Color.WHITE);
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent e) {
            _x = e.getX();
            _y = e.getY();
            switch (e.getAction()){
                case MotionEvent.ACTION_DOWN:
                    if(nodo && crear){ createNode(_x,_y); invalidate(); }
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

        public void createNode(float x, float y) {
            int id = nodes.size();
            Nodo a = new Nodo(x,y,id+1,"#E53935");
            nodes.add(a);
        }

        public void createAristaND(float x1,float x2,float y1, float y2) {
            Arista a = null;
            Nodo _uno = null;
            Nodo _dos = null;
            selected = 0;

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

            for(int i = 0;i<nodes.size();i++){
                nodes.get(i).setSelected(false);
                nodes.get(i).setColor("#E53935");
            }
        }

        public void drawTriangle(Canvas canvas, Paint paint, float x, float y, double angulo) {

            paint.setColor(Color.BLACK);
            int halfWidth = 80 / 2;
            Path path = new Path();
            path.moveTo(x, y - halfWidth); // Top
            path.lineTo(x - halfWidth, y + halfWidth); // Bottom left
            path.lineTo(x + halfWidth, y + halfWidth); // Bottom right
            path.lineTo(x, y - halfWidth); // Back to Top
            path.close();

            canvas.save();
            canvas.rotate((float)angulo,x,y);

            canvas.drawPath(path, paint);
            canvas.restore();
        }
        // Funciones que asignaran a cada nodo su in, y su feed, al crear los nodos en su constructor su entrada y su feed
        // deben ser de valor 0 para poder empezar a hacer los recorridos y poder comparar.
        public void recorridoida()
        {
            try {
                int s=0;
                int s1=0;
                int id2=(-1);
                for(Arista x:edges)
                {
                    s=0;
                    s1=0;
                    for(Nodo a:nodes) {
                        if (x.getX1() == a.getX() && x.getY1() == a.getY()) {
                            s1=a.getStart();
                            continue;
                        } else if (x.getX2() == a.getX() && x.getY2() == a.getY()) {
                            id2=a.getId();
                            continue;
                        }
                    }
                    s+=s1;
                    s+=x.getPeso();
                    if(nodes.get(id2).getStart()<s) //Reemplaza ahora con el mayor siempre en el Start del nodo siguiente, para que asi
                                                    //siempre se quede en el start el mayor en caso de convergencia
                    {
                        nodes.get(id2).setStart(s);
                    }
                }
               nodes.get(nodes.size()-1).setFeed(nodes.get(nodes.size()-1).getStart());
            }
            catch(Exception e)
            {
                Toast.makeText(MainActivity.this, "Surgió un error", Toast.LENGTH_SHORT).show();
            }

        }
        public void recorridovuelta()
        {
            //ArrayLists que guardaran todos los nodos finales y de comienzo escaneados de la matriz
            ArrayList<Integer> finales=new ArrayList<Integer>();
            ArrayList<Integer> principios=new ArrayList<Integer>();
            try {
                int s=0;
                int s1=0,s2=0;
                int id2=(-1),id1=(-1);
                double x1=0,x2=0,y1=0,y2=0;
                int flag=0;
                boolean papu=false;
                for(int i=0;i<nodes.size();i++) //Comenzar a analizar la matriz para encontrar la fila donde tdo es 0, eso quiere decir que es un final de un camino
                {
                    flag = 0;
                    for (int j = 0; j < nodes.size(); j++) {
                        if (matrix[i][j] != 0) {
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 0) //Si encuentra una fila con 0 encuentra un final y recupera ese nodo (El id para comparar)
                    {
                        Nodo aux = nodes.get(i);
                        nodes.get(i).setFeed(nodes.get(i).getStart()); //Pone el Start Igual al Feed en este nodo extremo
                        finales.add(aux.getId());
                    }
                }
                int princol=0;
                for(int i=0;i<nodes.size();i++) //Ahora analiza la matriz para encontrar principios, los principios son los que tienen columna donde tod es 0
                {
                    flag=0;
                    if(matrix[i][princol]!=0);
                        flag=1;
                    if (flag == 0) //Si encuentra una columna con 0 encuentra un nodo de principio y recupera ese nodo (EL id para luego comparar)
                    {
                        Nodo aux = nodes.get(i);
                        principios.add(aux.getId());
                    }
                    princol++;
                }
                for(int idfin:finales) { //Ahora recorrera a todos los nodos finales que encontro por medio de id
                    papu=false; //Variable flag
                    for(Nodo yas:nodes) //Ciclo báscio para sacar los puntos del nodo final
                    {
                        if(yas.getId()==idfin) {
                            x2 = yas.getX();
                            y2 = yas.getY();
                        }
                    }
                    while(true) { //Ciclo infinito para recorrer hasta el punto de comienzo
                        for (Arista b : edges) //Empieza a buscar en las aristas a la que corresponde este nodo final en coordenadas
                        {
                            if (b.getX2() == x2 && b.getY2() == y2) { //Si encuentra la arista donde esta el nodo final ahora toma las coordenadas del siguiente punto
                                x1 = b.getX1();
                                y1 = b.getY1();
                                for (Nodo c : nodes) //Busca ahora en los nodos los correspondientes con la arista para aplicar el restar
                                {
                                    //if (!c.getSelected()) { //Reutilizando el atributo de selected a manera de flag, si ya fue seleccionado el nodo no se lo toma en cuenta
                                        if (c.getX() == x2 && c.getY() == y2) { //Aqui va a guardar ahora el nodo 2 (su id) comparando las coordenadas
                                            s2 = c.getId();
                                            s = c.getFeed(); //Para hacer la resta empezamos con lo que sea que esta en el campo de Feed del nodo 2
                                        } else if (c.getX() == x1 && c.getY() == y1) { //Aqui va a guardar el nodo 1 (su id( comparando las coordenadas
                                            s1 = c.getId();
                                            s = s - c.getStart(); //Luego de agarrar el feed del nodo 2 se resta a eso el Start del nodo 1
                                        }
                                    //}
                                }
                                nodes.get(s1).setSelected(true); //Ya trabajamos los 2 nodos entonces los marcamos
                                nodes.get(s2).setSelected(true);
                                s = s - matrix[s1][s2]; /*Al final ahora solo nos queda restar lo que sea que esta en la arista Nota:
                                                        Tambien esto puede escribirse como s-=b.getPeso() pero el valor del peso ya esta guardado en
                                                        la matriz asi que a mi comodidad lo acomode asi
                                                         */
                                if (s < 0)     //Si resultan negativos convierte 0 la wea qlia,
                                    s = 0;
                                if (s < b.getHolgura())
                                    b.setHolgura(s); //Si el feed es menor lo remplaza, para que asi al final siempre queden los
                                //feeds mas cortos en caso de convergencia
                            }
                            x2 = x1;
                            y2 = y1; //Recorre ahora el punto 2 a punto 1 para pasar al siguiente nodo conectado
                            for(int i:principios) //Verifica si el nodo siguiente (de vuelta) es un nodo principal
                            {
                                if(s1==i)
                                    papu=true; //De ser principal cambia la variable papu a true lo cual rompe el ciclo infinito
                            }
                            if(papu)
                                    break;
                        }
                    }
                }
            }
            catch(Exception e)
            {
                Toast.makeText(MainActivity.this, "Surgió un error", Toast.LENGTH_SHORT).show();
            }

        }
        public void pintarHolgura() //Ya para pintar las holguras o bueno hacerlas mas anchas papu V:
        {
            for(Arista aux:edges)
            {
                if(aux.getHolgura()==0)
                {
                    //Funciones para pintar o hacer mas algo no se yo frontend
                    //P.S Ochinchin Daisuki
                }
            }
        }

        public double angule(float x1, float y1, float x2, float y2){
            double angule = 0;
            // ayudaaaaaaaaaaa!!!!!!!!!!
            return angule;
        }

    }

    // <---  END VIEW  --->
}
