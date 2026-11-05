package br.edu.ibmec.ProjetoBrinquedos.repository;

import br.edu.ibmec.ProjetoBrinquedos.model.Brinquedo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrinquedoRepository extends JpaRepository<Brinquedo, Long>
{
}
