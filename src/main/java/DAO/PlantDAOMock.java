package DAO;

import DTO.PlantDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlantDAOMock implements IDAO<PlantDTO, Integer, PlantDTO>, IDAOType<PlantDTO, String>{

    private static HashMap<Integer, PlantDTO> plants = new HashMap<>();

    private static int nextID = 0;

    static {
        plants.put(nextID++, new PlantDTO(nextID, "Rose", "Albertine", 400, 199.50));
        plants.put(nextID++, new PlantDTO(nextID, "Bush", "Aronia", 200, 169.50));
        plants.put(nextID++, new PlantDTO(nextID, "FruitAndBerries", "AromaApple", 350, 399.50));
        plants.put(nextID++, new PlantDTO(nextID, "Rhododendron", "Astrid", 40, 269.50));
        plants.put(nextID++, new PlantDTO(nextID, "Rose", "The DarkLady", 100, 199.50));
    }

    public List<PlantDTO> getAll(){
        return new ArrayList<>(plants.values().stream().toList());
    }

    public PlantDTO getByID(Integer id){
        return plants.get(id);
    }

    public List<PlantDTO> getByType(String type){
        List<PlantDTO> result = new ArrayList<>();
        for(Map.Entry<Integer, PlantDTO> entry: plants.entrySet()){
            if(entry.getValue().getType().equalsIgnoreCase(type)){
                result.add(entry.getValue());
            }
        }
        return result;
    }

    public PlantDTO add(PlantDTO DTO){
        plants.put(DTO.getID(), DTO);
        return DTO;
    }

    public List<PlantDTO> getPlantsLowerThanHeight(int height){
        return plants.entrySet().stream()
                .filter(plant -> plant.getValue().getMaxHeight() <= height)
                .map(entry -> entry.getValue())
                .collect(Collectors.toList());
    }

    public List<String> getPlantNames(){
        return plants.entrySet().stream()
                .map(entry -> entry.getValue().getName())
                .collect(Collectors.toList());
    }

    public List<PlantDTO> sortPlantsByName(){
        return plants.entrySet().stream()
                .sorted((entry1, entry2) -> entry1.getValue().getName().compareTo(entry2.getValue().getName()))
                .map(entry -> entry.getValue())
                .collect(Collectors.toList());
    }


}
