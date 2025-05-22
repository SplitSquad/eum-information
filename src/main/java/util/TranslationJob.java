package util;

import com.information.dto.InformationReqDto;
import com.information.entity.Information;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TranslationJob {
    private final Information information;
    private final InformationReqDto informationReqDto;
    private final Long informationId;
}
