class Segmento extends FigGeo{

    Punto p, q;

    Segmento(){
	Punto p = new Punto();
	Punto q = new Punto(0,1);

	this.p = p;
	this.q = q;
    }

    Segmento(Punto p, Punto q){
	if(p.equals(q)){System.out.println("Los dos puntos son iguales.");}
	this.p = p;
	this.q = q;
    }

    public String toString(){
	String s = "El segmento tiene como extremos: "+p.toString()+" y "+q.toString()+".";
	return s;
    }

    void traslada(double x, double y){
	p.traslada(x,y);
	q.traslada(x,y);
    }

    void rota(double angulo){
	p.rota(angulo);
	q.rota(angulo);
    }

    double distancia(FigGeo f){
	double d = -1;

	if(f instanceof Punto){Punto p = (Punto) f; d = this.distancia(p);}
	if(f instanceof Recta){Recta r = (Recta) f; d = this.distancia(r);}
	if(f instanceof Segmento){Segmento s = (Segmento) f; d = this.distancia(s);}
	if(f instanceof Circulo){Circulo c = (Circulo) f; d = c.distancia(this);}
	if(f instanceof Poligono){Poligono m = (Poligono) f; d = m.distancia(this);}
	return d;
    }

    double distancia(Punto t){
	double d = -1;
	double modulo, coorX, coorY;
	double angulo;
	double dist1, dist2;
	Recta r, s;
	Vector u;
	Punto p, q, x;

	r = this.rectaSobreSegmento();
	s = r.perpendicularPorP(t);
	x = r.interseccion(s);
	if(this.pertenece(x)){
	    d = x.distancia(t);
	}
	else{
	    p = this.puntoSegmento1();
	    q = this.puntoSegmento2();
	    dist1 = t.distancia(p); 
	    dist2 = t.distancia(q);
	    d = Math.min(dist1,dist2);
	}
	return d;
    }

    double distancia(Recta r){
	double d = -1;
	double dist1, dist2;
	Punto p, q, t;
	Recta s;

	s = this.rectaSobreSegmento();
	if(!r.paralela(s)){
	    t = r.interseccion(s);
	}
	else{
	    t = new Punto();
	}
	if((!r.paralela(s))&(this.pertenece(t))){d = 0;}
	else{
	    p = this.puntoSegmento1();
	    q = this.puntoSegmento2();
	    dist1 = r.distancia(p);
	    dist2 = r.distancia(q);
	    d = Math.min(dist1,dist2);
	}
	return d;
    }

    double distancia(Segmento s){
	double d = -1;
	Punto p1, p2, q1, q2;
	double dist1, dist2, dist3, dist4;
	double min1, min2;

	if(this.interseccion(s)){
	    d = 0;
	}
	else{
	    p1 = this.puntoSegmento1();
	    p2 = this.puntoSegmento2();
	    q1 = s.puntoSegmento1();
	    q2 = s.puntoSegmento2();
	    dist1 = this.distancia(q1);
	    dist2 = this.distancia(q2);
	    dist3 = s.distancia(p1);
	    dist4 = s.distancia(p2);
	    min1 = Math.min(dist1,dist2);
	    min2 = Math.min(dist3,dist4);
	    d = Math.min(min1,min2);
	}
	return d;
    }

    Punto puntoSegmento1(){
	return this.p;
    }

    Punto puntoSegmento2(){
	return this.q;
    }

    boolean pertenece(Punto p){
	boolean esta;
	double d;
	Recta r;
	double dpunto1, dpunto2;
	double modulo;
	Punto q, t;

	modulo = this.modulo();
	q = this.puntoSegmento1();
	t = this.puntoSegmento2();
	dpunto1 = p.distancia(q);
	dpunto2 = p.distancia(t);
	r = this.rectaSobreSegmento();
	d = r.distancia(p);
	esta = ((d == 0)&(dpunto1 <= modulo)&(dpunto2 <= modulo));
	return esta;
    }

    boolean interseccion(Segmento s){
	boolean cortan;
	Recta r,t;
	Punto p;
	Punto p1, p2, q1, q2;
	Vector u, v;
	double ang1, ang2;
	double coorX1, coorX2, coorY1, coorY2;
	double modulo1, modulo2;

	r = this.rectaSobreSegmento();
	t = s.rectaSobreSegmento();
     	p = r.interseccion(t);
	cortan = ((this.pertenece(p))&(s.pertenece(p)));
	return cortan;
    }

    Recta rectaSobreSegmento(){
	Recta r;
	Punto p, q;
	double coorX, coorY;
	double modulo;
	double angulo;
	Vector u;

	p = this.puntoSegmento1();
	q = this.puntoSegmento2();
	coorX = p.coordenadaX() - q.coordenadaX();
	coorY = p.coordenadaY() - q.coordenadaY();
	modulo = Math.sqrt(Math.pow(coorX,2) + Math.pow(coorY,2));
	coorX = coorX/modulo;
	angulo = Math.acos(coorX);
	u = new Vector(angulo);
	r = new Recta(p,u);
	return r;
    }

    double modulo(){
	double longitud;
	Punto p, q;
	double coorX, coorY;

	p = this.puntoSegmento1();
	q = this.puntoSegmento2();
	longitud = p.distancia(q);
	return longitud;
    }
}