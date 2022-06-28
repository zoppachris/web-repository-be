package id.wg.webrepo.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThesesDto extends BaseModelDto{
    public Long thesesId;
    public String thesesTitle;
    public String abstracts;
    public String keywords;
    public String year;
    private StudentsDto students;
    public String partialDocumentPath;
    public String fullDocumentPath;
    public String partialDocumentUrl;
    public String fullDocumentUrl;
}
