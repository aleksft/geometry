class Circulo extends Superficie{

    Punto c;
    double r;

    Circulo(){
	c = new Punto();
	r = 1;
    }

    Circulo(Punto p){
	c = p;
	r = 1;
    }

    Circulo(double radio){
	c = new Punto();
	r = radio;
    }

    Circulo(Punto p, double radio){
	c = p;
	r = radio;
    }

    public String toString(){
	String s = "Circulo de centro: "+c.toString()+" y radio: "+r;
	return s;
    }

    void traslada(double x, double y){
	Punto p = this.centro();
	p.traslada(x,y);
    }

    void rota(double angulo){
	Punto p = this.centro();
	p.rota(angulo);
    }

    double distancia(FigGeo f){
	double d = 0;

	if(f instanceof Punto){Punto p = (Punto) f; d = this.distancia(p);}
	if(f instanceof Recta){Recta r = (Recta) f; d = this.distancia(r);}
	if(f instanceof Segmento){Segmento s = (Segmento) f; d = this.distancia(s);}
	if(f instanceof Circulo){Circulo c = (Circulo) f; d = this.distancia(c);}
	if(f instanceof Poligono){Poligono p = (Poligono) f; d = p.distancia(this);}
	return d;
    }

    double area(){
	double a;
	double radio = this.radio();

	a = Math.PI*Math.pow(radio,2);
	return a;
    }

    double longitud(){
	double l;
	double radio = this.radio();

	l = 2*Math.PI*radio;
	return l;
    }

    Punto centro(){
	return this.c;
    }

    double radio(){
	return this.r;
    }

    String posicionPunto(Punto p){
	int posicion = this.posicionP(p);
	String s = "El punto ";

	if(posicion == -1){
	    s = s + "es interior a la circunferencia.";
	}
	if(posicion == 0){
	    s = s + "esta sobre la circunferencia.";
	}
	if(posicion == 1){
	    s = s + "es exterior a la circunferencia.";
	}
	return s;
    }

    int posicionP(Punto p){
	double x, y;
	double c1, c2;
	double posicion;
	Punto centro = this.centro();
	double radio = this.radio();
	int posrelativa = 2;

	x = p.coordenadaX();
	y = p.coordenadaY();
	c1 = centro.coordenadaX();
	c2 = centro.coordenadaY();
	posicion = Math.pow(x-c1,2) + Math.pow(y-c2,2);
	if(posicion < Math.pow(radio,2)){posrelativa = -1;}
	if(posicion == Math.pow(radio,2)){posrelativa = 0;}
	if(posicion > Math.pow(radio,2)){posrelativa = 1;}
	return posrelativa;
    }

    double distancia(Punto p){
	double d = -1;
	Punto centro = this.centro();
	double radio = this.radio();
	int posicion = posicionP(p);
	double dist = p.distancia(centro);

	if(posicion == -1){
	    d = 0;
	}
	if(posicion == 0){
	    d = 0;
	}
	if(posicion == 1){
	    d = dist - radio;
	}
	return d;
    }

    double distancia(Recta r){
	double d;
	Recta s;
	Punto c, p;

	c = this.centro();
	s = r.perpendicularPorP(c);
	p = r.interseccion(s);
	d = this.distancia(p);
	return d;
    }

    double distancia(Segmento s){
	double d;
	Recta r, t;
	Punto c, p;
	double d1, d2;
	Punto q1, q2;

	c = this.centro();
	r = s.rectaSobreSegmento();
	t = r.perpendicularPorP(c);
	p = r.interseccion(t);
	if(s.pertenece(p)){
	    d = this.distancia(p);
	}
	else{
	    q1 = s.puntoSegmento1();
	    q2 = s.puntoSegmento2();
	    d1 = this.distancia(q1);
	    d2 = this.distancia(q2);
	    d = Math.min(d1,d2);
	}
	return d;
    }

    double distancia(Circulo c){
	double d, dist;
	Punto p, q;
	double r1, r2, sumaradio;

	d = -1;
	p = this.centro();
	q = c.centro();
	r1 = this.radio();
	r2 = c.radio();
	dist = p.distancia(q);
	sumaradio = r1 + r2;
	if(dist <= sumaradio){
	    d = 0;
	}
	else{
	    d = dist - sumaradio;
	}
	return d;
    }

    Punto interseccion(Recta r){
	Punto p;
	double radio = this.radio();
	Punto c = this.centro();
	Punto q = r.puntoRecta();
	Vector u = r.direccion();
	double angulo = u.angulo();
	double a, b, d;
	double coorX, coorY;
	double landa, landa1;
	double c1, c2, c3;
	double x, y;

	a = u.modulo();
	coorX = c.coordenadaX() - q.coordenadaX();
	coorY = c.coordenadaY() - q.coordenadaY();
	b = 2*(Math.cos(angulo)*coorX + Math.sin(angulo)*coorY);
	c1 = Math.pow(q.coordenadaX(),2) + Math.pow(q.coordenadaY(),2);
	c2 = Math.pow(c.coordenadaX(),2) + Math.pow(c.coordenadaY(),2);
	c3 = q.coordenadaX()*c.coordenadaX() + q.coordenadaY()*c.coordenadaY();
	d = c1 + c2 - 2*c3 - Math.pow(radio,2);
	landa1 = b + Math.sqrt(Math.pow(b,2) - 4*a*d);
	landa = landa1/(2*a);
	x = q.coordenadaX() + landa*Math.cos(angulo);
	y = q.coordenadaY() + landa*Math.sin(angulo);
	p = new Punto(x,y);
	return p;
    }

    Punto interseccionInversa(Recta r){
	Punto p;
	double radio = this.radio();
	Punto c = this.centro();
	Punto q = r.puntoRecta();
	Vector u = r.direccion();
	double angulo = u.angulo();
	double a, b, d;
	double coorX, coorY;
	double landa, landa1;
	double c1, c2, c3;
	double x, y;

	a = u.modulo();
	coorX = c.coordenadaX() - q.coordenadaX();
	coorY = c.coordenadaY() - q.coordenadaY();
	b = 2*(Math.cos(angulo)*coorX + Math.sin(angulo)*coorY);
	c1 = Math.pow(q.coordenadaX(),2) + Math.pow(q.coordenadaY(),2);
	c2 = Math.pow(c.coordenadaX(),2) + Math.pow(c.coordenadaY(),2);
	c3 = q.coordenadaX()*c.coordenadaX() + q.coordenadaY()*c.coordenadaY();
	d = c1 + c2 - 2*c3 - Math.pow(radio,2);
	landa1 = b - Math.sqrt(Math.pow(b,2) - 4*a*d);
	landa = landa1/(2*a);
	x = q.coordenadaX() + landa*Math.cos(angulo);
	y = q.coordenadaY() + landa*Math.sin(angulo);
	p = new Punto(x,y);
	return p;
    }
}