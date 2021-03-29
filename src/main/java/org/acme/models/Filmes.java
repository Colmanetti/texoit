package org.acme.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "FILMS", schema = "DB01TEX")
@NamedNativeQueries({ @NamedNativeQuery(name = "LISTAR_TODOS_OS_FILMES", query = "SELECT "
        + " id , ano , titulo , produtores , estudio , vencedor "
        + " FROM DB01TEX.FILMS"
        + " ORDER BY produtores, ano"
        + " ", resultClass = Filmes.class), })
public class Filmes implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ano")
    private int ano;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "estudio")
    private String estudio;

    @Column(name = "produtores")
    private String produtores;

    @Column
    private String vencedor;

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEstudio() {
        return estudio;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }

    public String getProdutores() {
        return produtores;
    }

    public void setProdutores(String produtores) {
        this.produtores = produtores;
    }

    public String getVencedor() {
        return vencedor;
    }

    public void setVencedor(String vencedor) {
        this.vencedor = vencedor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
