package nl.jordy.petplacer.helpers.modalmapper.propertymaps;

import nl.jordy.petplacer.dtos.patch.ShelterPetPatchDTO;
import nl.jordy.petplacer.models.ShelterPet;
import org.modelmapper.PropertyMap;

public class ShelterPetPatchDTOToShelterPetPropertyMap extends PropertyMap<ShelterPetPatchDTO, ShelterPet> {
    @Override
    protected void configure() {
        map().setName(source.getPet().getName());
        map().setAge(source.getPet().getAge());
        map().setSpecies(source.getPet().getSpecies());
        map().setBreed(source.getPet().getBreed());
        map().setColor(source.getPet().getColor());
        map().setGender(source.getPet().getGender());
        map().setSize(source.getPet().getSize());
        map().setDescription(source.getPet().getDescription());
        map().setSpayedNeutered(source.getPet().getSpayedNeutered());
        map().setGoodWithKids(source.getPet().getGoodWithKids());
        map().setGoodWithDogs(source.getPet().getGoodWithDogs());
        map().setGoodWithCats(source.getPet().getGoodWithCats());
    }
}
