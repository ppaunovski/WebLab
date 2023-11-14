package mk.ukim.finki.mk.lab.repository;

import mk.ukim.finki.mk.lab.bootstrap.DataHolder;
import mk.ukim.finki.mk.lab.model.Production;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductionRepository {

    public List<Production> findAll(){
        return DataHolder.productions;
    }

    public Optional<Production> findById(int productionId) {
        return DataHolder.productions.stream().filter(p -> p.getId() == productionId).findFirst();
    }
}
