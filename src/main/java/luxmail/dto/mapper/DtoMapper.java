package luxmail.dto.mapper;

public interface DtoMapper<E, REQ, RES> {
    RES toDto(E entity);

    E fromDto(REQ dto);
}
