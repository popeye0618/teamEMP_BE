package emp.emp.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {
    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey; // 엑세스 키

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey; // 시크릿 키

    @Value("${cloud.aws.region.static}")
    private String region; // 리전 (지역)


    @Bean
    public S3Client s3Client() {
        // aws 기본 인증 객체
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        // aws s3에 접근할 수 있는 클라이언트 객체
        return S3Client.builder()
                .region(Region.of(region)) // 지역
                .credentialsProvider(StaticCredentialsProvider.create(credentials)) // S3Client에 인증 키를 전달
                .build();
    }
}
