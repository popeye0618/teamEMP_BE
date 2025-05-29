package emp.emp.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageDto {

  private Long imageId;
  private String fileName;
  private String filePath;
  private Long fileSize;
  private String contentType;

}
