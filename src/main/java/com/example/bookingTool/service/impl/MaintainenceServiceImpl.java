package com.example.bookingTool.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.bookingTool.dto.MaintainenceDTO;
import com.example.bookingTool.entity.MaintainenceEntity;
import com.example.bookingTool.handler.CustomGlobalException;
import com.example.bookingTool.mapper.MaintainenceMapper;
import com.example.bookingTool.repository.MaintainenceRepository;
import com.example.bookingTool.service.MaintainenceService;

@Service
public class MaintainenceServiceImpl implements MaintainenceService{

	@Autowired
	MaintainenceRepository maintainenceRepository;

	public List<MaintainenceDTO> listMaintainenceSlots() {
		List<MaintainenceEntity> allMaintainences = maintainenceRepository.findAll();
		List<MaintainenceDTO> processedMaintainences = new ArrayList<>();
		for (MaintainenceEntity Maintainence : allMaintainences) {
			processedMaintainences.add(MaintainenceMapper.convertToDTO(Maintainence));
		}
		return processedMaintainences;
	}

	public MaintainenceDTO getMaintainenceById(long id) {
		Optional<MaintainenceEntity> MaintainenceOptional = maintainenceRepository.findById(id);
		if (MaintainenceOptional.isEmpty()) {
			throw new CustomGlobalException("Maintainence NOT FOUND", "Maintainence not found for ID: " + id, HttpStatus.BAD_REQUEST);
		}
		return MaintainenceMapper.convertToDTO(MaintainenceOptional.get());
	}

	public void deleteMaintainence(long id) {
		if (!maintainenceRepository.existsById(id)) {
			throw new CustomGlobalException("Maintainence NOT FOUND", "Maintainence not found for ID: " + id, HttpStatus.BAD_REQUEST);            
		}
		maintainenceRepository.deleteById(id);
	}

	public MaintainenceDTO addMaintainence(MaintainenceDTO maintainenceDto) {
		
		MaintainenceEntity maintainence = MaintainenceMapper.toEntity(maintainenceDto);
		long slotId = maintainence.getSlotId();
		List<MaintainenceEntity> MaintainencesWithSameId = maintainenceRepository.findBySlotId(slotId);
		if (!MaintainencesWithSameId.isEmpty()) {
			String errorMessage = "Maintainence slot with the ID '" + slotId + "' already exists";
			throw new CustomGlobalException("Maintainence NOT FOUND", errorMessage, HttpStatus.BAD_REQUEST);
		}
		MaintainenceEntity maintainenceEntity = maintainenceRepository.save(maintainence);
		return MaintainenceMapper.convertToDTO(maintainenceEntity);
	}
}