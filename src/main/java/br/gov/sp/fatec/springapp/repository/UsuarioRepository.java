package br.gov.sp.fatec.springapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.gov.sp.fatec.springapp.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	public Usuario findTop1ByNomeOrEmail(String nome, String email);
	
	//query method
    public List<Usuario> findByNomeContainsIgnoreCase(String nome );

    public Usuario findByNome(String nome);

    public Usuario findByNomeAndSenha(String nome, String senha);

    public List<Usuario> findByAutorizacoesNome(String autorizacao);

    //JPQL
    @Query("select u from Usuario u where u.nome = ?1 ")
    public Usuario buscaUsuarioPorNome (String nome);

    @Query("select u from Usuario u where u.nome = ?1 and u.senha = ?2")
    public Usuario buscaUsuarioPorNomeESenha (String nome, String senha);

    @Query("select u from Usuario u inner join u.autorizacoes a where a.nome = ?1")
    public List<Usuario> buscaPorNomeAutorizacao(String autorizacao);

    @Query("select u from Usuario u where u.email = ?1 ")
    public Usuario buscarUsuarioPorEmail (String email);

    @Query("select u from Usuario u inner join u.estrelas e where e.nome = ?1")
    public Usuario buscaPorNomeEstrela(String estrela);
}
