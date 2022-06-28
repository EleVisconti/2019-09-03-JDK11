package it.polito.tdp.food.model;

import java.util.Objects;

public class Adiacenza {
   String tipo1;
   String tipo2;
   int peso;
public Adiacenza(String tipo1, String tipo2, int peso) {
	super();
	this.tipo1 = tipo1;
	this.tipo2 = tipo2;
	this.peso = peso;
}
@Override
public int hashCode() {
	return Objects.hash(peso, tipo1, tipo2);
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Adiacenza other = (Adiacenza) obj;
	return peso == other.peso && Objects.equals(tipo1, other.tipo1) && Objects.equals(tipo2, other.tipo2);
}
public String getTipo1() {
	return tipo1;
}
public void setTipo1(String tipo1) {
	this.tipo1 = tipo1;
}
public String getTipo2() {
	return tipo2;
}
public void setTipo2(String tipo2) {
	this.tipo2 = tipo2;
}
public int getPeso() {
	return peso;
}
public void setPeso(int peso) {
	this.peso = peso;
}
   
   
}
