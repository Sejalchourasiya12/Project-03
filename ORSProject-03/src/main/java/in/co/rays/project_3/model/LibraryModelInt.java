package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.LibraryDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface LibraryModelInt {

    public long add(LibraryDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(LibraryDTO dto) throws ApplicationException;

    public void update(LibraryDTO dto) throws ApplicationException, DuplicateRecordException;

    public List list() throws ApplicationException;

    public List list(int pageNo, int pageSize) throws ApplicationException;

    public List search(LibraryDTO dto) throws ApplicationException;

    public List search(LibraryDTO dto, int pageNo, int pageSize) throws ApplicationException;

    public LibraryDTO findByPK(long pk) throws ApplicationException;

    public LibraryDTO findByBookName(String bookName) throws ApplicationException;
}