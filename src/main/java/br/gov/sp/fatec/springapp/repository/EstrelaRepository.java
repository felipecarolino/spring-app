package br.gov.sp.fatec.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.gov.sp.fatec.springapp.entity.Estrela;

public interface EstrelaRepository extends JpaRepository<Estrela, Long> {
	@Query("select e from Estrela e where e.id = ?1")
    public Estrela buscaEstrelaPorId (String id);

    @Query("select e from Estrela e where e.nome = ?1 and e.id = ?2")
    public Estrela buscaEstrelaPorNomeEId (String nome, long id);
}
