class Recta extends FigGeo{

    Punto p;
    Vector u;

    Recta(){
	p = new Punto();
	u = new Vector();
	this.p = p;
	this.u = u;
    }

    Recta(Punto q, Vector v){
	this.p = q;
	this.u = v;
    }

    public String toString(){
	String s = "Recta que pasa por el punto:\n"+p.toString()+"\n Con direccion:\n"+
	    u.toString();
	return s;
    }

    void traslada(double x, double y){
	p.traslada(x,y);
    }

    void rota(double angulo){
	p.rota(angulo);
	u.rota(angulo);
    }

    double distancia(FigGeo f){
	double d = 0;

	if(f instanceof Punto){Punto p = (Punto) f; d = this.distancia(p);}
	if(f instanceof Recta){Recta r = (Recta) f; d = this.distancia(r);}
	if(f instanceof Segmento){Segmento s = (Segmento) f; d = s.distancia(this);}
	if(f instanceof Circulo){Circulo c = (Circulo) f; d = c.distancia(this);}
	if(f instanceof Poligono){Poligono p = (Poligono) f; d = p.distancia(this);}
	return d = 0;
    }

    double distancia (Punto p){
	Punto q;
	double d, angulo;
	Vector u, v;
	Recta s;

	s = this.perpendicularPorP(p);
	q = this.interseccion(s);
	d = q.distancia(p);
	return d;
    }

    double distancia(Recta r){
	double d = 0;
	Punto p, q;

	if(this.paralela(r)){
	    Vector u = this.direccion();
	    double angulo = u.angulo();

	    if((angulo != Math.PI/2)&(angulo != 3*Math.PI/2)){
		p = this.corteOY();
		q = this.corteOY();
	    }
	    else{
		p = this.corteOX();
		q = this.corteOX();
	    }
	    d = p.distancia(q);
	}
	else{
	    d = 0;
	}
	return d;
    }

    boolean paralela(Recta r){
	Vector x, y, y1, y2;

	x = this.direccion();
	y = r.direccion();
	y1 = y.clone();
	y1.rota(Math.PI);
	y2 = y.clone();
	y2.rota(2*Math.PI);
	return (x.equals(y)|x.equals(y1)|x.equals(y2));
    }

    Vector direccion(){
	return this.u;
    }

    Punto puntoRecta(){
	return this.p;
    }

    Punto corteOX(){
	Punto p, q;
	double x, y, t, angulo;
	Vector u = this.direccion();

	q = this.p;
	angulo = u.angulo();
	x = q.coordenadaX();
	y = q.coordenadaY();
	t = Math.cos(angulo)/Math.sin(angulo);
	p = new Punto(x-y*t,0);
	return p;
    }

    Punto corteOY(){
	Punto p, q;
	double x, y, t, angulo;
	Vector u = this.direccion();

	q = this.p;
	angulo = u.angulo();
	x = q.coordenadaX();
	y = q.coordenadaY();
	t = Math.sin(angulo)/Math.cos(angulo);
	p = new Punto(0,y-x*t);
	return p;
    }

    Punto interseccion(Recta r){
	Punto p, q, t;
	Vector u, v;
	double mu;
	double ang1, ang2;
	double x, y;
	boolean rparal;
	double coorX, coorY;

	rparal = this.paralela(r);
	if(rparal){
	    t = new Punto(0, 99999999);
	}
	else{
	    u = this.direccion();
	    v = r.direccion();
	    ang1 = u.angulo();
	    ang2 = v.angulo();
	    p = this.puntoRecta();
	    q = r.puntoRecta();
	    coorX = q.coordenadaX() - p.coordenadaX();
	    coorY = q.coordenadaY() - p.coordenadaY();
	    if((ang1+ang2 != 0)&(ang1+ang2 != Math.PI)&(ang1+ang2 != 2*Math.PI)){;
		mu = (coorX*Math.sin(ang2)+coorY*Math.cos(ang2))/Math.sin(ang1+ang2);
	    }
	    else{
		mu = (coorY*Math.cos(ang1) - coorX*Math.sin(ang1))/Math.sin(2*ang1);
	    }
	    x = p.coordenadaX() + mu*Math.cos(ang1);
	    y = p.coordenadaY() + mu*Math.sin(ang1);
	    t = new Punto(x,y);
	}
	return t;
    }

    Recta perpendicularPorP(Punto p){
	Recta r;
	double ang1, ang2;
	Vector u, v;

	u = this.direccion();
	ang1 = u.angulo();
	ang2 = ang1 + Math.PI/2;
	v = new Vector(ang2);
	r = new Recta(p,v);
	return r;
    }

    void rotaDesdeP(double constante){
	Vector v;

	v = this.direccion();
	v.rota(constante);
    }
}