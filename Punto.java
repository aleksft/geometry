class Punto extends FigGeo{

    double x, y;

    Punto(){
	x = 0;
	y = 0;
    }

    Punto(double u, double v){
	this.x = u;
	this.y = v;
    }

    public String toString(){
	String s = "El punto es: (" + x + ", " + y + ").";
	return s;
    }

    public boolean equals(Punto p){
	boolean igual;
	double x, y, p1, p2;

	x = this.coordenadaX();
	y = this.coordenadaY();
	p1 = p.coordenadaX();
	p2 = p.coordenadaY();
	igual = ((x == p1) & (y == p2));
	return igual;
    }

    protected Punto clone(){
	Punto q = new Punto(this.x,this.y);
	return q;
    }
    
    void traslada(double u, double v){
	this.x += u;
	this.y += v;
    }

    void rota(double angulo){
	double xOld = this.x;
	double yOld = this.y;

	this.x = xOld*Math.cos(angulo)-yOld*Math.sin(angulo);
	this.y = xOld*Math.sin(angulo)+yOld*Math.cos(angulo);
    }

    double distancia(FigGeo f){
	double d = 0;
	Punto p;
	
	if (f instanceof Punto) {
	    p = (Punto) f;
	    d = Math.sqrt(Math.pow(this.x-p.x,2) + Math.pow(this.y-p.y,2));
	}
	if(f instanceof Recta){Recta r = (Recta) f; d = r.distancia(this);}
	if(f instanceof Segmento){Segmento s = (Segmento) f; d = s.distancia(this);}
	if(f instanceof Circulo){Circulo c = (Circulo) f; d = c.distancia(this);}
	if(f instanceof Poligono){Poligono m = (Poligono) f; d = m.distancia(this);}
	return d;
    }

    double coordenadaX(){
	return this.x;
    }

    double coordenadaY(){
	return this.y;
    }
}