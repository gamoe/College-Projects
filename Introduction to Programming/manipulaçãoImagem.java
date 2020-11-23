class manipulaçãoImagem {

	//PONTO 1
		//FunçãoVecValueMax.Aux
		static int valorMaximo ( int [] v) {
			int i=0;
			int M=0;
			while (i < v.length) {
				if ( v[i] > M) {
					M= v[i];
				}
				i++;
			}
			return M;
		}

		//1

		 static ColorImage grafico2D(int []v, int x, int y, Color a) {
			int n= v.length;
			int height= valorMaximo(v);
			int width= n*x+(n+1)*y;
			ColorImage c= new ColorImage (width, height);
				for (int j= 0, tamanho= y; j<=v.length-1; tamanho= tamanho+y+x,j++) {
					for (int f= tamanho; f< tamanho + x; f++) {
						for (int l= height-v[j]; l< height; l++) {
						c.setColor(f,l,a);
					}
				}
			}
			return c;
		}
			
		

		//TESTE I

		 static ColorImage teste(){
			int [] v= {100,200,300,400};
			Color a= new Color (255,0,0);
			ColorImage g= grafico2D(v,50,50,a);
			return g;			
		}	
		
		 //PONTO 2
		//gradiente.Aux

		 static Color auxGradiente(Color c, int gradiente) {
			 int red= Math.round(c.getR()/(gradiente+1));
			 int green= Math.round(c.getG()/(gradiente+1));
			 int blue= Math.round(c.getB()/(gradiente+1));
			 
			 Color p = new Color(red, green, blue);
			 
			 return p;
			 
		 }
		 
		 //gradiente1.Aux
		 
		 static Color [] gradiente(Color c, int gradiente) {
			 
			 Color [] grad= new Color [gradiente+1];
			 for (int i=0; i!= grad.length; i++) {
				 for (int j=1; j!= i+2; j++) {
					 
					 Color p=  auxGradiente(c , gradiente);
					 
					 int red= p.getR()*j;
					 int green= p.getG()*j;
					 int blue= p.getB()*j;
					 
					 if(p.getR()*j>255) {
						 red=255;
					 }
					 if (p.getG()*j>255) {
						 green=255;
					 }
					 if (p.getB()*j>255) {
						 blue=255;
					 }
					 
					 Color k= new Color (red,green,blue);
					 grad[i]=k;
				 }
			 }
			 return grad;
		 }
		 
		 
		 //2
		 
		 static ColorImage grafico2Dgradiente(int []v, int x, int y, Color a, int grad) {
				int n= v.length;
				int height= valorMaximo(v);
				int width= n*x+(n+1)*y;
				ColorImage c= new ColorImage (width, height);
				
				for (int i=0; i!= v.length; i++) {
					int g= (i+1)*y+i*x;
					for( int j= 1; j!= grad+1; j++ ) {
				
					Color []p= gradiente(a, grad);
					
					for (int q=0; q!= j; q++) {
						for( int w= g+j; w!= g+x-j; w++ ) {
							for (int e= c.getHeight()-v[i]+j; e!= c.getHeight(); e++) {
								c.setColor(w, e, p[q]);
								}
							}
						}
					}
				}
				
				return c;
		 }
				
		 
		 //Teste II
		 
		 static void testeIIdef () {
			 int [] v= {100,200,300};
			 Color a= new Color (0,0,255);
			 ColorImage f= grafico2Dgradiente( v, 50, 50, a, 16);
			 return;
		 }

		//PONTO 3
		
		//pintarCirculos.Aux
		
		static void paintCircles( ColorImage img, int x1, int y1, int raio, Color f) {
			for (int x= x1-raio; x<= x1+raio; x++) {
				for( int y= y1+raio; y>= y1-raio & y >=0; y--) {
					if(((x-x1)*(x-x1))+ ((y-y1)*(y-y1)) <= raio*raio) {
						img.setColor(x,y,f);
						
					}
				}
			}
			return;
		}
		
		//3
		
		static ColorImage graficoDispersão (int [] v, int espaço, int raio, Color a) {
			int n= v.length;
			int height= valorMaximo(v);
			int width= 2*n*raio+ (n+1)*espaço;
			ColorImage c= new ColorImage( width, height);
			for (int i= 0; i<v.length; i++) {
				paintCircles(c, espaço+raio+(espaço+2*raio)*i, valorMaximo(v)- v[i]-1,raio,a);
			}
			return c;
		}
		
		//TESTE III
		
		static ColorImage testeIII() {
			int []v= {50,75,100,150,300};
			Color a= new Color (0,255,0);
			ColorImage g= graficoDispersão( v, 10 ,8 ,a);
			return g;
		}

		
		//PONTO 4
		
		public static ColorImage rotação90 (ColorImage c) {
			ColorImage newimg= new ColorImage(c.getHeight(), c.getWidth());
			for (int i= newimg.getWidth()-1; i>=0; i--) {
				for(int j=0; j< newimg.getHeight(); j++) {
					newimg.setColor(i, j, c.getColor(j, newimg.getWidth()-i-1));
				}
			}
		return newimg;
		}

		//TESTE IV
		
		static ColorImage testeIV() {
			int []v= {50,75,100,150,300};
			Color a= new Color (255,0,0);
			ColorImage g= grafico2D(v,50,50,a);
			ColorImage b= rotação90(g);
			return b;
		}

		//TESTE CLASS2
		
		static void testeClass2() {
			int []v= {100,200,300,400};
			Color g= new Color (255,0,0);
			ClassOfObjectsGraph b= new ClassOfObjectsGraph( "", "", "",grafico2D(v,50,50,g));
			return;
		}
		
		//TESTE CLASS3
		
		static void testeClasse3() {
			Color red= new Color (255,0,0);
			Color green= new Color (0,255,0);
			int [] v= {100,200,300,400};
			ClassOfObjectsGraph a = new ClassOfObjectsGraph(grafico2D(v,30,40,red));
			ClassOfObjectsGraph b = new ClassOfObjectsGraph(grafico2D(v,30,40,red));
			ClassOfObjectsGraph c = new ClassOfObjectsGraph(grafico2D(v,30,40,red));
			ClassOfObjectsGraph d = new ClassOfObjectsGraph(graficoDispersão(v,10,8,green));
			ClassOfObjectsGraph [] pilhagraficos= {b,c,d};
			
			SobreporGraficos e= new SobreporGraficos(pilhagraficos);
			e.AddGraphTopoPilha(a);
			e.addGrafico(a, 2);
			return;
		}	
}