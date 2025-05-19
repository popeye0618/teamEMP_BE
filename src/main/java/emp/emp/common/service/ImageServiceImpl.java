package emp.emp.common.service;

import emp.emp.common.dto.ImageDto;
import emp.emp.common.entity.Image;
import emp.emp.common.repository.ImageRepository;
import emp.emp.exception.BusinessException;
import emp.emp.medical.exception.MedicalResultErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

  private final ImageRepository imageRepository;

  /**
   * 이미지 업로드
   * @param file 업로드할 이미지파일
   * @return
   * @throws IOException
   */
  @Override
  @Transactional
  public ImageDto uploadImage(MultipartFile file) throws IOException{

    // 파일이 비었즌지 확인
    if(file.isEmpty()){
      throw new BusinessException(MedicalResultErrorCode.IMAGE_NOT_FOUND);
    }

    // 이미지 형식 확인
    String contentType = file.getContentType();
    if(contentType == null || !contentType.startsWith("image/")){
      throw new BusinessException(MedicalResultErrorCode.INVALID_IMAGE_FORMAT);
    }

    try{
      // 원본 파일명 가져오기
      String originalFileName = file.getOriginalFilename();

      // 파일의 확장자 가져오기
      String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

      // UUID를 사용하여 고유한 파일명 생성
      String savedFileName = UUID.randomUUID().toString() + extension;

      // 저장 경로 생성
      String filePath = "/uploads/" + savedFileName;

      // 이미지Entity 생성
      Image image = Image.builder()
              .fileName(originalFileName)
              .filePath(filePath)
              .fileSize(file.getSize())
              .contentType(file.getContentType())
              .build();

      // DB에 저장
      Image savedImage = imageRepository.save(image);

      // DTO로 변환하여 반환
      return convertToDto(savedImage);

    } catch (Exception e){
      throw new BusinessException(MedicalResultErrorCode.IMAGE_UPLOAD_FAILED);
    }

  }



  private ImageDto convertToDto(Image image){
    return ImageDto.builder()
            .imageId(image.getImageId())
            .fileName(image.getFileName())
            .filePath(image.getFilePath())
            .fileSize(image.getFileSize())
            .contentType(image.getContentType())
            .build();
  }


}
