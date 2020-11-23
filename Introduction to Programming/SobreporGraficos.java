class SobreporGraficos {
	
	
	//Atributos
	ClassOfObjectsGraph [] pilhagraficos;
	ClassOfObjectsGraph grafico;
	static final int MAX=10;
	int next;
	
	//Construtores 
	
	//ArmazenarImagensNumaPilha
	SobreporGraficos(ClassOfObjectsGraph [] vec){
		pilhagraficos= new ClassOfObjectsGraph[MAX];
		next=0;
		for(int i=0; i<vec.length; i++) {
			if( vec[i]!= null) {
				pilhagraficos[i]=vec[i];
				next++;
			}
		}
		
	}
	
	SobreporGraficos (ClassOfObjectsGraph grafico){
		this.pilhagraficos[0]= grafico;
		next=0;
	}
	
	int getNext() {
		return next;
	}
	
	//Procedimentos
	
	//2
	 
	void AddGraphTopoPilha ( ClassOfObjectsGraph grafico) {
		pilhagraficos[next]= grafico;
		next++;
		if(next>pilhagraficos.length)
		throw new IllegalStateException("No Space Left");
	}
	
	//3
	void RemoveGraph() {
		pilhagraficos [next-1]= null;
		next--;
	}
	
	//4
	
	ClassOfObjectsGraph obterGrafico () {
		return pilhagraficos[next-1];
	}
	
	//5
	
	void addGrafico ( ClassOfObjectsGraph grafico, int x) {
		for(int i= next; i<=x; i--) {
			pilhagraficos[i+1]= pilhagraficos[i];
		}
		pilhagraficos[x]= grafico;
	}
	
	//6
	
	void tradePosição ( int a, int b) {
		ClassOfObjectsGraph c= pilhagraficos [a];
		pilhagraficos [a]= pilhagraficos[b];
		pilhagraficos [b]=c;
	}
	
	//7
	//contarSemTitulo.Aux
	
	int contarSemTitulo (ClassOfObjectsGraph [] pilhagraficos) {
		int count=0;
		for( int i= 0; i != next; i++) {
			if (pilhagraficos[i].isEmpty()) {
				count++;
			}
		}
		return count;
	}
	
	//função7
	
	public ClassOfObjectsGraph [] getGraficoNoTitle () {
		int j=0;
		int length= contarSemTitulo(pilhagraficos);
		ClassOfObjectsGraph [] v= new ClassOfObjectsGraph [length];
		for (int i= 0; i != next ; i++) {
			if (pilhagraficos[i].isEmpty()) {
				v[j]= pilhagraficos [i];
				j++;
			}
		}
		return v;
	}
		
	//Exercício8
	
	//CondiçãoSemTítulo.Aux
	
	boolean noTitle( ClassOfObjectsGraph grafico) {
		if(grafico.isEmpty()) {
			return true;
		}
		return false;
	}
	
	//MeterNulosFinal.Aux
	
	void nullo(ClassOfObjectsGraph [] pilhagraficos) {
		int count=0;
		
			for (int i=0; i!= next - contarSemTitulo(pilhagraficos);i++) {
					for(int j=next-1; j>0; j--) {
						if( noTitle(pilhagraficos[i])==true) {
						if( noTitle (pilhagraficos[j])==true)
							count++;
						
						else
							j=0;
					}
					tradePosição(i,next-count-1);
				}
			}		
		}
	
	//8
	
	ClassOfObjectsGraph [] ordemAbecedário () {
		nullo(pilhagraficos);
		ClassOfObjectsGraph [] abecedárioVec = new 	ClassOfObjectsGraph [next];
		for (int i=0; i!= next; i++) {
			abecedárioVec[i]= pilhagraficos[i];
		}
		for (int j=0; j< abecedárioVec.length; j++) {
			for (int p= j+1 ; p< abecedárioVec.length; p++) {
				if (abecedárioVec [p].characters() != null)
					if ( abecedárioVec [p].characters().compareTo(abecedárioVec[j].characters())<0) {
						tradePosição (j,p);
					}
			}
		}
		return pilhagraficos;
	}
	
	
	//Exercício9
	//DefinirComprimentoDaNovaImagem.Aux
	
	int comprimentoNovo() {
		int comprimento=0;
		for (int i=0; i!= next; i++) {
			if (pilhagraficos[i].receberGráfico().getWidth() > comprimento) {
				comprimento= pilhagraficos[i].receberGráfico().getWidth();
			}
		}
		return comprimento;
	}
	
	//DefinirLarguraDaNovaImagem.Aux
	
	int larguraNovo() {
		int largura=0;
		for( int i =0; i!= next; i++) {
			if( pilhagraficos[i].receberGráfico().getHeight() > largura) {
				largura= pilhagraficos[i].receberGráfico().getHeight();
			}
		}
		return largura;
	}
	
	//9
	
	ColorImage sobreposição() {
		ColorImage p= new ColorImage (comprimentoNovo(), larguraNovo());

		for (int i= 0; i!=next; i++) {
			for (int a=0; a < pilhagraficos[i].receberGráfico().getWidth(); a++) {
				for (int b=0; b < pilhagraficos[i].receberGráfico().getHeight();b++) {
					if( pilhagraficos[i].receberGráfico().getColor(a, b).getR()!=0 || pilhagraficos[i].receberGráfico().getColor(a, b).getG()!=0 || pilhagraficos[i].receberGráfico().getColor(a, b).getB()!=0) {
						p.setColor(a,b,pilhagraficos[i].receberGráfico().getColor(a, b));
						}	
					}
				}	
			}
			return p;
	}

	//10
	ColorImage sobreposiçãoRotation() {
		return manipulaçãoImagem.rotação90(sobreposição());
		}
	}
