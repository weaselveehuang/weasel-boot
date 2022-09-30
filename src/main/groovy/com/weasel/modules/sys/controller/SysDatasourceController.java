package com.weasel.modules.sys.controller;

import ai.yue.library.base.view.R;
import ai.yue.library.base.view.Result;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.XmlUtil;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.ejlchina.searcher.BeanSearcher;
import com.weasel.modules.sys.entity.SysRole;
import com.weasel.modules.sys.service.SysRoleService;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Document;

/**
 * @author weasel
 * @version 1.0
 * @date 2022/3/24 17:35
 */
@Controller
@RequestMapping("/sys/dataSource")
@Slf4j
public class SysDatasourceController {
    @Autowired
    DynamicDataSourceProperties properties;
    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    BeanSearcher beanSearcher;

//    @GetMapping
//    public Result list() {
//        return R.success(properties.getDatasource());
//    }
//    @GetMapping
//    public Result list() {
//        return R.success(sysRoleService.page(new Page<>()));
//    }

    //    @GetMapping
//    @TransMethodResult
    public Result list() {
//        return R.success(sysRoleService.list());
        return R.success(beanSearcher.search(SysRole.class, MapUtil.newHashMap()));
    }

//    @GetMapping
//    @TransMethodResult
//    public SearchResult<SysRole> list() {
////        return sysRoleService.list();
//        return beanSearcher.search(SysRole.class, MapUtil.newHashMap());
//    }

    //    @GetMapping(value = "a.xml", produces={"application/xml; charset=UTF-8"})
//    @ResponseBody
//    @TransMethodResult
    public Document xml() {
        return XmlUtil.parseXml("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<catalog xmlns=\"http://www.unidata.ucar.edu/namespaces/thredds/InvCatalog/v1.0\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" \n" +
                "   name=\"Unidata THREDDS-IDD NetCDF-OpenDAP Server\" version=\"1.0.1\">\n" +
                "\n" +
                "  <dataset name=\"NCEP Model Data\">\n" +
                "    <metadata inherited=\"true\">\n" +
                "      <authority>edu.ucar.unidata</authority>\n" +
                "      <dataType>Grid</dataType>\n" +
                "      <dataFormat>NetCDF</dataFormat>\n" +
                "      <documentation type=\"rights\">Freely available</documentation>\n" +
                "      <documentation xlink:href=\"http://www.emc.ncep.noaa.gov/modelinfo/index.html\" xlink:title=\"NCEP Model documentation\"></documentation>\n" +
                "      <creator>\n" +
                "        <name vocabulary=\"DIF\">DOC/NOAA/NWS/NCEP</name>\n" +
                "        <contact url=\"http://www.ncep.noaa.gov/\" email=\"http://www.ncep.noaa.gov/mail_liaison.shtml\" />\n" +
                "      </creator>\n" +
                "      <publisher>\n" +
                "        <name vocabulary=\"DIF\">UCAR/UNIDATA</name>\n" +
                "        <contact url=\"https://www.unidata.ucar.edu/\" email=\"support@unidata.ucar.edu\" />\n" +
                "      </publisher>\n" +
                "      <timeCoverage>\n" +
                "        <end>present</end>\n" +
                "        <duration>14 days</duration>\n" +
                "      </timeCoverage>\n" +
                "    </metadata>\n" +
                "\n" +
                "    <datasetScan name=\"ETA Data\" ID=\"testEnhanced\"\n" +
                "                 path=\"testEnhanced\" location=\"D:/thredds/public/testdata\"\n" +
                "                 harvest=\"true\">\n" +
                "      <metadata inherited=\"true\">\n" +
                "        <documentation type=\"summary\">NCEP North American Model : AWIPS 211 (Q) Regional - CONUS (Lambert Conformal). Model runs are made at 12Z and 00Z, with analysis and forecasts every 6 hours out to 60 hours. Horizontal = 93 by 65 points, resolution 81.27 km, LambertConformal projection. Vertical = 1000 to 100 hPa pressure levels.</documentation>\n" +
                "        <geospatialCoverage>\n" +
                "          <northsouth>\n" +
                "            <start>26.92475</start>\n" +
                "            <size>15.9778</size>\n" +
                "            <units>degrees_north</units>\n" +
                "          </northsouth>\n" +
                "          <eastwest>\n" +
                "            <start>-135.33123</start>\n" +
                "            <size>103.78772</size>\n" +
                "            <units>degrees_east</units>\n" +
                "          </eastwest>\n" +
                "          <updown>\n" +
                "            <start>0.0</start>\n" +
                "            <size>0.0</size>\n" +
                "            <units>km</units>\n" +
                "          </updown>\n" +
                "        </geospatialCoverage>\n" +
                "      </metadata>\n" +
                "\n" +
                "      <filter>\n" +
                "        <include wildcard=\"*eta_211.nc\" />\n" +
                "      </filter>\n" +
                "\n" +
                "      <sort>\n" +
                "        <lexigraphicByName increasing=\"false\"/>\n" +
                "      </sort>\n" +
                "      <addLatest/>\n" +
                "\n" +
                "      <addTimeCoverage datasetNameMatchPattern=\"([0-9]{4})([0-9]{2})([0-9]{2})([0-9]{2})_eta_211.nc$\"\n" +
                "                       startTimeSubstitutionPattern=\"$1-$2-$3T$4:00:00\"\n" +
                "                       duration=\"60 hours\" />\n" +
                "    </datasetScan>\n" +
                "  </dataset>\n" +
                "</catalog>\n" +
                "\n");
    }
}
