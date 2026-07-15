package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.CandidateDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface CandidateModelInt {

	
	public long add(CandidateDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(CandidateDTO dto)throws ApplicationException;
	public void update(CandidateDTO dto)throws ApplicationException,DuplicateRecordException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(CandidateDTO dto)throws ApplicationException;
	public List search(CandidateDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public CandidateDTO findByPK(long pk)throws ApplicationException;
	public CandidateDTO findByName(String name)throws ApplicationException;
	}

