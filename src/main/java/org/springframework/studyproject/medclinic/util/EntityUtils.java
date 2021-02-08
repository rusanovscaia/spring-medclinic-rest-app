package org.springframework.studyproject.medclinic.util;

import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.studyproject.medclinic.model.BaseEntity;

import java.util.Collection;

public abstract class EntityUtils {
    public static <T extends BaseEntity> T getById(Collection<T> entities, Class<T> entityClass, int entityId)
            throws ObjectRetrievalFailureException {
        for (T entity : entities) {
            if (entity.getId() == entityId && entityClass.isInstance(entity)) {
                return entity;
            }
        }
        throw new ObjectRetrievalFailureException(entityClass, entityId);
    }
}
