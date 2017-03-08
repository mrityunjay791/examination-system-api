package com.example.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class EntityMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	/**
	 * Converts DTO to entity using {@link ModelMapper} to map fields between 
	 * the objects
	 *  
	 * @param dto The DTO Object
	 * @param entityClass The class to which the DTO will be converted to
	 * @return
	 */
	public Object entityFromDto(Object dto,Class<?> entityClass){
		return modelMapper.map(dto, entityClass);
	}
	
	/**
	 * Converts Entity to DTO using {@link ModelMapper} to map fields between 
	 * the objects
	 *  
	 * @param entity The entity class
	 * @param dtoClass The class to which the entity will be converted to
	 * @return 
	 */
	public Object entityToDto(Object entity,Class<?> dtoClass){
		return modelMapper.map(entity, dtoClass);
	}
	
	
}

