package apartinformation.apart.domain;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApartXmlParserOld {

    // https://velog.io/@67778773/spring-%ED%99%98%EA%B2%BD%EC%97%90%EC%84%9C-%EA%B3%B5%EA%B3%B5%EB%8D%B0%EC%9D%B4%ED%84%B0-%ED%99%9C%EC%9A%A9%ED%95%98%EA%B8%B0

    private int price;
    private String tradeType;
    private String buildYear;
    private String year;
    private String streetName;
    private String streetBuildingMainNumber;
    private String streetBuildingSubNumber;
    private String cityNumber;
    private String streetNameSerialNumber;
    private String streetUnderOrFloor;
    private String streetNameCode;
    private String lawCdStreetName;
    private String lawCdStreetNameMainNumber;
    private String lawCdStreetNameSubNumber;
    private String lawCdStreetCityCode;
    private String lawCdStreetSubCityCode;
    private String lawCdStreetZipCode;
    private String apartName;
    private String tradeMonth;
    private String tradeDay;
    private String serialNumber;
    private Double area;
    private String traderAddress;
    private String addressNumber;
    private String lawCd;
    private String floor;
    private LocalDate cancelDate;
    private String isCancel;


}
