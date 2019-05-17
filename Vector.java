class Vector{

    double angulo, longitud;

    Vector(){
	this(0,1);
    }

    Vector(double angulo){
	this.angulo = angulo;
	this.longitud = 1;
	this.rota(0);
    }

    Vector(double ang, double tam){
	this.angulo = ang;
	this.longitud = tam;
	this.rota(0); 
    }

    public String toString(){
	String s = "El vector tiene una longitud " + longitud + ", con angulo " + angulo;
	return s;
    }

    public boolean equals(Vector u){
	return (this.angulo() == u.angulo());
    }

    protected Vector clone(){
	Vector u = new Vector(this.angulo,this.longitud);
	return u;
    }

    void rota(double ang){
	this.angulo += ang;
	if (this.angulo >= 2*Math.PI) {
	    this.angulo -= 2*Math.PI;
	    this.rota(0);
	}
    }

    void factor(double r){
	if (r >= 0) {
	    this.longitud *= r;
	}
	else {
	    double t = -r;
	    this.longitud *= t;
	    this.rota(Math.PI);
	}
    }

    double angulo(){
	return this.angulo;
    }

    double modulo(){
	return this.longitud;
    }
}