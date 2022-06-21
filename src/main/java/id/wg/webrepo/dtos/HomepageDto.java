package id.wg.webrepo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomepageDto {
    public long totalRepository;
    public long totalDosen;
    public long totalMahasiswa;
}
