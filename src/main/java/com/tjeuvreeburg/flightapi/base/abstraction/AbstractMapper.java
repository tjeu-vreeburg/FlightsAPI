package com.tjeuvreeburg.flightapi.base.abstraction;

import com.tjeuvreeburg.flightapi.base.interfaces.GenericDto;
import com.tjeuvreeburg.flightapi.base.interfaces.GenericEntity;

public abstract class AbstractMapper<E extends GenericEntity, D extends GenericDto> {

    public abstract E toEntity(D d);

    public abstract D toDto(E e);
}