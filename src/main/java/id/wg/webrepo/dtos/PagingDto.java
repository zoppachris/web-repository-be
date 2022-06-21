package id.wg.webrepo.dtos;

import lombok.*;
import org.springframework.data.domain.Page;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagingDto {
    private Object content;
    private long totalElements;
    private int maxPage;
    private int page;
    private int size;

    public PagingDto(long totalElements,int page, int size){
        this.totalElements = totalElements;
        this.size = size;
        if(this.totalElements == 0) {
            this.maxPage =0;
            this.page=0;
        }else {
            this.maxPage=(int)totalElements/size;
            this.page = page;
        }
    }

    public static <T> PagingDto toPagingDto(Page<T> pages, int page, int size) {
        return PagingDto.builder()
                .maxPage(pages.getTotalPages() == 0 ? 0 : pages.getTotalPages())
                .page(page)
                .size(size)
                .totalElements(pages.getTotalElements())
                .content(pages.getContent())
                .build();
    }

    public int getOffset() {
        return this.page*this.size;
    }

    public boolean isLast() {
        return this.page >= this.maxPage;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public int getPage() {
        return page;
    }
}
