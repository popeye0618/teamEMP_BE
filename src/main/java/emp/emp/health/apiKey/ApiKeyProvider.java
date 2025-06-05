package emp.emp.health.apiKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyProvider {

    @Value("${api.public-data.key}")
    private String publicDataKey;

    @Value("${api.kakao-map.key}")
    private String kakaoMapKey;

    public String getPublicDataKey() {
        return publicDataKey;
    }

    public String getKakaoMapKey() {
        return kakaoMapKey;
    }
}