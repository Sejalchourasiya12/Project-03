package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.PodcastDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface PodcastModelInt {

    public long add(PodcastDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(PodcastDTO dto) throws ApplicationException;

    public void update(PodcastDTO dto) throws ApplicationException, DuplicateRecordException;

    public List list() throws ApplicationException;

    public List list(int pageNo, int pageSize) throws ApplicationException;

    public List search(PodcastDTO dto) throws ApplicationException;

    public List search(PodcastDTO dto, int pageNo, int pageSize) throws ApplicationException;

    public PodcastDTO findByPK(long pk) throws ApplicationException;

    public PodcastDTO findByPodcastCode(String podcastCode) throws ApplicationException;
}