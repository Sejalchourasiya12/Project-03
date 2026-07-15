package in.co.rays.project_3.model;

import java.util.List;
import in.co.rays.project_3.dto.AIRecommendationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * AIRecommendationModelInt - Interface for AI Recommendation Model
 *
 * @author Sejal Chourasiya
 */
public interface AIRecommendationModelInt {

    public long add(AIRecommendationDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(AIRecommendationDTO dto) throws ApplicationException;

    public void update(AIRecommendationDTO dto) throws ApplicationException, DuplicateRecordException;

    public List list() throws ApplicationException;

    public List list(int pageNo, int pageSize) throws ApplicationException;

    public List search(AIRecommendationDTO dto) throws ApplicationException;

    public List search(AIRecommendationDTO dto, int pageNo, int pageSize) throws ApplicationException;

    public AIRecommendationDTO findByPK(long pk) throws ApplicationException;

    public AIRecommendationDTO findByRecommendationCode(String recommendationCode) throws ApplicationException;
}