class Poligono extends Superficie{

    int N;
    Punto centro;
    Punto vertice;
    Punto[] polig;

    Poligono(){
	Punto c = new Punto();
	Punto v = new Punto(1,0);

	this.N = 3;
	this.centro = c;
	this.vertice = v;
	this.polig = this.restoDeVertices(3,c,v);
    }

    Poligono(int n){
	Punto c = new Punto();
	Punto v = new Punto(1,0);

	this.N = n;
	this.centro = c;
	this.vertice = v;
	if(n >= 3){
	    this.polig = this.restoDeVertices(n,c,v);
	}
	else{System.out.println("No es un poligono");}
    }

    Poligono(int n, Punto c, Punto v){
	this.N = n;
	this.centro = c;
	this.vertice = v;
	if(n >= 3){
	    this.polig = this.restoDeVertices(n,c,v);
	}
	else{System.out.println("No es un poligono");}
    }

    public String toString(){
	String s;
	int n = numeroVertices();
	Punto[] poligono = conjuntoVertices();

	s = "El poligono tiene "+n+" vertices, que son:\n";
	for(int i=0; i<n; i++){
	    s = s + poligono[i].toString()+"\n";
	}
	return s;
    }

    Punto[] restoDeVertices(int n, Punto c, Punto v){
	Circulo c1;
	double constante = 2*Math.PI/n;
	Punto q;
	Vector u;
	Recta r;
	Punto[] vector = new Punto[n];

	c1 = this.circunscrito(c,v);
	u = this.direccion(c,v);
	r = new Recta(c,u);
	vector[0] = v;
	for(int i=1; i<n; i++){
	    r.rotaDesdeP(constante);
	    q = c1.interseccion(r);
	    if(!this.pertenece(q,vector,i)){
		vector[i] = q;
	    }
	    else{
		q = c1.interseccionInversa(r);
		vector[i] = q;
	    }
	}
	return vector;
    }

    Circulo circunscrito(Punto c, Punto v){
	Circulo c1;
	Segmento s = new Segmento(c,v);
	double modulo = s.modulo();

	c1 = new Circulo(c,modulo);
	return c1;
    }

    Vector direccion(Punto c, Punto v){
	Vector u;
	Segmento s = new Segmento(c,v);
	Recta r = s.rectaSobreSegmento();

	u = r.direccion();
	return u;
    }

    void traslada(double x, double y){
	centro.traslada(x,y);
	vertice.traslada(x,y);
	for(int i=0; i<N; i++){
	    polig[i].traslada(x,y);
	}
    }

    void rota(double angulo){
	centro.rota(angulo);
	vertice.rota(angulo);
	for(int i=0; i<N; i++){
	    polig[i].rota(angulo);
	}
    }

    double distancia(FigGeo f){
	double d = -1;
	if(f instanceof Punto){Punto p = (Punto) f; d = this.distancia(p);}
	if(f instanceof Recta){Recta r = (Recta) f; d = this.distancia(r);}
	if(f instanceof Segmento){Segmento s = (Segmento) f; d = this.distancia(s);}
	if(f instanceof Circulo){Circulo c = (Circulo) f; d = this.distancia(c);}
	if(f instanceof Poligono){Poligono p = (Poligono) f; d = this.distancia(p);}
	return d;
    }

    double area(){
	double a;
	Segmento[] poligono;
	int n;
	double area, areacirculo;
	Circulo c;
	Punto vertice, centro;

	a = 0;
	n = this.numeroVertices();
	poligono = this.segmentoPoligono();
	centro = this.centro();
	vertice = this.vertice();
	c = this.circunscrito(centro, vertice);
	areacirculo = c.area();
	area = this.areaSegmentoCircular(n,c);
	a = areacirculo - n*area;
	return a;
    }

    String posicionPunto(Punto p){
	int posicion = this.posicionP(p);
	String s = "El punto ";

	if(posicion == -1){
	    s = s + "es interior.";
	}
	if(posicion == 0){
	    s = s + "esta sobre el poligono.";
	}
	if(posicion == 1){
	    s = s + "esta fuera del poligono.";
	}
	return s;
    }

    int posicionP(Punto p){
	int pos = 2;
	int posrelativa;
	Punto c, v;
	Circulo c1;
	Segmento s;
	Segmento[] poligonos;
	double d = -1;
	int n, i;

	c = this.centro();
	v = this.vertice();
	c1 = this.circunscrito(c,v);
	posrelativa = c1.posicionP(p);
	if(posrelativa == 1){
	    pos = 1;
	}
	if(posrelativa == 0){
	    if(this.esVertice(p)){
		pos = 0;
	    }
	    else{
		pos = 1;
	    }
	}
	if(posrelativa == -1){
	    s = new Segmento(c,p);
	    n = this.numeroVertices();
	    poligonos = segmentoPoligono();
	    d = s.distancia(poligonos[0]);
	    i = 1;
	    while((d != 0)&(i<n)){
		d = s.distancia(poligonos[i]);
		i++;
	    }
	    if((i<=n)&(d == 0)){pos = 1;}
	    else{pos = -1;}	}
	else{
	    pos = 2;
	}
	return pos;
    }

    double distancia(Punto p){
	double d;
	int posicion;
	Segmento[] poligono;
	int n, i;

	posicion = this.posicionP(p);
	if(posicion != 1){d = 0;}
	else{
	    poligono = this.segmentoPoligono();
	    n = this.numeroVertices();
	    d = p.distancia(poligono[0]);
	    for(i=1; i<n; i++){
		if(d > p.distancia(poligono[i])){
		    d = p.distancia(poligono[i]);
		}
	    }
	}
	return d;
    }

    double distancia(Recta r){
	double d;
	int n;
	Segmento[] poligono;

	poligono = this.segmentoPoligono();
	n = this.numeroVertices();
	d = r.distancia(poligono[0]);
	for(int i=1; i<n; i++){
	    if(d > r.distancia(poligono[i])){d = r.distancia(poligono[i]);}
	}
	return d;
    }

    double distancia(Segmento s){
	double d;
	int n;
	Segmento[] poligono;
	Punto p, q;

	p = s.puntoSegmento1();
	q = s.puntoSegmento2();
	if((this.posicionP(p) != 1)|(this.posicionP(q) != 1)){d = 0;}
	else{
	    poligono = this.segmentoPoligono();
	    n = this.numeroVertices();
	    d = s.distancia(poligono[0]);
	    for(int i=0; i<n; i++){
		if(d > s.distancia(poligono[i])){d = s.distancia(poligono[i]);}
	    }
	}
	return d;
    }

    double distancia(Circulo c){
	double d;
	int n;
	Segmento[] poligono;

	poligono = this.segmentoPoligono();
	n = this.numeroVertices();
	d = c.distancia(poligono[0]);
	for(int i=0; i<n; i++){
	    if(d > c.distancia(poligono[i])){d = c.distancia(poligono[i]);}
	}
	return d;
    }

    double distancia(Poligono m){
	double d;
	int n;
	Segmento[] poligono;

	poligono = this.segmentoPoligono();
	n = this.numeroVertices();
	d = m.distancia(poligono[0]);
	for(int i=0; i<n; i++){
	    if(d > m.distancia(poligono[i])){d = m.distancia(poligono[i]);}
	}
	return d;
    }

    Punto centro (){
	return this.centro;
    }

    Punto vertice(){
	return this.vertice;
    }

    Punto[] conjuntoVertices(){
	return this.polig;
    }

    int numeroVertices(){
	return this.N;
    }

    boolean esVertice(Punto p){
	boolean vertice = false;
	Punto[] poligono = this.conjuntoVertices();
	int n = this.numeroVertices();
	int i = 0;

	while((!p.equals(poligono[i]))&(i<n-1)){i++;}
	if(i<n-1){vertice = true;}
	else{vertice = (p.equals(poligono[i]));}
	return vertice;
    }

    Segmento[] segmentoPoligono(){
	Punto[] poligono = this.conjuntoVertices();
	int n = this.numeroVertices();
	Segmento[] segmentosp = new Segmento[n];

	for(int i=0; i<n-1; i++){
	    segmentosp[i] = new Segmento(poligono[i],poligono[i+1]);
	}
	segmentosp[n-1] = new Segmento(poligono[n-1],poligono[0]);
	return segmentosp;
    }

    boolean pertenece(Punto p, Punto[] vector, int n){
	boolean esta = false;
	int i = 0;

	while((!p.equals(vector[i]))&(i<n-1)){i++;}
	if(i<n-1){esta = true;}
	else{esta = (p.equals(vector[n-1]));}
	return esta;
    }

    Segmento unSegmento(){
	Segmento lado;
	Segmento[] poligono;

	poligono = this.segmentoPoligono();
	lado = poligono[0];
	return lado;
    }

    double areaSegmentoCircular(int n, Circulo c){
	double area;
	double areaCirculo, areaSector, areaTriangulo;
	Segmento seglado;
	double altura, lado;
	double h1, h2;
	double radio, medioLado;

	areaCirculo = c.area();
	radio = c.radio();
	areaSector = areaCirculo/n;
	seglado = this.unSegmento();
	lado = seglado.modulo();
	medioLado = lado/2;
	h1 = radio + Math.sqrt(Math.pow(radio,2) - Math.pow(medioLado,2));
	h2 = radio - Math.sqrt(Math.pow(radio,2) - Math.pow(medioLado,2));
	if((0 < h1)&(h1 < radio)){
	    if((0 < h2)&(h2 < radio)){
		System.out.println("Error, 2 medidas validas");
		altura = -1;
	    }
	    else{
		altura = h1;
	    }
	}
	else{
	    if((0 < h2)&(h2 < radio)){
		altura = h2;
	    }
	    else{
		System.out.println("Error, ningun valor valido");
		altura = -1;
	    }
	}
	areaTriangulo = altura*lado/2;
	area = areaSector - areaTriangulo;
	return area;
    }
}