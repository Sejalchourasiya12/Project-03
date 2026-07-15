package in.co.rays.project_3.model;

import java.util.List;
import in.co.rays.project_3.dto.VoiceCommandDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * VoiceCommandModelInt - Interface for Voice Command Model
 *
 * @author Sejal Chourasiya
 */
public interface VoiceCommandModelInt {

    public long add(VoiceCommandDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(VoiceCommandDTO dto) throws ApplicationException;

    public void update(VoiceCommandDTO dto) throws ApplicationException, DuplicateRecordException;

    public List list() throws ApplicationException;

    public List list(int pageNo, int pageSize) throws ApplicationException;

    public List search(VoiceCommandDTO dto) throws ApplicationException;

    public List search(VoiceCommandDTO dto, int pageNo, int pageSize) throws ApplicationException;

    public VoiceCommandDTO findByPK(long pk) throws ApplicationException;

    public VoiceCommandDTO findByCommandCode(String commandCode) throws ApplicationException;
}