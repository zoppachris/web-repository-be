package id.wg.webrepo.payload;

import java.util.List;
import lombok.Data;

@Data
public class PagingResponse {
    private Integer totalPages;
    private Integer page;
    private Integer pageSize;
    private Long totalElements;
    private List<?> content;
}
