package com.trandung.elearn.service.mapper;

import com.trandung.elearn.domain.*;
import com.trandung.elearn.service.dto.WordDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Word} and its DTO {@link WordDTO}.
 */
@Mapper(componentModel = "spring", uses = {SessionMapper.class})
public interface WordMapper extends EntityMapper<WordDTO, Word> {

    @Mapping(source = "session.id", target = "sessionId")
    WordDTO toDto(Word word);

    @Mapping(source = "sessionId", target = "session")
    Word toEntity(WordDTO wordDTO);

    default Word fromId(Long id) {
        if (id == null) {
            return null;
        }
        Word word = new Word();
        word.setId(id);
        return word;
    }
}
