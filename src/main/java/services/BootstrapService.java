package services;

import io.quarkus.runtime.StartupEvent;
import org.acme.dao.FilmesDao;
import org.acme.models.Filmes;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@ApplicationScoped
public class BootstrapService {


    @Inject
    Logger LOG;

    @Inject
    FilmesDao filmesDao;

    void onStart(@Observes StartupEvent ev) throws Exception {

        LOG.info("CARREGANDO A BASE DE DADOS APARTIR DO ARQUIVO CSV");

        InputStream in = null;
        BufferedReader reader = null;
        InputStreamReader isr = null;

        in = BootstrapService.class.getResourceAsStream("/scripts/movielist.csv");
        isr = new InputStreamReader(in);
        reader = new BufferedReader(isr);
        String line;
        reader.readLine();

        int count = 0;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(";");
            int ano = Integer.parseInt(data[0]);
            String titulo = data[1];
            String estudio = data[2];
            String produtores = data[3];
            String vencedor;
            try {
                vencedor = data[4];
                if (vencedor.equals("yes")) {
                    vencedor = "S";
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                vencedor = "N";
            }
            if (produtores.contains("and") && produtores.contains(",")){
                String[] produtoresVirgula = produtores.split(",");
                for (String produtorVirgula : produtoresVirgula){
                    if (produtorVirgula.contains("and")){
                        String[] produtoresAnd = produtorVirgula.split("and");
                        for (String produtorAnd : produtoresAnd){
                            Filmes filmes = new Filmes();
                            filmes.setAno(ano);
                            filmes.setEstudio(estudio);
                            filmes.setProdutores(produtorAnd.trim());
                            filmes.setTitulo(titulo);
                            filmes.setVencedor(vencedor);
                            filmesDao.insertFilme(filmes);
                            count++;
                        }
                    }else{
                        Filmes filmes = new Filmes();
                        filmes.setAno(ano);
                        filmes.setEstudio(estudio);
                        filmes.setProdutores(produtorVirgula.trim());
                        filmes.setTitulo(titulo);
                        filmes.setVencedor(vencedor);
                        filmesDao.insertFilme(filmes);
                        count++;
                    }
                }

            }else {
                if (produtores.contains("and")){
                    String[] produtoresAnd = produtores.split("and");
                    for (String produtorAnd : produtoresAnd){
                        Filmes filmes = new Filmes();
                        filmes.setAno(ano);
                        filmes.setEstudio(estudio);
                        filmes.setProdutores(produtorAnd.trim());
                        filmes.setTitulo(titulo);
                        filmes.setVencedor(vencedor);
                        filmesDao.insertFilme(filmes);
                        count++;
                    }
                }else{
                    Filmes filmes = new Filmes();
                    filmes.setAno(ano);
                    filmes.setEstudio(estudio);
                    filmes.setProdutores(produtores.trim());
                    filmes.setTitulo(titulo);
                    filmes.setVencedor(vencedor);
                    filmesDao.insertFilme(filmes);
                    count++;
                }

            }

        }

        reader.close();

        LOG.info("FORAM INSERIDAS " + count + " LINHAS APARTIR DO ARQUIVO");
    }


}
