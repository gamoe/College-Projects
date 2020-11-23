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
	
	void tradePosi��o ( int a, int b) {
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
	
	//fun��o7
	
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
		
	//Exerc�cio8
	
	//Condi��oSemT�tulo.Aux
	
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
					tradePosi��o(i,next-count-1);
				}
			}		
		}
	
	//8
	
	ClassOfObjectsGraph [] ordemAbeced�rio () {
		nullo(pilhagraficos);
		ClassOfObjectsGraph [] abeced�rioVec = new 	ClassOfObjectsGraph [next];
		for (int i=0; i!= next; i++) {
			abeced�rioVec[i]= pilhagraficos[i];
		}
		for (int j=0; j< abeced�rioVec.length; j++) {
			for (int p= j+1 ; p< abeced�rioVec.length; p++) {
				if (abeced�rioVec [p].characters() != null)
					if ( abeced�rioVec [p].characters().compareTo(abeced�rioVec[j].characters())<0) {
						tradePosi��o (j,p);
					}
			}
		}
		return pilhagraficos;
	}
	
	
	//Exerc�cio9
	//DefinirComprimentoDaNovaImagem.Aux
	
	int comprimentoNovo() {
		int comprimento=0;
		for (int i=0; i!= next; i++) {
			if (pilhagraficos[i].receberGr�fico().getWidth() > comprimento) {
				comprimento= pilhagraficos[i].receberGr�fico().getWidth();
			}
		}
		return comprimento;
	}
	
	//DefinirLarguraDaNovaImagem.Aux
	
	int larguraNovo() {
		int largura=0;
		for( int i =0; i!= next; i++) {
			if( pilhagraficos[i].receberGr�fico().getHeight() > largura) {
				largura= pilhagraficos[i].receberGr�fico().getHeight();
			}
		}
		return largura;
	}
	
	//9
	
	ColorImage sobreposi��o() {
		ColorImage p= new ColorImage (comprimentoNovo(), larguraNovo());

		for (int i= 0; i!=next; i++) {
			for (int a=0; a < pilhagraficos[i].receberGr�fico().getWidth(); a++) {
				for (int b=0; b < pilhagraficos[i].receberGr�fico().getHeight();b++) {
					if( pilhagraficos[i].receberGr�fico().getColor(a, b).getR()!=0 || pilhagraficos[i].receberGr�fico().getColor(a, b).getG()!=0 || pilhagraficos[i].receberGr�fico().getColor(a, b).getB()!=0) {
						p.setColor(a,b,pilhagraficos[i].receberGr�fico().getColor(a, b));
						}	
					}
				}	
			}
			return p;
	}

	//10
	ColorImage sobreposi��oRotation() {
		return manipula��oImagem.rota��o90(sobreposi��o());
		}
	}
