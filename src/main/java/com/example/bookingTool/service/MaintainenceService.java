package com.example.bookingTool.service;

import java.util.List;

import com.example.bookingTool.dto.MaintainenceDTO;
import com.example.bookingTool.entity.Maintainence;

public interface MaintainenceService {

	public List<MaintainenceDTO> listMaintainenceSlots();
	public MaintainenceDTO getMaintainenceById(long id);
	public MaintainenceDTO addMaintainence(Maintainence maintainence);
	public void deleteMaintainence(long id);
}