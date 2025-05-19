package emp.emp.common.controller;

import emp.emp.auth.custom.CustomUserDetails;
import emp.emp.common.dto.ImageDto;
import emp.emp.common.service.ImageService;
import emp.emp.util.api_response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/auth/user/images")
@RequiredArgsConstructor
public class ImageController {

  private final ImageService imageService;

  @PostMapping
  public ResponseEntity<Response<ImageDto>> uploadImage(
          @AuthenticationPrincipal CustomUserDetails userDetails,
          @RequestParam("file") MultipartFile file
  ) throws IOException {
    ImageDto imageDto = imageService.uploadImage(file);
    return Response.ok(imageDto).toResponseEntity();
  }

}
