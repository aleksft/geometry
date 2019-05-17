class Programa{

    public static void main(String [] args){
	double d;
	Punto p, q, t;
	Vector u, v;
	Recta r, s;
	Segmento s1, s2, s3;
	Circulo c1, c2;
	Poligono p1, p2;
	p = new Punto();
	q = new Punto(1,0);
	t = new Punto(0,2);
	u = new Vector(Math.PI/2);
	v = new Vector();
	r = new Recta(p,u);
	s = new Recta(q,v);
	s1 = new Segmento(p,q);
	s2 = new Segmento(p,t);
	s3 = new Segmento(q,t);
	c1 = new Circulo(p);
	c2 = new Circulo(3);
	p1 = new Poligono(4);
	p2 = new Poligono(3,p,q);
	d = p.distancia(q);
	System.out.println("Distancia de "+p.toString()+" a "+q.toString()+" = "+d);
	d = t.distancia(r);
	System.out.println("Distancia de "+t.toString()+" a "+r.toString()+" = "+d);
	d = c1.distancia(s2);
	System.out.println("Distancia de "+c1.toString()+" a "+s2.toString()+" = "+d);
	d = p1.distancia(c2);
	System.out.println("Distancia de "+p1.toString()+" a "+c2.toString()+" = "+d);
	System.out.println("Recta r: "+r.toString());
	System.out.println("Recta s: "+s.toString());
	s.rota(Math.PI);
	System.out.println("Rotamos s con angulo pi: "+s.toString());
    }
}