package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.AchievementDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface AchievementModelInt {

    public long add(AchievementDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(AchievementDTO dto) throws ApplicationException;

    public void update(AchievementDTO dto) throws ApplicationException, DuplicateRecordException;

    public List list() throws ApplicationException;

    public List list(int pageNo, int pageSize) throws ApplicationException;

    public List search(AchievementDTO dto) throws ApplicationException;

    public List search(AchievementDTO dto, int pageNo, int pageSize) throws ApplicationException;

    public AchievementDTO findByPK(long pk) throws ApplicationException;

    public AchievementDTO findByAchievementCode(String achievementCode) throws ApplicationException;
}