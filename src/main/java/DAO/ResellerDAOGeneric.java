package DAO;

import DTO.ResellerDTO;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ResellerDAOGeneric implements IDAO<ResellerDTO, Integer, ResellerDTO> {

    private static HashMap<Integer, ResellerDTO> resellers = new HashMap<>();

    static {
        resellers.put(1, new ResellerDTO(1, "Lyngby Planteskole", "Firskovvej 18", "33212334"));
        resellers.put(1, new ResellerDTO(2, "Glostrup Planter", "Tværvej 35", "32233232"));
        resellers.put(1, new ResellerDTO(3, "Holbæk Planteskole", "Stenhusvej 49", "59430945"));
    }

    @Override
    public List<ResellerDTO> getAll() {
        return resellers.entrySet().stream()
                .map(entry -> entry.getValue())
                .collect(Collectors.toList());
    }

    @Override
    public ResellerDTO getByID(Integer integer) {
        return resellers.get(integer);
    }

    @Override
    public ResellerDTO add(ResellerDTO resellerDTO) {
        resellers.put(resellerDTO.getId(), resellerDTO);
        return resellerDTO;
    }
}
