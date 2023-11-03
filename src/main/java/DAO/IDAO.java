package DAO;

import Exceptions.APIException;

import java.util.List;

public interface IDAO<DTO, ID, entity> {

    List<entity> getAll() throws APIException;
    entity getByID(ID id) throws APIException;
    entity add(DTO dto);
}
