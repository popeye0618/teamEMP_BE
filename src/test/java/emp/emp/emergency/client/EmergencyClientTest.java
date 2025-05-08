package emp.emp.emergency.client;

import emp.emp.emergency.dto.EmergencyAedDTO;
import emp.emp.emergency.dto.EmergencyRoomDTO;
import emp.emp.emergency.dto.UserLocationDTO;
import emp.emp.emergency.service.EmergencyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmergencyClientTest {
    @Autowired
    private EmergencyClient emergencyClient;    // 실제 클라이언트 주입

    @Test
    void SeoulGetUserLocationtest() {
        // 서울툭별시
        UserLocationDTO seoulJunggu = emergencyClient.GetUserLocation("37.5665", "126.9780");
        UserLocationDTO seoulGangnam = emergencyClient.GetUserLocation("37.5175686", "127.0474869");
        UserLocationDTO seoulGangdong = emergencyClient.GetUserLocation("37.52479", "127.1344");
        UserLocationDTO seoulGangbuk = emergencyClient.GetUserLocation("37.63695556", "127.0277194");

        assertNotNull(seoulJunggu);
        assertEquals("서울특별시", seoulJunggu.getSido());
        assertEquals("중구", seoulJunggu.getSigungu());


        assertNotNull(seoulGangnam);
        assertEquals("서울특별시", seoulGangnam.getSido());
        assertEquals("강남구", seoulGangnam.getSigungu());

        assertNotNull(seoulGangdong);
        assertEquals("서울특별시", seoulGangdong.getSido());
        assertEquals("강동구", seoulGangdong.getSigungu());

        assertNotNull(seoulGangbuk);
        assertEquals("서울특별시", seoulGangbuk.getSido());
        assertEquals("강북구", seoulGangbuk.getSigungu());

        // 추가 구 테스트 코드
        // 강서구
        UserLocationDTO seoulGangseo = emergencyClient.GetUserLocation("37.5509", "126.8495");
        assertNotNull(seoulGangseo);
        assertEquals("서울특별시", seoulGangseo.getSido());
        assertEquals("강서구", seoulGangseo.getSigungu());

        // 관악구
        UserLocationDTO seoulGwanak = emergencyClient.GetUserLocation("37.4784", "126.9516");
        assertNotNull(seoulGwanak);
        assertEquals("서울특별시", seoulGwanak.getSido());
        assertEquals("관악구", seoulGwanak.getSigungu());

        // 광진구
        UserLocationDTO seoulGwangjin = emergencyClient.GetUserLocation("37.5385", "127.0829");
        assertNotNull(seoulGwangjin);
        assertEquals("서울특별시", seoulGwangjin.getSido());
        assertEquals("광진구", seoulGwangjin.getSigungu());

        // 구로구
        UserLocationDTO seoulGuro = emergencyClient.GetUserLocation("37.4954", "126.8874");
        assertNotNull(seoulGuro);
        assertEquals("서울특별시", seoulGuro.getSido());
        assertEquals("구로구", seoulGuro.getSigungu());

        // 금천구
        UserLocationDTO seoulGeumcheon = emergencyClient.GetUserLocation("37.4568", "126.9018");
        assertNotNull(seoulGeumcheon);
        assertEquals("서울특별시", seoulGeumcheon.getSido());
        assertEquals("금천구", seoulGeumcheon.getSigungu());

        // 노원구
        UserLocationDTO seoulNowon = emergencyClient.GetUserLocation("37.6552", "127.0771");
        assertNotNull(seoulNowon);
        assertEquals("서울특별시", seoulNowon.getSido());
        assertEquals("노원구", seoulNowon.getSigungu());

        // 도봉구
        UserLocationDTO seoulDobong = emergencyClient.GetUserLocation("37.6688", "127.0471");
        assertNotNull(seoulDobong);
        assertEquals("서울특별시", seoulDobong.getSido());
        assertEquals("도봉구", seoulDobong.getSigungu());

        // 동대문구
        UserLocationDTO seoulDongdaemun = emergencyClient.GetUserLocation("37.5746", "127.0395");
        assertNotNull(seoulDongdaemun);
        assertEquals("서울특별시", seoulDongdaemun.getSido());
        assertEquals("동대문구", seoulDongdaemun.getSigungu());

        // 동작구
        UserLocationDTO seoulDongjak = emergencyClient.GetUserLocation("37.5124", "126.9391");
        assertNotNull(seoulDongjak);
        assertEquals("서울특별시", seoulDongjak.getSido());
        assertEquals("동작구", seoulDongjak.getSigungu());

        // 마포구
        UserLocationDTO seoulMapo = emergencyClient.GetUserLocation("37.5639", "126.9084");
        assertNotNull(seoulMapo);
        assertEquals("서울특별시", seoulMapo.getSido());
        assertEquals("마포구", seoulMapo.getSigungu());

        // 서대문구
        UserLocationDTO seoulSeodaemun = emergencyClient.GetUserLocation("37.5785", "126.9365");
        assertNotNull(seoulSeodaemun);
        assertEquals("서울특별시", seoulSeodaemun.getSido());
        assertEquals("서대문구", seoulSeodaemun.getSigungu());

        // 서초구
        UserLocationDTO seoulSeocho = emergencyClient.GetUserLocation("37.4836", "127.0327");
        assertNotNull(seoulSeocho);
        assertEquals("서울특별시", seoulSeocho.getSido());
        assertEquals("서초구", seoulSeocho.getSigungu());

        // 성동구
        UserLocationDTO seoulSeongdong = emergencyClient.GetUserLocation("37.5636", "127.0342");
        assertNotNull(seoulSeongdong);
        assertEquals("서울특별시", seoulSeongdong.getSido());
        assertEquals("성동구", seoulSeongdong.getSigungu());

        // 성북구
        UserLocationDTO seoulSeongbuk = emergencyClient.GetUserLocation("37.5894", "127.0164");
        assertNotNull(seoulSeongbuk);
        assertEquals("서울특별시", seoulSeongbuk.getSido());
        assertEquals("성북구", seoulSeongbuk.getSigungu());

        // 송파구
        UserLocationDTO seoulSongpa = emergencyClient.GetUserLocation("37.5145", "127.1062");
        assertNotNull(seoulSongpa);
        assertEquals("서울특별시", seoulSongpa.getSido());
        assertEquals("송파구", seoulSongpa.getSigungu());

        // 양천구
        UserLocationDTO seoulYangcheon = emergencyClient.GetUserLocation("37.5168", "126.8661");
        assertNotNull(seoulYangcheon);
        assertEquals("서울특별시", seoulYangcheon.getSido());
        assertEquals("양천구", seoulYangcheon.getSigungu());

        // 영등포구
        UserLocationDTO seoulYeongdeungpo = emergencyClient.GetUserLocation("37.5260", "126.8963");
        assertNotNull(seoulYeongdeungpo);
        assertEquals("서울특별시", seoulYeongdeungpo.getSido());
        assertEquals("영등포구", seoulYeongdeungpo.getSigungu());

        // 용산구
        UserLocationDTO seoulYongsan = emergencyClient.GetUserLocation("37.5326", "126.9905");
        assertNotNull(seoulYongsan);
        assertEquals("서울특별시", seoulYongsan.getSido());
        assertEquals("용산구", seoulYongsan.getSigungu());

        // 은평구
        UserLocationDTO seoulEunpyeong = emergencyClient.GetUserLocation("37.6026", "126.9291");
        assertNotNull(seoulEunpyeong);
        assertEquals("서울특별시", seoulEunpyeong.getSido());
        assertEquals("은평구", seoulEunpyeong.getSigungu());

        // 종로구
        UserLocationDTO seoulJongno = emergencyClient.GetUserLocation("37.5728", "126.9790");
        assertNotNull(seoulJongno);
        assertEquals("서울특별시", seoulJongno.getSido());
        assertEquals("종로구", seoulJongno.getSigungu());

        // 중랑구
        UserLocationDTO seoulJungnang = emergencyClient.GetUserLocation("37.6065", "127.0927");
        assertNotNull(seoulJungnang);
        assertEquals("서울특별시", seoulJungnang.getSido());
        assertEquals("중랑구", seoulJungnang.getSigungu());

    }

    @Test
    void GyeonggiDoGetUserLocationTest() {
        // 가평군
        UserLocationDTO gapyeong = emergencyClient.GetUserLocation("37.8315", "127.5106");
        assertNotNull(gapyeong);
        assertEquals("경기도", gapyeong.getSido());
        assertEquals("가평군", gapyeong.getSigungu());

        // 고양시
        UserLocationDTO goyang = emergencyClient.GetUserLocation("37.6559", "126.8351");
        assertNotNull(goyang);
        assertEquals("경기도", goyang.getSido());
        assertEquals("고양시", goyang.getSigungu());

        // 과천시
        UserLocationDTO gwacheon = emergencyClient.GetUserLocation("37.4291", "126.9873");
        assertNotNull(gwacheon);
        assertEquals("경기도", gwacheon.getSido());
        assertEquals("과천시", gwacheon.getSigungu());

        // 광명시
        UserLocationDTO gwangmyeong = emergencyClient.GetUserLocation("37.4786", "126.8644");
        assertNotNull(gwangmyeong);
        assertEquals("경기도", gwangmyeong.getSido());
        assertEquals("광명시", gwangmyeong.getSigungu());

        // 광주시
        UserLocationDTO gwangju = emergencyClient.GetUserLocation("37.4119", "127.2577");
        assertNotNull(gwangju);
        assertEquals("경기도", gwangju.getSido());
        assertEquals("광주시", gwangju.getSigungu());

        // 구리시
        UserLocationDTO guri = emergencyClient.GetUserLocation("37.5939", "127.1301");
        assertNotNull(guri);
        assertEquals("경기도", guri.getSido());
        assertEquals("구리시", guri.getSigungu());

        // 군포시
        UserLocationDTO gunpo = emergencyClient.GetUserLocation("37.3612", "126.9370");
        assertNotNull(gunpo);
        assertEquals("경기도", gunpo.getSido());
        assertEquals("군포시", gunpo.getSigungu());

        // 김포시
        UserLocationDTO gimpo = emergencyClient.GetUserLocation("37.6154", "126.7157");
        assertNotNull(gimpo);
        assertEquals("경기도", gimpo.getSido());
        assertEquals("김포시", gimpo.getSigungu());

        // 남양주시
        UserLocationDTO namyangju = emergencyClient.GetUserLocation("37.6364", "127.2165");
        assertNotNull(namyangju);
        assertEquals("경기도", namyangju.getSido());
        assertEquals("남양주시", namyangju.getSigungu());

        // 동두천시
        UserLocationDTO dongducheon = emergencyClient.GetUserLocation("37.9037", "127.0606");
        assertNotNull(dongducheon);
        assertEquals("경기도", dongducheon.getSido());
        assertEquals("동두천시", dongducheon.getSigungu());

        // 부천시
        UserLocationDTO bucheon = emergencyClient.GetUserLocation("37.5037", "126.7661");
        assertNotNull(bucheon);
        assertEquals("경기도", bucheon.getSido());
        assertEquals("부천시", bucheon.getSigungu());

        // 성남시
        UserLocationDTO seongnam = emergencyClient.GetUserLocation("37.4449", "127.1389");
        assertNotNull(seongnam);
        assertEquals("경기도", seongnam.getSido());
        assertEquals("성남시", seongnam.getSigungu());

        // 수원시
        UserLocationDTO suwon = emergencyClient.GetUserLocation("37.2636", "127.0286");
        assertNotNull(suwon);
        assertEquals("경기도", suwon.getSido());
        assertEquals("수원시", suwon.getSigungu());

        // 시흥시
        UserLocationDTO siheung = emergencyClient.GetUserLocation("37.3795", "126.8026");
        assertNotNull(siheung);
        assertEquals("경기도", siheung.getSido());
        assertEquals("시흥시", siheung.getSigungu());

        // 안산시
        UserLocationDTO ansan = emergencyClient.GetUserLocation("37.3219", "126.8307");
        assertNotNull(ansan);
        assertEquals("경기도", ansan.getSido());
        assertEquals("안산시", ansan.getSigungu());

        // 안성시
        UserLocationDTO anseong = emergencyClient.GetUserLocation("37.0079", "127.2797");
        assertNotNull(anseong);
        assertEquals("경기도", anseong.getSido());
        assertEquals("안성시", anseong.getSigungu());

        // 안양시
        UserLocationDTO anyang = emergencyClient.GetUserLocation("37.3942", "126.9567");
        assertNotNull(anyang);
        assertEquals("경기도", anyang.getSido());
        assertEquals("안양시", anyang.getSigungu());

        // 양주시
        UserLocationDTO yangju = emergencyClient.GetUserLocation("37.7852", "127.0459");
        assertNotNull(yangju);
        assertEquals("경기도", yangju.getSido());
        assertEquals("양주시", yangju.getSigungu());

        // 양평군
        UserLocationDTO yangpyeong = emergencyClient.GetUserLocation("37.4912", "127.4875");
        assertNotNull(yangpyeong);
        assertEquals("경기도", yangpyeong.getSido());
        assertEquals("양평군", yangpyeong.getSigungu());

        // 여주시
        UserLocationDTO yeoju = emergencyClient.GetUserLocation("37.2983", "127.6371");
        assertNotNull(yeoju);
        assertEquals("경기도", yeoju.getSido());
        assertEquals("여주시", yeoju.getSigungu());

        // 연천군
        UserLocationDTO yeoncheon = emergencyClient.GetUserLocation("38.0964", "127.0752");
        assertNotNull(yeoncheon);
        assertEquals("경기도", yeoncheon.getSido());
        assertEquals("연천군", yeoncheon.getSigungu());

        // 오산시
        UserLocationDTO osan = emergencyClient.GetUserLocation("37.1498", "127.0772");
        assertNotNull(osan);
        assertEquals("경기도", osan.getSido());
        assertEquals("오산시", osan.getSigungu());

        // 용인시
        UserLocationDTO yongin = emergencyClient.GetUserLocation("37.2342", "127.2016");
        assertNotNull(yongin);
        assertEquals("경기도", yongin.getSido());
        assertEquals("용인시", yongin.getSigungu());

        // 의왕시
        UserLocationDTO uiwang = emergencyClient.GetUserLocation("37.3444", "126.9682");
        assertNotNull(uiwang);
        assertEquals("경기도", uiwang.getSido());
        assertEquals("의왕시", uiwang.getSigungu());

        // 의정부시
        UserLocationDTO uijeongbu = emergencyClient.GetUserLocation("37.7380", "127.0469");
        assertNotNull(uijeongbu);
        assertEquals("경기도", uijeongbu.getSido());
        assertEquals("의정부시", uijeongbu.getSigungu());

        // 이천시
        UserLocationDTO icheon = emergencyClient.GetUserLocation("37.2723", "127.4350");
        assertNotNull(icheon);
        assertEquals("경기도", icheon.getSido());
        assertEquals("이천시", icheon.getSigungu());

        // 파주시
        UserLocationDTO paju = emergencyClient.GetUserLocation("37.7599", "126.7803");
        assertNotNull(paju);
        assertEquals("경기도", paju.getSido());
        assertEquals("파주시", paju.getSigungu());

        // 평택시
        UserLocationDTO pyeongtaek = emergencyClient.GetUserLocation("36.9925", "127.1126");
        assertNotNull(pyeongtaek);
        assertEquals("경기도", pyeongtaek.getSido());
        assertEquals("평택시", pyeongtaek.getSigungu());

        // 포천시
        UserLocationDTO pocheon = emergencyClient.GetUserLocation("37.8952", "127.2004");
        assertNotNull(pocheon);
        assertEquals("경기도", pocheon.getSido());
        assertEquals("포천시", pocheon.getSigungu());

        // 하남시
        UserLocationDTO hanam = emergencyClient.GetUserLocation("37.5392", "127.2141");
        assertNotNull(hanam);
        assertEquals("경기도", hanam.getSido());
        assertEquals("하남시", hanam.getSigungu());

        // 화성시
        UserLocationDTO hwaseong = emergencyClient.GetUserLocation("37.1995", "126.8310");
        assertNotNull(hwaseong);
        assertEquals("경기도", hwaseong.getSido());
        assertEquals("화성시", hwaseong.getSigungu());
    }

    @Test
    void testGetUserLocationGangwon() {
        // 강릉시
        UserLocationDTO gangneung = emergencyClient.GetUserLocation("37.7519", "128.8760");
        assertNotNull(gangneung);
        assertEquals("강원특별자치도", gangneung.getSido());
        assertEquals("강릉시", gangneung.getSigungu());

        // 고성군
        UserLocationDTO goseong = emergencyClient.GetUserLocation("38.3802", "128.4677");
        assertNotNull(goseong);
        assertEquals("강원특별자치도", goseong.getSido());
        assertEquals("고성군", goseong.getSigungu());

        // 동해시
        UserLocationDTO donghae = emergencyClient.GetUserLocation("37.5247", "129.1142");
        assertNotNull(donghae);
        assertEquals("강원특별자치도", donghae.getSido());
        assertEquals("동해시", donghae.getSigungu());

        // 삼척시
        UserLocationDTO samcheok = emergencyClient.GetUserLocation("37.4499", "129.1679");
        assertNotNull(samcheok);
        assertEquals("강원특별자치도", samcheok.getSido());
        assertEquals("삼척시", samcheok.getSigungu());

        // 속초시
        UserLocationDTO sokcho = emergencyClient.GetUserLocation("38.2070", "128.5918");
        assertNotNull(sokcho);
        assertEquals("강원특별자치도", sokcho.getSido());
        assertEquals("속초시", sokcho.getSigungu());

        // 양구군
        UserLocationDTO yanggu = emergencyClient.GetUserLocation("38.1058", "127.9895");
        assertNotNull(yanggu);
        assertEquals("강원특별자치도", yanggu.getSido());
        assertEquals("양구군", yanggu.getSigungu());

        // 양양군
        UserLocationDTO yangyang = emergencyClient.GetUserLocation("38.0754", "128.6189");
        assertNotNull(yangyang);
        assertEquals("강원특별자치도", yangyang.getSido());
        assertEquals("양양군", yangyang.getSigungu());

        // 영월군
        UserLocationDTO yeongwol = emergencyClient.GetUserLocation("37.1834", "128.4617");
        assertNotNull(yeongwol);
        assertEquals("강원특별자치도", yeongwol.getSido());
        assertEquals("영월군", yeongwol.getSigungu());

        // 원주시
        UserLocationDTO wonju = emergencyClient.GetUserLocation("37.3422", "127.9200");
        assertNotNull(wonju);
        assertEquals("강원특별자치도", wonju.getSido());
        assertEquals("원주시", wonju.getSigungu());

        // 인제군
        UserLocationDTO inje = emergencyClient.GetUserLocation("38.0694", "128.1706");
        assertNotNull(inje);
        assertEquals("강원특별자치도", inje.getSido());
        assertEquals("인제군", inje.getSigungu());

        // 정선군
        UserLocationDTO jeongseon = emergencyClient.GetUserLocation("37.3807", "128.6608");
        assertNotNull(jeongseon);
        assertEquals("강원특별자치도", jeongseon.getSido());
        assertEquals("정선군", jeongseon.getSigungu());

        // 철원군
        UserLocationDTO cheorwon = emergencyClient.GetUserLocation("38.1466", "127.3124");
        assertNotNull(cheorwon);
        assertEquals("강원특별자치도", cheorwon.getSido());
        assertEquals("철원군", cheorwon.getSigungu());

        // 춘천시
        UserLocationDTO chuncheon = emergencyClient.GetUserLocation("37.8813", "127.7300");
        assertNotNull(chuncheon);
        assertEquals("강원특별자치도", chuncheon.getSido());
        assertEquals("춘천시", chuncheon.getSigungu());

        // 태백시
        UserLocationDTO taebaek = emergencyClient.GetUserLocation("37.1641", "128.9894");
        assertNotNull(taebaek);
        assertEquals("강원특별자치도", taebaek.getSido());
        assertEquals("태백시", taebaek.getSigungu());

        // 평창군
        UserLocationDTO pyeongchang = emergencyClient.GetUserLocation("37.3719", "128.3907");
        assertNotNull(pyeongchang);
        assertEquals("강원특별자치도", pyeongchang.getSido());
        assertEquals("평창군", pyeongchang.getSigungu());

        // 홍천군
        UserLocationDTO hongcheon = emergencyClient.GetUserLocation("37.6970", "127.8889");
        assertNotNull(hongcheon);
        assertEquals("강원특별자치도", hongcheon.getSido());
        assertEquals("홍천군", hongcheon.getSigungu());

        // 화천군
        UserLocationDTO hwacheon = emergencyClient.GetUserLocation("38.1062", "127.7082");
        assertNotNull(hwacheon);
        assertEquals("강원특별자치도", hwacheon.getSido());
        assertEquals("화천군", hwacheon.getSigungu());

        // 횡성군
        UserLocationDTO hoengseong = emergencyClient.GetUserLocation("37.4918", "127.9878");
        assertNotNull(hoengseong);
        assertEquals("강원특별자치도", hoengseong.getSido());
        assertEquals("횡성군", hoengseong.getSigungu());
    }

    @Test
    void testGetUserLocationBusan() {
        // 강서구
        UserLocationDTO gangseo = emergencyClient.GetUserLocation("35.2121", "128.9831");
        assertNotNull(gangseo);
        assertEquals("부산광역시", gangseo.getSido());
        assertEquals("강서구", gangseo.getSigungu());

        // 금정구
        UserLocationDTO geumjeong = emergencyClient.GetUserLocation("35.2427", "129.0927");
        assertNotNull(geumjeong);
        assertEquals("부산광역시", geumjeong.getSido());
        assertEquals("금정구", geumjeong.getSigungu());

        // 기장군
        UserLocationDTO gijang = emergencyClient.GetUserLocation("35.2446", "129.2224");
        assertNotNull(gijang);
        assertEquals("부산광역시", gijang.getSido());
        assertEquals("기장군", gijang.getSigungu());

        // 남구
        UserLocationDTO namgu = emergencyClient.GetUserLocation("35.1365", "129.0843");
        assertNotNull(namgu);
        assertEquals("부산광역시", namgu.getSido());
        assertEquals("남구", namgu.getSigungu());

        // 동구
        UserLocationDTO donggu = emergencyClient.GetUserLocation("35.1290", "129.0454");
        assertNotNull(donggu);
        assertEquals("부산광역시", donggu.getSido());
        assertEquals("동구", donggu.getSigungu());

        // 동래구
        UserLocationDTO dongnae = emergencyClient.GetUserLocation("35.1965", "129.0845");
        assertNotNull(dongnae);
        assertEquals("부산광역시", dongnae.getSido());
        assertEquals("동래구", dongnae.getSigungu());

        // 부산진구
        UserLocationDTO busanjin = emergencyClient.GetUserLocation("35.1628", "129.0527");
        assertNotNull(busanjin);
        assertEquals("부산광역시", busanjin.getSido());
        assertEquals("부산진구", busanjin.getSigungu());

        // 북구
        UserLocationDTO bukgu = emergencyClient.GetUserLocation("35.1971", "129.0123");
        assertNotNull(bukgu);
        assertEquals("부산광역시", bukgu.getSido());
        assertEquals("북구", bukgu.getSigungu());

        // 사상구
        UserLocationDTO sasang = emergencyClient.GetUserLocation("35.1527", "128.9897");
        assertNotNull(sasang);
        assertEquals("부산광역시", sasang.getSido());
        assertEquals("사상구", sasang.getSigungu());

        // 사하구
        UserLocationDTO saha = emergencyClient.GetUserLocation("35.1042", "128.9744");
        assertNotNull(saha);
        assertEquals("부산광역시", saha.getSido());
        assertEquals("사하구", saha.getSigungu());

        // 서구
        UserLocationDTO seogu = emergencyClient.GetUserLocation("35.0981", "129.0242");
        assertNotNull(seogu);
        assertEquals("부산광역시", seogu.getSido());
        assertEquals("서구", seogu.getSigungu());

        // 수영구
        UserLocationDTO suyeong = emergencyClient.GetUserLocation("35.1456", "129.1131");
        assertNotNull(suyeong);
        assertEquals("부산광역시", suyeong.getSido());
        assertEquals("수영구", suyeong.getSigungu());

        // 연제구
        UserLocationDTO yeonje = emergencyClient.GetUserLocation("35.1758", "129.0786");
        assertNotNull(yeonje);
        assertEquals("부산광역시", yeonje.getSido());
        assertEquals("연제구", yeonje.getSigungu());

        // 영도구
        UserLocationDTO yeongdo = emergencyClient.GetUserLocation("35.0913", "129.0675");
        assertNotNull(yeongdo);
        assertEquals("부산광역시", yeongdo.getSido());
        assertEquals("영도구", yeongdo.getSigungu());

        // 중구
        UserLocationDTO junggu = emergencyClient.GetUserLocation("35.1064", "129.0334");
        assertNotNull(junggu);
        assertEquals("부산광역시", junggu.getSido());
        assertEquals("중구", junggu.getSigungu());

        // 해운대구
        UserLocationDTO haeundae = emergencyClient.GetUserLocation("35.1631", "129.1639");
        assertNotNull(haeundae);
        assertEquals("부산광역시", haeundae.getSido());
        assertEquals("해운대구", haeundae.getSigungu());
    }


    @Test
    void testGetUserLocationDaegu() {
        // 남구
        UserLocationDTO namgu = emergencyClient.GetUserLocation("35.8459", "128.5972");
        assertNotNull(namgu);
        assertEquals("대구광역시", namgu.getSido());
        assertEquals("남구", namgu.getSigungu());

        // 달서구
        UserLocationDTO dalseo = emergencyClient.GetUserLocation("35.8297", "128.5329");
        assertNotNull(dalseo);
        assertEquals("대구광역시", dalseo.getSido());
        assertEquals("달서구", dalseo.getSigungu());

        // 달성군
        UserLocationDTO dalseong = emergencyClient.GetUserLocation("35.7744", "128.4309");
        assertNotNull(dalseong);
        assertEquals("대구광역시", dalseong.getSido());
        assertEquals("달성군", dalseong.getSigungu());

        // 동구
        UserLocationDTO donggu = emergencyClient.GetUserLocation("35.8859", "128.6354");
        assertNotNull(donggu);
        assertEquals("대구광역시", donggu.getSido());
        assertEquals("동구", donggu.getSigungu());

        // 북구
        UserLocationDTO bukgu = emergencyClient.GetUserLocation("35.8858", "128.5831");
        assertNotNull(bukgu);
        assertEquals("대구광역시", bukgu.getSido());
        assertEquals("북구", bukgu.getSigungu());

        // 서구
        UserLocationDTO seogu = emergencyClient.GetUserLocation("35.8714", "128.5591");
        assertNotNull(seogu);
        assertEquals("대구광역시", seogu.getSido());
        assertEquals("서구", seogu.getSigungu());

        // 수성구
        UserLocationDTO suseong = emergencyClient.GetUserLocation("35.8582", "128.6320");
        assertNotNull(suseong);
        assertEquals("대구광역시", suseong.getSido());
        assertEquals("수성구", suseong.getSigungu());

        // 중구
        UserLocationDTO junggu = emergencyClient.GetUserLocation("35.8698", "128.6062");
        assertNotNull(junggu);
        assertEquals("대구광역시", junggu.getSido());
        assertEquals("중구", junggu.getSigungu());
    }

    @Test
    void testGetUserLocationIncheon() {
        // 강화군
        UserLocationDTO ganghwa = emergencyClient.GetUserLocation("37.7465", "126.4881");
        assertNotNull(ganghwa);
        assertEquals("인천광역시", ganghwa.getSido());
        assertEquals("강화군", ganghwa.getSigungu());

        // 계양구
        UserLocationDTO gyeyang = emergencyClient.GetUserLocation("37.5374", "126.7379");
        assertNotNull(gyeyang);
        assertEquals("인천광역시", gyeyang.getSido());
        assertEquals("계양구", gyeyang.getSigungu());

        // 미추홀구 (구 남구)
        UserLocationDTO michuhol = emergencyClient.GetUserLocation("37.4635", "126.6500");
        assertNotNull(michuhol);
        assertEquals("인천광역시", michuhol.getSido());
        assertEquals("미추홀구", michuhol.getSigungu());

        // 남동구
        UserLocationDTO namdong = emergencyClient.GetUserLocation("37.4475", "126.7319");
        assertNotNull(namdong);
        assertEquals("인천광역시", namdong.getSido());
        assertEquals("남동구", namdong.getSigungu());

        // 동구
        UserLocationDTO donggu = emergencyClient.GetUserLocation("37.4743", "126.6430");
        assertNotNull(donggu);
        assertEquals("인천광역시", donggu.getSido());
        assertEquals("동구", donggu.getSigungu());

        // 부평구
        UserLocationDTO bupyeong = emergencyClient.GetUserLocation("37.5065", "126.7212");
        assertNotNull(bupyeong);
        assertEquals("인천광역시", bupyeong.getSido());
        assertEquals("부평구", bupyeong.getSigungu());

        // 서구
        UserLocationDTO seogu = emergencyClient.GetUserLocation("37.5459", "126.6756");
        assertNotNull(seogu);
        assertEquals("인천광역시", seogu.getSido());
        assertEquals("서구", seogu.getSigungu());

        // 연수구
        UserLocationDTO yeonsu = emergencyClient.GetUserLocation("37.4101", "126.6785");
        assertNotNull(yeonsu);
        assertEquals("인천광역시", yeonsu.getSido());
        assertEquals("연수구", yeonsu.getSigungu());

        // 옹진군
        UserLocationDTO ongjin = emergencyClient.GetUserLocation("37.4463", "126.6356");
        assertNotNull(ongjin);
        assertEquals("인천광역시", ongjin.getSido());
        assertEquals("옹진군", ongjin.getSigungu());

        // 중구
        UserLocationDTO junggu = emergencyClient.GetUserLocation("37.4734", "126.6211");
        assertNotNull(junggu);
        assertEquals("인천광역시", junggu.getSido());
        assertEquals("중구", junggu.getSigungu());
    }


    @Test
    void testGetUserLocationGwangju() {
        // 광산구
        UserLocationDTO gwangsan = emergencyClient.GetUserLocation("35.1397", "126.7938");
        assertNotNull(gwangsan);
        assertEquals("광주광역시", gwangsan.getSido());
        assertEquals("광산구", gwangsan.getSigungu());

        // 남구
        UserLocationDTO namgu = emergencyClient.GetUserLocation("35.1330", "126.9023");
        assertNotNull(namgu);
        assertEquals("광주광역시", namgu.getSido());
        assertEquals("남구", namgu.getSigungu());

        // 동구
        UserLocationDTO donggu = emergencyClient.GetUserLocation("35.1459", "126.9233");
        assertNotNull(donggu);
        assertEquals("광주광역시", donggu.getSido());
        assertEquals("동구", donggu.getSigungu());

        // 북구
        UserLocationDTO bukgu = emergencyClient.GetUserLocation("35.1743", "126.9119");
        assertNotNull(bukgu);
        assertEquals("광주광역시", bukgu.getSido());
        assertEquals("북구", bukgu.getSigungu());

        // 서구
        UserLocationDTO seogu = emergencyClient.GetUserLocation("35.1519", "126.8894");
        assertNotNull(seogu);
        assertEquals("광주광역시", seogu.getSido());
        assertEquals("서구", seogu.getSigungu());
    }

    @Test
    void testGetUserLocationDaejeon() {
        // 대덕구
        UserLocationDTO daedeok = emergencyClient.GetUserLocation("36.3465", "127.4148");
        assertNotNull(daedeok);
        assertEquals("대전광역시", daedeok.getSido());
        assertEquals("대덕구", daedeok.getSigungu());

        // 동구
        UserLocationDTO donggu = emergencyClient.GetUserLocation("36.3110", "127.4343");
        assertNotNull(donggu);
        assertEquals("대전광역시", donggu.getSido());
        assertEquals("동구", donggu.getSigungu());

        // 서구
        UserLocationDTO seogu = emergencyClient.GetUserLocation("36.3548", "127.3844");
        assertNotNull(seogu);
        assertEquals("대전광역시", seogu.getSido());
        assertEquals("서구", seogu.getSigungu());

        // 유성구
        UserLocationDTO yuseong = emergencyClient.GetUserLocation("36.3624", "127.3561");
        assertNotNull(yuseong);
        assertEquals("대전광역시", yuseong.getSido());
        assertEquals("유성구", yuseong.getSigungu());

        // 중구
        UserLocationDTO junggu = emergencyClient.GetUserLocation("36.3254", "127.4214");
        assertNotNull(junggu);
        assertEquals("대전광역시", junggu.getSido());
        assertEquals("중구", junggu.getSigungu());
    }

    @Test
    void testGetUserLocationUlsan() {
        // 중구
        UserLocationDTO junggu = emergencyClient.GetUserLocation("35.5699", "129.3328");
        assertNotNull(junggu);
        assertEquals("울산광역시", junggu.getSido());
        assertEquals("중구", junggu.getSigungu());

        // 남구
        UserLocationDTO namgu = emergencyClient.GetUserLocation("35.5437", "129.3301");
        assertNotNull(namgu);
        assertEquals("울산광역시", namgu.getSido());
        assertEquals("남구", namgu.getSigungu());

        // 동구
        UserLocationDTO donggu = emergencyClient.GetUserLocation("35.5047", "129.4172");
        assertNotNull(donggu);
        assertEquals("울산광역시", donggu.getSido());
        assertEquals("동구", donggu.getSigungu());

        // 북구
        UserLocationDTO bukgu = emergencyClient.GetUserLocation("35.5828", "129.3611");
        assertNotNull(bukgu);
        assertEquals("울산광역시", bukgu.getSido());
        assertEquals("북구", bukgu.getSigungu());

        // 울주군
        UserLocationDTO ulju = emergencyClient.GetUserLocation("35.6223", "129.2422");
        assertNotNull(ulju);
        assertEquals("울산광역시", ulju.getSido());
        assertEquals("울주군", ulju.getSigungu());
    }

    @Test
    void testGetUserLocationSejong() {
        UserLocationDTO jochiwon = emergencyClient.GetUserLocation("36.6038", "127.2970");
        assertNotNull(jochiwon);
        assertEquals("세종특별자치시", jochiwon.getSido());
        assertEquals("세종특별자치시", jochiwon.getSigungu());  // 또는 "조치원읍" 등의 값일 수 있음 (API 반환값에 따라 달라짐)
    }


    @Test
    void testGetUserLocationChungbuk() {
        // 청주시
        UserLocationDTO cheongju = emergencyClient.GetUserLocation("36.6424", "127.4890");
        assertNotNull(cheongju);
        assertEquals("충청북도", cheongju.getSido());
        assertEquals("청주시", cheongju.getSigungu());

        // 충주시
        UserLocationDTO chungju = emergencyClient.GetUserLocation("36.9913", "127.9252");
        assertNotNull(chungju);
        assertEquals("충청북도", chungju.getSido());
        assertEquals("충주시", chungju.getSigungu());

        // 제천시
        UserLocationDTO jecheon = emergencyClient.GetUserLocation("37.1394", "128.2114");
        assertNotNull(jecheon);
        assertEquals("충청북도", jecheon.getSido());
        assertEquals("제천시", jecheon.getSigungu());

        // 보은군
        UserLocationDTO boeun = emergencyClient.GetUserLocation("36.4893", "127.7291");
        assertNotNull(boeun);
        assertEquals("충청북도", boeun.getSido());
        assertEquals("보은군", boeun.getSigungu());

        // 옥천군
        UserLocationDTO okcheon = emergencyClient.GetUserLocation("36.3070", "127.5719");
        assertNotNull(okcheon);
        assertEquals("충청북도", okcheon.getSido());
        assertEquals("옥천군", okcheon.getSigungu());

        // 영동군
        UserLocationDTO yeongdong = emergencyClient.GetUserLocation("36.1753", "127.7832");
        assertNotNull(yeongdong);
        assertEquals("충청북도", yeongdong.getSido());
        assertEquals("영동군", yeongdong.getSigungu());

        // 진천군
        UserLocationDTO jincheon = emergencyClient.GetUserLocation("36.8541", "127.4357");
        assertNotNull(jincheon);
        assertEquals("충청북도", jincheon.getSido());
        assertEquals("진천군", jincheon.getSigungu());

        // 괴산군
        UserLocationDTO goesan = emergencyClient.GetUserLocation("36.8153", "127.7873");
        assertNotNull(goesan);
        assertEquals("충청북도", goesan.getSido());
        assertEquals("괴산군", goesan.getSigungu());

        // 음성군
        UserLocationDTO eumseong = emergencyClient.GetUserLocation("36.9400", "127.6912");
        assertNotNull(eumseong);
        assertEquals("충청북도", eumseong.getSido());
        assertEquals("음성군", eumseong.getSigungu());

        // 단양군
        UserLocationDTO danyang = emergencyClient.GetUserLocation("36.9845", "128.3654");
        assertNotNull(danyang);
        assertEquals("충청북도", danyang.getSido());
        assertEquals("단양군", danyang.getSigungu());

        // 증평군
        UserLocationDTO jeungpyeong = emergencyClient.GetUserLocation("36.7860", "127.5827");
        assertNotNull(jeungpyeong);
        assertEquals("충청북도", jeungpyeong.getSido());
        assertEquals("증평군", jeungpyeong.getSigungu());
    }


    @Test
    void testGetUserLocationChungnam() {
        // 천안시
        UserLocationDTO cheonan = emergencyClient.GetUserLocation("36.8151", "127.1135");
        assertNotNull(cheonan);
        assertEquals("충청남도", cheonan.getSido());
        assertEquals("천안시", cheonan.getSigungu());

        // 공주시
        UserLocationDTO gongju = emergencyClient.GetUserLocation("36.4566", "127.1192");
        assertNotNull(gongju);
        assertEquals("충청남도", gongju.getSido());
        assertEquals("공주시", gongju.getSigungu());

        // 보령시
        UserLocationDTO boryeong = emergencyClient.GetUserLocation("36.3350", "126.6127");
        assertNotNull(boryeong);
        assertEquals("충청남도", boryeong.getSido());
        assertEquals("보령시", boryeong.getSigungu());

        // 아산시
        UserLocationDTO asan = emergencyClient.GetUserLocation("36.7902", "127.0018");
        assertNotNull(asan);
        assertEquals("충청남도", asan.getSido());
        assertEquals("아산시", asan.getSigungu());

        // 서산시
        UserLocationDTO seosan = emergencyClient.GetUserLocation("36.7852", "126.4505");
        assertNotNull(seosan);
        assertEquals("충청남도", seosan.getSido());
        assertEquals("서산시", seosan.getSigungu());

        // 논산시
        UserLocationDTO nonsan = emergencyClient.GetUserLocation("36.1894", "127.1000");
        assertNotNull(nonsan);
        assertEquals("충청남도", nonsan.getSido());
        assertEquals("논산시", nonsan.getSigungu());

        // 계룡시
        UserLocationDTO gyeryong = emergencyClient.GetUserLocation("36.2750", "127.2485");
        assertNotNull(gyeryong);
        assertEquals("충청남도", gyeryong.getSido());
        assertEquals("계룡시", gyeryong.getSigungu());

        // 당진시
        UserLocationDTO dangjin = emergencyClient.GetUserLocation("36.8899", "126.6290");
        assertNotNull(dangjin);
        assertEquals("충청남도", dangjin.getSido());
        assertEquals("당진시", dangjin.getSigungu());

        // 금산군
        UserLocationDTO geumsan = emergencyClient.GetUserLocation("36.1086", "127.4882");
        assertNotNull(geumsan);
        assertEquals("충청남도", geumsan.getSido());
        assertEquals("금산군", geumsan.getSigungu());

        // 부여군
        UserLocationDTO buyeo = emergencyClient.GetUserLocation("36.2758", "126.9097");
        assertNotNull(buyeo);
        assertEquals("충청남도", buyeo.getSido());
        assertEquals("부여군", buyeo.getSigungu());

        // 서천군
        UserLocationDTO seocheon = emergencyClient.GetUserLocation("36.0800", "126.6913");
        assertNotNull(seocheon);
        assertEquals("충청남도", seocheon.getSido());
        assertEquals("서천군", seocheon.getSigungu());

        // 청양군
        UserLocationDTO cheongyang = emergencyClient.GetUserLocation("36.4592", "126.8020");
        assertNotNull(cheongyang);
        assertEquals("충청남도", cheongyang.getSido());
        assertEquals("청양군", cheongyang.getSigungu());

        // 홍성군
        UserLocationDTO hongseong = emergencyClient.GetUserLocation("36.6010", "126.6607");
        assertNotNull(hongseong);
        assertEquals("충청남도", hongseong.getSido());
        assertEquals("홍성군", hongseong.getSigungu());

        // 예산군
        UserLocationDTO yesan = emergencyClient.GetUserLocation("36.6813", "126.8447");
        assertNotNull(yesan);
        assertEquals("충청남도", yesan.getSido());
        assertEquals("예산군", yesan.getSigungu());

        // 태안군
        UserLocationDTO taean = emergencyClient.GetUserLocation("36.7456", "126.2979");
        assertNotNull(taean);
        assertEquals("충청남도", taean.getSido());
        assertEquals("태안군", taean.getSigungu());
    }

    @Test
    void testGetUserLocationJeonbuk() {
        // 전주시
        UserLocationDTO jeonju = emergencyClient.GetUserLocation("35.8242", "127.1480");
        assertNotNull(jeonju);
        assertEquals("전북특별자치도", jeonju.getSido());
        assertEquals("전주시", jeonju.getSigungu());

        // 군산시
        UserLocationDTO gunsan = emergencyClient.GetUserLocation("35.9676", "126.7366");
        assertNotNull(gunsan);
        assertEquals("전북특별자치도", gunsan.getSido());
        assertEquals("군산시", gunsan.getSigungu());

        // 익산시
        UserLocationDTO iksan = emergencyClient.GetUserLocation("35.9483", "126.9576");
        assertNotNull(iksan);
        assertEquals("전북특별자치도", iksan.getSido());
        assertEquals("익산시", iksan.getSigungu());

        // 정읍시
        UserLocationDTO jeongeup = emergencyClient.GetUserLocation("35.5700", "126.8558");
        assertNotNull(jeongeup);
        assertEquals("전북특별자치도", jeongeup.getSido());
        assertEquals("정읍시", jeongeup.getSigungu());

        // 남원시
        UserLocationDTO namwon = emergencyClient.GetUserLocation("35.4163", "127.3906");
        assertNotNull(namwon);
        assertEquals("전북특별자치도", namwon.getSido());
        assertEquals("남원시", namwon.getSigungu());

        // 김제시
        UserLocationDTO gimje = emergencyClient.GetUserLocation("35.8034", "126.8809");
        assertNotNull(gimje);
        assertEquals("전북특별자치도", gimje.getSido());
        assertEquals("김제시", gimje.getSigungu());

        // 완주군
        UserLocationDTO wanju = emergencyClient.GetUserLocation("35.9044", "127.2125");
        assertNotNull(wanju);
        assertEquals("전북특별자치도", wanju.getSido());
        assertEquals("완주군", wanju.getSigungu());

        // 진안군
        UserLocationDTO jinan = emergencyClient.GetUserLocation("35.7917", "127.4248");
        assertNotNull(jinan);
        assertEquals("전북특별자치도", jinan.getSido());
        assertEquals("진안군", jinan.getSigungu());

        // 무주군
        UserLocationDTO muju = emergencyClient.GetUserLocation("36.0068", "127.6611");
        assertNotNull(muju);
        assertEquals("전북특별자치도", muju.getSido());
        assertEquals("무주군", muju.getSigungu());

        // 장수군
        UserLocationDTO jangsu = emergencyClient.GetUserLocation("35.6473", "127.5203");
        assertNotNull(jangsu);
        assertEquals("전북특별자치도", jangsu.getSido());
        assertEquals("장수군", jangsu.getSigungu());

        // 임실군
        UserLocationDTO imsil = emergencyClient.GetUserLocation("35.6128", "127.2792");
        assertNotNull(imsil);
        assertEquals("전북특별자치도", imsil.getSido());
        assertEquals("임실군", imsil.getSigungu());

        // 순창군
        UserLocationDTO sunchang = emergencyClient.GetUserLocation("35.3743", "127.1373");
        assertNotNull(sunchang);
        assertEquals("전북특별자치도", sunchang.getSido());
        assertEquals("순창군", sunchang.getSigungu());

        // 고창군
        UserLocationDTO gochang = emergencyClient.GetUserLocation("35.4358", "126.7020");
        assertNotNull(gochang);
        assertEquals("전북특별자치도", gochang.getSido());
        assertEquals("고창군", gochang.getSigungu());

        // 부안군
        UserLocationDTO buan = emergencyClient.GetUserLocation("35.7316", "126.7331");
        assertNotNull(buan);
        assertEquals("전북특별자치도", buan.getSido());
        assertEquals("부안군", buan.getSigungu());
    }


    @Test
    void testGetUserLocationJeonnam() {
        // 목포시
        UserLocationDTO mokpo = emergencyClient.GetUserLocation("34.8118", "126.3922");
        assertNotNull(mokpo);
        assertEquals("전라남도", mokpo.getSido());
        assertEquals("목포시", mokpo.getSigungu());

        // 여수시
        UserLocationDTO yeosu = emergencyClient.GetUserLocation("34.7604", "127.6622");
        assertNotNull(yeosu);
        assertEquals("전라남도", yeosu.getSido());
        assertEquals("여수시", yeosu.getSigungu());

        // 순천시
        UserLocationDTO suncheon = emergencyClient.GetUserLocation("34.9506", "127.4878");
        assertNotNull(suncheon);
        assertEquals("전라남도", suncheon.getSido());
        assertEquals("순천시", suncheon.getSigungu());

        // 나주시
        UserLocationDTO naju = emergencyClient.GetUserLocation("35.0158", "126.7173");
        assertNotNull(naju);
        assertEquals("전라남도", naju.getSido());
        assertEquals("나주시", naju.getSigungu());

        // 광양시
        UserLocationDTO gwangyang = emergencyClient.GetUserLocation("34.9377", "127.6962");
        assertNotNull(gwangyang);
        assertEquals("전라남도", gwangyang.getSido());
        assertEquals("광양시", gwangyang.getSigungu());

        // 담양군
        UserLocationDTO damyang = emergencyClient.GetUserLocation("35.3220", "126.9877");
        assertNotNull(damyang);
        assertEquals("전라남도", damyang.getSido());
        assertEquals("담양군", damyang.getSigungu());

        // 곡성군
        UserLocationDTO gokseong = emergencyClient.GetUserLocation("35.2818", "127.2922");
        assertNotNull(gokseong);
        assertEquals("전라남도", gokseong.getSido());
        assertEquals("곡성군", gokseong.getSigungu());

        // 구례군
        UserLocationDTO gurye = emergencyClient.GetUserLocation("35.2026", "127.4629");
        assertNotNull(gurye);
        assertEquals("전라남도", gurye.getSido());
        assertEquals("구례군", gurye.getSigungu());

        // 고흥군
        UserLocationDTO goheung = emergencyClient.GetUserLocation("34.6113", "127.2853");
        assertNotNull(goheung);
        assertEquals("전라남도", goheung.getSido());
        assertEquals("고흥군", goheung.getSigungu());

        // 보성군
        UserLocationDTO boseong = emergencyClient.GetUserLocation("34.7714", "127.0799");
        assertNotNull(boseong);
        assertEquals("전라남도", boseong.getSido());
        assertEquals("보성군", boseong.getSigungu());

        // 화순군
        UserLocationDTO hwasun = emergencyClient.GetUserLocation("35.0647", "126.9864");
        assertNotNull(hwasun);
        assertEquals("전라남도", hwasun.getSido());
        assertEquals("화순군", hwasun.getSigungu());

        // 장흥군
        UserLocationDTO jangheung = emergencyClient.GetUserLocation("34.6816", "126.9069");
        assertNotNull(jangheung);
        assertEquals("전라남도", jangheung.getSido());
        assertEquals("장흥군", jangheung.getSigungu());

        // 강진군
        UserLocationDTO gangjin = emergencyClient.GetUserLocation("34.6420", "126.7674");
        assertNotNull(gangjin);
        assertEquals("전라남도", gangjin.getSido());
        assertEquals("강진군", gangjin.getSigungu());

        // 해남군
        UserLocationDTO haenam = emergencyClient.GetUserLocation("34.5731", "126.5989");
        assertNotNull(haenam);
        assertEquals("전라남도", haenam.getSido());
        assertEquals("해남군", haenam.getSigungu());

        // 영암군
        UserLocationDTO yeongam = emergencyClient.GetUserLocation("34.8001", "126.6967");
        assertNotNull(yeongam);
        assertEquals("전라남도", yeongam.getSido());
        assertEquals("영암군", yeongam.getSigungu());

        // 무안군
        UserLocationDTO muan = emergencyClient.GetUserLocation("34.9904", "126.4816");
        assertNotNull(muan);
        assertEquals("전라남도", muan.getSido());
        assertEquals("무안군", muan.getSigungu());

        // 함평군
        UserLocationDTO hampyeong = emergencyClient.GetUserLocation("35.0659", "126.5167");
        assertNotNull(hampyeong);
        assertEquals("전라남도", hampyeong.getSido());
        assertEquals("함평군", hampyeong.getSigungu());

        // 영광군
        UserLocationDTO yeonggwang = emergencyClient.GetUserLocation("35.2771", "126.5120");
        assertNotNull(yeonggwang);
        assertEquals("전라남도", yeonggwang.getSido());
        assertEquals("영광군", yeonggwang.getSigungu());

        // 장성군
        UserLocationDTO jangseong = emergencyClient.GetUserLocation("35.3019", "126.7849");
        assertNotNull(jangseong);
        assertEquals("전라남도", jangseong.getSido());
        assertEquals("장성군", jangseong.getSigungu());

        // 완도군
        UserLocationDTO wando = emergencyClient.GetUserLocation("34.3110", "126.7550");
        assertNotNull(wando);
        assertEquals("전라남도", wando.getSido());
        assertEquals("완도군", wando.getSigungu());

        // 진도군
        UserLocationDTO jindo = emergencyClient.GetUserLocation("34.4868", "126.2631");
        assertNotNull(jindo);
        assertEquals("전라남도", jindo.getSido());
        assertEquals("진도군", jindo.getSigungu());

        // 신안군
        UserLocationDTO shinan = emergencyClient.GetUserLocation("34.8330", "126.1150");
        assertNotNull(shinan);
        assertEquals("전라남도", shinan.getSido());
        assertEquals("신안군", shinan.getSigungu());
    }

    @Test
    void testGetUserLocationGyeongbuk() {
        // 포항시
        UserLocationDTO pohang = emergencyClient.GetUserLocation("36.0190", "129.3435");
        assertNotNull(pohang);
        assertEquals("경상북도", pohang.getSido());
        assertEquals("포항시", pohang.getSigungu());

        // 경주시
        UserLocationDTO gyeongju = emergencyClient.GetUserLocation("35.8562", "129.2246");
        assertNotNull(gyeongju);
        assertEquals("경상북도", gyeongju.getSido());
        assertEquals("경주시", gyeongju.getSigungu());

        // 김천시
        UserLocationDTO gimcheon = emergencyClient.GetUserLocation("36.1398", "128.1135");
        assertNotNull(gimcheon);
        assertEquals("경상북도", gimcheon.getSido());
        assertEquals("김천시", gimcheon.getSigungu());

        // 안동시
        UserLocationDTO andong = emergencyClient.GetUserLocation("36.5684", "128.7293");
        assertNotNull(andong);
        assertEquals("경상북도", andong.getSido());
        assertEquals("안동시", andong.getSigungu());

        // 구미시
        UserLocationDTO gumi = emergencyClient.GetUserLocation("36.1198", "128.3443");
        assertNotNull(gumi);
        assertEquals("경상북도", gumi.getSido());
        assertEquals("구미시", gumi.getSigungu());

        // 영주시
        UserLocationDTO yeongju = emergencyClient.GetUserLocation("36.8057", "128.6241");
        assertNotNull(yeongju);
        assertEquals("경상북도", yeongju.getSido());
        assertEquals("영주시", yeongju.getSigungu());

        // 영천시
        UserLocationDTO yeongcheon = emergencyClient.GetUserLocation("35.9724", "128.9387");
        assertNotNull(yeongcheon);
        assertEquals("경상북도", yeongcheon.getSido());
        assertEquals("영천시", yeongcheon.getSigungu());

        // 상주시
        UserLocationDTO sangju = emergencyClient.GetUserLocation("36.4135", "128.1586");
        assertNotNull(sangju);
        assertEquals("경상북도", sangju.getSido());
        assertEquals("상주시", sangju.getSigungu());

        // 문경시
        UserLocationDTO mungyeong = emergencyClient.GetUserLocation("36.5864", "128.1863");
        assertNotNull(mungyeong);
        assertEquals("경상북도", mungyeong.getSido());
        assertEquals("문경시", mungyeong.getSigungu());

        // 경산시
        UserLocationDTO gyeongsan = emergencyClient.GetUserLocation("35.8250", "128.7417");
        assertNotNull(gyeongsan);
        assertEquals("경상북도", gyeongsan.getSido());
        assertEquals("경산시", gyeongsan.getSigungu());

        // 군위군
        UserLocationDTO gunwi = emergencyClient.GetUserLocation("36.2428", "128.5728");
        assertNotNull(gunwi);
        assertEquals("경상북도", gunwi.getSido());
        assertEquals("군위군", gunwi.getSigungu());

        // 의성군
        UserLocationDTO uiseong = emergencyClient.GetUserLocation("36.3528", "128.6970");
        assertNotNull(uiseong);
        assertEquals("경상북도", uiseong.getSido());
        assertEquals("의성군", uiseong.getSigungu());

        // 청송군
        UserLocationDTO cheongsong = emergencyClient.GetUserLocation("36.4362", "129.0571");
        assertNotNull(cheongsong);
        assertEquals("경상북도", cheongsong.getSido());
        assertEquals("청송군", cheongsong.getSigungu());

        // 영양군
        UserLocationDTO yeongyang = emergencyClient.GetUserLocation("36.6667", "129.1142");
        assertNotNull(yeongyang);
        assertEquals("경상북도", yeongyang.getSido());
        assertEquals("영양군", yeongyang.getSigungu());

        // 영덕군
        UserLocationDTO yeongdeok = emergencyClient.GetUserLocation("36.4153", "129.3656");
        assertNotNull(yeongdeok);
        assertEquals("경상북도", yeongdeok.getSido());
        assertEquals("영덕군", yeongdeok.getSigungu());

        // 청도군
        UserLocationDTO cheongdo = emergencyClient.GetUserLocation("35.6479", "128.7339");
        assertNotNull(cheongdo);
        assertEquals("경상북도", cheongdo.getSido());
        assertEquals("청도군", cheongdo.getSigungu());

        // 고령군
        UserLocationDTO goryeong = emergencyClient.GetUserLocation("35.7266", "128.2629");
        assertNotNull(goryeong);
        assertEquals("경상북도", goryeong.getSido());
        assertEquals("고령군", goryeong.getSigungu());

        // 성주군
        UserLocationDTO seongju = emergencyClient.GetUserLocation("35.9191", "128.2828");
        assertNotNull(seongju);
        assertEquals("경상북도", seongju.getSido());
        assertEquals("성주군", seongju.getSigungu());

        // 칠곡군
        UserLocationDTO chilgok = emergencyClient.GetUserLocation("36.0056", "128.4016");
        assertNotNull(chilgok);
        assertEquals("경상북도", chilgok.getSido());
        assertEquals("칠곡군", chilgok.getSigungu());

        // 예천군
        UserLocationDTO yecheon = emergencyClient.GetUserLocation("36.6574", "128.4528");
        assertNotNull(yecheon);
        assertEquals("경상북도", yecheon.getSido());
        assertEquals("예천군", yecheon.getSigungu());

        // 봉화군
        UserLocationDTO bonghwa = emergencyClient.GetUserLocation("36.8932", "128.7327");
        assertNotNull(bonghwa);
        assertEquals("경상북도", bonghwa.getSido());
        assertEquals("봉화군", bonghwa.getSigungu());

        // 울진군
        UserLocationDTO uljin = emergencyClient.GetUserLocation("36.9932", "129.4005");
        assertNotNull(uljin);
        assertEquals("경상북도", uljin.getSido());
        assertEquals("울진군", uljin.getSigungu());

        // 울릉군
        UserLocationDTO ulleung = emergencyClient.GetUserLocation("37.5045", "130.8575");
        assertNotNull(ulleung);
        assertEquals("경상북도", ulleung.getSido());
        assertEquals("울릉군", ulleung.getSigungu());
    }


    @Test
    void testGetUserLocationGyeongbuk2() {
        // 포항시
        UserLocationDTO pohang = emergencyClient.GetUserLocation("36.0199", "129.3434");
        assertNotNull(pohang);
        assertEquals("경상북도", pohang.getSido());
        assertEquals("포항시", pohang.getSigungu());

        // 경주시
        UserLocationDTO gyeongju = emergencyClient.GetUserLocation("35.8562", "129.2246");
        assertNotNull(gyeongju);
        assertEquals("경상북도", gyeongju.getSido());
        assertEquals("경주시", gyeongju.getSigungu());

        // 구미시
        UserLocationDTO gumi = emergencyClient.GetUserLocation("36.1194", "128.3435");
        assertNotNull(gumi);
        assertEquals("경상북도", gumi.getSido());
        assertEquals("구미시", gumi.getSigungu());

        // 안동시
        UserLocationDTO andong = emergencyClient.GetUserLocation("36.5684", "128.7294");
        assertNotNull(andong);
        assertEquals("경상북도", andong.getSido());
        assertEquals("안동시", andong.getSigungu());

        // 김천시
        UserLocationDTO gimcheon = emergencyClient.GetUserLocation("36.1398", "128.1135");
        assertNotNull(gimcheon);
        assertEquals("경상북도", gimcheon.getSido());
        assertEquals("김천시", gimcheon.getSigungu());
    }




        @Test
        void testGetEmergencyRoomInformationApi() throws IOException {
            // 테스트용 사용자 위치
            UserLocationDTO location = new UserLocationDTO().builder()
                    .sido("서울특별시")
                    .sigungu("종로구")
                    .build();

            List<EmergencyRoomDTO> emergencyRooms = emergencyClient.GetEmergencyRoomInformationApi(location);

            System.out.println("===== 응급실 정보 목록 출력 =====");
            for (EmergencyRoomDTO dto : emergencyRooms) {
                System.out.println(dto);
            }
            System.out.println("===== 출력 끝 =====");

            Assertions.assertNotNull(emergencyRooms);
        }


//        @Test
//        void testGetEmergencyAedInformationApi() throws IOException {
//            // 테스트용 사용자 위치
//            UserLocationDTO location = new UserLocationDTO().builder()
//                    .sido("서울특별시")
//                    .sigungu("종로구")
//                    .build();
//
//            List<EmergencyAedDTO> emergencyAed = emergencyClient.GetEmergencyAedInformationApi(location);
//            System.out.println("========================================");
//            for (EmergencyAedDTO dto : emergencyAed) {
//                System.out.println(dto);
//            }
//            System.out.println("========================================");
//
//            Assertions.assertNotNull(emergencyAed);
//        }

}


// 인천광역시 경상북도 경상남도 제주특별자치도
