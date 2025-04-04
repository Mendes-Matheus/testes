package br.com.mendes.controle_gastos.entity.transferencia.repository;

import br.com.mendes.controle_gastos.entity.transferencia.model.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransferenciaRepository extends JpaRepository <Transferencia, Long> {
}
