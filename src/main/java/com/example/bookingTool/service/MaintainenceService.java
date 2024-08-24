package com.example.bookingTool.service;

import java.util.List;

import com.example.bookingTool.dto.MaintainenceDTO;

public interface MaintainenceService {

	public List<MaintainenceDTO> listMaintainenceSlots();
	public MaintainenceDTO getMaintainenceById(long id);
	public MaintainenceDTO addMaintainence(MaintainenceDTO maintainence);
	public void deleteMaintainence(long id);
}