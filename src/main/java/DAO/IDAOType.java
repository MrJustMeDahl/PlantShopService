package DAO;

import Exceptions.APIException;

import java.util.List;

public interface IDAOType<entity, type>{

    List<entity> getByType(type type) throws APIException;
}
