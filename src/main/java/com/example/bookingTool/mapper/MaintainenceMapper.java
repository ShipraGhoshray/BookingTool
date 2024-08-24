package com.example.bookingTool.mapper;

import com.example.bookingTool.dto.MaintainenceDTO;
import com.example.bookingTool.entity.MaintainenceEntity;

public class MaintainenceMapper {

	private MaintainenceMapper() {
	}

	public static MaintainenceDTO convertToDTO(MaintainenceEntity maintainence) {
		MaintainenceDTO maintainenceDTO = new MaintainenceDTO();
		maintainenceDTO.setSlotId(maintainence.getSlotId());
		maintainenceDTO.setStartTime(maintainence.getStartTime());
		maintainenceDTO.setEndTime(maintainence.getEndTime());
		return maintainenceDTO;
	}
	
	public static MaintainenceEntity toEntity(MaintainenceDTO maintainenceDTO) {
		MaintainenceEntity maintainence = new MaintainenceEntity();
		maintainence.setSlotId(maintainenceDTO.getSlotId());
		maintainence.setStartTime(maintainenceDTO.getStartTime());
		maintainence.setEndTime(maintainenceDTO.getEndTime());
		return maintainence;
	}
}