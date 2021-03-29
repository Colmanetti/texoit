package org.acme.dao;


import org.acme.models.Filmes;
import org.eclipse.microprofile.opentracing.Traced;

import javax.enterprise.context.RequestScoped;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Traced
@RequestScoped
public class FilmesDao {

	@PersistenceContext
	EntityManager em;

	@Transactional(rollbackOn = Exception.class)
	public void insertFilme(Filmes filmes)  {
		em.persist(filmes);
	}

	public List<Filmes> getListFilmes() {
		String nameQuery = "LISTAR_TODOS_OS_FILMES";
		TypedQuery<Filmes> query = em.createNamedQuery(nameQuery, Filmes.class);

		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return new ArrayList<>();
		}

	}
}
