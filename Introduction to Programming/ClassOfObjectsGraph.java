class ClassOfObjectsGraph {
	
	//Atributos
	
	 ColorImage grafico;
	 String titulo;
	 String titulox;
	 String tituloy;
	
	
	//Construtores
	
	ClassOfObjectsGraph (String titulo, String titulox, String tituloy, ColorImage grafico){
		this.grafico= grafico;
		this.titulo= titulo;
		this.titulox= titulox;
		this.tituloy= tituloy;
					
	}
	ClassOfObjectsGraph (ColorImage grafico){
		this.grafico=grafico;
	}
	
	//Procedimentos
	
	//1
	void newTitleOrModify ( String a) {
		this.titulo=a;
	}
	
	//2
	void TitleOfxOrModify ( String b) {
		this.titulox=b;
	}
	
	//3
	
	void TitleOfyOrModify (String c) {
		this.tituloy= c;
	}
	
	
	//4
	
	void transparency () {
		for (int x=0 ; x < grafico.getWidth(); x++) {
			for ( int y=0 ; y < grafico.getHeight(); y++) {
				if ((x+y)%2 != 0) {
					grafico.setColor(x, y , new Color(0,0,0));
				}
			}
		}
	}
	
	//5
	
	ColorImage receberGráfico () {
		return this.grafico;
	}
	
	//6
	
	String characters () {
		return this.titulo;
	}
	String charactersx () {
		return this.titulox;
	}
	String charactersy () {
		return this.tituloy;
	}
	String receberInfoTextual () {
		return characters()+ " " + charactersx()+ " " + charactersy();
	}
	
	boolean isEmpty() {
		return this.titulo==null;
	}
}
