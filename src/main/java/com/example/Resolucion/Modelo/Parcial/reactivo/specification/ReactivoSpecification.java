package com.example.Resolucion.Modelo.Parcial.reactivo.specification;

import com.example.Resolucion.Modelo.Parcial.reactivo.model.Reactivo;
import org.springframework.data.jpa.domain.Specification;

public class ReactivoSpecification {
    public static Specification<Reactivo> nombreContains(String nombre) {
        return (root, query, cb) -> nombre == null || nombre.isBlank()
                ? cb.conjunction()
                : cb.like(cb.lower(root.get("nombre")), "%" + nombre.toLowerCase() + "%");
    }

    public static Specification<Reactivo> nivelPeligroEquals(Integer nivelPeligro) {
        return (root, query, cb) -> nivelPeligro == null
                ? cb.conjunction()
                : cb.equal(root.get("nivelPeligro"), nivelPeligro);
    }

    public static Specification<Reactivo> esPrecursorEquals(Boolean esPrecursor) {
        return (root, query, cb) -> esPrecursor == null
                ? cb.conjunction()
                : cb.equal(root.get("esPrecursorQuimico"), esPrecursor);
    }
}
