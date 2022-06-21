package id.wg.webrepo.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value={"partialDocumentUrl", "fullDocumentUrl"}, allowSetters= true)
public class ThesesDto extends BaseModelDto{
    public Long thesesId;
    public String thesesTitle;
    public String abstracts;
    public String keywords;
    public String documentUrl;
    public String year;
    private StudentsDto students;
    public String partialDocumentUrl;
    public String fullDocumentUrl;
}
