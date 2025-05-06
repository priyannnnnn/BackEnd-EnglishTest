package TestEnglishApi.TestEnglishApi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordWithCategory {

    private String word;
    private String correctCategory;
}
