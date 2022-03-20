package apartinformation.apart.controller;

import apartinformation.apart.domain.ApartXmlParser;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static apartinformation.apart.excel.ApartLawCd.APART_CD_LIST;

@Controller
public class TestController {

    @RequestMapping("/")
    @ResponseBody
    public String abc1() throws IOException, JDOMException, InterruptedException {

        /**
         * URL : http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev?
         * ServiceKey : ...
         * pageNo 1
         * numOfRows : 1000000
         * LAWD_CD : 11110
         * DEAL_YMD : 202112
         */

        for (int i = 1 ; i < 100 ; i++){

            // URL 만들기
            StringBuilder sb = new StringBuilder();
            sb.append("http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev?");
            sb.append("ServiceKey=");
            sb.append("f%2B4Qzb2y%2B19Z3ZowyE%2BHN%2BPODKYQcaHPe3NAoZxrLXHckpDJgQ%2FrUnxVs%2BVyPs0UWH4r9co7qB%2F%2BJNRC7RrYbg%3D%3D");
            sb.append("&pageNo=" + 1);
            sb.append("&numOfRows=" + 1000000);
            sb.append("&LAWD_CD=" + APART_CD_LIST[i]);
            sb.append("&DEAL_YMD=" + 202112);


            // URL 연결
            URL url = new URL(sb.toString());
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setRequestProperty("Content-Type","application/xml");
            conn.setRequestMethod("get");
            conn.connect();


            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(conn.getInputStream());

            Element root = document.getRootElement();
            Element body = root.getChild("body");
            Element items = body.getChild("items");
            List<Element> item = items.getChildren("item");

            System.out.println("NOW LAW_CD = " + APART_CD_LIST[i]);

            for (Element element : item) {
                ApartXmlParser apartXmlParser = transferXmlToParser(element);
                System.out.println("apartXmlParser = " + apartXmlParser);
            }
        }

        return "ok";
    }



    private ApartXmlParser transferXmlToParser(Element item) {

        ApartXmlParser.ApartXmlParserBuilder builder = ApartXmlParser.builder();

        List<Element> children = item.getChildren();
        for (Element child : children) {
            mappingFromItemToParser(builder, child);
        }

        return builder.build();

    }

    private void mappingFromItemToParser(ApartXmlParser.ApartXmlParserBuilder builder, Element item) {

        String value = item.getContent(0).getValue().trim();
        String name = item.getName();

        if(value.equals("")){
            return;
        }

        if(name.equals("거래금액")){
            builder.price(Integer.valueOf(value.replace(",", "")));
        }

        if(name.equals("거래유형")){
            builder.tradeType(value);
        }

        if(name.equals("건축년도")){
            builder.buildYear(value);
        }

        if(name.equals("년")){
            builder.year(value);
        }

        if(name.equals("도로명")){
            builder.streetName(value);
        }

        if(name.equals("법정동")){
            builder.lawCdStreetName(value);
        }


        if(name.equals("아파트")){
            builder.apartName(value);
        }

        if(name.equals("월")){
            builder.tradeMonth(value);
        }

        if(name.equals("일")){
            builder.tradeDay(value);
        }

        if(name.equals("층")){
            builder.floor(value);
        }

        if(name.equals("전용면적")){
            builder.area(Double.valueOf(value));
        }

        if(name.equals("해제사유발생일")){
            builder.cancelDate(LocalDate.parse(value.replace(".",""), DateTimeFormatter.ofPattern("yyMMdd")));
        }

        if(name.equals("해제여부")){
            builder.isCancel(value);
        }

        if(name.equals("지역코드")){
            builder.lawCd(value);
        }

        if(name.equals("지번")){
            builder.addressNumber(value);
        }

        if(name.equals("도로명코드")){
            builder.streetNameCode(value);
        }
    }



}


