package emp.emp.common.service;

import emp.emp.common.dto.ImageDto;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

  /**
   * 이미지 업로드
   * @param file 업로드할 이미지파일
   * @return 저장된 이미지의 정보
   * @throws IOException
   */
  ImageDto uploadImage(MultipartFile file) throws IOException;

}
