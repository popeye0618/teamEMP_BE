package emp.emp.common.service;

import emp.emp.common.dto.ImageDto;
import java.io.IOException;

import emp.emp.common.entity.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

  /**
   * 이미지 업로드
   * @param file 업로드할 이미지파일
   * @return 저장된 이미지의 정보
   * @throws IOException
   */
  ImageDto uploadImage(MultipartFile file) throws IOException;

  /**
   * 이미지 조회
   * @param imageId
   * @return 조회한 이미지 정보
   */
  ImageDto getImage(Long imageId);

  /**
   * 이미지Entity 조회
   * @param imageId
   * @return
   */
  Image getImageEntity(Long imageId);

}
