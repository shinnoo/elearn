package com.trandung.elearn.service.mapper;

import com.trandung.elearn.domain.*;
import com.trandung.elearn.service.dto.SessionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Session} and its DTO {@link SessionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SessionMapper extends EntityMapper<SessionDTO, Session> {


    @Mapping(target = "words", ignore = true)
    @Mapping(target = "removeWord", ignore = true)
    Session toEntity(SessionDTO sessionDTO);

    default Session fromId(Long id) {
        if (id == null) {
            return null;
        }
        Session session = new Session();
        session.setId(id);
        return session;
    }
}
