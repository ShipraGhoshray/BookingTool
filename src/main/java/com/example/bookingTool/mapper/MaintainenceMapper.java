package com.example.bookingTool.mapper;

import com.example.bookingTool.dto.MaintainenceDTO;
import com.example.bookingTool.entity.Maintainence;

public class MaintainenceMapper {

	private MaintainenceMapper() {
	}

	public static MaintainenceDTO convertToDTO(Maintainence maintainence) {
		MaintainenceDTO maintainenceDTO = new MaintainenceDTO();
		maintainenceDTO.setSlotId(maintainence.getSlotId());
		maintainenceDTO.setStartTime(maintainence.getStartTime());
		maintainenceDTO.setEndTime(maintainence.getEndTime());
		return maintainenceDTO;
	}
}