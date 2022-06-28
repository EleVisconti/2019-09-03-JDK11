package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;


public class Model {
	FoodDao dao;
	private Graph<String, DefaultWeightedEdge> grafo;
	private List<String> vertici;
	private List<Adiacenza> adiacenze;
	private double pesoMax ;
	private List<String> camminoMax ;
	
	
	public Model() {
		 dao = new FoodDao(); 
	}
	
	public List<String> getTipoPorzione(){
	  return this.dao.getTipoPorzione();
	}

	public void creaGrafo(int cal) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.vertici = new ArrayList<String>(this.dao.getVertex(cal));
		Graphs.addAllVertices(this.grafo, this.vertici);
		
		adiacenze = new ArrayList<Adiacenza>(this.dao.getArchi());
		for (Adiacenza a : adiacenze) {
			if (this.grafo.vertexSet().contains(a.getTipo1()) && 
					this.grafo.vertexSet().contains(a.getTipo2())) {
				Graphs.addEdge(this.grafo, a.getTipo1(), a.getTipo2(), a.getPeso());
			}
		}

	}
	
	public String getDirette(String porzione) {
		String s="";
		List<String> vicini = Graphs.neighborListOf(this.grafo, porzione) ;
		//List<PorzioneAdiacente> result = new ArrayList<>();
		for(String v: vicini) {
			double peso = this.grafo.getEdgeWeight(this.grafo.getEdge(porzione, v)) ;
			s+="\n"+v+" "+peso;
		}
		return s;
		
	}
	
	public List<String> getVertici() {
		return vertici;
	}

	public void setVertici(List<String> vertici) {
		this.vertici = vertici;
	}

	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public void cercaCammino(String partenza, int N) {
		this.camminoMax = null ;
		this.pesoMax = 0.0 ;
		
		List<String> parziale = new ArrayList<>() ;
		parziale.add(partenza) ;
		
		search(parziale, 1, N);
	}
	
	private void search(List<String> parziale, int livello, int N) {
		
		if(livello == N+1) {
			double peso = pesoCammino(parziale) ;
			if(peso>this.pesoMax) {
				this.pesoMax=peso ;
				this.camminoMax = new ArrayList<>(parziale);
			}
			return ;
		}
		
		List<String> vicini = Graphs.neighborListOf(this.grafo, parziale.get(livello-1)) ;
		for(String v : vicini) {
			if(!parziale.contains(v)) {
				parziale.add(v) ;
				search(parziale, livello+1, N) ;
				parziale.remove(parziale.size()-1) ;
			}
		}
	}

	private double pesoCammino(List<String> parziale) {
		double peso = 0.0 ;
		for(int i=1; i<parziale.size(); i++) {
			double p = this.grafo.getEdgeWeight(this.grafo.getEdge(parziale.get(i-1), parziale.get(i))) ;
			peso += p ;
		}
		return peso ;
	}
	
	public double getPesoMax() {
		return pesoMax;
	}

	public List<String> getCamminoMax() {
		return camminoMax;
	}
	
	
	
	
}
