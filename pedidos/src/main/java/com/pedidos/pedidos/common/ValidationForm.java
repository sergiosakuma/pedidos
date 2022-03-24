package com.pedidos.pedidos.common;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Clase para validar Bean
 */
public class ValidationForm {

    static private Logger logger = Logger.getLogger("ValidationForm");
    static private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    static public <T> Set<ConstraintViolation<T>> getViolations(T entity) {
        return factory.getValidator().validate(entity);
    }

    /**
     * Obtiene los mensajes de error de validacion
     *
     * @param entity entidad a validar
     * @param <T>
     * @return
     */
    static public <T> Map<String, Object> getViolationsMessage(T entity) {
        Set<ConstraintViolation<T>> constraintViolations = factory.getValidator().validate(entity);
        StringBuilder msg = new StringBuilder();

        for (ConstraintViolation<T> v : constraintViolations) {
            msg.append(v.getPropertyPath() + " = " + v.getInvalidValue() + "   error:" + v.getMessage() + "\n");
        }

        Map<String, Object> map = new HashMap<String, Object>();
        if (msg.length() == 0) {
            map.put("success", true);
        } else {
            logger.warning(("Lista de errores " + msg.toString()));
            map.put("success", false);
        }
        map.put("result", msg.toString());
        return map;
    }

    static public <T> Boolean isValid(T entity) {
        return factory.getValidator().validate(entity).size() <= 0;
    }

}
