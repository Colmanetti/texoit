package services;

import org.acme.dao.FilmesDao;
import org.acme.models.Filmes;
import org.eclipse.microprofile.opentracing.Traced;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.*;

@RequestScoped
@Traced
public class FilmesServices {

    @Inject
    FilmesDao filmesDao;

    public void getIntervalProducer() {
        List<Filmes> listaFilmes = filmesDao.getListFilmes();

        List<Filmes> listaFilmesVencedores = new ArrayList<>();
        for (Filmes filme : listaFilmes){
            if (filme.getVencedor().equalsIgnoreCase("S")){
                listaFilmesVencedores.add(filme);
            }
        }

        Map<String, List<Integer>> mapProdutoresAnos = new HashMap<>();
        for (Filmes filme : listaFilmesVencedores){
            List<Integer> listaAno = new ArrayList<>();
            for (Filmes filmes1 : listaFilmesVencedores){
                if (filme.getProdutores().equalsIgnoreCase(filmes1.getProdutores())){
                    listaAno.add(filmes1.getAno());
                }
            }
            if (!mapProdutoresAnos.containsKey(filme.getProdutores())){
                mapProdutoresAnos.put(filme.getProdutores() , listaAno);
            }
        }

        Map<String, List<Integer>> mapProdutoresComMinimoDoisPremios = new HashMap<>();
        for (Map.Entry<String, List<Integer>> item : mapProdutoresAnos.entrySet()){
            if(item.getValue().size() > 1){
                mapProdutoresComMinimoDoisPremios.put(item.getKey(), item.getValue());
            }
        }

        for (Map.Entry<String, List<Integer>> item : mapProdutoresComMinimoDoisPremios.entrySet()){
            String produtor = item.getKey();
            int diferencaAnosPremio = item.getValue().get(item.getValue().size() - 1) - item.getValue().get(0);

        }
    }
}
