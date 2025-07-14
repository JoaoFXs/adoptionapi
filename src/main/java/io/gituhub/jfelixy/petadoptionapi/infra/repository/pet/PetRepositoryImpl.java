package io.gituhub.jfelixy.petadoptionapi.infra.repository.pet;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;


/** Marca a classe como parte da camada de persistência,
 * facilitando o tratamento de exceções relacionadas a banco e
 * tornando-a elegível para injeção pelo Spring.**/
@Repository
public class PetRepositoryImpl implements PetRepositoryCustom{

    /** @PersistenceContext: Injeta o EntityManager, necessário para montar queries dinâmicas em
     * tempo de execução (diferente do que usamos com @Query em interfaces).**/
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object> findByDistinctValue(String fieldValue) {


        /** Definindo valores permitidos, boa práticas para evitar erros **/
        List<Object> valuesAllow = List.of(
                "age",
                "type",
                "breed",
                "sex",
                "size",
                "temperament",
                "location"
        );

        if(!valuesAllow.contains(fieldValue)){
            throw new IllegalArgumentException("Invalid field: " + fieldValue);
        }

        if(fieldValue.equalsIgnoreCase("location")){
            return entityManager.createQuery("SELECT DISTINCT CONCAT(p.address, '/ ', p.city, '/ ', p.province, '/ ', p.cep) FROM Pet p", Object.class).getResultList();

        }
        return entityManager.createQuery("SELECT DISTINCT "+ fieldValue + " FROM Pet p", Object.class).getResultList();
    }

}
