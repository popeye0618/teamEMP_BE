package emp.emp.common.entity;

import emp.emp.util.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Image extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "image_id")
  private Long imageId; // 이미지 고유Id

  private String fileName; // 원본파일명

  private String filePath; // 저장경로

  private Long fileSize; //파일의 byte크기

  private String contentType; //MIME 타입

}
